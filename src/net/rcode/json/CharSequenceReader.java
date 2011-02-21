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
	public void close() throws IOException {
	}

	@Override
	public int read(char[] dest, int index, int len) throws IOException {
		int remaining=source.length()-position;
		if (remaining<=0) return -1;
		if (remaining>len) remaining=len;
		
		for (int i=0; i<remaining; i++) {
			dest[index+i]=source.charAt(position+i);
		}
		
		position+=remaining;
		return remaining;
	}

	@Override
	public int read(char[] buf) throws IOException {
		return read(buf, 0, buf.length);
	}
	
	@Override
	public int read() throws IOException {
		if (position<source.length()) return source.charAt(position++);
		else return -1;
	}}
