package com.example.core.config;

import io.minio.MinioClient;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${miniO.server.address}")
    String address;

    @Value("${miniO.user.login}")
    String login;

    @Value("${miniO.user.password}")
    String password;


    @Bean
    public OkHttpClient OkHttpClientFactory() {
        return new OkHttpClient();
    }

    @Bean
    public MinioClient getMiniOClient(){
        try {
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(address)
                            .credentials(login, password)
                            .httpClient(OkHttpClientFactory())
                            .build();
            return minioClient;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
