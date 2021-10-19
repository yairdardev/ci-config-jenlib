@Library('JenkinsLib_Jenlib') _

def kwj = [
    'scmvars': null,
    'task_parse_result': [:],
    'params':[
        'taskwork_dir': env.taskwork_dir ?: '.',
        'taskfile_path': env.taskfile_path ?: 'Taskfile.yml',
        'entrypoint_task': env.entrypoint_task ?: 'ci-flow',
        'params_slug': env.params_slug ?: ''
    ]
]


node {
    
    stage('start'){
        sh 'echo Init Flow'
        def scmvars = checkout scm

        if (kwj.params.params_slug) {
            def loaded_yaml = readYaml file: kwj.params.params_slug
            kwj.params << loaded_yaml
        }        
    }

    dir(kwj.params.taskwork_dir){
        echo "would unflod ${kwj.params.taskcmd}"
        jen.step_stages_from_tasks(
            kwj, 
            '.' ,
            kwj.params.taskfile_path,
            kwj.params.entrypoint_task
        )
    }

    stage('finis'){
        sh 'echo Over Flow'
    }
}
