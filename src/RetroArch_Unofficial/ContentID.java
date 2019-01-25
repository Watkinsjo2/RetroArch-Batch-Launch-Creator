package RetroArch_Unofficial;

import java.io.File;

public class ContentID {

	private String contentName, location, corePlugin, fileType;

	public ContentID(String contentData, File contentFile) {

		contentName = contentData.substring(0, contentData.length() - 4);
		location = contentFile.getAbsolutePath();
		corePlugin = "";
		fileType = contentData.substring(contentData.length() - 4, contentData.length());
	}

	public ContentID() {

		contentName = "";
		location = "";
		corePlugin = "";
		fileType = "";
	}

	public String getContentName() {
		return contentName;
	}

	public String getLocation() {
		return location;
	}

	public String getCorePluglin() {
		return corePlugin;
	}

	public String getFileType() {
		return fileType;
	}
	
	public void setContentName(String newName) {
		contentName = newName;
	}
	
	public void setLocation(String newLocation) {
		location = newLocation;
	}
	
	public void setCorePlugin(String newCorePlugin) {
		corePlugin = newCorePlugin;
	}
	
	public void setFileType(String newFileType) {
		fileType = newFileType;
	}

}
