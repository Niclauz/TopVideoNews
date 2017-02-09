package cn.api.message;

import java.util.List;

import cn.api.model.Type;

public class TypeQueryResponse {
	private List<Type> typeList;

	public List<Type> getTypeList() 
	{
		return typeList;
	}

	public void setTypeList(List<Type> typeList) 
	{
		this.typeList = typeList;
	}
	
}
