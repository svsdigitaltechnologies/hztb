package com.svs.hztb.awsclient.config;

import org.springframework.stereotype.Component;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * This class is a Configuration Provider class which provides the s3 client
 * object using the credentials
 *
 * @author skairamkonda
 */
@Component
public class AWSConfigurationProvider {
	private AmazonS3 amazonS3Client;

	/**
	 * Constructor
	 */
	public AWSConfigurationProvider() {
		amazonS3Client = new AmazonS3Client(new InstanceProfileCredentialsProvider());
	}

	public AmazonS3 getAmazonS3Client() {
		return amazonS3Client;
	}
}
