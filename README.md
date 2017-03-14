# Git Repo for Blue Pipeline Web-App

This is a web-app which will utilize various 3rd party apps/scripts for extracting entity information from documents.

## Version 1.3

- Upload file capability
- Tokenize Input File
- Extract Entities
- Shared Code with all Pipeline Projects

## Downloading

```sh
git clone https://github.com/artistech-inc/pipeline-base.git
cd pipeline-base
mvn clean install
cd ..
git clone https://github.com/artistech-inc/blue-pipeline.git
cd blue-pipeline-web
mvn clean package
```

## Configuration

Update the [pipeline.yml](https://github.com/artistech-inc/blue-pipeline-web/blob/master/src/main/resources/pipeline.yml) file.  Each component must have the proper path value set.  This is the location where the external process will execute from.

- CAMR (see below and also yellow-pipeline-web)
- Elisa (set the url for Elisa) (ex: http://localhost:3300/elisa_ie)

The `data-path` value must be somewhere that Tomcat can write to.

## Compilation

This project should be able opened using Netbeans as a Maven Web-App. It will automatically detect the type of project.

The project can also be compiled on the command line directly using maven.

```sh
cd blue-pipeline-web
mvn clean package
```
## Dependencies

- [ArtisTech's camr yellow-pipeline fork/branch](https://github.com/artistech-inc/camr/tree/yellow-pipeline)
- [ELISA-IE](https://hub.docker.com/r/zhangb8/lorelei/)

## Deployment

The output from compilation is in the `target/` directory as `blue-pipeline-web-1.3.war`. This war can be deployed to Tomcat's `webapps` directory. Once deployed, it can be accessed via `http://<ip_address:port>/blue-pipeline-web-1.3/`.

## Bugs

- Issues and bugs can be e-mailed or registered.

## TODO

- Do something with the output from Elisa

## Future (possibly not in scope)

- Database
- Users
