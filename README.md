# Hepsitodo App Backend

Please follow operations that shown below to run this project.

### Database Initialization

* `docker-compose up -d db`
* Visit the [http://localhost:8091/](http://localhost:8091/)
* Login with user name and password (username: **Administrator**, password: **password**)
* Create **bucket** called `todo_bucket`
* Create a **document** called `todos` on `todo_bucket`
* Create a **document** called `users` on `todo_bucket`
* Run `CREATE PRIMARY INDEX ON todo_bucket._default.users` command to create index.
* Run `CREATE PRIMARY INDEX ON todo_bucket._default.todos` command to create index.

### Start the Service

* Run `docker-compose up -d todo_server`

### Start the UI App

* If everything is ok, you can run the ui application that shown this [repo](https://github.com/sinyorre/hepsitodo-ui).
