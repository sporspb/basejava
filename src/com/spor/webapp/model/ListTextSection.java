package com.spor.webapp.model;

import java.util.List;

public class ListTextSection extends AbstractSectionData {

    private List<String> textList;

    public ListTextSection(List<String> textList) {
        this.textList = textList;
    }

    public List<String> getTextList() {
        return textList;
    }

    public void setTextList(List<String> textList) {
        this.textList = textList;
    }
}
