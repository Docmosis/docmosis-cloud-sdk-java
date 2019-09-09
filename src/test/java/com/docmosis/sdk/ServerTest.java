package com.docmosis.sdk;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.localserver.LocalServerTestBase;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.docmosis.sdk.template.DeleteTemplateResponse;
import com.docmosis.sdk.template.Template;

import junit.framework.TestCase;

public class ServerTest extends TestCase {
	@Test
	public void testRenderer() throws Exception
    {
		MyTest mt = new MyTest();
		mt.myMethodTest();
		
    }
public class MyTest extends LocalServerTestBase {

	    HttpHost httpHost;
	    @Before
	    public void setUp() throws Exception {
	      super.setUp();
	      this.serverBootstrap.registerHandler("/*", new HttpRequestHandler() {
	        @Override
	        public void handle(HttpRequest request, HttpResponse response, HttpContext context) 
	                          throws HttpException, IOException {
	            System.out.println(request.getRequestLine().getMethod());
	            response.setStatusCode(HttpStatus.SC_OK);
	            
	        }

	      });

	      httpHost = start();

	    }

	    @Test
	    public void myMethodTest() throws Exception {
	        //Create an instance and give the server url it should call
	        //MyClass instance = new MyClass(httpHost.toURI());
	        //Test method that calls the server
	        //instance.myMethod();
	        DeleteTemplateResponse rsp = Template
					.delete()
					.addTemplateName("foo")
					.execute("httpHost.toURI()", "");
	        assertTrue(rsp.hasSucceeded());
	    }

	    @After @Override
	    public void shutDown() throws Exception {
	     if(this.httpclient != null) {
	        this.httpclient.close();
	     }
	    if(this.server != null) {
	        this.server.shutdown(0L, TimeUnit.SECONDS);
	    }
	   }

}
}
