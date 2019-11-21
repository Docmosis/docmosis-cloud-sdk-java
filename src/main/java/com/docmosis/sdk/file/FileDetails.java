package com.docmosis.sdk.file;

import java.util.Date;

/**
 * 
 * Class representing a File Details object including:
 * name - the file name
 * lastModifiedMillisSinceEpoch - last modified in milliseconds
 * lastModifiedISO8601 - last modified yyyy-MM-dd'T'HH:mm:ssZ
 * sizeBytes - the size in bytes
 * metaData - the metadata stored with the file (if requested)
 * 
 */
public class FileDetails {

	private String name;
	private long lastModifiedMillisSinceEpoch;
	private Date lastModifiedISO8601;
	private long sizeBytes; //In Bytes
	private String metaData;

	public FileDetails(String name, long lastModifiedMillisSinceEpoch, Date lastModifiedISO8601, long sizeBytes,
			String metaData) {
		this.name = name;
		this.lastModifiedMillisSinceEpoch = lastModifiedMillisSinceEpoch;
		this.lastModifiedISO8601 = lastModifiedISO8601;
		this.sizeBytes = sizeBytes;
		this.metaData = metaData;
	}

	/**
	 * 
	 * @return the file name
	 */
	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return last modified in milliseconds
	 */
	public long getLastModifiedMillisSinceEpoch() {
		return lastModifiedMillisSinceEpoch;
	}

	protected void setLastModifiedMillisSinceEpoch(long lastModifiedMillisSinceEpoch) {
		this.lastModifiedMillisSinceEpoch = lastModifiedMillisSinceEpoch;
	}

	/**
	 * 
	 * @return last modified yyyy-MM-dd'T'HH:mm:ssZ
	 */
	public Date getLastModifiedISO8601() {
		return lastModifiedISO8601;
	}

	protected void setLastModifiedISO8601(Date lastModifiedISO8601) {
		this.lastModifiedISO8601 = lastModifiedISO8601;
	}

	/**
	 * 
	 * @return the size in bytes
	 */
	public long getSizeBytes() {
		return sizeBytes;
	}

	protected void setSizeBytes(long sizeBytes) {
		this.sizeBytes = sizeBytes;
	}

	/**
	 * 
	 * @return the metadata stored with the file (if requested)
	 */
	public String getMetaData() {
		return metaData;
	}

	protected void setMetaData(String metaData) {
		this.metaData = metaData;
	}

	@Override
	public String toString() {
		return "FileDetails [name=" + name + ", lastModifiedMillisSinceEpoch=" + lastModifiedMillisSinceEpoch
				+ ", lastModifiedISO8601=" + lastModifiedISO8601 + ", sizeBytes=" + sizeBytes + ", metaData=" + metaData
				+ "]";
	}
}
