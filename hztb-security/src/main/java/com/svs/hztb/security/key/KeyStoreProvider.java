package com.svs.hztb.security.key;

import java.security.KeyStore;

public interface KeyStoreProvider {
	KeyStore getDefaultTrustStore();
}
