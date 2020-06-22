package com.boc.accuratetest.demo;
import java.io.*;

public class MyReaderLineNumberDemo {
	public static void main(String[] args) {
		MyReaderForLineNumber mrln = null;
		try {
			mrln = new MyReaderForLineNumber("MyReaderLineNumberDemo.java");

			String line = null;
			mrln.setStartLineNumber(1);
			mrln.setEndLineNumber(-1);
			while ((line = mrln.readLine()) != null) {
				System.out.println(mrln.getLineNumber() + ":" + line);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (null != mrln)
					mrln.close();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}
}

class MyBufferedReader {
	private FileReader fileReader = null;

	public MyBufferedReader(String fileName) throws IOException {
		this.fileReader = new FileReader(new File(fileName));
	}

	public String readLine() throws IOException {
		StringBuilder sb = new StringBuilder();
		int ch = 0;
		while ((ch = fileReader.read()) != -1) {
			if ('\r' == ch)
				continue;
			if ('\n' == ch)
				return sb.toString();
			else
				sb.append((char) ch);
		}

		if (sb.length() != 0)
			return sb.toString();

		return null;
	}

	public void close() throws IOException {
		if (null != fileReader)
			this.fileReader.close();
	}
}

class MyReaderLineNumber extends MyBufferedReader {
	private int lineNumber = 0;

	public MyReaderLineNumber(String fileName) throws IOException {
		super(fileName);
	}

	public String readLine() throws IOException {
		lineNumber++;
		return super.readLine();
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public int getLineNumber() {
		return lineNumber;
	}
}

class MyReaderForLineNumber extends MyReaderLineNumber {
	private int startLineNumber = 0;
	private int endLineNumber = Integer.MAX_VALUE;

	public MyReaderForLineNumber(String fileName) throws IOException {
		super(fileName);
	}

	public String readLine() throws IOException {
		String result = null;
		while ((result = super.readLine()) != null) {
			if (super.getLineNumber() >= this.startLineNumber && this.endLineNumber >= super.getLineNumber())
				return result;
		}

		return null;
	}

	public int getStartLineNumber() {
		return this.startLineNumber;
	}

	public void setStartLineNumber(int s) {
		if (s >= 0)
			this.startLineNumber = s;
	}

	public int getEndLineNumber() {
		return this.endLineNumber;
	}

	public void setEndLineNumber(int e) {
		if (e > 0)
			this.endLineNumber = e;

	}
}