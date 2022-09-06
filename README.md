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

**_NOTE:_** Postman Collection file can be found at this [address](https://github.com/sinyorre/hepsitodo-backend/blob/master/hepsiemlak.postman_collection.json)

**_NOTE:_** Swagger UI can be found at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) (Please use with **_/v1/api-docs_** path)

### Start the UI App

* If everything is ok, you can run the ui application that shown this [repo](https://github.com/sinyorre/hepsitodo-ui).
