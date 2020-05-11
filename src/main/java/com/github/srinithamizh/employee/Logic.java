package com.github.srinithamizh.employee;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.storagegateway.model.InternalServerErrorException;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.srinithamizh.employee.model.Employee;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
public class Logic {
    private String accessKey = "Your Access Key";
    private String secretKey = "Your Secret Key";

    AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
    AmazonS3 s3Client = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_EAST_1)
            .build();

    public void saveEmployee(String bucketName, String employeeId, byte[] object)  {
        try{
            InputStream inputStream = new ByteArrayInputStream(object);
            ObjectMetadata objectMetaData = new ObjectMetadata();
            objectMetaData.setContentLength(object.length);
            objectMetaData.setContentType("application/json");
            s3Client.putObject(bucketName, employeeId, inputStream, objectMetaData);
        }catch(Exception ex){
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    public  byte[] getEmployeeById(String bucketName, String employeeId){
        try(S3Object object = s3Client.getObject(bucketName,employeeId)){
            try(S3ObjectInputStream objectInputStream  = object.getObjectContent()){
                return IOUtils.toByteArray(objectInputStream);
            }
        }catch (AmazonS3Exception ex){
            return null;
        }catch (Exception ex){
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    public static byte[] toByteArray(Employee employee) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsBytes(employee);
        }catch (Exception ex){
            throw new InternalServerErrorException("Couldn't convert the Employee to ByteArray");
        }
    }
}
