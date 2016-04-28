package com.svs.hztb.awsclient.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

@Component
public class AWSConfigurationProvider {
	private Environment env;
	private AWSCredentials awsCredentials;
	private AmazonS3 amazonS3Client;

	@Value("${aws.accesskey}")
	private String awsAccessKeyId;

	@Value("${aws.secretkey}")
	private String awsSecretKey;

	@Autowired
	public AWSConfigurationProvider(Environment env) {
		this.env = env;
		awsCredentials = new AWSCredentials() {

			@Override
			public String getAWSSecretKey() {
				return awsSecretKey;
			}

			@Override
			public String getAWSAccessKeyId() {
				return awsAccessKeyId;
			}
		};
		amazonS3Client = new AmazonS3Client(awsCredentials);
	}

	public AmazonS3 getAmazonS3Client() {
		return amazonS3Client;
	}
	
	
}
