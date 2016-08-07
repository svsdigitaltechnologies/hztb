package com.svs.hztb.aws.client;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
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

	@Value("${aws.selfie.bucketname}")
	private String selfieBucketName;

	@Value("${aws.hztb.bucketname}")
	private String hztbBucketName;

	@Value("${aws.s3url}")
	private String awss3Url;

	private static final String SLASH = "/";

	final String URL = "URL";
	final String FILENAME = "FILENAME";

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
	@Async
	public void putObject(byte[] objectBytes, String fileName, String requestId) {
		LOGGER.debug("AWSS3ClientProcessor.putObject request start {}", requestId);
		ByteArrayInputStream bis = new ByteArrayInputStream(objectBytes);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentType("image/jpeg");
		omd.setContentLength(objectBytes.length);

		/*
		 * awsConfigurationProvider.getAmazonS3Client().putObject(new
		 * PutObjectRequest(hztbBucketName, fileName, bis, omd)
		 * .withCannedAcl(CannedAccessControlList.PublicRead));
		 */

		LOGGER.debug("S3 put request completed with fileName: {}", fileName);

	}

	public Map<String, String> prepareFileName(String notificationType, String objectName, String requestId) {
		Map<String, String> map = new HashMap<>();
		LOGGER.debug("AWSS3ClientProcessor.prepareFileName request start {}", requestId);

		String fileName = null;

		if ("Profile".equals(notificationType)) {
			fileName = profileBucketName + SLASH + objectName + ".jpg";
			map.put(URL, "http://static.dnaindia.com/sites/default/files/2015/06/05/343491-hrithik-hi-res-2.jpg");

		} else if ("Product".equals(notificationType)) {
			fileName = productBucketName + SLASH + objectName + ".jpg";

		} else if ("Selfie".equals(notificationType)) {
			fileName = selfieBucketName + SLASH + objectName + ".jpg";
			map.put(URL, "http://diabetesdad.org/files/2014/11/Rockstar.jpg");

		}

		String url = awss3Url + SLASH + hztbBucketName + SLASH + fileName;
		// map.put(URL, url);

		map.put(FILENAME, fileName);
		LOGGER.debug("S3 prepareFileName request completed with fileName: {}", fileName);
		LOGGER.debug("S3 prepareFileName request completed with URL: {}", url);

		return map;
	}

}
