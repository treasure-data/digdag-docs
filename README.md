# digdag, a workflow automation tool

**Digdag** is a tool that runs multiple tasks in order. You can automate your workflows using digdag's schedulers, scripting, parameterized execution, task dependencies, error notifications, visual status monitors, and retrying.

## Creating a workflow

A workflow is a YAML file:

```yaml
basic_example_workflow:
  +my_task_1:
    sh>: "echo this task runs first."

  +my_task_2:
    sh>: "echo this task runs next."

  +any_task_name_here:
    +nested_task:
      sh>: "echo tasks can be nested like this."

    +nested_task_2:
      sh>: "echo nesting is useful for grouping"

  +parallel_task_foo:
    parallel: true

    +bar:
      sh>: "echo if 'parallel: true' is set, child tasks run in parallel"

    +baz:
      sh>: "echo bar and baz run in parallel"

  +abc:
    sh>: "echo please check other examples in examples/ directory for more features."
```

* Name of a task begins with `+`.
* Tasks can run in parallel if you set `parallel: true` option.
* `sh>:` executes a shell command or script.

For more examples, please check files at [examples/](examples/):

* **examples/shell_envvar.yml** uses environment variables.
* **examples/python_args.yml** uses python scripting.
* **examples/schedule.yml** uses scheduled execution. You can run it using `digdag sched examples/schedule.yml` command.
* **examples/include.yml** uses `include` tag to include another yaml file.
* **examples/error_task.yml** uses `error:` task to notice errors of a task.


## Running a workflow

```
$ digdag run examples/basic.yml
```

## Server mode (experimental)

With server mode, you can start a workflow using a REST API.

```
$ digdag server examples/basic.yml
$ curl -X PUT -H "Content-Type: application/json" -d '{"workflow":"basic_example_workflow"}' "http://127.0.0.1:9090/api/sessions"
```

Following REST API endpoints are available:

```
GET  /api/repositories                                # list the latest revisions of repositories
GET  /api/repositories/<id>                           # show the latest revision of a repository
GET  /api/repositories/<id>?revision=name             # show a former revision of a repository
GET  /api/repositories/<id>/workflows                 # list workflows of the latest revision of a repository
GET  /api/repositories/<id>/workflows?revision=name   # list workflows of a former revision of a repository
GET  /api/repositories/<id>/archive                   # download archive file of the latest revision of a repository
GET  /api/repositories/<id>/archive?revision=name     # download archive file of a former revision of a repository
PUT  /api/repositories?repository=<name>&revision=<name>  # create a new revision (also create a repository if it doesn't exist)
GET  /api/workflows                                   # list workflows of the latest revision of all repositories
GET  /api/workflows/<id>                              # show a particular workflow (which belongs to a revision)
GET  /api/sessions                                    # list sessions from recent to old
GET  /api/sessions?repository=<name>                  # list sessions that belong to a particular repository
GET  /api/sessions?repository=<name>&workflow=<name>  # list sessions that belong to a particular workflow
GET  /api/sessions/<id>                               # show a session
GET  /api/sessions/<id>/tasks                         # list tasks of a session
PUT  /api/sessions                                    # start a new session (should this be POST?)
GET  /api/schedules                                   # list schedules of the latest revision of all repositories
GET  /api/schedules/<id>                              # show a particular schedule (which belongs to a workflow)
```


## Status & Roadmap

* **DONE**: command-line mode
* **DONE**: resuming a failed workflow (-r option)
* **DONE**: starting a workflow from a specific task (-f option)
* **DONE**: parameterized eecution (-X option)
* **DONE**: scheduled execution (`sched` command) (automatic reloading of workflow definitions is TODO).
* **DONE**: triggering a task if a workflow doesn't finish in time (trigger criteria using duration time is TODO)
* **DONE**: visualizing a workflow (-s option of run, or `show` command) (it depends on graphviz. prettier visualization is TODO)
* TODO: back-filling scheduled executions
* TODO: server mode (partially implemented in `server` command)
* TODO: REST API implementation
* TODO: task execution in a docker container
* TODO: ruby execution plugin
* TODO: presto execution plugin
* TODO: hive execution plugin
* TODO: installing library dependencies of python, ruby, and other scripts using buildpack
* TODO: multiple job queues to run tasks on a specific role of agent servers
* TODO: task execution using remote agent servers
* TODO: single-tenant task queue using RDB and hazelcast
* TODO: limitting max concurrent tasks for each queues
* TODO: centralized task logging in server mode
* TODO: multi-user authentication using Shiro
* TODO: multi-tenant task queue
* TODO: better name, logo, and mascot
* TODO: license, documentation and open-source

