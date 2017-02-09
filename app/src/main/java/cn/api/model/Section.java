package cn.api.model;

import java.io.Serializable;

public class Section implements Serializable
{
	/**
	 * productCode      产品id
	 * sectionId        专区id
	 * name             专区名字
	 * image1                            缩略图地址
	 */
	private String 			productCode="";
	private int             sectionId=-1;
	private String 			name="";
	private String 			image1="";
	private String 			image2="";
	private String 			image3="";
	private String 			image4="";
	
	
	public String getProductCode()
	{
		return productCode;
	}
	public void setProductCode(String productCode)
	{
		this.productCode = productCode;
	}
	public int getSectionId() {
		return sectionId;
	}
	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getImage1()
	{
		return image1;
	}
	public void setImage1(String image1)
	{
		this.image1 = image1;
	}
	public String getImage2()
	{
		return image2;
	}
	public void setImage2(String image2)
	{
		this.image2 = image2;
	}
	public String getImage3()
	{
		return image3;
	}
	public void setImage3(String image3)
	{
		this.image3 = image3;
	}
	public String getImage4()
	{
		return image4;
	}
	public void setImage4(String image4)
	{
		this.image4 = image4;
	}

	
	
}
