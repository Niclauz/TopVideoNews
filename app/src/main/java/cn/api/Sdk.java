package cn.api;


public class Sdk
{
	public static void main(String[] args) 
	{
//		SectionQueryResponse resp=Sdk.sectionQuery("1","1");
//		SectionWithContentQueryResponse resp = Sdk.sectionWithContentQuery("1", "1", 1, 4);
//		ContentCreateRequest req=new ContentCreateRequest();
//		Content c=new Content();
////		c.setComposeType("onlyMain");
////		ContentMain cm=new ContentMain();
////		cm.setTitle1("内容3");
////		cm.setPublishTime("0010-00-00");
////		c.setMainContent(cm);
//		ContentSub sub=new ContentSub();
//		sub.setTitle1("内容分集1");
//		sub.setMainId(4);
//		List<ContentSub> list=new ArrayList<ContentSub>();
//		list.add(sub);
//		c.setComposeType("onlySub");
//		c.setSubContentList(list);
//		req.setContent(c);
//		ContentCreateResponse resp=Sdk.contentCreate(req);
//		System.out.println(new Gson().toJson(resp));

//		String publishtime=cm.getPublishTime()==""?"0000-00-00":cm.getPublishTime();
//		System.out.println(publishtime);
	}
	public static class Url
	{
		//专区列表查询
		public static String SectionQueryUrl="http://test.biedese.cn/content.webapi/section_query";
		//专区列表及列表内容查询
		public static String SectionWithContentQueryUrl="http://test.biedese.cn/content.webapi/sectionwithcontent_query";
		//专区列表内容查询
		public static String ContentListOfsectionUrl="http://test.biedese.cn/content.webapi/contentlistofsection_query";
		public static String ContenCreatetUrl="http://test.biedese.cn/content.webapi/content_create";
		public static String ContentSubCreateUrl="http://test.biedese.cn/content.webapi/contentsub_create";
		public static String ContentListCreateUrl="http://test.biedese.cn/content.webapi/contentlist_create";
		
	}
	
//	public static SectionQueryResponse sectionQuery(String productCode, String channelId)
//	{
//		SectionQueryRequest req=new SectionQueryRequest();
//		req.setChannelId(channelId);
//		req.setProductCode(productCode);
//		SectionQueryResponse resp=HttpPost.callObject(Url.SectionQueryUrl, req, SectionQueryResponse.class);
//		return resp;
//
//	}
	
//	public static SectionWithContentQueryResponse sectionWithContentQuery(String productCode, String channelId, int pageSize)
//	{
//		SectionWithContentQueryRequest req=new SectionWithContentQueryRequest();
//		req.setChannelId(channelId);
//		req.setProductCode(productCode);
//		req.setPageSize(pageSize);
//		SectionWithContentQueryResponse resp=HttpPost.callObject(Url.SectionWithContentQueryUrl, req, SectionWithContentQueryResponse.class);
//		return resp;
//
//	}
	
//	public static ContentListOfSectionQueryResponse contentListOfSectionQuery(String productCode, String sectionName)
//	{
//		ContentListOfSectionQueryRequest req=new ContentListOfSectionQueryRequest();
//		req.setProductCode(productCode);
//		req.setSectionName(sectionName);
//		ContentListOfSectionQueryResponse resp=HttpPost.callObject(Url.ContentListOfsectionUrl, req, ContentListOfSectionQueryResponse.class);
//		return resp;
//
//	}
	
//	public static ContentCreateResponse contentCreate(ContentCreateRequest req)
//	{
//		ContentCreateResponse resp=
//			HttpPost.callObject(Url.ContenCreatetUrl, req, ContentCreateResponse.class);
//		return resp;
//	}
	
//	public static ContentSubCreateResponse contentSubCreate(ContentSubCreateRequest req)
//	{
//		ContentSubCreateResponse resp=HttpPost.callObject(Url.ContentSubCreateUrl, req, ContentSubCreateResponse.class);
//		return resp;
//	}
//
//	public static ContentListCreateResponse contentListCreate(ContentListCreateRequest req)
//	{
//		ContentListCreateResponse resp=HttpPost.callObject(Url.ContentListCreateUrl, req, ContentListCreateResponse.class);
//		return resp;
//	}
}
