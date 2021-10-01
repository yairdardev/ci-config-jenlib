@Grab('org.yaml:snakeyaml:1.17')
@Library('JenkinsLib_Jenlib') _
import org.yaml.snakeyaml.Yaml
List projects = new Yaml().load(("conf/projects.yml" as File).text)

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
        kwj.scmvars = checkout scm
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
