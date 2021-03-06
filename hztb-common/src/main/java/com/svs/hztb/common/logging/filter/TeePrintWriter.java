package com.svs.hztb.common.logging.filter;

import java.io.PrintWriter;

/**
 * 
 * 
 * @author skairamkonda
 *
 */
public class TeePrintWriter extends PrintWriter {

	PrintWriter branch;

	/**
	 * 
	 * @param writer
	 * @param branch
	 */
	public TeePrintWriter(PrintWriter writer, PrintWriter branch) {
		super(writer, true);
		this.branch = branch;
	}

	@Override
	public void write(int c) {
		super.write(c);
		super.flush();
		branch.write(c);
		branch.flush();
	}

	@Override
	public void write(char[] buf, int off, int len) {
		super.write(buf, off, len);
		super.flush();
		branch.write(buf, off, len);
		branch.flush();
	}

	@Override
	public void write(String s, int off, int len) {
		super.write(s, off, len);
		super.flush();
		branch.write(s, off, len);
		branch.flush();
	}

	@Override
	public void flush() {
		super.flush();
		branch.flush();
	}
}
