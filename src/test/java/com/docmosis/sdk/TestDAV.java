package com.docmosis.sdk;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.HttpResponse;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.RequestLine;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultBHttpServerConnectionFactory;
import org.apache.http.impl.DefaultHttpRequestFactory;
import org.apache.http.impl.io.DefaultHttpRequestParserFactory;
import org.apache.http.impl.io.DefaultHttpResponseWriterFactory;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.localserver.LocalServerTestBase;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.BasicLineParser;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.docmosis.sdk.template.DeleteTemplateResponse;
import com.docmosis.sdk.template.Template;

public class TestDAV extends LocalServerTestBase {

    @Before @Override
    public void setUp() throws Exception {
        super.setUp();
        HttpRequestFactory requestFactory = new DefaultHttpRequestFactory() {
            @Override
            public HttpRequest newHttpRequest(final RequestLine requestline) throws MethodNotSupportedException {
                if (requestline.getMethod().equalsIgnoreCase("BLAH")) {
                    return new BasicHttpRequest(requestline);
                }
                return super.newHttpRequest(requestline);
            }
        };
        HttpMessageParserFactory<HttpRequest> requestParserFactory = new DefaultHttpRequestParserFactory(
                BasicLineParser.INSTANCE, requestFactory);
        DefaultBHttpServerConnectionFactory connectionFactory = new DefaultBHttpServerConnectionFactory(
                ConnectionConfig.DEFAULT, requestParserFactory, DefaultHttpResponseWriterFactory.INSTANCE);
        this.serverBootstrap.setConnectionFactory(connectionFactory);
    }

    @Test
    public void testBlah() throws Exception {

        this.serverBootstrap.registerHandler("/stuff", new HttpRequestHandler() {
            @Override
            public void handle(
                    final HttpRequest request,
                    final HttpResponse response,
                    final HttpContext context) throws HttpException, IOException {
                response.setEntity(new StringEntity("Blah", ContentType.TEXT_PLAIN));
            }
        });
        
        final HttpHost target = start();
        //final HttpRequest get = new BasicHttpRequest("BLAH", "/stuff");
        //final CloseableHttpResponse response = this.httpclient.execute(target, get);
        DeleteTemplateResponse rsp = Template
				.delete()
				.addTemplateName("foo")
				.execute(target.toString(), "");
        try {
            Assert.assertTrue(rsp.hasSucceeded());
            //EntityUtils.consume(response.getEntity());
        } finally {
            //response.close();
        }
    }

}
