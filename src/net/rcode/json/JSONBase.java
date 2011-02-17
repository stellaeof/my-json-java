package net.rcode.json;

/**
 * Common base class for JSON objects.  Provides primitives for querying
 * an object tree.
 * 
 * @author stella
 *
 */
public abstract class JSONBase {
	/**
	 * Performs a cast from a source object to a target type.
	 * Here are the rules.  If from is null or undefined, null
	 * is returned.
	 * @param <T>
	 * @param from
	 * @param to
	 * @return the cast value or null if the cast could not be performed
	 */
	@SuppressWarnings("unchecked")
	public static <T> T cast(Object from, Class<T> to) {
		try {
			if (from==null || from==JSONObject.NULL) return null;
			if (to==String.class) {
				return (T)from.toString();
			} else if (to==Integer.class) {
				if (from instanceof Number) return (T)Integer.valueOf(((Number)from).intValue());
				return (T)Integer.valueOf(Integer.parseInt(from.toString()));
			} else if (to==Long.class) {
				if (from instanceof Number) return (T)Long.valueOf(((Number)from).longValue());
				return (T)Long.valueOf(Long.parseLong(from.toString()));
			} else if (to==Double.class) {
				if (from instanceof Number) return (T)Double.valueOf(((Number)from).doubleValue());
				return (T)Double.valueOf(Double.parseDouble(from.toString()));
			} else if (to==Boolean.class) {
				if (from instanceof Boolean) return (T)from;
				if (from instanceof String) {
					if ("true".equalsIgnoreCase((String)from)) return (T)Boolean.TRUE;
					if ("false".equalsIgnoreCase((String)from)) return (T)Boolean.FALSE;
				}
				return null;
			} else {
				return null;
			}
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * Lookup a property by name.  For objects, this just looks up the
	 * field with the given name.  For arrays, the special member "length"
	 * is supported and returns an Integer length.  Array indices are
	 * interpreted as well.
	 * 
	 * @param name
	 * @return the member or null if not defined
	 */
	public abstract Object lookupProperty(String name);
	
	/**
	 * Looks up a property of the given type
	 * @param <T>
	 * @param name
	 * @param ofType
	 * @return the value or null if doesn't exist
	 */
	public <T> T lookupProperty(String name, Class<T> ofType) {
		return cast(lookupProperty(name), ofType);
	}
	
	/**
	 * Looks up a property of a type inferred by a non-null default
	 * value
	 * @param <T>
	 * @param name
	 * @param defaultValue
	 * @return property or defaultValue
	 */
	@SuppressWarnings("unchecked")
	public <T> T lookupProperty(String name, T defaultValue) {
		if (defaultValue==null) {
			return (T)lookupProperty(name);
		} else {
			T ret=(T)lookupProperty(name, defaultValue.getClass());
			if (ret==null) return defaultValue;
			else return ret;
		}
	}
	
	/**
	 * Parse a path and evaluate it.
	 * @param expression
	 * @return null if any part of expression is not found.  otherwise the result
	 * of the last part of the expression
	 */
	public Object lookup(String expressionString) {
		JSONExpression expr=new JSONExpression(expressionString);
		return lookup(expr);
	}

	/**
	 * Evaluate a path against a pre-parsed expression
	 * @param expr
	 * @return result or null if any part is not found
	 */
	public Object lookup(JSONExpression expr) {
		Object context=this;
		for (int i=0; i<expr.length(); i++) {
			String component=expr.at(i);
			if (context instanceof JSONBase) {
				JSONBase json=(JSONBase)context;
				context=json.lookupProperty(component);
				if (context==null) return null;
			} else {
				// Not an indexable - punt
				return null;
			}
		}
		
		return context;
	}
	
	/**
	 * Lookup and do a type cast
	 * @param <T>
	 * @param expr
	 * @param ofType
	 * @return cast lookup value
	 */
	public <T> T lookup(JSONExpression expr, Class<T> ofType) {
		return cast(lookup(expr), ofType);
	}
	
	public <T> T lookup(String expr, Class<T> ofType) {
		return lookup(new JSONExpression(expr), ofType);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T lookup(JSONExpression expr, T defaultValue) {
		if (defaultValue==null) {
			return (T)lookup(expr);
		} else {
			T ret=(T)lookup(expr, defaultValue.getClass());
			if (ret==null) return defaultValue;
			else return ret;
		}
	}
	
	public <T> T lookup(String expr, T defaultValue) {
		return lookup(new JSONExpression(expr), defaultValue);
	}
}
