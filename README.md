# Git Repo for Blue Pipeline Web-App

This is a web-app which will utilize various 3rd party apps/scripts for extracting entity information from documents.

## Version 1.0

- Upload file capability
- Tokenize Input File
- Extract Entities (display to console only for now)

## Downloading

`git clone https://github.com/artistech-inc/blue-pipeline-web.git`

## Configuration

Update the [WEB-INF/web.xml](https://github.com/artistech-inc/blue-pipeline-web/blob/master/src/main/webapp/WEB-INF/web.xml) file. Each Servlet that utilizes an external application/script/process must have the path to the application set. For now, this includes:

- CAMR (see below and also yellow-pipeline-web)
- Elisa (set the url for Elisa)

Along with the web.xml file, the [META-INF/context.xml](https://github.com/artistech-inc/blue-pipeline-web/blob/master/src/main/webapp/META-INF/context.xml) must be configured. The `data_path` value must be somewhere that Tomcat can write to.

## Compilation

This project should be able opened using Netbeans as a Maven Web-App. It will automatically detect the type of project.

The project can also be compiled on the command line directly using maven.

```sh
cd blue-pipeline-web
mvn clean package
```
## Dependencies
To tokenize each sentence prior to extraction.

- [ArtisTech's camr yellow-pipeline fork/branch](https://github.com/artistech-inc/camr/tree/yellow-pipeline)

## Deployment

The output from compilation is in the `target/` directory as `blue-pipeline-web-1.0.war`. This war can be deployed to Tomcat's `webapps` directory. Once deployed, it can be accessed via `http://<ip_address:port>/blue-pipeline-web-1.0/`.

## Bugs

- Issues and bugs can be e-mailed or registered.

## TODO

- Save output from Elisa

## Future (possibly not in scope)

- Database
- Users
