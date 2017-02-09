package cn.api.message;

public class SectionWithContentQueryRequest extends SectionQueryRequest
{
//	private int						startId=0;
	private int						pageSize=0;
	
//	public int getStartId() {
//		return startId;
//	}
//	public void setStartId(int startId) {
//		this.startId = startId;
//	}
	
	public int getPageSize() 
	{
		return pageSize;
	}
	public void setPageSize(int pageSize) 
	{
		this.pageSize = pageSize;
	}
}
