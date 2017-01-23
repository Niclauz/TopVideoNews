package cn.api.message;

//查询专区列表
public class SectionQueryRequest
{
	private String productCode="";//所属专区id
	private String channelId="";//所属渠道id
	
	
	public String getProductCode()
	{
		return productCode;
	}
	public void setProductCode(String productCode)
	{
		this.productCode = productCode;
	}
	public String getChannelId()
	{
		return channelId;
	}
	public void setChannelId(String channelId)
	{
		this.channelId = channelId;
	}
	
	
}
