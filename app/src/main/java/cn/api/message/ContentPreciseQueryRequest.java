package cn.api.message;

public class ContentPreciseQueryRequest {
    private int isMian;
    private long contentId;

    public int getIsMian() {
        return isMian;
    }

    public void setIsMian(int isMian) {
        this.isMian = isMian;
    }

    public long getContentId() {
        return contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }
}
