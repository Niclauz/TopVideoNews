package cn.api.model;

import java.util.List;

public class SectionWithContent extends Section
{

//	int						startId=0;
	private List<Content> 	contentList=null;
	
//	public int getStartId() {
//		return startId;
//	}
//	public void setStartId(int startId) {
//		this.startId = startId;
//	}
	public List<Content> getContentList() {
		return contentList;
	}
	public void setContentList(List<Content> contentList) {
		this.contentList = contentList;
	}
	
}
