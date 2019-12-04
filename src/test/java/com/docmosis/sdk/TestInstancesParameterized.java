package com.docmosis.sdk;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.docmosis.sdk.convert.ConverterException;
import com.docmosis.sdk.convert.MutableConverterResponse;
import com.docmosis.sdk.render.MutableRenderResponse;
import com.docmosis.sdk.response.DocmosisCloudResponse.PreviousFailureInformation;

import junit.framework.TestCase;

@RunWith(Parameterized.class)
public class TestInstancesParameterized extends TestCase {
	
	@Parameter(0)
    public Class<?> clss;
    
    @Parameter(1)
    public List<Field> fields;
    
    @Parameter(2)
    public List<Method> methods;
    
    @Parameter(3)
    public List<Constructor<?>> constructors;
    
    @Parameter(4)
    public List<Method> setterNames;
    
    @Parameter(5)
    public List<Method> getterNames;

	@Parameters(name = "{0},{1},{2},{3},{4},{5}")
    public static Object[][] data() {
    	
		Class<?>[] classList = {MutableConverterResponse.class, MutableRenderResponse.class, ConverterException.class, PreviousFailureInformation.class};
		Object[][] data = new Object[classList.length][];
		
		for (int i = 0; i < classList.length ; i++) {
			List<Field> fields = SDKObjectFactory.getFields(classList[i]);
			List<Method> methods = SDKObjectFactory.getMethods(classList[i]);
			List<Constructor<?>> constructors = SDKObjectFactory.getConstructors(classList[i]);

			List<Method> setterNames = new ArrayList<Method>();
			List<Method> getterNames = new ArrayList<Method>();
			
			String fieldName;
			String fieldSetter;
			String fieldGetter;
			Method setterMethod;
			Method getterMethod;

			for (Field f : fields) {
				//Determine likely getter and setter names
				fieldName = f.getName();
				fieldSetter = "set".concat(fieldName.substring(0, 1).toUpperCase().concat(fieldName.substring(1, fieldName.length())));
				fieldGetter = "get".concat(fieldName.substring(0, 1).toUpperCase().concat(fieldName.substring(1, fieldName.length())));
				
				setterMethod = null;
				getterMethod = null;

				//Check if getter / setter exists
				for (Method m : methods) {
					if (m.getName().contentEquals(fieldSetter)) {
						setterMethod = m;
					}
					else if (m.getName().contentEquals(fieldGetter)) {
						getterMethod = m;
					}
				}
				setterNames.add(setterMethod);
				getterNames.add(getterMethod);
			}
		
			data[i] = new Object[] { classList[i], fields, methods, constructors, setterNames, getterNames };
		}
		return data;
    }

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
	public void testSetters() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		for (int i = 0; i < fields.size(); i++) {
			if (setterNames.get(i) != null) {
				setterTest(fields.get(i), setterNames.get(i));
			}
			else {
				System.err.println("No setter for: " + clss.getName() + "." + fields.get(i).getName());
			}
		}
	}
    
    @Test
	public void testGetters() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		for (int i = 0; i < fields.size(); i++) {
			if (getterNames.get(i) != null) {
				getterTest(fields.get(i), getterNames.get(i));
			}
			else {
				System.err.println("No getter for: " + clss.getName() + "." + fields.get(i).getName());
			}
		}
	}
    
    @Test
	public void testConstructors() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

    	for (Constructor<?> c : constructors) {
			if (c.getParameterCount() > 0) {
				constructorTest(c);
			}
		}
	}
	
	public void setterTest(Field field, Method method) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		final Object obj = SDKObjectFactory.getInstance(constructors, clss);
		System.out.println(method.getName());
		assertTrue(method.getParameterCount() == 1); //Assume 1 parameter per setter.
		assertTrue(method.getParameters()[0].getType().equals(field.getType())); //Assume Field type and parameter type are equal.
		
		final Object param = SDKObjectFactory.getRandomObject(method.getParameters()[0].getType());
		method.invoke(obj, param);
		assertTrue(param.equals(field.get(obj)));
	}
	

	public void getterTest(Field field, Method method) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		final Object obj = SDKObjectFactory.getInstance(constructors, clss);
		System.out.println(method.getName());
		assertTrue(method.getParameterCount() == 0); //Assume 0 parameters per getter.
		assertTrue(method.getReturnType().equals(field.getType())); //Assume Field type and return type are equal.
		
		final Object param = SDKObjectFactory.getRandomObject(method.getReturnType());
		field.set(obj, param);
		assertTrue(param.equals(method.invoke(obj)));
	}
	
	public void constructorTest(Constructor<?> constructor) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (constructor.getParameterCount() == 1 && constructor.getParameters()[0].getType().equals(clss)) { //Copy constructor
			System.out.println(clss.getName() + " has a copy constructor");
			//Invoke and compare all fields
			final Object param = SDKObjectFactory.getRandomObject(constructor.getParameters()[0].getType());
			final Object obj = constructor.newInstance(param);
			for (Field f : fields) {
				assertTrue(f.get(obj).equals(f.get(param)));
			}
		}
		else if (constructor.getParameterCount() != 0) {  //Other constructor
			Object[] params = new Object[constructor.getParameterCount()];
			for (int i = 0; i < constructor.getParameterCount(); i++) {
				params[i] = SDKObjectFactory.getRandomObject(constructor.getParameters()[i].getType());
			}
			//Invoke and compare on parameter names
			final Object obj = constructor.newInstance(params);
			for (java.lang.reflect.Parameter p : constructor.getParameters()) {
				boolean found = false;
				for (Field f : fields) {
					if (p.getName().equals(f.getName())) {
						assertTrue(f.get(obj).equals(f.get(p)));
						found = true;
					}
				}
				if (!found) {
					System.err.println("No matching field for constructor param: " + p.getName() + " of class: " + clss.getName());
				}
			}
		}
		else { //Default constructor
			
		}
	}
}
