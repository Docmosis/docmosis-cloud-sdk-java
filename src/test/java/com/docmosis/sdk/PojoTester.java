package com.docmosis.sdk;

import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.NoFieldShadowingRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

public class PojoTester {
	// The package to be tested
	  private String packageName = "com.docmosis.sdk";

	  @Test
	  public void validate() {
	    Validator validator = ValidatorBuilder.create()
	                            .with(new SetterMustExistRule(),
	                                  new GetterMustExistRule(),
	                                  new NoPublicFieldsRule(),
	                                  new NoFieldShadowingRule())
	                            .with(new SetterTester(),
	                                  new GetterTester())
	                            .build();
	    
	    Validator validatorRRParams = ValidatorBuilder.create()
				                .with(new SetterMustExistRule(),
				                      new GetterMustExistRule(),
				                      new NoFieldShadowingRule())
				                .with(new SetterTester(),
				                      new GetterTester())
				                .build();
	    
	    Validator validatorRequest = ValidatorBuilder.create()
                .with(new GetterMustExistRule(),
                      new NoFieldShadowingRule())
                .with(new GetterTester())
                .build();

	    PojoClassFilter filter = new FilterParamsClasses();
	    
	    for(  PojoClass  pojoClass :  PojoClassFactory.getPojoClassesRecursively(packageName, filter)  ) {
	    	System.out.println("Testing " + pojoClass.getName());
	    	if (pojoClass.getName().contains("RenderRequestParams")) {
	    		validatorRRParams.validate( pojoClass );
	    	}
	    	else if (pojoClass.getName().contains("Request") && !(pojoClass.getName().contains("Params")) || pojoClass.getName().toLowerCase().contains("response")) {
	    		validatorRequest.validate( pojoClass );
	    	}
	    	else {
	    		validator.validate( pojoClass );
	    	}
	    }
	  }
	  

	  private static class FilterParamsClasses implements PojoClassFilter {
		  @Override
		    public boolean include(PojoClass pojoClass) {
		      return (pojoClass.getName().toLowerCase().contains("request") || pojoClass.getName().toLowerCase().contains("response") || 
		    		  pojoClass.getName().toLowerCase().contains("details")  || pojoClass.getName().toLowerCase().contains("rendertag")) && 
		    		  !(pojoClass.isAbstract() || pojoClass.isNestedClass()  || pojoClass.isInterface() || pojoClass.getName().contains("Parameters") || 
		    		    pojoClass.getName().contains("handlers") || pojoClass.getName().contains("Exception") || pojoClass.getName().contains("RenderFormRequestParams") || 
		    		    pojoClass.getName().contains("GetSampleDataResponse") || pojoClass.getName().contains("GetTemplateStructureResponse"));
		    }
		  }
}
