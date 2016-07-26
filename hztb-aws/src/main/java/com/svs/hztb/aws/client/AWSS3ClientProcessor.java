package com.svs.hztb.aws.client;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.svs.hztb.awsclient.config.AWSConfigurationProvider;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;

/**
 * This class is a Processor class for S3 put objects implementation for user
 * related methods
 * 
 * @author skairamkonda
 */
@Component
public class AWSS3ClientProcessor {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(AWSS3ClientProcessor.class);

	@Autowired
	private AWSConfigurationProvider awsConfigurationProvider;

	@Value("${aws.profile.bucketname}")
	private String profileBucketName;

	@Value("${aws.product.bucketname}")
	private String productBucketName;

	@Value("${aws.hztb.bucketname}")
	private String hztbBucketName;

	@Value("${aws.s3url}")
	private String awss3Url;

	private static final String SLASH = "/";

	/**
	 * This method is used to put and object onto S3.
	 * 
	 * @param objectBytes
	 * @param notificationType
	 * @param objectName
	 * @param requestId
	 * @return String
	 * 
	 */
	public String putObject(byte[] objectBytes, String notificationType, String objectName, String requestId) {
		LOGGER.debug("AWSS3ClientProcessor.putObject request start {}", requestId);
		ByteArrayInputStream bis = new ByteArrayInputStream(objectBytes);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentType("image/jpeg");
		omd.setContentLength(objectBytes.length);

		String fileName = null;

		if ("Profile".equals(notificationType)) {
			fileName = profileBucketName + SLASH + objectName + ".jpg";
		} else if ("Product".equals(notificationType)) {
			fileName = productBucketName + SLASH + objectName + ".jpg";
		}
		LOGGER.debug("S3 put request start {}", fileName);
		/*
		 * awsConfigurationProvider.getAmazonS3Client().putObject(new
		 * PutObjectRequest(hztbBucketName, fileName, bis, omd)
		 * .withCannedAcl(CannedAccessControlList.PublicRead));
		 */

		return awss3Url + SLASH + hztbBucketName + SLASH + fileName;
	}
}
