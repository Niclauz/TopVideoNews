package cn.api.message;


import java.util.ArrayList;
import java.util.List;

import cn.api.model.Section;

public class SectionQueryResponse {
    private List<Section> sectionList = new ArrayList<Section>();

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public void addSection(Section section) {
        sectionList.add(section);
    }

}
