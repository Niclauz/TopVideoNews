package cn.api;


public class Sdk
{
	public static void main(String[] args) 
	{
		
	}

	public static class Conf {
		public static String product_code =  "zuixinwen";
	}
	public static class Url
	{
		//专区列表查询
		public static String SectionQueryUrl=
//			"http://127.0.0.1:8080/jc.content.webapi/section_query";
				"http://test.biedese.cn/content.webapi/section_query";
		//专区列表及列表内容查询
		public static String SectionWithContentQueryUrl=
				"http://test.biedese.cn/content.webapi/sectionwithcontent_query";
		//			"http://127.0.0.1:8080/jc.content.webapi/sectionwithcontent_query";
		//专区列表内容查询
		public static String ContentListOfsectionUrl=
//			"http://127.0.0.1:8080/jc.content.webapi/contentlistofsection_query";
				"http://test.biedese.cn/content.webapi/contentlistofsection_query";
		//创建主内容
		public static String ContenCreatetUrl=
//			"http://127.0.0.1:8080/jc.content.webapi/content_create";
				"http://test.biedese.cn/content.webapi/content_create";
		//创建子内容
		public static String ContentSubCreateUrl=
				"http://test.biedese.cn/content.webapi/contentsub_create";
		//创建多条内容
		public static String ContentListCreateUrl=
				"http://test.biedese.cn/content.webapi/contentlist_create";
		//产品分类1查询
		public static String Type1QueryUrl=
				"http://test.biedese.cn/content.webapi/type1_query";
		//			"http://127.0.0.1:8080/jc.content.webapi/type1_query";
		//产品分类2查询
		public static String Type2QueryUrl=
				"http://test.biedese.cn/content.webapi/type2_query";
		//			"http://127.0.0.1:8080/jc.content.webapi/type2_query";
		//分类内容查询
		public static String ContentListByTypeQueryUrl=
			"http://test.biedese.cn/content.webapi/contentlistbytype_query";
		//		"http://127.0.0.1:8080/jc.content.webapi/contentlistbytype_query";
		public static String ContentListByTypeQueryJsonPUrl=
				"http://test.biedese.cn/content.webapi/contentlistbytype_queryjsonp";
//			"http://127.0.0.1:8080/jc.content.webapi/contentlistbytype_queryjsonp";

		public static String Type1WithContentQueryUrl=
				"http://test.biedese.cn/content.webapi/type1_with_content_query";
//			"http://127.0.0.1:8080/jc.content.webapi/type1_with_content_query";

		public static String Type2WithContentQueryUrl=
				"http://test.biedese.cn/content.webapi/type2_with_content_query";
		//			"http://127.0.0.1:8080/jc.content.webapi/type2_with_content_query";
		//精准查询一条内容
		public static String ContentPreciseQuery=
//			"http://127.0.0.1:8080/jc.content.webapi/contentprecise_query";
				"http://test.biedese.cn/content.webapi/contentprecise_query";
		//精准查询多条内容
		public static String ContentListPreciseQuery=
//			"http://127.0.0.1:8080/jc.content.webapi/contentlistprecise_query";
				"http://test.biedese.cn/content.webapi/contentlistprecise_query";

		public static String ContentVagueQuery=
//			"http://127.0.0.1:8080/jc.content.webapi/contentvague_query";
				"http://test.biedese.cn/content.webapi/contentvague_query";

		public static String ContentListOfCreatorQuery=
//			"http://127.0.0.1:8080/jc.content.webapi/contentlistofcreator_query";
				"http://test.biedese.cn/content.webapi/contentlistofcreator_query";

		public static String BoardWithContentQueryUrl=
//			"http://test.biedese.cn/content.webapi/board_with_content_query";
				"http://127.0.0.1:8080/jc.content.webapi/board_with_content_query";
		public static String ContentListOfBoardQueryUrl=
//			"http://test.biedese.cn/content.webapi/content_list_of_board_query";
				"http://127.0.0.1:8080/jc.content.webapi/content_list_of_board_query";

		public static String BoardQueryUrl=
//			"http://test.biedese.cn/content.webapi/board_query";
				"http://127.0.0.1:8080/jc.content.webapi/board_query";
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
//	public static ContentListOfSectionQueryResponse contentListOfSectionQuery(String productCode,int sectionId,int pageNum,int pageSize)
//	{
//		ContentListOfSectionQueryRequest req=new ContentListOfSectionQueryRequest();
//		req.setProductCode(productCode);
//		req.setSectionId(sectionId);
//		req.setPageNum(pageNum);
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
//	public static TypeQueryResponse type1Query(TypeQueryRequest req)
//	{
//		TypeQueryResponse resp=HttpPost.callObject(Url.Type1QueryUrl, req, TypeQueryResponse.class);
//		return resp;
//	}
//
//	public static TypeQueryResponse type2Query(TypeQueryRequest req)
//	{
//		TypeQueryResponse resp=HttpPost.callObject(Url.Type2QueryUrl, req, TypeQueryResponse.class);
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
//	public static ContentListPreciseQueryResponse contentListPreciseQuery(ContentListPreciseQueryRequest req)
//	{
//		ContentListPreciseQueryResponse resp=HttpPost.callObject(Url.ContentListPreciseQuery, req, ContentListPreciseQueryResponse.class);
//		return resp;
//	}
//
//	public static ContentVagueQueryResponse contentVagueQuery(ContentVagueQueryRequest req)
//	{
//		ContentVagueQueryResponse resp=HttpPost.callObject(Url.ContentVagueQuery, req, ContentVagueQueryResponse.class);
//		return resp;
//	}
//
//
//	public static ContentListOfCreatorQueryResponse contentListOfCreatorQuery(ContentListOfCreatorQueryRequest req)
//	{
//		ContentListOfCreatorQueryResponse resp=HttpPost.callObject(Url.ContentListOfCreatorQuery, req, ContentListOfCreatorQueryResponse.class);
//		return resp;
//	}
//
//	public static ContentListWithTypeQueryResponse contentListWithType1Query(ContentListWithTypeQueryRequest req)
//	{
//		ContentListWithTypeQueryResponse resp=HttpPost.callObject(Url.ContentListWithType1QueryUrl, req, ContentListWithTypeQueryResponse.class);
//		return resp;
//	}
//
//	public static ContentListWithTypeQueryResponse contentListWithType2Query(ContentListWithTypeQueryRequest req)
//	{
//		ContentListWithTypeQueryResponse resp=HttpPost.callObject(Url.ContentListWithType2QueryUrl, req, ContentListWithTypeQueryResponse.class);
//		return resp;
//	}
}
