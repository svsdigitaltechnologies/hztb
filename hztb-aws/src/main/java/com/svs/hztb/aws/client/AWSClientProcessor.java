package com.svs.hztb.aws.client;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.svs.hztb.awsclient.config.AWSConfigurationProvider;

@Component
public class AWSClientProcessor {

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

	private final String SLASH = "/";

	public String execute(byte[] bytes, String type, String name) {

		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentType("image/jpeg");
		omd.setContentLength(bytes.length);

		String fileName = null;

		switch (type) {
		case "Profile":
			fileName = profileBucketName + SLASH + name + ".jpg";
			break;
		case "Product":
			fileName = productBucketName + SLASH + name + ".jpg";
			break;
		}
		awsConfigurationProvider.getAmazonS3Client().putObject(new PutObjectRequest(hztbBucketName, fileName, bis, omd)
				.withCannedAcl(CannedAccessControlList.PublicRead));

		return awss3Url + SLASH + hztbBucketName + SLASH + fileName;
	}
}
