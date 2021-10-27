package com.djv.bodesafio.djvbox.s3.service.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

@Service
public class S3ServicesImplement implements S3Services {

	private Logger logger = LoggerFactory.getLogger(S3ServicesImplement.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${amazonProperties.bucketname}")
	private String bucketName;

	@Override
	public ByteArrayOutputStream downloadFile(String keyName) {
		try {
			S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));

			InputStream is = s3object.getObjectContent();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int len;
			byte[] buffer = new byte[4096];
			while ((len = is.read(buffer, 0, buffer.length)) != -1) {
				baos.write(buffer, 0, len);
			}

			return baos;
		} catch (IOException ioe) {
			logger.error("IOException: " + ioe.getMessage());
		} catch (AmazonServiceException ase) {
			logger.info("sCaught an AmazonServiceException from GET requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
			throw ase;
		} catch (AmazonClientException ace) {
			logger.info("Caught an AmazonClientException: ");
			logger.info("Error Message: " + ace.getMessage());
			throw ace;
		}

		return null;
	}

	@Override
	public String uploadFile(MultipartFile file) throws IOException {
		try {
			String key = generateRandomString() + "." + generateFileType(file.getOriginalFilename());
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.getSize());
			s3client.putObject(bucketName, key, file.getInputStream(), metadata);
			return key;
		} catch (IOException ioe) {
			logger.error("IOException: " + ioe.getMessage());
			throw ioe;
		} catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
			throw ase;
		} catch (AmazonClientException ace) {
			logger.info("Caught an AmazonClientException: ");
			logger.info("Error Message: " + ace.getMessage());
			throw ace;
		}
	}

	private String generateFileType(String string) {
		String[] arr = string.split("\\.");
		String type = arr[arr.length - 1];
		return type;
	}

	private String generateRandomString() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		return random.ints(leftLimit, rightLimit + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

	}

	@Override
	public void deleteFile(String file) {
		s3client.deleteObject(new DeleteObjectRequest(bucketName, file));

	}
}