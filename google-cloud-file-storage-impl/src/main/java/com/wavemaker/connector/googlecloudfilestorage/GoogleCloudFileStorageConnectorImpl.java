package com.wavemaker.connector.googlecloudfilestorage;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import com.wavemaker.connector.exception.*;
import com.wavemaker.connector.model.GoogleCloudFileStorageObjectSummary;
import com.wavemaker.connector.properties.GoogleCloudFileStoragePropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import jakarta.annotation.Nullable;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class GoogleCloudFileStorageConnectorImpl extends AbstractGoogleCloudFileStorageConnector{

    private static final Logger logger = LoggerFactory.getLogger(GoogleCloudFileStorageConnectorImpl.class);

    public GoogleCloudFileStorageConnectorImpl(GoogleCloudFileStoragePropertiesService googleCloudFileStoragePropertiesService) {
        super(googleCloudFileStoragePropertiesService);
    }

    /**
     * Returns a list of summary information about the objects in the specified bucket of a project.
     *
     * @param folderPath            is the directory path to the file
     * @param retrieveAll           is used to fetch all the files in a bucket
     * @return: {@link List<GoogleCloudFileStorageObjectSummary> object summary} A listing of the objects in the specified bucket of a project, along with any other associated information
     * @throws ServiceAccountCredentialsFileNotFoundException   is thrown when there is an error in reading the file
     * @throws ServiceAccountCredentialsCreationException       is thrown when there is an error in creating the credentials
     * @throws GoogleObjectsNotFoundException                   is thrown when is there is an error in retrieving the list of objects
     */
    @Override
    public List<GoogleCloudFileStorageObjectSummary> listObjects(@Nullable String folderPath, Boolean retrieveAll){
        Page<Blob> blobs;
        List<GoogleCloudFileStorageObjectSummary> googleCloudFileStorageObjectSummaries = new ArrayList<>();

        Bucket bucket = null;
        try {
            bucket = getStorage(getFileUsingClassPathResource(getGoogleCloudFileStoragePropertiesService().getGoogleServiceAccountFileName())).get(getGoogleCloudFileStoragePropertiesService().getBucketName());

        } catch (ServiceAccountCredentialsFileNotFoundException e) {
           throw new ServiceAccountCredentialsFileNotFoundException(e.getMessage(), e);

        } catch (ServiceAccountCredentialsCreationException e) {
            throw new ServiceAccountCredentialsCreationException(e.getMessage(), e);
        }

        if (retrieveAll && (folderPath == null))
                blobs = bucket.list();
            else {
                if (folderPath == null)
                    blobs = bucket.list(Storage.BlobListOption.currentDirectory());
                else
                    blobs = bucket.list(Storage.BlobListOption.prefix(folderPath), Storage.BlobListOption.currentDirectory());
            }

            if (blobs == null) {
                logger.error("Error in retrieving the list of objects");
                throw new GoogleObjectsNotFoundException("Error in retrieving the list of objects");
            }


        for (Blob blob : blobs.iterateAll()) {
            GoogleCloudFileStorageObjectSummary googleCloudFileStorageObjectSummary = new GoogleCloudFileStorageObjectSummary();
            googleCloudFileStorageObjectSummary.setBucketName(blob.getBucket());
            googleCloudFileStorageObjectSummary.setObjectName(blob.getName());
            googleCloudFileStorageObjectSummary.seteTag(blob.getEtag());
            googleCloudFileStorageObjectSummary.setSize(blob.getSize());

            googleCloudFileStorageObjectSummaries.add(googleCloudFileStorageObjectSummary);

        }
        return googleCloudFileStorageObjectSummaries;
    }

    /**
     * Uploads a new file object to the specified cloud storage bucket of a project. You Must have ADMIN permissions on bucket to add an object to it.
     *
     * @param objectName                is provided by the user for the uploaded file
     * @param fileInputStream           InputStream of the file to be uploaded
     * @param contentType               ContentType of the file to be uploaded
     * @return {@link Boolean}
     * @throws GoogleObjectUploadException                     is thrown when there is an error in uploading the file to cloud
     * @throws ServiceAccountCredentialsCreationException      is thrown when there is an error in creating the credentials from file
     * @throws ServiceAccountCredentialsFileNotFoundException  is thrown when there is an error in reading the file
     */
    @Override
    public Boolean uploadObject(String objectName, InputStream fileInputStream, String contentType){
        logger.info("Uploading the file to cloud storage");

        BlobId blobId = BlobId.of(getGoogleCloudFileStoragePropertiesService().getBucketName(), objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
        try {
            getStorage(getFileUsingClassPathResource(getGoogleCloudFileStoragePropertiesService().getGoogleServiceAccountFileName())).createFrom(blobInfo, fileInputStream);
        }
        catch (IOException e){
            logger.error(e.getMessage());
            throw new GoogleObjectUploadException(e.getMessage(), e);

        } catch (ServiceAccountCredentialsFileNotFoundException e) {
            throw new ServiceAccountCredentialsFileNotFoundException(e.getMessage(),e);

        } catch (ServiceAccountCredentialsCreationException e) {
            throw new ServiceAccountCredentialsCreationException(e.getMessage(), e);
        }

        logger.info("File uploaded to bucket " + getGoogleCloudFileStoragePropertiesService().getBucketName() + " as " + objectName);
        return true;
    }

    /**
     * Gets the object from cloud storage under the specified bucket of a project. To download the object user must have a proper service account file
     *
     * @param objectName                        is the full path of the object to be downloaded
     * @return {@link OutputStream}             is downloaded in the form of output stream
     * @throws GoogleObjectDownloadException    is thrown when there is an error in downloading the file from the cloud
     * @throws ServiceAccountCredentialsCreationException      is thrown when there is an error in creating the credentials from file
     * @throws ServiceAccountCredentialsFileNotFoundException  is thrown when there is an error in reading the file
     */
    @Override
    public OutputStream downloadObject(String objectName){
        logger.info("Downloading the file from cloud storage");

        OutputStream bos = new ByteArrayOutputStream();
        Blob blob = null;
        try {
            blob = getStorage(getFileUsingClassPathResource(getGoogleCloudFileStoragePropertiesService().getGoogleServiceAccountFileName())).get(BlobId.of(getGoogleCloudFileStoragePropertiesService().getBucketName(), objectName));
        }
        catch (ServiceAccountCredentialsFileNotFoundException e) {
            throw new ServiceAccountCredentialsFileNotFoundException(e.getMessage(),e);

        } catch (ServiceAccountCredentialsCreationException e) {
            throw new ServiceAccountCredentialsCreationException(e.getMessage(), e);
        }

        if(blob == null)
            throw new GoogleObjectDownloadException("Error in downloading the file from cloud storage");

        blob.downloadTo(bos);

        logger.info("Downloaded the object from Google cloud storage");
        return bos;
    }

    /**
     * Deletes the object from cloud storage under the specified bucket of project.
     *
     * @param objectName                is the full path of the file to be deleted from the cloud
     * @return {@link Boolean}
     * @throws ServiceAccountCredentialsCreationException      is thrown when there is an error in creating the credentials from file
     * @throws ServiceAccountCredentialsFileNotFoundException  is thrown when there is an error in reading the file
     */
    @Override
    public Boolean deleteObject(String objectName){
        logger.info("Deleting file from cloud storage");
        try {
            return getStorage(getFileUsingClassPathResource(getGoogleCloudFileStoragePropertiesService().getGoogleServiceAccountFileName())).delete(getGoogleCloudFileStoragePropertiesService().getBucketName(), objectName);

        } catch (ServiceAccountCredentialsFileNotFoundException e) {
            throw new ServiceAccountCredentialsFileNotFoundException(e.getMessage(),e);

        } catch (ServiceAccountCredentialsCreationException e) {
            throw new ServiceAccountCredentialsCreationException(e.getMessage(), e);
        }
    }

    private File getFileUsingClassPathResource(String fileName) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            return new File(classLoader.getResource(fileName).getFile());
        } catch (NullPointerException e) {
            return null;
        }
    }

}