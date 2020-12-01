package com.wavemaker.connector.googlecloudfilestorage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import com.wavemaker.connector.exception.ServiceAccountCredentialsCreationException;
import com.wavemaker.connector.exception.ServiceAccountCredentialsFileNotFoundException;
import com.wavemaker.connector.properties.GoogleCloudFileStoragePropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public abstract class AbstractGoogleCloudFileStorageConnector implements GoogleCloudFileStorageConnector {

    private static final Logger logger = LoggerFactory.getLogger(AbstractGoogleCloudFileStorageConnector.class);

    private GoogleCloudFileStoragePropertiesService googleCloudFileStoragePropertiesService;

    public AbstractGoogleCloudFileStorageConnector(GoogleCloudFileStoragePropertiesService googleCloudFileStoragePropertiesService) {
        this.googleCloudFileStoragePropertiesService = googleCloudFileStoragePropertiesService;
    }

    private GoogleCredentials getGoogleServiceCredentials(File serviceAccountCredentialsFile) {
        logger.info("Fetching the service account credentials");

        try{
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(serviceAccountCredentialsFile))
                    .createScoped(Lists.newArrayList(googleCloudFileStoragePropertiesService.getGoogleApi()));
            return credentials;
        }
        catch (IOException e){
            logger.error(e.getMessage());
            throw new ServiceAccountCredentialsCreationException(e.getMessage(),e);
        }
    }

    public Storage getStorage(File serviceAccountCredentialsFile) {
        logger.info("Fetching the storage object");

        if(serviceAccountCredentialsFile == null){
            logger.error("Error in reading the service account credentials");
            throw new ServiceAccountCredentialsFileNotFoundException("Error in reading the service account credentials");
        }

        return StorageOptions.newBuilder()
                .setProjectId(googleCloudFileStoragePropertiesService.getProjectId())
                .setCredentials(getGoogleServiceCredentials(serviceAccountCredentialsFile))
                .build()
                .getService();
    }


    public GoogleCloudFileStoragePropertiesService getGoogleCloudFileStoragePropertiesService(){
        return googleCloudFileStoragePropertiesService;
    }
}
