package cn.api.model;

import java.util.List;

public class Content
{
	private String				composeType=""; 
	private ContentMain 		mainContent=null;
	private List<ContentSub> 	subContentList=null;
	public String getComposeType()
	{
		return composeType;
	}
	public void setComposeType(String composeType)
	{
		this.composeType = composeType;
	}
	public ContentMain getMainContent()
	{
		return mainContent;
	}
	public void setMainContent(ContentMain mainContent)
	{
		this.mainContent = mainContent;
	}
	public List<ContentSub> getSubContentList()
	{
		return subContentList;
	}
	public void setSubContentList(List<ContentSub> subContentList)
	{
		this.subContentList = subContentList;
	}
	
	
}
