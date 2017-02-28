package cn.api.message;

public class ContentListByTypeQueryRequest {
    private String productCode;// 产品代号 例如：zuixinwen
    private int type1Code = 0; //
    private int type2Code = 0; // 分类代号  测试请使用1
    private long pageNum; // 页码  1,2,3...
    private int pageSize; //每页数量

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
