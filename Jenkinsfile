pipeline {
    agent any

    environment {
        MAVEN_HOME = tool name: 'Maven3', type: 'maven'
        PATH = "${MAVEN_HOME}/bin:${PATH}"
        HEADLESS = "true"   // Headless browser execution
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
                echo "🧪 Running Selenium + TestNG tests in headless mode..."
                sh 'mvn test -DHEADLESS=true'
            }
        }

        stage('Generate Extent Report') {
            steps {
                echo "📊 Preparing Extent report..."
                script {
                    // Make sure the report directory exists and copy reports
                    sh 'mkdir -p target/extent-report'
                    sh 'cp -r test-output/ExtentReport/* target/extent-report/ || true'
                }
            }
        }

        stage('Publish Test Reports') {
            steps {
                echo "📢 Publishing Extent HTML Report..."
                publishHTML([
                    reportDir: 'target/extent-report',
                    reportFiles: 'index.html',      // Ensure this matches your ExtentReport output file
                    reportName: 'Extent Test Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: false
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
            echo "❌ Pipeline failed. Check logs and Extent Report!"
        }
    }
}