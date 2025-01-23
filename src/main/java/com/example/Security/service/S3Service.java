//package com.example.Security.service;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.*;
//import com.amazonaws.AmazonServiceException;
//import com.example.Security.config.S3Config;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@Service
//public class S3Service {
//
//    @Value("${aws.s3.bucketName}")
//    private String bucketName;
//
//    private final AmazonS3 amazonS3;
//
//    public S3Service(AmazonS3 amazonS3) {
//        this.amazonS3 = amazonS3;
//    }
//
//    public String uploadFile(MultipartFile file) throws IOException {
//        try {
//            String fileName = file.getOriginalFilename();
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentLength(file.getSize());
//
//            // Upload the file to S3
//            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
//
//            return fileName;
//        } catch (AmazonServiceException e) {
//            throw new IOException("Error uploading file to S3", e);
//        }
//    }
//
//    public S3Object getFile(String fileName) {
//        return amazonS3.getObject(new GetObjectRequest(bucketName, fileName));
//    }
//
//    public void deleteFile(String fileName) {
//        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
//    }
//}
package com.example.Security.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class S3Service {

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    private final AmazonS3 amazonS3;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    /**
     * Generate a presigned URL for uploading an image.
     *
     * @param accountName The name of the account.
     * @param accountId   The ID of the account.
     * @param subFolder   The subfolder name within the account folder.
     * @param fileName    The original file name.
     * @return The presigned URL and the unique object key.
     */
    public PresignedUrlResponse generateUploadUrl(String accountName, String accountId, String subFolder, String fileName) {
        // Build folder path: accountName+accountId/subFolder/
        String folderPath = accountName + accountId + "/" + subFolder + "/";
        String uniqueFileName = UUID.randomUUID().toString() + "-" + fileName; // Append UUID to file name
        String objectKey = folderPath + uniqueFileName;

        // Generate presigned URL for PUT
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000); // 1 hour expiration
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectKey)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        URL uploadUrl = amazonS3.generatePresignedUrl(request);

        return new PresignedUrlResponse(uploadUrl.toString(), objectKey);
    }

    /**
     * Generate a presigned URL for downloading an image.
     *
     * @param objectKey The unique object key.
     * @return The presigned URL for downloading.
     */
    public String generateDownloadUrl(String objectKey) {
        // Generate presigned URL for GET
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000); // 1 hour expiration
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectKey)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);

        URL downloadUrl = amazonS3.generatePresignedUrl(request);
        return downloadUrl.toString();
    }

    /**
     * DTO for returning presigned URL response.
     */
    public static class PresignedUrlResponse {
        private String presignedUrl;
        private String objectKey;

        public PresignedUrlResponse(String presignedUrl, String objectKey) {
            this.presignedUrl = presignedUrl;
            this.objectKey = objectKey;
        }

        public String getPresignedUrl() {
            return presignedUrl;
        }

        public String getObjectKey() {
            return objectKey;
        }
    }
}
