pipeline {
    agent any

    stages {
        stage('Compile, test') {
            steps {
                git 'https://github.com/Tiuters/spring-boot-for-jenkins.git'
                bat "mvn clean compile test"
            }
        }
        stage("foo") {
            steps {
                script {
                    env.FILENAME = 'dokerand/spring-with-controller:latest'
                }
                echo "****************************************************************************************************"
                echo "${env.FILENAME}"
            }
        }
        stage('Build JAR') {
            steps {
                bat "mvn clean package spring-boot:repackage"
            }
        }
        stage('Create Docker Image') {
            steps {
                bat "docker build -t ${env.FILENAME} ."
            }

        }
        stage('Deploy to DockerHub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials',
                    usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {

                        bat "docker login --username $USERNAME --password $PASSWORD"
                        bat "docker push ${env.FILENAME}"
                    }
                }
            }
        }
        stage('Run Docker Container') {
            steps {
                bat "docker stop spring-from-jenkins"
                bat "docker rm spring-from-jenkins"
                bat "docker rmi dokerand/spring-with-controller:latest"
                bat "docker run -d --name spring-from-jenkins -p 8081:8081 dokerand/spring-with-controller:latest"
            }

        }
    }
}