package cn.api.model;

import java.io.Serializable;

public class ContentBase implements Serializable {
    /**
     * id
     * title1                              名称
     * title2
     * typeMedia         内容格式：音频or视频
     * typePattern       内容形式：电影，电视剧，小说等区分
     * typeSize 		  内容大小：短视频，长视频等区分
     * type1                                内部定义的标签，可以根据产品自定义
     * type2
     * typeList          内容来源处的标签
     * area1
     * area2
     * detail
     * tag
     * playMethod
     * playStreaming     视频流的播放地址
     * playURL           视频播放页的地址
     * duration          时长
     * size
     * image1                              缩略图的地址
     * publicYear
     * publishTime
     * creatorAccount    内容创建者
     * isprivate         是否私有
     * srcSite           内容原站点
     */
    private Long id;
    private String title1 = "";
    private String title2 = "";
    private int typeMedia = 0;
    private int typePattern = 0;
    private int typeSize = 0;
    private int type1 = 0;
    private int type2 = 0;
    private String typeList = "";
    private String area1 = "";
    private String area2 = "";
    private String detail = "";
    private String tag = "";
    private int playMethod = 0;
    private String playStreaming = "";
    private String playURL = "";
    private long duration = 0;
    private long size = 0;
    private String image1 = "";
    private String image2 = "";
    private String image3 = "";
    private String image4 = "";
    private String image5 = "";
    private String image6 = "";
    private int publicYear = 0;
    private String publishTime = "";
    private String creatorAccount = "";
    private int isprivate = 0;
    private String srcSite = "";
    private String srcAlbumId = "";
    private String srcId = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public int getTypeMedia() {
        return typeMedia;
    }

    public void setTypeMedia(int typeMedia) {
        this.typeMedia = typeMedia;
    }

    public int getTypePattern() {
        return typePattern;
    }

    public void setTypePattern(int typePattern) {
        this.typePattern = typePattern;
    }

    public int getTypeSize() {
        return typeSize;
    }

    public void setTypeSize(int typeSize) {
        this.typeSize = typeSize;
    }

    public int getType1() {
        return type1;
    }

    public void setType1(int type1) {
        this.type1 = type1;
    }

    public int getType2() {
        return type2;
    }

    public void setType2(int type2) {
        this.type2 = type2;
    }

    public String getTypeList() {
        return typeList;
    }

    public void setTypeList(String typeList) {
        this.typeList = typeList;
    }

    public String getArea1() {
        return area1;
    }

    public void setArea1(String area1) {
        this.area1 = area1;
    }

    public String getArea2() {
        return area2;
    }

    public void setArea2(String area2) {
        this.area2 = area2;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getPlayMethod() {
        return playMethod;
    }

    public void setPlayMethod(int playMethod) {
        this.playMethod = playMethod;
    }

    public String getPlayStreaming() {
        return playStreaming;
    }

    public void setPlayStreaming(String playStreaming) {
        this.playStreaming = playStreaming;
    }

    public String getPlayURL() {
        return playURL;
    }

    public void setPlayURL(String playURL) {
        this.playURL = playURL;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    public String getImage6() {
        return image6;
    }

    public void setImage6(String image6) {
        this.image6 = image6;
    }

    public int getPublicYear() {
        return publicYear;
    }

    public void setPublicYear(int publicYear) {
        this.publicYear = publicYear;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getCreatorAccount() {
        return creatorAccount;
    }

    public void setCreatorAccount(String creatorAccount) {
        this.creatorAccount = creatorAccount;
    }

    public String getSrcSite() {
        return srcSite;
    }

    public void setSrcSite(String srcSite) {
        this.srcSite = srcSite;
    }

    public String getSrcAlbumId() {
        return srcAlbumId;
    }

    public void setSrcAlbumId(String srcAlbumId) {
        this.srcAlbumId = srcAlbumId;
    }

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public int getPrivate() {
        return isprivate;
    }

    public void setPrivate(int isprivate) {
        this.isprivate = isprivate;
    }


}
