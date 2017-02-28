package cn.api.message;

public class Type1QueryRequest {
	
	private String productCode;
	private boolean onlyType1=true;

	public String getProductCode() 
	{
		return productCode;
	}

	public void setProductCode(String productCode) 
	{
		this.productCode = productCode;
	}

	public boolean isOnlyType1() {
		return onlyType1;
	}

	public void setOnlyType1(boolean onlyType1) {
		this.onlyType1 = onlyType1;
	}
	
}
