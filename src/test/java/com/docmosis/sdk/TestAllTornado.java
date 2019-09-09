package com.docmosis.sdk;

import java.io.File;

import org.junit.Test;

import com.docmosis.sdk.convert.Converter;
import com.docmosis.sdk.convert.ConverterException;
import com.docmosis.sdk.convert.ConverterResponse;
import com.docmosis.sdk.environmentconfiguration.Environment;
import com.docmosis.sdk.ping.Ping;
import com.docmosis.sdk.render.RenderResponse;
import com.docmosis.sdk.render.Renderer;
import com.docmosis.sdk.template.GetSampleDataResponse;
import com.docmosis.sdk.template.GetTemplateStructureResponse;
import com.docmosis.sdk.template.Template;

import junit.framework.TestCase;

public class TestAllTornado extends TestCase {
	
	private static final String DEFAULT_TEMPLATE_NAME = "WelcomeTemplate.doc";
	private static final String ACCESS_KEY = "";
	private static final String FILE_TO_UPLOAD = "testFiles/myTemplateFile.docx";
	
	private static final String NONEXISTENT_FILE_NAME = "myNonExistentTemplateFile.docx";
	
	//Send returned files here
	private static final String OUT = "testFiles/output/Out";

	public TestAllTornado( String testName )
    {
        super( testName );
        Environment.setDefaults("http://localhost:8080/rs/", ACCESS_KEY);
    }

/*
  ******************
  * Renderer Tests *
  ******************
*/

	@Test
	public void testPing() {
		try {
			assertTrue(Ping.execute());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testRender() {
		try {
			File outputFile = new File(OUT);
			RenderResponse rsp = Renderer.render().templateName(DEFAULT_TEMPLATE_NAME).outputName(OUT).outputFormat("pdf").sendTo(outputFile)
					.data("{\"title\":\"This is Docmosis Cloud\\nTue Aug 27 16:10:12 AWST 2019\",\"messages\":[{\"msg\":\"This cloud experience is better than I thought.\"},{\"msg\":\"The sun is shining.\"},{\"msg\":\"Right, now back to work.\"}]}")
					.execute();
			assertTrue(rsp.hasSucceeded());
			rsp = Renderer.render().templateName(DEFAULT_TEMPLATE_NAME).outputName(OUT).outputFormat("pdf").sendTo(outputFile)
					.data("<title>This is Docmosis Cloud\r\n" + 
							"Tue Aug 27 16:11:54 AWST 2019</title>")
					.execute();
			assertTrue(rsp.hasSucceeded());
			rsp = Renderer.render().templateName(NONEXISTENT_FILE_NAME).outputName(OUT).outputFormat("pdf").sendTo(outputFile)
					.data("{\"title\":\"This is Docmosis Cloud\\nTue Aug 27 16:10:12 AWST 2019\",\"messages\":[{\"msg\":\"This cloud experience is better than I thought.\"},{\"msg\":\"The sun is shining.\"},{\"msg\":\"Right, now back to work.\"}]}")
					.execute();
			assertFalse(rsp.hasSucceeded());
		} catch (Exception e) {
			fail();
		}
	}

/*
  *****************
  * Convert Tests *
  *****************
*/


	@Test
	public void testConvert() {
		try {
			File convertFile = new File(FILE_TO_UPLOAD);
			File outputFile = new File(OUT + "1");
			ConverterResponse rsp = Converter.convert().fileToConvert(convertFile).outputName(OUT + "1" + ".pdf").sendTo(outputFile).execute();
			assertTrue(rsp.hasSucceeded());
			try {
				convertFile = new File(NONEXISTENT_FILE_NAME);
				rsp = Converter.convert().fileToConvert(convertFile).outputName(OUT + "1" + ".pdf").sendTo(outputFile).execute();
				assertFalse(rsp.hasSucceeded());
			} catch (Exception e1) {
				assertTrue(e1 instanceof ConverterException);
			}
		} catch (Exception e2) {
			fail();
		}
	}


/*
  ******************
  * Template Tests *
  ******************
*/

	@Test
	public void testGetTemplateStructure() {
		try {
			GetTemplateStructureResponse rsp = Template.getStructure().templateName(DEFAULT_TEMPLATE_NAME).execute();
			assertTrue(rsp.hasSucceeded());
			rsp = Template.getStructure().templateName(NONEXISTENT_FILE_NAME).execute();
			assertFalse(rsp.hasSucceeded());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetSampleData() {
		try {
			GetSampleDataResponse rsp = Template.getSampleData().templateName(DEFAULT_TEMPLATE_NAME).format("json").execute();
			assertTrue(rsp.hasSucceeded());
			rsp = Template.getSampleData().templateName(DEFAULT_TEMPLATE_NAME).format("xml").execute();
			assertTrue(rsp.hasSucceeded());
			rsp = Template.getSampleData().templateName(DEFAULT_TEMPLATE_NAME).execute();
			assertTrue(rsp.hasSucceeded());
			rsp = Template.getSampleData().templateName(NONEXISTENT_FILE_NAME).execute();
			assertFalse(rsp.hasSucceeded());
		} catch (Exception e) {
			fail();
		}
	}
}
