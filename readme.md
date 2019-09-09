Docmosis Web Services SDK (Java Edition) Readme 
===============================================

Welcome to the Docmosis Web Services SDK (Java Edition).  This SDK makes it easy 
to invoke API calls on Docmosis web service end points (such as the public 
Docmosis Cloud Services).  

To run this SDK you require Java 1.6 or later and a Docmosis Cloud or Tornado 
account.

If you don't already have a Docmosis account you can sign up for a free trial
at: https://www.docmosis.com/try.

Please look at the sample code for some examples to get started.  More 
information about rendering documents can be found in the Web Services Guide 
and the Template Guide in the Docmosis support site (http://www.docmosis.com)

##### Overview

The Docmosis cloud services is a REST-based API. All calls to Docmosis are 
made using HTTPS POST requests. This SDK provides an easy to use wrapper 
around the REST-based API. Each service is provided in the form of simple 
request and response objects.

###### *Request*

Each service endpoint has a corresponding request object which allows you to set 
input parameters. After filling in the request parameters, the request can be 
invoked by calling the execute method. The execute method will make the Web 
Service call and return a corresponding response object.


###### *Response*

Each service endpoint has a corresponding response object which is returned from 
calling execute on the request object. Typically the response object is used to 
check the success of the request using:

```
response.hasSucceeded() 
```
If any data has been returned from the request (eg The List Templates request) 
then this will be stored within the response object.

###### *Authentication*

Use the Environment classes and Endpoint enum to configure your endpoint and your 
API key. It is a global configuration and can be setup as part of your
server initialization.

```
Environment.setDefaults(Endpoint.DWS_VERSION_3_AUS, ACCESS_KEY);
```
There are also additional overloaded execute methods that take in an environment 
configuration object (or Endpoint and Access Key variables) and applies 
it only for that request, eg:

```
request.execute(environment);
request.execute(url, accessKey);
request.execute(accessKey);
```

##### The Docmosis Services

###### *The Render Service*
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
RenderResponse response = Renderer
                          .render()
                          .templateName(TEMPLATE_NAME)
                          .outputName(OUTPUT_FILE_NAME)
                          .sendTo(outputFile)
                          .data(dataString)
                          .execute();
```

###### *The Convert Service*
The convert service allows files to be converted between formats. The process is simple conversion with no concept of templates and data and applies to Spreadsheet, presentation and drawing types of document.

```
File convertFile = new File(FILE_TO_CONVERT);
File outputFile = new File(OUTPUT_FILE_NAME);
ConverterResponse response = Converter
                             .convert()
                             .fileToConvert(convertFile)
                             .outputName(OUTPUT_FILE_NAME)
                             .sendTo(outputFile)
                             .execute();
```

###### *The Template Services*
The template services include:

- List Templates Service

```
ListTemplatesResponse templates = Template.list().execute();
templates.toString();
```

- Upload Template Service

```
File uploadFile = new File(TEMPLATE_TO_UPLOAD);
UploadTemplateResponse uploadedTemplate = Template
                                           .upload()
                                           .templateFile(uploadFile)
                                           .execute();
```

- Get Template Service

```
File outputFile = new File(TEMPLATE_TO_GET);
GetTemplateResponse template = Template
                                .get()
                                .addTemplateName(TEMPLATE_TO_GET)
                                .sendTo(outputFile)
                                .execute();
```

- Delete Template Service

```
DeleteTemplateResponse deleteTemplate = Template
                                         .delete()
                                         .addTemplateName(TEMPLATE_TO_DELETE)
                                         .execute();
```

- Get Template Details Service

```
GetTemplateDetailsResponse templateDetails =  Template
                                               .getDetails()
                                               .templateName(TEMPLATE_NAME)
                                               .execute();
TemplateDetails template = templateDetails.getDetails();
```

- Get Template Structure Service - Returns a json format description of the template structure.

```
GetTemplateStructureResponse templateStructure = Template
                                                  .getStructure()
                                                  .templateName(TEMPLATE_NAME)
                                                  .execute();
templateStructure.toString();
```

- Get Sample Data Service - Generates and returns sample data for the template based on its current structures in json or xml format.

```
GetSampleDataResponse sampleData = Template
                                    .getSampleData()
                                    .templateName(TEMPLATE_NAME)
                                    .format("json")
                                    .execute();
sampleData.toString();
```

###### *The Image Services*
The image services include:

- List Images Service

```
ListImagesResponse images = Image.list().execute();
images.toString();
```

- Upload Image Service

```
File uploadFile = new File(IMAGE_TO_UPLOAD);
UploadImageResponse uploadedImage = Image
                                     .upload()
                                     .imageFile(uploadFile)
                                     .execute();
```

- Get Image Service

```
File outputFile = new File(IMAGE_TO_GET);
GetImageResponse image = Image
                          .get()
                          .addImageName(IMAGE_TO_GET)
                          .sendTo(outputFile)
                          .execute();
```

- Delete Image Service

```
DeleteImageResponse deleteImage = Image
                                   .delete()
                                   .addImageName(IMAGE_TO_DELETE)
                                   .execute();
```

###### *The File Storage Services*
These services are available if File Storage is enabled on your account.

The File Storage services include:

- List Files Service

```
ListFilesResponse files = FileStorage.list().execute();
files.toString();
```

- Put File Service

```
PutFileResponse uploadedFile = FileStorage
                                .put()
                                .file(uploadFile)
                                .execute();

```

- Get File Service

```
GetFileResponse file = FileStorage
                        .get()
                        .fileName(FILE_TO_GET)
                        .sendTo(outputFile)
                        .execute();
```

- Delete File Service

```
DeleteFilesResponse deletedFile = FileStorage
                                   .delete()
                                   .path(FILE_TO_DELETE)
                                   .execute();
```

- Rename File Service

```
RenameFilesResponse renamedFile = FileStorage
                                   .rename()
                                   .fromPath(FILE_TO_RENAME)
                                   .toPath(NEW_NAME)
                                   .execute();
```

###### *The Get Render Tags Service*
The get render tags service allows statistics to be retrieved on renders that were tagged with user-defined phrases (“tags”).

```
GetRenderTagsResponse renderTags = RenderTags
                                    .get()
                                    .tags("list;of;tags;")
                                    .year(2019)
                                    .month(1)
                                    .nMonths(6)
                                    .execute();
renderTags.toString()
```

###### *The Ping Service*

```
if (Ping.execute()) {
	\\Docmosis REST web services are online and there is at least one Docmosis server listening.
}
```

