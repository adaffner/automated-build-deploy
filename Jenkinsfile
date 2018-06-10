pipeline {
  agent any
  options {
    buildDiscarder(logRotator(numToKeepStr: '5'))
  }
  tools {
    maven 'Maven 3.3.9' 
    jdk 'jdk8'
  }
  stages {
    stage('build hello world'){
      steps{
         echo "This is a minimal pipeline"
      }
    }
    }
  }
}
