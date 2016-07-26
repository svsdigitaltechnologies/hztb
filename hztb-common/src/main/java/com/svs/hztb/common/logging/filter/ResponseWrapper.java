package com.svs.hztb.common.logging.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.io.output.TeeOutputStream;

/**
 * ResponseWrapper
 * 
 * @author skairamkonda
 *
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

	private ByteArrayOutputStream bos = new ByteArrayOutputStream();
	private PrintWriter writer = new PrintWriter(bos);

	/**
	 * ResponseWrapper constructor
	 * 
	 * @param response
	 * @throws IOException
	 */
	public ResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return new ServletOutputStream() {
			private TeeOutputStream tee = new TeeOutputStream(ResponseWrapper.super.getOutputStream(), bos);

			@Override
			public void write(int b) throws IOException {
				tee.write(b);
			}

			@Override
			public void setWriteListener(WriteListener writeListener) {
				// nothing
			}

			@Override
			public boolean isReady() {
				return false;
			}
		};
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return new TeePrintWriter(super.getWriter(), writer);
	}

	/**
	 * 
	 * @return
	 */
	public byte[] toByteArray() {
		return bos.toByteArray();
	}
}
