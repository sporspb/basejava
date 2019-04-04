package com.spor.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection<T> extends AbstractSection {

    private List<T> ObjectList;

    public ListSection(List<T> ObjectList) {
        this.ObjectList = ObjectList;
    }

    public List<T> getObjectList() {
        return ObjectList;
    }

    public void setObjectList(List<T> ObjectList) {
        this.ObjectList = ObjectList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListSection)) return false;
        ListSection<?> that = (ListSection<?>) o;
        return Objects.equals(getObjectList(), that.getObjectList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getObjectList());
    }
}
