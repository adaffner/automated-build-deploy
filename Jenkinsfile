pipeline {
  agent any
  options {
    buildDiscarder(logRotator(numToKeepStr: '5'))
  }
  stages {
    stage('build hello world'){
      steps{
      }
        sh 'mvn -B -DskipTests clean package
    }
  }
}
