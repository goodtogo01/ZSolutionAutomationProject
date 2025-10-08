pipeline {
    agent any

    environment {
        MAVEN_HOME = tool name: 'Maven3', type: 'maven'
        PATH = "${MAVEN_HOME}/bin:${PATH}"
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo "✅ Checking out source code..."
                git branch: 'main', url: 'https://github.com/goodtogo01/ZSolutionAutomationProject'
            }
        }

        stage('Build Application') {
            steps {
                echo "🏗️ Building application using Maven..."
                sh 'mvn clean compile'
            }
        }

        stage('Run Automation Tests') {
            steps {
                echo "🧪 Running Selenium + TestNG tests..."
                sh 'mvn test'
            }
        }

        stage('Generate Extent Report') {
            steps {
                echo "📊 Collecting Extent report..."
                script {
                    // Move report to Jenkins accessible location
                    sh 'mkdir -p target/extent-report'
                    sh 'cp -r test-output/* target/extent-report/ || true'
                }
            }
        }

        stage('Publish Test Reports') {
            steps {
                echo "📢 Publishing TestNG & Extent Reports..."
                publishHTML([ 
                    reportDir: 'target/extent-report', 
                    reportFiles: 'extent-report.html', 
                    reportName: 'Extent Report', 
                    keepAll: true, 
                    alwaysLinkToLastBuild: true, 
                    allowMissing: true 
                ])
            }
        }

    }

    post {
        always {
            echo "🧹 Cleaning up workspace..."
            cleanWs()
        }
        success {
            echo "✅ Pipeline completed successfully!"
        }
        failure {
            echo "❌ Pipeline failed. Please check logs and Extent Report."
        }
    }
}