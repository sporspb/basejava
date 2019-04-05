package com.spor.webapp.model;

import java.util.List;
import java.util.Objects;

public class PlaceListSection extends AbstractSection {

    private List<Place> List;

    public PlaceListSection(List<Place> List) {
        this.List = List;
    }

    public List getList() {
        return List;
    }

    public void setList(List<Place> list) {
        List = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceListSection)) return false;
        PlaceListSection that = (PlaceListSection) o;
        return Objects.equals(getList(), that.getList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getList());
    }
}
