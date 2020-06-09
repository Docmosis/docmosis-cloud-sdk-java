package com.docmosis.sdk;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.docmosis.sdk.template.JobStatus;

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
    	
		Class<?>[] classList = {/*ConverterRequestParams.class, MutableRenderResponse.class, PreviousFailureInformation.class, Proxy.class, DocmosisException.class*/ JobStatus.class};
//		try {
//			classList = getClasses("com.docmosis.sdk");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.exit(-1);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.exit(-1);
//		}
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
			//Invoke and compare all fields
			final Object param = SDKObjectFactory.getRandomObject(constructor.getParameters()[0].getType());
			final Object obj = constructor.newInstance(param);
			assertTrue(equalByValue(obj, param));
		}
		else if (constructor.getParameterCount() != 0) {  //Other constructor
			//Problem with missing param names
			Object[] paramValues = new Object[constructor.getParameterCount()];
			java.lang.reflect.Parameter[] params = constructor.getParameters();
			//Build random parameters to use for initialisation.
			for (int i = 0; i < constructor.getParameterCount(); i++) {
				paramValues[i] = SDKObjectFactory.getRandomObject(params[i].getType());
			}
			//Invoke and compare on parameter names
			final Object obj = constructor.newInstance(paramValues);
			for (int j = 0; j < constructor.getParameterCount(); j++) {
				boolean found = false;
				for (Field f : fields) {
					if (params[j].getName().equals(f.getName())) {
						Object fObj = f.get(obj);
						Class<?> pType = paramValues[j].getClass();
						Class<?> fType = fObj.getClass();
						assertTrue(pType.equals(fType)); //Types should be equal
						assertTrue(equalByValue(fObj, paramValues[j])); //Values should be equal
						found = true;
					}
				}
				if (!found) {
					System.err.println("No matching field for constructor param: " + params[j].getName() + " of class: " + clss.getName());
				}
				assertTrue(found); //param names should match field names.
			}
		}
		else { //Default constructor
			
		}
	}
	
//	@Test
//	public void testEquality() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//
//		assertTrue(equalByValue(new String("ab"), new String("ab")));
//		assertFalse(equalByValue(new String("aba"), new String("abb")));
//	}
	
	private boolean equalByValue(Object o1, Object o2) throws IllegalArgumentException, IllegalAccessException {
        if ((o1 == null && o2 != null) || (o1 != null && o2 == null)) {
        	return false;
        }
        if (o1 == null && o2 == null) {
        	return true;
        }
        if (o1.getClass() != o2.getClass()) {
        	return false;
        }
        if (o1.equals(o2)) {
        	return true;
        }
        //Check equality by value
        List<Field> fields = SDKObjectFactory.getFields(o1.getClass());
        for (Field f : fields) {
        	if (f.getType().isPrimitive()/* || f.getType().equals(String.class)*/) {
//        		Object fo1 = f.get(o1);
//        		Object fo2 = f.get(o2);
        		if (!f.get(o1).equals(f.get(o2))) {
        			return false;
        		}
        	}
        	else if(f.getType().equals(java.lang.String.class)) {
        		String so1 = (String)f.get(o1);
        		String so2 = (String)f.get(o2);
        		if ((so1 == null && so2 != null) || (so1 != null && so2 == null)) {
                	return false;
                }
                if (so1 == null && so2 == null) {
                	return true;
                }
                return so1.equals(so2);
        	}
    		else {
    			if (!equalByValue(f.get(o1), f.get(o2))){
    				return false;
    			}
    		}
        }
        return true;
	}
	
	private static Class<?>[] getClasses(String packageName)
	        throws ClassNotFoundException, IOException {
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    assert classLoader != null;
	    String path = packageName.replace('.', '/');
	    Enumeration<URL> resources = classLoader.getResources(path);
	    List<File> dirs = new ArrayList<File>();
	    while (resources.hasMoreElements()) {
	        URL resource = resources.nextElement();
	        dirs.add(new File(resource.getFile()));
	    }
	    ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
	    for (File directory : dirs) {
	        classes.addAll(findClasses(directory, packageName));
	    }
	    for (Class<?> c : classes) {
	        System.out.println(c.getName());
	    }
	    return classes.toArray(new Class[classes.size()]);
	}
	
	private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
	    List<Class<?>> classes = new ArrayList<Class<?>>();
	    if (!directory.exists()) {
	        return classes;
	    }
	    File[] files = directory.listFiles();
	    for (File file : files) {
	        if (file.isDirectory()) {
	            assert !file.getName().contains(".");
	            classes.addAll(findClasses(file, packageName + "." + file.getName()));
	        } else if (file.getName().endsWith(".class")) {
	            classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
	        }
	    }
	    return classes;
	}
}
