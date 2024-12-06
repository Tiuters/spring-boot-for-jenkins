pipeline {
    agent any

    stages {
        stage("Make variable") {
            steps {
                script {
                    env.FILENAME = 'dokerand/spring-with-controller:1'
                }
                echo "****************************************************************************************************"
                echo "${env.FILENAME}"
            }
        }
        stage("Scan with SonarQube"){
            steps{
                withSonarQubeEnv(installationName: 'my-sonar'){
                    bat "mvn clean install org.sonarsource.scanner.maven:sonar-maven-plugin:5.0.0.4389:sonar"
                }
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
                bat "docker rmi dokerand/spring-with-controller:1"
                bat "docker run -d --name spring-from-jenkins -p 8081:8081 dokerand/spring-with-controller:1"
            }
        }
    }
}