pipeline {

    agent any

    environment {
        MYSQL_ROOT_LOGIN = credentials('mariadb')
    }
    stages {

        stage('Packaging/Pushing image') {

            steps {
                withDockerRegistry(credentialsId: 'dockerhub', url: 'https://index.docker.io/v1/') {
                    sh 'docker build -t phuongnt99/springboot .'
                    sh 'docker push phuongnt99/springboot'
                }
            }
        }


        stage('Deploy Spring Boot to DEV') {
            steps {
                echo 'Deploying and cleaning'
                sh 'docker image pull phuongnt99/springboot'
                sh 'docker container stop khalid-springboot || echo "this container does not exist" '
                sh 'docker network create dev || echo "this network exists"'
                sh 'echo y | docker container prune '

                sh 'docker container run -d --rm --name phuongnt99-springboot -p 8081:8080 --network dev phuongnt99/springboot'
            }
        }

    }
    post {
        // Clean after build
        always {
            cleanWs()
        }
    }
}