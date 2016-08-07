package com.svs.hztb.common.logging.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

/**
 * BufferedServlertInputStream
 * 
 * @author skairamkonda
 *
 */
public class BufferedServlertInputStream extends ServletInputStream {

	private ByteArrayInputStream bais;

	/**
	 * 
	 * @param bais
	 */
	public BufferedServlertInputStream(ByteArrayInputStream bais) {
		this.bais = bais;
	}

	@Override
	public int available() throws IOException {
		return this.bais.available();
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setReadListener(ReadListener readListener) {
		// do nothing
	}

	@Override
	public int read() throws IOException {
		return this.bais.read();
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return this.bais.read(b, off, len);
	}

}
