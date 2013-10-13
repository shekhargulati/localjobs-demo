## Create LocalJobs Application 

LocalJobs application is a location aware Job search application.

```
$ rhc create-app localjobs jbosseap mongodb-2.2 postgresql-9.2 -s
$ rhc add-cartridge http://cartreflect-claytondev.rhcloud.com/reflect?github=smarterclayton/openshift-redis-cart --app localjobs
$ rhc env set SPRING_PROFILES_ACTIVE=openshift
```

The command shown above will create a scalable JBossEAP application. The application will use PostgreSQL and MongoDB for persistence. The postgres database will be used for storing User information and MongoDB database will be used for persisting Jobs information. The application also use searchify -- Full Text Search as a Service for full text search.


## Pulling code from github and pushing to OpenShift

```
$ git rm -rf src pom.xml
$ git commit -am “delete template app”
$ git remote add upstream -m master https://github.com/shekhargulati/localjobs-demo.git
$ git pull -s recursive -X theirs upstream master
```

## Deploy the application to cloud

```
git push
```

## Application in Cloud

The application will be up and running at following url http://localjobs-{domain-name}.rhcloud.com
