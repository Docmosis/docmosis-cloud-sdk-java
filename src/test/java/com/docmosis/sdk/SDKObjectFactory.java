package com.docmosis.sdk;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.docmosis.sdk.response.DocmosisCloudResponse.PreviousFailureInformation;

public class SDKObjectFactory {
	
	/**********************
	 * Reflection Getters *
	 **********************/
	
	public static List<Field> getFields(Class<?> clss) {
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
	
	public static List<Method> getMethods(Class<?> clss) {
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

	public static List<Constructor<?>> getConstructors(Class<?> clss) {
		Class<?> current = clss;
		List<Constructor<?>> constructors = new ArrayList<Constructor<?>>();
		constructors.addAll(Arrays.asList(current.getDeclaredConstructors()));
		for (Constructor<?> c: constructors) {
			c.setAccessible(true);
		}
		return constructors;
	}
	
	/*
	 * Returns instance of class, if no default constructor is present, an instance instantiated with random parameters is returned.
	 */
	public static Object getInstance(List<Constructor<?>> constructors, Class<?> clss) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<?> constructor = null;
		if (constructors.size() > 0) {
			//Find default constructor
			for (Constructor<?> c : constructors) {
				if (c.getParameterCount() == 0) {
					constructor = c;
				}
			}
			if (constructor == null) {
				//Find any constructor thats not a copy constructor
				for (Constructor<?> c : constructors) {
					if (c.getParameterCount() > 0 && !c.getParameters()[0].getType().equals(clss)) {
						constructor = c;
					}
				}
			}
			if (constructor == null) {
				constructor = constructors.get(0);
			}
		}
		return getInstance(constructor);
	}
	
	
	public static Object getInstance(Constructor<?> constructor) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object obj = null;
		if (constructor != null) {
			if (constructor.getParameterCount() == 0) { //Default Constructor
				obj = constructor.newInstance();
			}
			else {
				//Create random parameters and instantiate
				Object[] params = new Object[constructor.getParameterCount()];
				for (int i = 0; i < constructor.getParameterCount(); i++) {
					params[i] = getRandomObject(constructor.getParameters()[i].getType());
				}
				obj = constructor.newInstance(params);
			}
		}
		return obj;
	}

	
	/******************
	 * Random Getters *
	 ******************/
	
	public static Object getRandomObject(Class<?> type) {
		Object obj = null;
		if (type.equals(String.class)) {
			obj = getRandomString();
		}
		else if (type.equals(int.class)) {
			obj = getRandomInt();
		}
		else if (type.equals(long.class)) {
			obj = getRandomLong();
		}
		else if (type.equals(PreviousFailureInformation.class)) {
			obj = getRandomPreviousFailureInformation();
		}
		else {
			System.err.println(type.getName() + " not yet defined");
		}
		return obj;
	}
	
	public static String getRandomString() {
		return getRandomString(100);
	}
	
	public static String getRandomString(int count) {
		final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			sb.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return sb.toString();
	}

	public static PreviousFailureInformation getRandomPreviousFailureInformation() {
		return new PreviousFailureInformation(getRandomInt(1000), getRandomString(getRandomInt()),
				getRandomString(getRandomInt()), getRandomString(getRandomInt()));
	}
	
	public static int getRandomInt() {
		return getRandomInt(100);
	}
	
	public static int getRandomInt(int upperLimit) {
		Random rand = new Random();
		return rand.nextInt(upperLimit);
	}
	
	public static long getRandomLong() {
		return getRandomLong(100);
	}
	
	public static long getRandomLong(int upperLimit) {
		Random rand = new Random();
		return (long) rand.nextInt(upperLimit);
	}
}
