pipeline {
  agent {label 'worker1'}
   tools {
     maven 'maven-3'
  }
  options {
    buildDiscarder(logRotator(numToKeepStr: '5'))
  }
  stages {
    stage ('build hello world') {
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }
  }
}
