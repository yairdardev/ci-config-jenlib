@Library('JenkinsLib_Jenlib') _

def kwj = [
    'scmvars': null,
    'task_parse_result': [:],
    'params':[
        // 'taskcmd': 'task -t  jobtask/Taskfile.yml ci-flow'
        'taskcmd': env.taskcmd,
        'taskupath': env.taskupath
    ]
]

node {

    stage('start'){
        sh 'echo START'
        kwj.scmvars = checkout scm
    }

    dir('.'){
        echo "would unflod ${kwj.params.taskcmd}"
        jen.step_stages_from_tasks(
            kwj, '.' ,'Taskfile.yml', 'ci-flow'
        )
    }

    stage('finis'){
        sh 'echo FINSH'
    }
}
