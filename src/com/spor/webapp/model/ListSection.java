package com.spor.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {

    private java.util.List List;

    public ListSection(List List) {
        this.List = List;
    }

    public java.util.List getList() {
        return List;
    }

    public void setList(java.util.List list) {
        List = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListSection)) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(List, that.List);
    }

    @Override
    public int hashCode() {
        return Objects.hash(List);
    }
}
