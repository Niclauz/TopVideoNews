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
		public static String ContentListOfsectionUrl=
//			"http://127.0.0.1:8080/jc.content.webapi/contentlistofsection_query";
			"http://test.biedese.cn/content.webapi/contentlistofsection_query";
		//创建主内容
		public static String ContenCreatetUrl="http://test.biedese.cn/content.webapi/content_create";
		//创建子内容
		public static String ContentSubCreateUrl="http://test.biedese.cn/content.webapi/contentsub_create";
		//创建多条内容
		public static String ContentListCreateUrl="http://test.biedese.cn/content.webapi/contentlist_create";
		//产品分类查询
		public static String TypeQueryUrl="http://test.biedese.cn/content.webapi/type_query";
//			"http://127.0.0.1:8080/jc.content.webapi/type_query";
		//分类内容查询
		public static String ContentListByTypeQueryUrl="http://test.biedese.cn/content.webapi/contentlistbytype_query";
//			"http://127.0.0.1:8080/jc.content.webapi/contentlistbytype_query";
		//精准查询
		public static String ContentPreciseQuery=
//			"http://127.0.0.1:8080/jc.content.webapi/contentprecise_query";
			"http://test.biedese.cn/content.webapi/contentprecise_query";
	
		public static String ContentVagueQuery=
//			"http://127.0.0.1:8080/jc.content.webapi/contentvague_query";
			"http://test.biedese.cn/content.webapi/contentvague_query";
		
	}
	
//	public static SectionQueryResponse sectionQuery(String productCode,String channelId)
//	{
//		SectionQueryRequest req=new SectionQueryRequest();
//		req.setChannelId(channelId);
//		req.setProductCode(productCode);
//		SectionQueryResponse resp=HttpPost.callObject(Url.SectionQueryUrl, req, SectionQueryResponse.class);
//		return resp;
//
//	}
//
//	public static SectionWithContentQueryResponse sectionWithContentQuery(String productCode,String channelId,int pageSize)
//	{
//		SectionWithContentQueryRequest req=new SectionWithContentQueryRequest();
//		req.setChannelId(channelId);
//		req.setProductCode(productCode);
//		req.setPageSize(pageSize);
//		SectionWithContentQueryResponse resp=HttpPost.callObject(Url.SectionWithContentQueryUrl, req, SectionWithContentQueryResponse.class);
//		return resp;
//
//	}
//
//	public static ContentListOfSectionQueryResponse contentListOfSectionQuery(String productCode,int sectionId,int startId,int pageSize)
//	{
//		ContentListOfSectionQueryRequest req=new ContentListOfSectionQueryRequest();
//		req.setProductCode(productCode);
//		req.setSectionId(sectionId);
//		req.setStartId(startId);
//		req.setPageSize(pageSize);
//		ContentListOfSectionQueryResponse resp=HttpPost.callObject(Url.ContentListOfsectionUrl, req, ContentListOfSectionQueryResponse.class);
//		return resp;
//
//	}
//
//	public static ContentCreateResponse contentCreate(ContentCreateRequest req)
//	{
//		ContentCreateResponse resp=
//			HttpPost.callObject(Url.ContenCreatetUrl, req, ContentCreateResponse.class);
//		return resp;
//	}
//
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
//
//
//	public static TypeQueryResponse typeQuery(TypeQueryRequest req)
//	{
//		TypeQueryResponse resp=HttpPost.callObject(Url.TypeQueryUrl, req, TypeQueryResponse.class);
//		return resp;
//	}
//
//	public static ContentListByTypeQueryResponse contentListByTypeQuery(ContentListByTypeQueryRequest req)
//	{
//		ContentListByTypeQueryResponse resp=HttpPost.callObject(Url.ContentListByTypeQueryUrl, req, ContentListByTypeQueryResponse.class);
//		return resp;
//	}
//
//	public static ContentPreciseQueryResponse contentPreciseQuery(ContentPreciseQueryRequest req)
//	{
//		ContentPreciseQueryResponse resp=HttpPost.callObject(Url.ContentPreciseQuery, req, ContentPreciseQueryResponse.class);
//		return resp;
//	}
//
//	public static ContentVagueQueryResponse contentVagueQuery(ContentVagueQueryRequest req)
//	{
//		ContentVagueQueryResponse resp=HttpPost.callObject(Url.ContentVagueQuery, req, ContentVagueQueryResponse.class);
//		return resp;
//	}
}
