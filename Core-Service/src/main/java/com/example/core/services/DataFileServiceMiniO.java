package com.example.core.services;

import com.example.core.entities.Animal;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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
    public Object getObject(String value) {
        return null;
    }
}
