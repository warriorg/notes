pipeline {
	agent {
		label 'nodejs18-slave'
	}
	options {
		// 禁止同时运行多个流水线
		disableConcurrentBuilds()
	}

	//常量参数，初始确定后一般不需更改
	environment {
		BUSINESS = "cms"    // 命名空间
		PROJECT = "cms-web"     // 模块名
		GITCRED = 'c91592d4-b863-47fc-adce-c146a49b5c10'
		SERICEGITPATH= "http://${GIT_URL}/cms/cms-web.git"
		HARBORPATH = "${HARBOR_URL}"
		current_module = "." //应用名
		SERVICE = "cms-web" //应用名

	}
	parameters {
		// 环境新增
		choice(choices: ['dev'], description: '操作环境', name: 'Profile')
		// 动作
		choice(name: 'Status', choices: ['Deploy', 'Rollback', 'Sonarqube'], description: 'Deploy 发布;Rollback 回退;Sonarqube 扫描')
		// git分支选择
		gitParameter name: 'BRANCH_TAG',
				type: 'PT_BRANCH_TAG',
				branchFilter: 'origin/(.*)',
				defaultValue: 'dev',
				selectedValue: 'DEFAULT',
				sortMode: 'DESCENDING_SMART',
				description: 'Select your branch or tag.'
	}
	stages {

		stage("Pre work") {
			steps {
				script {
					module = "${current_module}".toString()
					namespace = "${BUSINESS}"
					VERSION = VersionNumber (versionNumberString: '${BUILD_DATE_FORMATTED, "yyyyMMdd"}-${BRANCH_TAG}-v${BUILDS_TODAY}')
					imagePath = "${HARBORPATH}/${BUSINESS}-${Profile}/${SERVICE}:${VERSION}"
				}
				echo "Pre work echo"
				echo "choose service: ${SERVICE}"
				echo "branch: ${BRANCH_TAG}"
				echo "imagePath : ${imagePath}"
				deleteDir()

				dir("src") {
					git branch: "${BRANCH_TAG}", credentialsId: "${GITCRED}", url: "${SERICEGITPATH}"
				}
				dir("cicddir") {
					git branch: "main", credentialsId: "${GITCRED}", url: 'http://${GIT_URL}/wufan/jenkins-pipeline.git'
					sh("chmod -R +x ./cicd/*")
				}
			}
		}

	// SonarQube 代码审查阶段  20250122新增
	stage('代码审查') {
		when {
			environment name: 'Status', value: 'Sonarqube'
		}
		steps {
			echo '开始代码审查'
			script {
				def scannerHome = tool 'SonarQube'
				withSonarQubeEnv('sonarqube') {
					timeout(time: 15, unit: 'MINUTES') { // 超时机制
						sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=${BUSINESS}::${Profile}-${PROJECT} -Dsonar.sources=./src"
					}
				}
			}
			echo '代码审查完成'
		}
	}

		// 选择部署
		stage('Docker Build') {
			when {
				environment name: 'Status', value: 'Deploy'
			}
			steps {
				withCredentials([usernamePassword(credentialsId: 'DockerServer', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {                 
					sh 'docker --version' 
					sh "echo 'docker login -u ${USERNAME} -p ${PASSWORD} ${HARBORPATH}'"
					sh "docker login -u ${USERNAME} -p ${PASSWORD} ${HARBORPATH}"
				}
				echo 'Credentials SUCCESS'
				script {
					echo "image : ${imagePath}"
					dir("cicddir") {
						sh("bash -x ./cicd/front_build_cms.sh ${module} ${imagePath} ${Profile}")
					}
					echo "Docker 编译完成"
				}

			}
		}
		stage("Deploy") {
			when {
				expression { env.Status == "Deploy" || env.Status == "Rollback"}
			}
			steps {
				dir("cicddir") {
					script {

						if (Status == 'Deploy') {
							sh("./cicd/deploy.sh --env=${Profile} --action=deploy  --namespace=${namespace} --servicename=${SERVICE} --serviceimage=${imagePath}")
							echo "deploy success"
						} else if (Status == 'Rollback') {
							sh("./cicd/deploy.sh --env=${Profile} --action=rollback --namespace=${namespace} --servicename=${SERVICE}")
							echo "rollback success"
						}
					}
				}
			}
		}
	}
}