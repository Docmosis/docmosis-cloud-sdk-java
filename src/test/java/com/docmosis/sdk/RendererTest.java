package com.docmosis.sdk;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.localserver.LocalServerTestBase;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.junit.Test;

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.render.RenderRequest;
import com.docmosis.sdk.render.RenderResponse;
import com.docmosis.sdk.render.Renderer;

import junit.framework.TestCase;

public class RendererTest extends TestCase {

	private static final String ACCESS_KEY = "XXX"; //TODO Delete
	private static final String TEMPLATE_NAME = "samples/WelcomeTemplate.docx";
	private static final String OUTPUT_FORMAT = "pdf";
	private static final String OUTPUT_FILE = "output_cloud." + OUTPUT_FORMAT;

	public RendererTest( String testName )
    {
		super( testName );
    }

	
	@Test
	public void testRenderer()
    {
		TestServer ts = new TestServer();
		try {
			String url = ts.build(HttpStatus.SC_NOT_FOUND, "Test");
			Environment.setDefaults(url, ACCESS_KEY);
			RenderRequest req = Renderer.render();
			RenderResponse response = req.execute(TEMPLATE_NAME, OUTPUT_FILE,"test Data");
			assertFalse(response.hasSucceeded());
		}
		catch (Exception e){
			System.out.println("Error: " + e.getMessage());
			StackTraceElement[] trace = e.getStackTrace();
			for (StackTraceElement elem : trace) {
			    System.err.println(elem);
			}
		}
    }
	
	@Test
	public void testServer()
    {
		RendererTestOk rto = new RendererTestOk();
		try {
			assert(rto.testCase().getStatusLine().getStatusCode() == 200);
		}
		catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}
    }
	public static class TestServer extends LocalServerTestBase {
		 HttpHost httpHost;
	    private String startServer(String urlSuffix, HttpRequestHandler handler) throws Exception{
	    	this.start();
	        this.serverBootstrap.registerHandler(urlSuffix, handler);
	        HttpHost target = start();
	        String serverUrl = "http://localhost:" + target.getPort();
	        return serverUrl;
	    }

	    public String build(int status, String responseContent) throws Exception{
	    	this.setUp();
	        String baseURL = startServer("/api", new myHttpRequestHandler(status, responseContent));
	        //HttpClient httpClient = HttpClients.custom().build();
	        httpHost = start();
	        return baseURL + "/api";
	    }
	    public static class myHttpRequestHandler implements HttpRequestHandler
	    {
	    	private int status;
	    	private String responseContent;
	    	
	    	public myHttpRequestHandler(int status, String responseContent)
	    	{
	    		this.status = status;
	    		this.responseContent = responseContent;
	    	}
            @Override
            public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
                response.setStatusCode(status);
                response.setEntity(new StringEntity(responseContent));
                response.setHeader("Server", "Docmosis");
            }
        }
	}
	public static class RendererTestOk extends LocalServerTestBase {
		
	    private String startServer(String urlSuffix, HttpRequestHandler handler) throws Exception{
	    	this.start();
	        this.serverBootstrap.registerHandler(urlSuffix, handler);
	        HttpHost target = start();
	        String serverUrl = "http://localhost:" + target.getPort();
	        return serverUrl;
	    }

	    public HttpResponse testCase() throws Exception{
	    	this.setUp();
	        String baseURL = startServer("/api", new HttpRequestHandler() {
	            @Override
	            public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
	                response.setStatusCode(HttpStatus.SC_OK);
	                response.setEntity(new StringEntity("foobar"));
	            }
	        });

	        HttpClient httpClient;
	        httpClient = HttpClients.custom().build();

	        HttpGet method = new HttpGet(baseURL + "/api");
	        HttpResponse response = httpClient.execute(method);
	        return response;
	    }
	}

}
