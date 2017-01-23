package cn.api.model;

import java.util.List;

public class Content
{
	private String				composeType=""; //区分是否为主内容  onlyMain,onlySub
	private ContentMain 		mainContent=null; //主内容 
	private List<ContentSub> 	subContentList=null;//子内容
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
