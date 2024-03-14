package com.example.core.services;

import com.example.core.entities.Animal;
import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service(value = "DataFileServiceMiniO")
public class DataFileServiceMiniO implements DataFileService <Animal>{

    @Value("${miniO.container}")
    private String bucket;

    @Autowired
    private final MinioClient minioClient;

    @PostConstruct
    private void init(){
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs
                    .builder()
                    .bucket(bucket)
                    .build());
            if (!found) {
                // Make a new bucket called 'bucket'.
                minioClient.makeBucket(MakeBucketArgs
                        .builder()
                        .bucket(bucket)
                        .build());
            } else {
                System.out.println("Bucket " + bucket + " already exists.");
            }
        }catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e){
            System.out.println("Error : " + e);
        }
    }

    @Override
    public boolean putObject(Animal animal) {
        try {
            // Upload unknown sized input stream.
//            minioClient.putObject(PutObjectArgs.builder()
//                    .bucket("my-bucket name")
//                    .object("myobject name")
//                    .stream(inputStream, -1, 10485760)
//                    .contentType("video/mp4")
//                    .build());
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucket)
                            .object(animal.getNickName())
                            .filename("Core-Service/files/" + animal.getViewAnimal() + ".jpg" )
                            .build());
            System.out.println( "'files/ " + animal.getNickName() + " is successfully uploaded as " + "object " + animal.getNickName() + " to - bucket 'bucket'.");
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            System.out.println("Error occurred: " + e);
            return false;
        }
        return true;
    }

    @Override
    public String getUrlObject(Animal animal) {
        // Get presigned URL string to upload 'my-objectname' in 'my-bucketname' // with an expiration of 1 day.
        String url = null;
        try {
            url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucket)
                            .object(animal.getNickName())
                            .expiry(1, TimeUnit.DAYS)
                            .build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(url);
        return url;
    }
}
