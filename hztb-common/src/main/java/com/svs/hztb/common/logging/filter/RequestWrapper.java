package com.svs.hztb.common.logging.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

public class RequestWrapper extends HttpServletRequestWrapper {

	private ByteArrayInputStream bais = null;
	private ByteArrayOutputStream baos = null;
	private BufferedServlertInputStream bsis = null;
	private byte[] buffer = null;
	
	public RequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		InputStream is = request.getInputStream();
		this.baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int letti;
		while((letti = is.read(buf)) > 0 ){
			this.baos.write(buf, 0, letti);
		}
		this.buffer = this.baos.toByteArray();
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		this.bais = new ByteArrayInputStream(this.buffer);
		this.bsis = new BufferedServlertInputStream(this.bais);
		return this.bsis;
	}

	public String getContents() throws IOException {
		return IOUtils.toString(getInputStream(), getCharacterEncoding());
	}
}
