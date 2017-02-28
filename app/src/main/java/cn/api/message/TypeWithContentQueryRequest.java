package cn.api.message;

public class TypeWithContentQueryRequest {
	private String productCode;
	private long pageNum;
	private int pageSize;
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public long getPageNum() {
		return pageNum;
	}
	public void setPageNum(long pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
