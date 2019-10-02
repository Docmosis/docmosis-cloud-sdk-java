/*
 *   Copyright 2019 Docmosis.com or its affiliates.  All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 *   or in the LICENSE file accompanying this file.
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.docmosis.sdk.request;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.docmosis.sdk.environment.Environment;

	/**
	 * Abstract Class used for requests that may return an InputStream.
	 * Contains an OutputManager to manage outputting of the returned InputStream.
	 * @param <T> Request class
	 */
	public abstract class DocmosisCloudFileRequest<T extends DocmosisCloudRequest<?>> extends DocmosisCloudRequest<T> {
		
		private OutputManager output =  new OutputManager();

		public DocmosisCloudFileRequest(final String servicePath) {
			super(servicePath);
		}
		
		public DocmosisCloudFileRequest(final String servicePath, final Environment environment) {
			super(servicePath, environment);
		}
		
		/**
		 * Sets the OutputStream for the returned file.
		 * Calling this method will clear any previous call to {@link #sendTo(File)}.
		 * 
		 * No management of the given stream is performed (eg close()).  The result is simply streamed
		 * there so the caller must create and also close the stream. 
		 * 
		 * @param outputStream The output that the returned File will be sent to
		 * @return this request for method chaining
		 */
		public T sendTo(OutputStream outputStream) {
			output.setOutput(outputStream);
			return getThis();
		}
		
		/**
		 * Sets the File output for the returned file.
		 * Calling this method will clear any previous call to {@link #sendTo(OutputStream)}.
		 * @param outputFile The file the returned file will be saved to
		 * @return this request for method chaining
		 */
		public T sendTo(File outputFile) {
			output.setOutput(outputFile);
			return getThis();
		}
		
		/**
		 * Get name of the output destination.
		 * For Files the Full Path is returned.
		 * For Streams the type of Stream is returned.
		 * @return output file name or stream type
		 */
		public String getOutputName() {
			return output.getOutputName();
		}
		
		/**
		 * Outputs the InputStream to the currently set File or OutputStream
		 * @param inputStream returned document
		 * @throws IOException if an IO problem occurs or output not set
		 */
		public void sendDocumentTo(InputStream inputStream) throws IOException {
				output.sendDocumentTo(inputStream);
		}

		@Override
		public String toString() {
			return "output=" + output.toString() + ", " + super.toString();
		}


		/**
		 * Container class to manage the OutputStream
		 *
		 */
		private static class OutputManager{
			private File outputFile;
			private OutputStream outputStream;
			private boolean isFileOutput;
			
			public OutputManager(){}

			public File getOutputFile() {
				return outputFile;
			}

			public void setOutput(File outputFile) {
				this.outputFile = outputFile;
				setFileOutput(true);
				if (outputStream != null) {
					outputStream = null;
				}
			}

			public OutputStream getOutputStream() {
				return outputStream;
			}

			public void setOutput(OutputStream outputStream) {
				this.outputStream = outputStream;
				setFileOutput(false);
				if (outputFile != null) {
					outputFile = null;
				}
			}

			public boolean isFileOutput() {
				return isFileOutput;
			}

			private void setFileOutput(boolean isFileOutput) {
				this.isFileOutput = isFileOutput;
			}
			
			private String getOutputName() {
				String rtn = "";
				if (outputFile != null) {
					try {
						rtn = outputFile.getCanonicalPath();
					} catch (IOException e) {
						//e.printStackTrace();
					}
				}
				else if (outputStream != null) {
					rtn = outputStream.getClass().getName();
				}
				return rtn;
			}
			
			/**
			 * Send the returned Document to the given file.  If there is no document, nothing is done.
			 * 
			 * NOTE: this method can only be used once since it exhausts the 
			 * InputStream returned.  If you need to send the document
			 * to multiple destinations after you have received it, you will need to 
			 * read it into memory or to a file first.
			 * 
			 * @param inputStream returned document
			 * @throws FileNotFoundException if output file not set/valid
			 * @throws IOException if an IO problem occurs or output not set
			 */
			public void sendDocumentTo(InputStream inputStream) throws FileNotFoundException, IOException
			{
				if (inputStream != null) {
					if (isFileOutput()) {
						if (getOutputFile() != null) {
							if (getOutputFile().getParentFile() != null) {
								getOutputFile().getParentFile().mkdirs();
							}
							FileOutputStream fout = new FileOutputStream(getOutputFile());
							try {
								sendDocumentTo(inputStream, fout);
							} finally {
								fout.close();
							}
						} else {
							throw new IOException("outputFile not set");
						}
					} else {
						if (getOutputStream() != null) {
							sendDocumentTo(inputStream, getOutputStream());
						} else {
							throw new IOException("outputStream not set");
						}
					}
				}
			}
			
			
			/**
			 * Send the returned Document to the given outputStream. If there is no document, nothing is done.
			 * 
			 * NOTE: this method can only be used once since it exhausts the 
			 * InputStream returned.  If you need to send the document
			 * to multiple destinations after you have received it, you will need to 
			 * read it into memory or to a file first.
			 * 
			 * @param inputStream returned document
			 * @param outputStream the destination to send the document
			 * 
			 * @throws IOException if an IO problem occurs
			 */
			public void sendDocumentTo(InputStream inputStream, OutputStream outputStream) throws IOException
			{
				byte[] buffer = new byte[8192];
				int len;
				
				long count = 0;
				while((len = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, len);
					count = count + len;
				}
				outputStream.flush();
				
				// input stream exhausted - might as well close it.
				inputStream.close();
				inputStream = null;
			}

			@Override
			public String toString() {
				return "OutputManager [" + ((isFileOutput) ? "outputFile=" + getOutputName() : "outputStream=" + getOutputName()) + "]";
			}
		}
		
		protected abstract T getThis();
	

	}