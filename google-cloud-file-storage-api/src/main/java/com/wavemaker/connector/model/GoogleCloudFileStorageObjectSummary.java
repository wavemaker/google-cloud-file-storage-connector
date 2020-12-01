package com.wavemaker.connector.model;

/**
 * @author <a href="mailto:karthik.peerlagudem@wavemaker.com">Karthik Peerlagudem</a>
 * @since 25/11/20
 */

public class GoogleCloudFileStorageObjectSummary {

    protected String bucketName;
    protected String objectName;
    protected String eTag;
    protected long size;
    protected String downloadableURL;

    public String getDownloadableURL() {
        return downloadableURL;
    }

    public void setDownloadableURL(String downloadableURL) {
        this.downloadableURL = downloadableURL;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String toString() {
        return "GoogleCloudFileStorageObjectSummary{bucketName='" + this.bucketName + '\'' + ", objectName='" + this.objectName + '\'' + ", eTag='" + this.eTag + '\'' + ", size=" + this.size + ", downloadableURL=" + this.downloadableURL+ '}';
    }
}
