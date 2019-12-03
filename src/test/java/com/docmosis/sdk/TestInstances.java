package com.docmosis.sdk;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.suppliers.TestedOn;

import com.docmosis.sdk.convert.MutableConverterResponse;
import com.docmosis.sdk.render.MutableRenderResponse;
import com.docmosis.sdk.response.DocmosisCloudResponse.PreviousFailureInformation;

import junit.framework.TestCase;

public class TestInstances extends TestCase {

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	public TestInstances( String testName )
    {
        super( testName );
    }

	@Test
	public void testPOJO() {
		Class<?>[] clsses = {MutableConverterResponse.class, MutableRenderResponse.class};
		for (Class<?> c : clsses) {
			try {
				testGettersSetters(c);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail();
			}
		}
		
	}
	public void testGettersSetters(Class<?> clss) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//List<Field> privateFields = new ArrayList<Field>();
		List<Field> fields = getFields(clss);
		//List<Constructor> constructors = getConstructors(clss);
		List<Method> methods = getMethods(clss);

		final Object obj; // = clss.newInstance();

		String fieldName;
		String fieldSetter;
		String fieldGetter;

		for (Field f : fields) {
			//Determine likely getter and setter names
			fieldName = f.getName();
			fieldSetter = "set".concat(fieldName.substring(0, 1).toUpperCase().concat(fieldName.substring(1, fieldName.length())));
			fieldGetter = "get".concat(fieldName.substring(0, 1).toUpperCase().concat(fieldName.substring(1, fieldName.length())));
			System.out.println(fieldName + " " + fieldSetter + " " + fieldGetter);
			//Check if getter / setter exists
			for (Method m : methods) {
				System.out.println(m.getName() + " " + m.isAccessible());
				if (m.getName().contentEquals(fieldSetter)) {
					setterTest(f, m, clss);
				}
				else if (m.getName().contentEquals(fieldGetter)) {
					//getterTest(f, m, clss);
				}
			}
		}
	}
	
	@Test
	public void setterTest(Field field, Method method, Class clss) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		final Object obj = clss.newInstance();
		Random rand = new Random();
		final int num = rand.nextInt(100);
		assertTrue(method.getParameterCount() == 1); //Assume 1 parameter per setter.
		if (method.getParameters()[0].getType().equals(String.class)) {
			final String s = randomString(num);
			method.invoke(obj, s);
			assertTrue(s.equals(field.get(obj)));
			//System.out.println(s + " : " + field.get(obj));
		}
		else if (method.getParameters()[0].getType().equals(int.class)) {
			method.invoke(obj, num);
			assertTrue(num == (int) field.get(obj));
		}
		else if (method.getParameters()[0].getType().equals(long.class)) {
			method.invoke(obj, num);
			assertTrue(num == (long) field.get(obj));
		}
		else if (method.getParameters()[0].getType().equals(PreviousFailureInformation.class)) {
			
		}
		else {
			System.err.println(method.getParameters()[0].getType() + " Not yet defined");
		}
	}
	
	@Test
	public void getterTest(Field field, Method method, Class clss) {
		final MutableConverterResponse cr = new MutableConverterResponse();
	}
	
	public List<Field> getFields(Class<?> clss) {
		Class<?> current = clss;
		List<Field> fields = new ArrayList<Field>();
		while(current.getSuperclass()!=null){ // we don't want to process Object.class
			fields.addAll(Arrays.asList(current.getDeclaredFields()));
		    current = current.getSuperclass();
		}
		for (Field f: fields) {
			f.setAccessible(true);
		}
		return fields;
	}
	
	public List<Method> getMethods(Class<?> clss) {
		Class<?> current = clss;
		List<Method> methods = new ArrayList<Method>();
		while(current.getSuperclass()!=null){ // we don't want to process Object.class
			methods.addAll(Arrays.asList(current.getDeclaredMethods()));
		    current = current.getSuperclass();
		}
		for (Method m: methods) {
			m.setAccessible(true);
		}
		return methods;
	}
	
	public static String randomString(int count) {
		StringBuilder sb = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			sb.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return sb.toString();
	}
}
