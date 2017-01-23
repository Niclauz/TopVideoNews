package cn.api.message;

import java.util.List;

import cn.api.model.SectionWithContent;


public class SectionWithContentQueryResponse //extends SectionQueryResponse
{
	private List<SectionWithContent> sectionWithContentList=null;

	public List<SectionWithContent> getSectionWithContentList()
	{
		return sectionWithContentList;
	}

	public void setSectionWithContentList(
			List<SectionWithContent> sectionWithContentList)
	{
		this.sectionWithContentList = sectionWithContentList;
	}
	
	
	
}
