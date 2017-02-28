package cn.api.model;

import java.util.List;

public class TypeWithContent{
	
	private String productCode;
	private int type1Code=-1;
	private int type2Code=-1;
	private String typeName;
	private List<Content> contentList=null;

	public List<Content> getContentList() {
		return contentList;
	}
	public void setContentList(List<Content> contentList) {
		this.contentList = contentList;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public int getType1Code() {
		return type1Code;
	}
	public void setType1Code(int type1Code) {
		this.type1Code = type1Code;
	}
	public int getType2Code() {
		return type2Code;
	}
	public void setType2Code(int type2Code) {
		this.type2Code = type2Code;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
