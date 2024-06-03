pipeline {
    agent any

    environment {
        MAVEN_HOME = tool name: 'Maven 3.9.6', type: 'maven'
        JAVA_HOME = tool name: 'Java 21', type: 'jdk'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    env.PATH = "${env.JAVA_HOME}/bin:${env.MAVEN_HOME}/bin:${env.PATH}"
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Containerize') {
            steps {
                script {
                    def services = ['book', 'borrowing', 'notification']
                    services.each { service ->
                        sh """
                            docker build -t ${IMAGE_NAME}/${service}:latest ./${service}
                        """
                    }
                }
            }
        }

        stage('Push Image') {
            when {
                expression { return params.PUSH_IMAGE }
            }
            steps {
                script {
                    def services = ['book', 'borrowing', 'notification']
                    docker.withRegistry(DOCKER_REPO, DOCKER_CREDENTIALS_ID) {
                        services.each { service ->
                            sh """
                                docker tag ${IMAGE_NAME}/${service}:latest ${DOCKER_REPO}/${service}:latest
                                docker push ${DOCKER_REPO}/${service}:latest
                            """
                        }
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    def services = ['book', 'borrowing', 'notification']
                    services.each { service ->
                        sh """
                            docker run --rm ${DOCKER_REPO}/${service}:latest mvn test
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
