package net.rcode.json;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Simple JSON expression.  The expression is just a sequence of
 * names separated by periods to be evaluated.  Maybe extend it later
 * to be a little more.
 * 
 * @author stella
 *
 */
public class JSONExpression {
	private static final Pattern SEP_PATTERN=Pattern.compile("\\.");
	
	private List<String> components;
	
	public JSONExpression(String expression) {
		this(parse(expression));
	}
	
	public JSONExpression(List<String> components) {
		this.components=components;
	}
	
	public static List<String> parse(String expression) {
		return Arrays.asList(SEP_PATTERN.split(expression));
	}
	
	public int length() {
		return components.size();
	}
	
	public String at(int i) {
		return components.get(i);
	}
}
