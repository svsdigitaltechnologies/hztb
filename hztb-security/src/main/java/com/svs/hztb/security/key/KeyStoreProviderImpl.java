package com.svs.hztb.security.key;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class KeyStoreProviderImpl implements KeyStoreProvider {
	private KeyStore trustStore;
	
	@PostConstruct
	public void init() {
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, "changeit".toCharArray());
		} catch(KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
			System.out.println("Exception occured");
		}
	}

	@Override
	public KeyStore getDefaultTrustStore() {
		return trustStore;
	}
}
