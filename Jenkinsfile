pipeline {
    agent any

    environment {
        MAVEN_HOME = tool name: 'Maven3', type: 'maven'
        PATH = "${MAVEN_HOME}/bin:${PATH}"
        BROWSER = "headless" // 👈 To control browser visibility easily
    }

    triggers {
        // Automatically run this pipeline on every GitHub push
        githubPush()
        // Optional fallback (poll every 30 mins if webhook fails)
        pollSCM('H/30 * * * *')
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
                sh 'mvn clean compile -Dmaven.test.skip=true'
            }
        }

        stage('Run Automation Tests') {
            steps {
                echo "🧪 Running Selenium + TestNG tests (Headless Mode)..."
                // Pass a system property to control headless execution in your framework
                sh 'mvn test -Dheadless=true'
            }
        }

        stage('Generate Extent Report') {
            steps {
                echo "📊 Collecting Extent report..."
                script {
                    // Copy the entire folder, not just the HTML file
                    sh '''
                        echo "🔧 Preparing Extent report directory..."
                        mkdir -p target/extent-report
                        cp -r test-output/* target/extent-report/ || true
                        echo "📁 Files copied to Jenkins report directory:"
                        ls -la target/extent-report/
                    '''
                }
            }
        }

        stage('Publish Test Reports') {
            steps {
                echo "📢 Publishing Extent Reports..."
                publishHTML([
                    reportDir: 'target/extent-report',
                    reportFiles: 'ExtentReport.html',
                    reportName: '📊 Extent Test Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: false,
                    includes: '**/*' // ensures CSS/JS/media are included
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