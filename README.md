## Create LocalJobs Application 

LocalJobs application is a location aware Job search application.

```
rhc app create localjobs jbosseap postgresql-8.4 mongodb-2.2 -s
```

The command shown above will create a scalable JBossEAP application. The application will use PostgreSQL and MongoDB for persistence. The postgres database will be used for storing User information and MongoDB database will be used for persisting Jobs information. The application also use searchify -- Full Text Search as a Service for full text search.


## Pulling code from github and pushing to OpenShift

```
git remote add upstream -m master git://github.com/shekhargulati/localjobs-scalable.git
 
git pull -s recursive -X theirs upstream master

```
## Importing Jobs Data to MongoDB

```
rhc app show -a localjobs

scp jobs-data.json <instance_ssh_access>:app-root/data

ssh <instance_ssh_access>

mongoimport -d localjobs -c jobs --file jobs-data.json -u $OPENSHIFT_MONGODB_DB_USERNAME -p $OPENSHIFT_MONGODB_DB_PASSWORD -h $OPENSHIFT_MONGODB_DB_HOST -port $OPENSHIFT_MONGODB_DB_PORT

login to database using mongo client and create a 2d index
> db.jobs.ensureIndex({"location":"2d"})

```

## Run MongoDB queries

While you are in mongo shell lets execute some commands

```
## Count of all the Java jobs near to my location

db.jobs.find({"location":{$near : [11.5992338,48.1530699]},"skills":"java"}).limit(2)


## Give me Address of all the Java jobs near to my location

db.jobs.find({"location":{$near : [11.5992338 , 48.1530699]},"skills":"java"},{"formattedAddress":1}).limit(2)
```

## Deploy the application to cloud

```
git push
```

## Application in Cloud

The application will be up and running at following urls
http://localjobs-cix.rhcloud.com/