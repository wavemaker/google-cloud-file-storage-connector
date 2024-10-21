package com.wavemaker.connector.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.Nonnull;

/**
 * @author <a href="mailto:karthik.peerlagudem@wavemaker.com">Karthik Peerlagudem</a>
 * @since 25/11/20
 */

@Service
public class GoogleCloudFileStoragePropertiesService {

    @Nonnull
    @Value("${google.api.url}")
    private String googleApi;

    @Nonnull
    @Value("${google.project.id}")
    private String projectId;

    @Nonnull
    @Value("${google.bucket.name}")
    private String bucketName;

    @Value("${google.service.account.file.name}")
    private String googleServiceAccountFileName;

    public String getGoogleServiceAccountFileName() {
        return googleServiceAccountFileName;
    }

    public GoogleCloudFileStoragePropertiesService setGoogleServiceAccountFileName(String googleServiceAccountFileName) {
        this.googleServiceAccountFileName = googleServiceAccountFileName;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public GoogleCloudFileStoragePropertiesService setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getBucketName() {
        return bucketName;
    }

    public GoogleCloudFileStoragePropertiesService setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public String getGoogleApi() {
        return googleApi;
    }

    public GoogleCloudFileStoragePropertiesService setGoogleApi(String googleApi) {
        this.googleApi = googleApi;
        return this;
    }
}
