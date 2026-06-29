pipeline {
    agent any

    // Definimos variables globales para no repetir código
    environment {
        APP_NAME = 'ecommerce-api-catalog'
        IMAGE_NAME = "mi-ecommerce/${APP_NAME}"
        DOCKER_NETWORK = 'ecommerce-network'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Obteniendo código de GitHub...'
                checkout scm
            }
        }
        
        stage('Build & Test') {
            steps {
                echo 'Compilando Spring Boot y ejecutando pruebas...'
                // Ejecutamos ambas fases. Si falla una prueba, el pipeline se detiene.
                sh './mvnw clean package'
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                echo 'Auditando la calidad del código fuente...'
                // Asegúrate de que 'SonarQube-Local' esté configurado en: Manage Jenkins -> System
                withSonarQubeEnv('SonarQube-Local') {
                    sh './mvnw sonar:sonar -Dsonar.projectKey=ecommerce-api-catalog -Dsonar.projectName="E-Commerce Catalog API"'
                }
            }
        }
        
        stage('Docker Build') {
            steps {
                echo 'Construyendo la imagen Docker...'
                sh "docker build -t ${IMAGE_NAME}:latest ."
            }
        }
        
        stage('Security Scan (Trivy)') {
            steps {
                echo 'Buscando vulnerabilidades CRÍTICAS y ALTAS en la imagen...'
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    sh "docker run --rm -v /var/run/docker.sock:/var/run/docker.sock aquasec/trivy image --format template --template \"@/contrib/html.tpl\" --exit-code 1 --severity HIGH,CRITICAL ${IMAGE_NAME}:latest > trivy-report.html"
                }
                archiveArtifacts artifacts: 'trivy-report.html', allowEmptyArchive: true
            }
        }
        
        stage('Deploy Local') {
            steps {
                echo 'Desplegando la API localmente conectada a la base de datos...'
                sh """
                    docker stop ${APP_NAME}-container || true
                    docker rm ${APP_NAME}-container || true
                    docker run -d \
                      --name ${APP_NAME}-container \
                      --network ${DOCKER_NETWORK} \
                      -p 8085:8085 \
                      -e SPRING_DATASOURCE_URL=jdbc:postgresql://ecommerce-postgres:5432/ecommerce_db \
                      -e SPRING_DATA_REDIS_HOST=ecommerce-redis \
                      ${IMAGE_NAME}:latest
                """
            }
        }
    }
}