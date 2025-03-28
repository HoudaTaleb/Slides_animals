pipeline {
    agent any  // Exécuter sur n'importe quel agent disponible

    environment {
        DOCKER_IMAGE = 'Nour/slides_animals_image:latest'  // Change ceci avec ton image
        DOCKER_CREDENTIALS = 'docker-hub-credentials-id' 
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/HoudaTaleb/Slides_animals'  // Change l'URL avec ton dépôt
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build(DOCKER_IMAGE)
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS) {
                        docker.image(DOCKER_IMAGE).push()
                    }
                }
            }
        }
    }
}
