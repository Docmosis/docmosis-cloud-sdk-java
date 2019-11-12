package com.docmosis.sdk.rendertags;

import java.util.List;

/**
 * Class representing a renderTags array of objects including:
 * year – the year
 * month – the month number
 * tags – an array of objects as follows:
 * 	name – the tag
 * 	countPages – the number of pages rendered for this tag in the year and month
 * 	coundDocuments – the number of documents rendered against this tag in the year and month
 *
 */
public class RenderTag {

	private int year;
	private int month;
	private List<Tag> tags = null;
	
	public RenderTag(int year, int month, List<Tag> tags) {
		this.year = year;
		this.month = month;
		this.tags = tags;
	}

	/**
	 * 
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	protected void setYear(int year) {
		this.year = year;
	}

	/**
	 * 
	 * @return the month number
	 */
	public int getMonth() {
		return month;
	}

	protected void setMonth(int month) {
		this.month = month;
	}

	/**
	 * 
	 * @return List of Tag Objects
	 */
	public List<Tag> getTags() {
		return tags;
	}

	protected void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("Year: " + getYear());
		sb.append(System.getProperty("line.separator"));
		sb.append("Month: " + getMonth());
		sb.append(System.getProperty("line.separator"));
		if (tags != null) { //Build formatted String to return.
			for(Tag tg : tags) {
				sb.append(tg.toString());
				sb.append(System.getProperty("line.separator"));
			}
		}
		return sb.toString();
	}
	
	/**
	 * Class representing a Tag object including:
	 * name – the tag
	 * countPages – the number of pages rendered for this tag in the year and month
	 * coundDocuments – the number of documents rendered against this tag in the year and month
	 *
	 */
	public static class Tag {
		
		private String name;
		private int countPages;
		private int countDocuments;

		public Tag(String name, int countPages, int countDocuments) {
			this.name = name;
			this.countPages = countPages;
			this.countDocuments = countDocuments;
		}

		/**
		 * 
		 * @return the tag
		 */
		public String getName() {
			return name;
		}

		protected void setName(String name) {
			this.name = name;
		}

		/**
		 * 
		 * @return the number of pages rendered for this tag in the year and month
		 */
		public int getCountPages() {
			return countPages;
		}

		protected void setCountPages(int countPages) {
			this.countPages = countPages;
		}

		/**
		 * 
		 * @return the number of documents rendered against this tag in the year and month
		 */
		public int getCountDocuments() {
			return countDocuments;
		}

		protected void setCountDocuments(int countDocuments) {
			this.countDocuments = countDocuments;
		}

		@Override
		public String toString() {
			return "Tag [name=" + name + ", countPages=" + countPages + ", countDocuments=" + countDocuments + "]";
		}
	}
}
