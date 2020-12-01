## Connector  Introduction
Connector is a Java based backend extension for WaveMaker applications. Connectors are built as Java modules & exposes java based SDK to interact with the connector implementation.
Each connector is built for a specific purpose and can be integrated with one of the external services. Connectors are imported & used in the WaveMaker application. Each connector runs on its own container thereby providing the ability to have it’s own version of the third party dependencies.
## Features of Connectors
1. Connector is a java based extension which can be integrated with external services and reused in many Wavemaker applications.
1. Each connector can work as an SDK for an external system.
1. Connectors can be imported once in a WaveMaker application and used many times in the applications by creating multiple instances.
1. Connectors are executed in its own container in the WaveMaker application, as a result there are no dependency version conflict issues between connectors.
## About Google cloud file storage Connector
## Introduction
Cloud Storage is a flexible, scalable, and durable storage option for your virtual machine instances. You can read and write files to Cloud Storage buckets from almost anywhere, so you can use buckets as common storage between your instances, App Engine, your on-premises systems, and other cloud services.
This connector exposes api to interact with Google cloud file storage from WaveMaker application.
## Prerequisite
1. Google cloud storage service account file(.json)
1. Google cloud storage Project Id and Bucket name
1. Java 1.8 or above
1. Maven 3.1.0 or above
1. Any java editor such as Eclipse, Intellij..etc


## Build
You can build this connector using following command
```
mvn clean install
```

## Deploy
You can import connector dist/google-cloud-file-storage.zip artifact in WaveMaker studio application using file upload option, after uploading into wavemaker, you can update your profile properties such as Project Id, Bucket name and Service file name.


## Use Google cloud file storage connector in WaveMaker
```

@Autowired
private GoogleCloudFileStorageConnector googleCloudFileStorageConnector;

List<GoogleCloudFileStorageObjectSummary> listObjects = googleCloudFileStorageConnector.listObjects(folderPath, retrieveAll);
googleCloudFileStorageConnector.downloadObject(objectName);

```
Apart from above apis, there are other apis exposed in this connector, please visit connector interface in the api module.