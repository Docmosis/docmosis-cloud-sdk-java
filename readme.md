Docmosis Cloud SDK DWS3 Java - Readme
=====================================

Welcome to the Docmosis Web Services SDK (Java Edition). This SDK makes it easy 
to invoke API calls on Docmosis web service end points (such as the public 
Docmosis Cloud Services).

This version is intended for users of Docmosis Web Services 3 (DWS3).  

To run this SDK you require Java 1.6 or later and a Docmosis DWS3 Cloud account.

If you don't already have a Docmosis account you can sign up for a free trial
at: [docmosis.com/products/cloud/try.html](https://www.docmosis.com/products/cloud/try.html).

Please look at the [examples](https://github.com/Docmosis/docmosis-cloud-sdk-java/tree/DWS3/examples) 
on our [github project page](https://github.com/Docmosis/docmosis-cloud-sdk-java/tree/DWS3) 
for some sample code to get started.  More information about rendering documents 
can be found in the Web Services Guide and the Template Guide at the Docmosis 
support site: [resources.docmosis.com](https://resources.docmosis.com/)

## Installation

#### Maven

Add the following dependency to your `pom.xml` file:

```
<dependency>
  <groupId>com.docmosis</groupId>
  <artifactId>docmosis-cloud-dws3-sdk-java</artifactId>
  <version>1.X.X</version>
</dependency>
```

Please be sure to replace `1.X.X` with the latest version number of the DWS3 SDK.

#### JAR file

The current release of the jar can be found under [dist](https://github.com/Docmosis/docmosis-cloud-sdk-java/tree/DWS3/dist), or you can 
build the jar yourself with the following steps:

1. Download the [Docmosis Cloud SDK Java (DWS3) project](https://github.com/Docmosis/docmosis-cloud-sdk-java/tree/DWS3)
2. Unzip the package
3. Build the jar using:

    ```
    $ mvn package
    ```
4. Import the jar into your project or add to your projects classpath

## Overview

The Docmosis cloud services is a REST-based API. All calls to Docmosis are 
made using HTTPS POST requests. This SDK provides an easy to use wrapper 
around the REST-based API. Each service is provided in the form of simple 
request and response objects.

Typically your code will specify the environment then call the services 
required.  For example:

```
	Environment.setDefaults(accessKey);
	
	ListTemplatesResponse response = Template.list().execute();
	List<TemplateDetails> list = response.list();
	for(TemplateDetails td : list) {
		System.out.println("template: " + td.getName() + " size=" + td.getSizeBytes() + " bytes");
	}
```

#### Request

Each service endpoint has a corresponding request object which allows you to set 
input parameters. After filling in the request parameters, the request can be 
invoked by calling the execute method. The execute method will make the Web 
Service call and return a corresponding response object.

By default the request will use default settings configured for the environment,
however each request can specify the environment settings to use.  More details
on Environment settings are below. 


#### Response

Each service endpoint has a corresponding response object which is returned from 
calling execute on the request object. Typically the response object is used to 
check the success of the request using:

```
	response.hasSucceeded() 
```
If any data has been returned from the request (eg The List Templates request) 
then this will be stored within the response object.

#### Envrionment and Authentication

Use the Environment classes to configure your settings.  It is a global configuration 
and can be setup as part of your server initialization.  For example, to set the 
defaults for all subsequent calls:

```
	Environment.setDefaults(ACCESS_KEY);
```

Then environment settings can also specify service endpoint, connection timeouts, 
retries and proxy configuration.

As mentioned earlier, requests will use the default environment settings unless 
settings are explicitly passed to execute().  For example, the following code
will specify an environment explicitly for the list templates request:

```
	Environment env = EnvironmentBuilder.getDefaultEnvironment()
			.setAccessKey(accessKey)
			.setEndpoint(Endpoint.DWS_VERSION_3_USA)
			.build();
	
	ListTemplatesResponse response = Template.list().execute(env);
```

## The Docmosis Services

#### The Render Service
The Docmosis render service can:
 - merge data into DOCX, DOC or ODT templates
 - produce PDF,DOCX,DOC,ODT output
 - stream the resulting document back, email it, store it in the cloud or any
   combination.  You can even stream, say a PDF whilst emailing a DOC in the 
   single request.
 - the template features are many including:
    - text insertion anywhere (headers, footers, body, text boxes, tables)
    - image insertion (headers, footers, body, tables)
    - table expansion and manipulation
    - content stripping / repetition
    - hyperlinking
    - and many more features

```
	String dataString = ...
	File outputFile = new File(OUTPUT_FILE_NAME);
	RenderResponse response = Renderer.render()
		.templateName(TEMPLATE_NAME)
		.outputName(OUTPUT_FILE_NAME)
		.sendTo(outputFile)
		.data(dataString)
		.execute();

```

The render service can also be invoked with key/value pair data:

```
	File outputFile = new File(OUTPUT_FILE_NAME);
	RenderResponse response = Renderer.renderForm()
		.templateName(TEMPLATE_NAME)
		.outputName(OUTPUT_FILE_NAME)
		.sendTo(outputFile)
		.data(name1, value1)
		.data(name2, value2)
		.execute();

```

#### The Convert Service
The convert service allows files to be converted between formats. The process 
is simple conversion with no concept of templates and data and applies to 
Spreadsheet, presentation and drawing types of document.

```
	File convertFile = new File(FILE_TO_CONVERT);
	File outputFile = new File(OUTPUT_FILE_NAME);
	ConverterResponse response = Converter.convert()
		.fileToConvert(convertFile)
		.outputName(OUTPUT_FILE_NAME)
		.sendTo(outputFile)
		.execute();
```

#### The Template Services
The template services include:

- List Templates Service

```
	ListTemplatesResponse response = Template.list().execute();
	System.out.println(response.toString());
```

- Upload Template Service - Upload template from a file, input stream or byte array.

```
	File uploadFile = new File(TEMPLATE_TO_UPLOAD);
	UploadTemplateResponse response = Template.upload()
		.templateFile(uploadFile)
		.execute();
```
- Batch Upload Template Service

```
	File uploadFile = new File(TEMPLATES_TO_UPLOAD_ZIP_FILE);
	UploadTemplateBatchResponse response = Template.uploadBatch()
		.templateZip(uploadFile)
		.execute();

	//Check Status
	UploadTemplateBatchStatusResponse statusResponse = response.statusRequest()
		.execute();

	//Cancel
	UploadTemplateBatchCancelResponse cancelResponse = response.cancelRequest()
		.execute();
```

- Get Template Service

```
	File outputFile = new File(TEMPLATE_TO_GET);
	GetTemplateResponse response = Template.get()
		.addTemplateName(TEMPLATE_TO_GET)
		.sendTo(outputFile)
		.execute();
```

- Delete Template Service

```
	DeleteTemplateResponse response = Template.delete()
		.addTemplateName(TEMPLATE_TO_DELETE)
		.execute();
```

- Get Template Details Service

```
	GetTemplateDetailsResponse response =  Template.getDetails()
		.templateName(TEMPLATE_NAME)
		.execute();
	TemplateDetails template = response.getDetails();
```

- Get Template Structure Service - Returns a json format description of the template structure.

```
	GetTemplateStructureResponse response = Template.getStructure()
		.templateName(TEMPLATE_NAME)
		.execute();
	System.out.println(response.toString());
```

- Get Sample Data Service - Generates and returns sample data for the template based on its current structures in json or xml format.

```
	GetSampleDataResponse response = Template.getSampleData()
		.templateName(TEMPLATE_NAME)
		.format("json")
		.execute();
	System.out.println(response.toString());
```

#### The Image Services
The image services include:

- List Images Service

```
	ListImagesResponse response = Image.list().execute();
	System.out.println(response.toString());
```

- Upload Image Service - Upload image from a file, input stream or byte array.

```
	File uploadFile = new File(IMAGE_TO_UPLOAD);
	UploadImageResponse response = Image.upload()
		.imageFile(uploadFile)
		.execute();
```

- Get Image Service

```
	File outputFile = new File(IMAGE_TO_GET);
	GetImageResponse response = Image.get()
		.addImageName(IMAGE_TO_GET)
		.sendTo(outputFile)
		.execute();
```

- Delete Image Service

```
	DeleteImageResponse response = Image.delete()
		.addImageName(IMAGE_TO_DELETE)
		.execute();
```

#### The File Storage Services
These services are available if File Storage is enabled on your account.

The File Storage services include:

- List Files Service

```
	ListFilesResponse response = FileStorage.list().execute();
	for(FileDetails fDetails : response.getFiles()) {
		System.out.println(fDetails.getName() + ":" + fDetails.getSizeBytes());
	}
```

- Put File Service - Upload file from a file, input stream or byte array.

```
	File uploadFile = new File(FILE_TO_UPLOAD);
	PutFileResponse response = FileStorage.put()
		.file(uploadFile)
      	.execute();
```

- Get File Service

```
	File outputFile = new File(FILE_TO_GET);
	GetFileResponse response = FileStorage.get()
		.fileName(FILE_TO_GET)
		.sendTo(outputFile)
		.execute();
```

- Delete File Service

```
	DeleteFilesResponse response = FileStorage.delete()
		.path(FILE_TO_DELETE)
		.execute();
```

- Rename File Service

```
	RenameFilesResponse response = FileStorage.rename()
		.fromPath(FILE_TO_RENAME)
		.toPath(NEW_NAME)
		.execute();
```

#### The Get Render Tags Service
The get render tags service allows statistics to be retrieved on renders that were tagged with user-defined phrases (“tags”).

```
	GetRenderTagsResponse response = RenderTags.get()
		.tags("list;of;tags")
		.year(2020)
		.month(6)
		.nMonths(6)
		.execute();
	System.out.println(response.toString());
```

#### The Ping Service

```
	if (Ping.execute()) {
		\\ Docmosis REST web services are online and there is at least one Docmosis server listening.
	}
```

## License

Please see the LICENSE file.
