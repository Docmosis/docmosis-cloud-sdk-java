package com.docmosis.sdk;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.docmosis.sdk.convert.MutableConverterResponse;
import com.docmosis.sdk.render.MutableRenderResponse;
import com.docmosis.sdk.response.DocmosisCloudResponse.PreviousFailureInformation;

import junit.framework.TestCase;

@RunWith(Parameterized.class)
public class TestInstancesParameterized extends TestCase {

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	@Parameters(name = "{0}")
    public static Object[] data() {
    	return new Object[] {MutableConverterResponse.class, MutableRenderResponse.class};
    }

    @Parameter(0)
    public Class<?> clss;

//	@Test
//	public void testPOJO() {
//		Class<?>[] clsses = {MutableConverterResponse.class};
//		for (Class<?> c : clsses) {
//			try {
//				testGettersSetters(c);
//			} catch (IllegalArgumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//	}

    @Test
	public void testGettersSetters() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//List<Field> privateFields = new ArrayList<Field>();
		List<Field> fields = getFields(clss);
		//List<Constructor> constructors = getConstructors(clss);
		List<Method> methods = getMethods(clss);
		List<Constructor<?>> constructors = getConstructors(clss);
		Constructor<?> mainConstructor = null;
		
		for (Constructor<?> c : constructors) {
			if (c.getParameterCount() == 0) {
				mainConstructor = c;
			}
		}
		
		if (mainConstructor != null) {
			String fieldName;
			String fieldSetter;
			String fieldGetter;
			
			System.out.print("");
	
			for (Field f : fields) {
				//Determine likely getter and setter names
				fieldName = f.getName();
				fieldSetter = "set".concat(fieldName.substring(0, 1).toUpperCase().concat(fieldName.substring(1, fieldName.length())));
				fieldGetter = "get".concat(fieldName.substring(0, 1).toUpperCase().concat(fieldName.substring(1, fieldName.length())));
				//System.out.println(fieldName + " " + fieldSetter + " " + fieldGetter);
				//Check if getter / setter exists
				for (Method m : methods) {
					if (m.getName().contentEquals(fieldSetter)) {
						setterTest(f, m, mainConstructor);
					}
					else if (m.getName().contentEquals(fieldGetter)) {
						//getterTest(f, m, mainConstructor);
					}
				}
			}
		}
		else {
			System.err.println("No zero argument constructor defined");
		}
	}
	
	public void setterTest(Field field, Method method, Constructor<?> mainConstructor) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		final Object obj = mainConstructor.newInstance();
		Random rand = new Random();
		final int num = rand.nextInt(100);
		System.out.println(method.getName());
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
	

	public void getterTest(Field field, Method method, Constructor<?> mainConstructor) {
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

	public List<Constructor<?>> getConstructors(Class<?> clss) {
		Class<?> current = clss;
		List<Constructor<?>> constructors = new ArrayList<Constructor<?>>();
		constructors.addAll(Arrays.asList(current.getDeclaredConstructors()));
		for (Constructor<?> c: constructors) {
			c.setAccessible(true);
		}
		return constructors;
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
