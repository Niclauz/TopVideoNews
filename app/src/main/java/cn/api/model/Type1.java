package cn.api.model;

import java.io.Serializable;
import java.util.List;

public class Type1 implements Serializable
{
	/**
	 * productCode    产品id
	 * type1Code      分类1id  
	 * onlyMain       是否只有父分类
	 * type2List      子分类
	 * name           分类的名字
	 */
	private String productCode;
	private int type1Code=-1;
	private boolean onlyType1=true;
	private List<Type2> type2List=null;
	private String name;
	
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
	
	public boolean isOnlyType1() {
		return onlyType1;
	}
	public void setOnlyType1(boolean onlyType1) {
		this.onlyType1 = onlyType1;
	}
	public List<Type2> getType2List() {
		return type2List;
	}
	public void setType2List(List<Type2> type2List) {
		this.type2List = type2List;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
