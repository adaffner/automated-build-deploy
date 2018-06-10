pipeline {
  agent any
  options {
    buildDiscarder(logRotator(numToKeepStr: '5'))
  }
  stages {
    stage('build hello world'){
      steps{
         echo "This is a minimal pipeline"
      }
    }
  }
}
