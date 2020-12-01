package com.wavemaker.connector.googlecloudfilestorage;

import com.wavemaker.connector.configuration.GoogleCloudFileStorageConnectorTestConfiguration;
import com.wavemaker.connector.exception.*;
import com.wavemaker.connector.model.GoogleCloudFileStorageObjectSummary;
import com.wavemaker.connector.properties.GoogleCloudFileStoragePropertiesService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = GoogleCloudFileStorageConnectorTestConfiguration.class)
public class GoogleCloudFileStorageConnectorTest {

    @Autowired
    private GoogleCloudFileStorageConnector googleCloudFileStorageConnector;

    @Autowired
    private GoogleCloudFileStoragePropertiesService googleCloudFileStoragePropertiesService;

    private static String objectName = "sample_file";
    private static final byte[] CONTENT = "Hello, World!".getBytes(UTF_8);
    private static String contentType = "text/plain";

    private Boolean exceptionRaised;

    @Test
    public void listObjectsTest(){
        exceptionRaised = false;
        File tempFile = createDummyFile();

        try {
            List<GoogleCloudFileStorageObjectSummary> gfsObjectSummaryList = googleCloudFileStorageConnector.listObjects(null, false);
            assert tempFile != null;
            googleCloudFileStorageConnector.uploadObject(objectName,
                    Files.newInputStream(Paths.get(tempFile.getPath())),contentType);
            List<GoogleCloudFileStorageObjectSummary> gfsObjectSummaryListNew = googleCloudFileStorageConnector.listObjects(null, false);
            Assert.assertEquals((gfsObjectSummaryList.size()+1),gfsObjectSummaryListNew.size());

        }
        catch (ServiceAccountCredentialsCreationException e) {
            exceptionRaised = true;
            Assert.assertTrue(e instanceof ServiceAccountCredentialsCreationException);
        }
        catch (ServiceAccountCredentialsFileNotFoundException e) {
            exceptionRaised = true;
            Assert.assertTrue(e instanceof ServiceAccountCredentialsFileNotFoundException);
        }
        catch (GoogleObjectsNotFoundException e) {
            exceptionRaised = true;
            Assert.assertTrue(e instanceof GoogleObjectsNotFoundException);
        }
        catch (IOException e) {
            exceptionRaised = true;
            Assert.assertTrue(e instanceof IOException);
        }
        catch (GoogleObjectUploadException e) {
            exceptionRaised = true;
            Assert.assertTrue(e instanceof GoogleObjectUploadException);
        }
        finally {
            tempFile.delete();
            if(!exceptionRaised) {
                try {
                    googleCloudFileStorageConnector.deleteObject(objectName);
                } catch (ServiceAccountCredentialsCreationException | ServiceAccountCredentialsFileNotFoundException e) {
                    Assert.fail("Error in deleting the uploaded sample file");
                }
            }
        }

    }

    @Test
    public void uploadObjectTest() {
        exceptionRaised = false;
        File tempFile = createDummyFile();
        try {
            Boolean result = googleCloudFileStorageConnector.uploadObject(objectName,
                    Files.newInputStream(Paths.get(tempFile.getPath())),contentType);
            Assert.assertEquals(true, result);

        }
        catch (IOException e) {
            exceptionRaised = true;
            Assert.assertTrue(e instanceof IOException);
        }
        catch (ServiceAccountCredentialsFileNotFoundException e) {
            exceptionRaised = true;
            Assert.assertTrue(e instanceof ServiceAccountCredentialsFileNotFoundException);
            Assert.assertTrue(e.getMessage().equalsIgnoreCase("Error in reading the service account credentials"));
        }
        catch (ServiceAccountCredentialsCreationException e) {
            exceptionRaised = true;
            Assert.assertTrue(e instanceof ServiceAccountCredentialsCreationException);

        }
        catch (GoogleObjectUploadException e) {
            exceptionRaised = true;
            Assert.assertTrue(e instanceof GoogleObjectUploadException);

        }
        finally {
            tempFile.delete();
            if(!exceptionRaised) {
                try {
                    googleCloudFileStorageConnector.deleteObject(objectName);
                } catch (ServiceAccountCredentialsFileNotFoundException | ServiceAccountCredentialsCreationException e) {
                    Assert.fail("Error in deleting the uploaded sample file");
                }
            }
        }
    }

    @Test
    public void downloadObjectTest(){
        exceptionRaised = false;
        OutputStream outputStream = null;
        File tempFile = createDummyFile();

        try {
            googleCloudFileStorageConnector.uploadObject(objectName,
                    Files.newInputStream(Paths.get(tempFile.getPath())),contentType);

            outputStream = googleCloudFileStorageConnector.downloadObject(objectName);
            Assert.assertEquals("Hello, World!", outputStream.toString());

        }
        catch (IOException e) {
            exceptionRaised = true;
            Assert.assertTrue(e instanceof IOException);
        }
        catch (ServiceAccountCredentialsFileNotFoundException e) {
            exceptionRaised = true;
            Assert.assertTrue(e instanceof ServiceAccountCredentialsFileNotFoundException);
        }
        catch (ServiceAccountCredentialsCreationException e) {
            exceptionRaised = true;
            Assert.assertTrue(e instanceof ServiceAccountCredentialsCreationException);
        }
        catch (GoogleObjectDownloadException e) {
            exceptionRaised = true;
            Assert.assertTrue(e instanceof GoogleObjectDownloadException);
        }
        catch (GoogleObjectUploadException e) {
            exceptionRaised = true;
            Assert.assertTrue(e instanceof GoogleObjectUploadException);
        }
        finally {
            tempFile.delete();
            if(!exceptionRaised) {
                try {
                    googleCloudFileStorageConnector.deleteObject(objectName);
                } catch (ServiceAccountCredentialsCreationException | ServiceAccountCredentialsFileNotFoundException e) {
                    Assert.fail("Error in deleting the uploaded sample file");
                }
            }
        }

    }

    @Test
    public void deleteObjectTest(){
        exceptionRaised = false;
        File tempFile = createDummyFile();

        try {
            googleCloudFileStorageConnector.uploadObject(objectName,
                    Files.newInputStream(Paths.get(tempFile.getPath())),contentType);

            Boolean result = googleCloudFileStorageConnector.deleteObject( objectName);
            Assert.assertEquals(true, result);
        } catch (IOException e) {
            exceptionRaised = true;
            Assert.assertTrue(e instanceof IOException);
        } catch (ServiceAccountCredentialsFileNotFoundException e) {
            exceptionRaised = true;
            Assert.assertTrue(e instanceof ServiceAccountCredentialsFileNotFoundException);
        } catch (ServiceAccountCredentialsCreationException e) {
            exceptionRaised = true;
            Assert.assertTrue(e instanceof ServiceAccountCredentialsCreationException);
        } catch (GoogleObjectUploadException e) {
            exceptionRaised = true;
            Assert.assertTrue(e instanceof GoogleObjectUploadException);
        }
        finally {
            tempFile.delete();
            if(!exceptionRaised) {
                try {
                    googleCloudFileStorageConnector.deleteObject(objectName);
                } catch (ServiceAccountCredentialsCreationException | ServiceAccountCredentialsFileNotFoundException e) {
                    Assert.fail("Error in deleting the uploaded sample file");
                }
            }
        }

    }

    private File createDummyFile(){
        try {
            File tempFile = File.createTempFile("sample_file", ".txt");
            Files.write(tempFile.toPath(), CONTENT);
            return tempFile;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}