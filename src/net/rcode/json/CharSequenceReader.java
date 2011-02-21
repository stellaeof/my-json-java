package net.rcode.json;

import java.io.IOException;
import java.io.Reader;

public class CharSequenceReader extends Reader {
	private int position;
	private CharSequence source;
	
	public CharSequenceReader(CharSequence source) {
		this.source=source;
	}
	
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int read() throws IOException {
		// TODO Auto-generated method stub
		return super.read();
	}
	
	@Override
	public int read(char[] cbuf) throws IOException {
		// TODO Auto-generated method stub
		return super.read(cbuf);
	}
	
	@Override
	public void close() throws IOException {
	}
}
