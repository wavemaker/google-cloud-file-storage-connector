package com.wavemaker.connector.googlecloudfilestorage;

import com.wavemaker.connector.exception.*;
import com.wavemaker.connector.model.GoogleCloudFileStorageObjectSummary;
import com.wavemaker.runtime.connector.annotation.WMConnector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


@WMConnector(name = "googlecloudfilestorage",
        description = "A simple google cloud file storage connector that can be used in Wavemaker application")
public interface GoogleCloudFileStorageConnector {

    /**
     * Api to list out the objects in a bucket
     *
     * @param folderPath            is the directory path to the file
     * @param retrieveAll           is enabled to fetch the list all files in a bucket
     * @return: {@link List<GoogleCloudFileStorageObjectSummary> object summary} A listing of the objects in the specified bucket of a project, along with any other associated information
     * @throws ServiceAccountCredentialsFileNotFoundException   is thrown when there is an error in reading the file
     * @throws ServiceAccountCredentialsCreationException       is thrown when there is an error in creating the credentials
     * @throws GoogleObjectsNotFoundException                   is thrown when is there is an error in retrieving the list of objects
     */
    public List<GoogleCloudFileStorageObjectSummary> listObjects(String folderPath, Boolean retrieveAll);

    /**
     * Api to upload an object to a bucket
     *
     * @param objectName                is the name of the file to be uploaded
     * @param fileInputStream           InputStream of the file to be uploaded
     * @param contentType               ContentType of the file to be uploaded
     * @return {@link Boolean}
     * @throws GoogleObjectUploadException                     is thrown when there is an error in uploading the file to cloud
     * @throws ServiceAccountCredentialsCreationException      is thrown when there is an error in creating the credentials from file
     * @throws ServiceAccountCredentialsFileNotFoundException  is thrown when there is an error in reading the file
     */
    public Boolean uploadObject(String objectName, InputStream fileInputStream, String contentType);

    /**
     * Api to download an object from the bucket
     *
     * @param objectName                                        is full path of the object to be downloaded from the cloud
     * @return {@link OutputStream}                             is downloaded in the form of OutputStream
     * @throws GoogleObjectDownloadException                    is thrown when there is an error in downloading the file from the cloud
     * @throws ServiceAccountCredentialsCreationException       is thrown when there is an error in creating the credentials from file
     * @throws ServiceAccountCredentialsFileNotFoundException   is thrown when there is an error in reading the file
     */

    public OutputStream downloadObject(String objectName);

    /**
     * Api to delete an object in a bucket
     *
     * @param objectName                                       is full path of the file to be deleted from the cloud
     * @return {@link Boolean}                                 returns true if the delete is successful if not false
     * @throws ServiceAccountCredentialsCreationException      is thrown when there is an error in creating the credentials from file
     * @throws ServiceAccountCredentialsFileNotFoundException  is thrown when there is an error in reading the file
     */
    public Boolean deleteObject(String objectName);
}