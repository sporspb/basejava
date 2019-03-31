package com.spor.webapp.model;

import java.util.List;

public class ListDataSection extends AbstractSectionData {

    private List<Data> dataList;

    public ListDataSection(List<Data> dataList) {
        this.dataList = dataList;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }
}
