package cn.api.message;

public class Type2QueryRequest {
	
	private String productCode;
	private int type1Code=-1;

	public String getProductCode() 
	{
		return productCode;
	}

	public void setProductCode(String productCode) 
	{
		this.productCode = productCode;
	}

	public int getType1Code() {
		return type1Code;
	}

	public void setType1Code(int type1Code) {
		this.type1Code = type1Code;
	}
	
}
