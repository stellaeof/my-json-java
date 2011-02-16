package net.rcode.json;

import junit.framework.TestCase;

public class TestExpression extends TestCase {
	private JSONObject obj1;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		obj1=new JSONObject();
		obj1.put("rootProp", "Root Property");
		JSONObject subObject=new JSONObject();
		obj1.put("sub", subObject);
		subObject.put("intValue", 1);
		subObject.put("doubleValue", 1.2);
		subObject.put("longValue", 12l);
		subObject.put("stringValue", "some string");
		subObject.put("trueString", "true");
		subObject.put("falseString", "false");
		subObject.put("trueLit", true);
		subObject.put("falseLit", false);
		
		JSONArray ary=new JSONArray();
		obj1.put("ary", ary);
		ary.put(true);
		ary.put(1);
	}
	
	public void testLookup() {
		assertEquals("Root Property", obj1.lookup("rootProp"));
		assertTrue(obj1.lookup("sub") instanceof JSONObject);
		assertEquals(1, obj1.lookup("sub.intValue"));
		assertEquals(1.2, obj1.lookup("sub.doubleValue"));
		assertEquals(12l, obj1.lookup("sub.longValue"));
		assertEquals("some string", obj1.lookup("sub.stringValue"));
		assertEquals("true", obj1.lookup("sub.trueString"));
		assertEquals("false", obj1.lookup("sub.falseString"));
		assertEquals(true, obj1.lookup("sub.trueLit"));
		assertEquals(false, obj1.lookup("sub.falseLit"));
		assertNull(obj1.lookup("sub.nonexisting"));
		
		assertEquals(2, obj1.lookup("ary.length"));
		assertEquals(true, obj1.lookup("ary.0"));
		assertEquals(1, obj1.lookup("ary.1"));
		assertNull(obj1.lookup("ary.2"));
	}

	public void testLookupCast() {
		assertEquals("Root Property", obj1.lookup("rootProp", "default"));
		assertEquals(Integer.valueOf(12), obj1.lookup("sub.doesntexist",  12));
		assertEquals(Long.valueOf(12), obj1.lookup("sub.doesntexist",  12l));
		assertEquals(Double.valueOf(12), obj1.lookup("sub.doesntexist",  12.0));
		assertEquals("12", obj1.lookup("sub.longValue",  "12"));
		assertEquals(Boolean.TRUE, obj1.lookup("sub.doesntexist",  true));
		assertEquals(Boolean.FALSE, obj1.lookup("sub.doesntexist",  false));
	}

}
