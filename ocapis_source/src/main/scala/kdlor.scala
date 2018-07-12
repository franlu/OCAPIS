package cristinahg.ocapis
import breeze.linalg._
import breeze.numerics._
private class kdlor {

  // TODO: implement QUICKRBF
  def computeKernelMatrix(patterns1: DenseMatrix[Double], patterns2:DenseMatrix[Double],kType:String,kParam:Array[Double])={
    val Nf1=patterns1.cols
    val Nf2=patterns2.cols
    var KM=DenseMatrix.zeros[Double](Nf1,Nf2)

    kType.toUpperCase match {
      case "GAUSS" | "GAUSSIAN"| "RBF" => (1 to Nf2).foreach(i=> KM(::, i) := exp(-kParam(1)*
        sum(
          ((patterns1-patterns2(::,i)*DenseMatrix.ones[Double](1,Nf1)) *:* (patterns1-patterns2(::,i)*DenseMatrix.ones[Double](1,Nf1))).t,Axis._0)))
      case "LINEAR" => KM=(patterns1.t * patterns2)/:/patterns1.rows

      case "POLYNOMIAL" | "POLY" => { var multplusbias=(patterns1.t * patterns2)
                                      multplusbias:+=1
                                      KM= (multplusbias/:/patterns1.rows)
                                      KM:^=kParam(1)
      }
      case "SIGMOID" => {
          if (kParam.length <2) {
          throw sys.error("Sigmoid kernel needs two parameters")
          }else (1 to Nf2).foreach(i=> KM(::, i):= tanh(patterns1.t * patterns2(::,i)*kParam(1)*kParam(2)))
      }
      case _ =>  throw sys.error("Unknown kernel. Avaiable kernels are: Gauss, Linear, Poly, or Sigmoid.")
      }
    }
  }

