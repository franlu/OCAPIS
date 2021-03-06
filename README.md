# [OCAPIS](https://cristinahg.github.io/OCAPIS/) 

Package for ordinal data classification and preprocessing implementing algorithms in Scala


[![Issues](https://img.shields.io/github/issues/CristinaHG/OCAPIS.svg)](https://github.com/CristinaHG/OCAPIS/issues)
[![license](https://img.shields.io/github/license/CristinaHG/OCAPIS.svg)](https://www.gnu.org/licenses/gpl.html)
[![Forks](https://img.shields.io/github/forks/CristinaHG/OCAPIS.svg)](https://github.com/CristinaHG/OCAPIS/network/members)
[![Stars](https://img.shields.io/github/stars/CristinaHG/OCAPIS.svg)](https://github.com/CristinaHG/OCAPIS/stargazers)
[![R language](https://img.shields.io/badge/language-R-lightgrey.svg)](https://www.r-project.org/)
[![Scala language](https://img.shields.io/badge/language-Scala-red.svg)](https://www.scala-lang.org/)

---

Included algorithms are:

### Classification algorithms
- Support vector machine for ordinal data
- Ordinal regression
- Kernel Discriminant Learning for Ordinal Regression 
- Weighted K-Nearest Neightborgs for monotonic and ordinal data

### Preprocessing algorithms
- Feature selection for monotonic and ordinal data
- Instance selection for monotonic and ordinal data

## Installation

### Dependencies 
Before installing OCAPIS you need to get Python (>=2.7), Scala(>=2.11) and libsvm-weights-3.17 installed on your system, if they are not yet.

#### Installing Python
If using Linux, you can easily install Python from the command line, just typing:

```
$ sudo apt-get install python3
```
If your system is an Ubuntu distribution, or its counterpart in the distro you use. If you are not using Linux or you are not convinced  to install Python through command line, just check this official [Python Installation guide](https://wiki.python.org/moin/BeginnersGuide/Download).

#### Installing Scala
Similarly, if using Linux, you can install Scala from repo. For example, for Linux Mint just type:

```
sudo apt install scala
```
In any other case just check the **Other ways to install Scala** section from the official [Scala Installation Guide](https://www.scala-lang.org/download/).

#### Installing Libsvm-weights

Libsvm-weights-3.17 is required as it is used by SVMOP method. To install it, just follow the instructions in **Installation and Data Format** section from the README on [Libsvm-weights](https://github.com/claesenm/EnsembleSVM/tree/master/libsvm-weights-3.17).

### Installing OCAPIS
After installing the external dependencies, the latest version of OCAPIS can be installed from GitHub via:

```
devtools::install_github("cristinahg/OCAPIS/OCAPIS")
```
The rest of the dependencies will be automatically installed. These are [Reticulate](https://github.com/rstudio/reticulate) and [Rscala](https://github.com/dbdahl/rscala).

## Usage

Below are shown examples of how to use all classification and preprocessing methods, using an ordinal dataset named **balance-scale**.

### Classification

```r
# Data reading
dattrain<-read.table("train_balance-scale.0", sep=" ")
trainlabels<-dattrain[,ncol(dattrain)]
traindata=dattrain[,-ncol(dattrain)]
dattest<-read.table("test_balance-scale.0", sep=" ")
testdata<-dattest[,-ncol(dattest)]
testlabels<-dattest[,ncol(dattest)]

# SVMOP
modelstrain<-svmofit(traindata,trainlabels,TRUE,0.1,0.1)
predictions<-svmopredict(modelstrain,testdata)
sum(predictions[[2]]==testlabels)/nrow(dattest)

# POM
fit<-pomfit(traindata,trainlabels,"logistic")
predictions<-pompredict(fit,testdata)
projections<-predictions[[1]]
predictedLabels<-predictions[[2]]
sum(predictedLabels==testlabels)/nrow(dattest)

# KDLOR
myfit<-kdlortrain(traindata,trainlabels,"rbf",10,0.001,1)
pred<-kdlorpredict(myfit,traindata,testdata)
sum(pred[[1]]==testlabels)/nrow(dattest)

# WKNNOR
predictions<-wknnor(traindata,trainlabels,testdata,5,2,"rectangular",FALSE)
sum(predictions==testlabels)/nrow(dattest)
mae(testlabels,predictions)
```

### Preprocessing

```r
# Feature Selector
selected<-fselector(traindata,trainlabels,2,2,8)
trainselected<-traindata[,selected]
```

```r
# Instance Selector
selected<-iselector(traindata,trainlabels,0.02,0.1,5)
trainselected<-selected[,-ncol(selected)]
trainlabels<-selected[,ncol(selected)]
```

For more details about method params, see [OCAPIS documentation](https://cristinahg.github.io/OCAPIS/reference/).


## References:
1. E. Frank and M. Hall, "A simple approach to ordinal classification"
in Proceedings of the 12th European Conference on Machine Learning,
ser. EMCL'01. London, UK: Springer-Verlag, 2001, pp. 145–156.
https://doi.org/10.1007/3-540-44795-4_13
2. W. Waegeman and L. Boullart, "An ensemble of weighted support
vector machines for ordinal regression", International Journal
of Computer Systems Science and Engineering, vol. 3, no. 1,
pp. 47–51, 2009.
3. P.A. Gutiérrez, M. Pérez-Ortiz, J. Sánchez-Monedero,
F. Fernández-Navarro and C. Hervás-Martínez
Ordinal regression methods: survey and experimental study
IEEE Transactions on Knowledge and Data Engineering, Vol. 28. Issue 1
2016
http://dx.doi.org/10.1109/TKDE.2015.2457911
4. P. McCullagh, Regression models for ordinal data,  Journal of
the Royal Statistical Society. Series B (Methodological), vol. 42,
no. 2, pp. 109–142, 1980.
5. B.-Y. Sun, J. Li, D. D. Wu, X.-M. Zhang, and W.-B. Li,
Kernel discriminant learning for ordinal regression
IEEE Transactions on Knowledge and Data Engineering, vol. 22,
no. 6, pp. 906-910, 2010.
https://doi.org/10.1109/TKDE.2009.170
6. Duivesteijn, Wouter & Feelders, Ad. (2008). Nearest Neighbour Classification with Monotonicity Constraints. 301-316. 10.1007/978-3-540-87479-9_38.
7. Cano, José & García, S. (2017). Training Set Selection for Monotonic Ordinal Classification. Data & Knowledge Engineering. 112. 10.1016/j.datak.2017.10.003. 
8. Hu, Qinghua & Pan, Weiwei & Zhang, Lei & Zhang, David & Song, Yanping & Guo, Maozu & Yu, Daren. (2012). Feature Selection for Monotonic Classification. IEEE T. Fuzzy Systems. 20. 69-81. 10.1109/TFUZZ.2011.2167235. 
9. Hechenbichler, Schliep:
Weighted k-Nearest-Neighbor Techniques and Ordinal Classification Sonderforschungsbereich 386, Paper 399 (2004)
https://epub.ub.uni-muenchen.de/1769/1/paper_399.pdf
