package cn.api.model;

public class ContentSub extends ContentBase
{
	private int		mainId=0;//主内容id
	private int		subSeq=0;//子集序列 第？集
	public int getMainId()
	{
		return mainId;
	}
	public void setMainId(int mainId)
	{
		this.mainId = mainId;
	}
	public int getSubSeq()
	{
		return subSeq;
	}
	public void setSubSeq(int subSeq)
	{
		this.subSeq = subSeq;
	}
	
	
}
