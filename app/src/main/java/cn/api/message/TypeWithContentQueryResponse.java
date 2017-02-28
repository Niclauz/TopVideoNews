package cn.api.message;


import java.util.List;

import cn.api.model.TypeWithContent;

public class TypeWithContentQueryResponse {
	
	private List<TypeWithContent> typeWithContentList=null;

	public List<TypeWithContent> getTypeWithContentList() {
		return typeWithContentList;
	}

	public void setTypeWithContentList(List<TypeWithContent> typeWithContentList) {
		this.typeWithContentList = typeWithContentList;
	}
}
