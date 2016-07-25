package com.svs.hztb.aws.client;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.svs.hztb.awsclient.config.AWSConfigurationProvider;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;

@Component
public class AWSClientProcessor {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(AWSClientProcessor.class);

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

	public String execute(byte[] bytes, String type, String name) {

		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentType("image/jpeg");
		omd.setContentLength(bytes.length);

		String fileName = null;

		if ("Profile".equals(type)) {
			fileName = profileBucketName + SLASH + name + ".jpg";
		} else if ("Product".equals(type)) {
			fileName = productBucketName + SLASH + name + ".jpg";
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
