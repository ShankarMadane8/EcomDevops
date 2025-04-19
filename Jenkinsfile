pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "shankar888/springboot-k8s-demo"
        DOCKER_TAG = "latest"
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo "Cloning the repository..."
                git branch: 'main', url: 'https://github.com/ShankarMadane8/EcomDevops.git'
            }
        }

        stage('Build Maven Project') {
            steps {
                echo "Building the Maven project..."
                bat "mvn clean package -DskipTests"
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building Docker image..."
                script {
                    docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                echo "Pushing Docker image to DockerHub..."
                withDockerRegistry([credentialsId: 'dockerhub', url: '']) {
                    script {
                        docker.image("${DOCKER_IMAGE}:${DOCKER_TAG}").push()
                    }
                }
            }
        }

        stage('Deploy Container') {
            steps {
                echo 'Deploying the Docker container...'
                script {
                    bat """
                       docker rm -f ecom-container || echo "No existing container to remove"
                       docker run -d -p 8182:9999 --name ecom-container ${env.DOCKER_IMAGE}:${env.DOCKER_TAG}
                    """
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully!"
        }
        failure {
            echo "Pipeline failed!"
        }
    }
}
