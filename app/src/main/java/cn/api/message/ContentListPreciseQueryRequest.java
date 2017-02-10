package cn.api.message;

import java.util.List;

public class ContentListPreciseQueryRequest {
    private List<ContentPreciseQueryRequest> requestList;

    public List<ContentPreciseQueryRequest> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<ContentPreciseQueryRequest> requestList) {
        this.requestList = requestList;
    }
}
