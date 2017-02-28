package cn.api.model;


public class Type2 {

    /**
     * productCode    产品id
     * parentCode     父分类id
     * type2Code      分类1id
     * isGetType2            是否携带子分类
     * type2List      子分类
     * name           分类的名字
     */
    private String productCode;
    private int type1Code = -1;
    private int type2Code = -1;
    private String name;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getParentCode() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
