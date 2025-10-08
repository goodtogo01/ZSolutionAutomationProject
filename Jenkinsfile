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
                git branch: 'main', url: 'https://github.com/khosruz/ZSolutionAutomationProject.git'
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
        echo "📊 Preparing Extent report..."
        script {
            // Create a folder under workspace for Jenkins to read
            sh 'mkdir -p target/extent-report'
            
            // Copy your existing ExtentReport.html to target folder
            sh 'cp /Users/khosruzzaman/ALL_JAVA/FrameWork/ZSolutionAutomationProject/test-output/ExtentReport.html target/extent-report/index.html'
        }
    }
}

stage('Publish Test Reports') {
    steps {
        echo "📢 Publishing Extent HTML Report..."
        publishHTML([
            reportDir: 'target/extent-report',  // folder inside Jenkins workspace
            reportFiles: 'index.html',          // renamed from ExtentReport.html
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
            echo "❌ Pipeline failed. Please check logs and Extent Report."
        }
    }
}