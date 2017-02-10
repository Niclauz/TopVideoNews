package cn.api.model;

public class Type {
    /**
     * typeNum        分类区别编号 TypeNum.type1 or TypeNum.type2
     * 对应 content type1 or type2
     * productCode    产品id
     * code           分类id
     * name           分类的名字
     */
    private int typeNum = 0;
    private String productCode;
    private String code;
    private String name;

    public int getType() {
        return typeNum;
    }

    public void setType(int typeNum) {
        this.typeNum = typeNum;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
