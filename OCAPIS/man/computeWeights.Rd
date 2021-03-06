% Generated by roxygen2: do not edit by hand
% Please edit documentation in R/svmop.R
\name{computeWeights}
\alias{computeWeights}
\title{compute weights for each instance}
\usage{
computeWeights(p, tags)
}
\arguments{
\item{p}{An int number.}

\item{tags}{A vector of tags.}
}
\value{
The weights array for data instances given a value of p.
}
\description{
compute weights for each instance in the data set
}
\examples{
computeWeights(1,c(1,2,3,1,2,1))

}
