package com.spor.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TextListSection extends AbstractSection {

    private List<String> List;

    public TextListSection() {
    }

    public TextListSection(String... items) {
        this(Arrays.asList(items));
    }

    public TextListSection(List<String> List) {
        this.List = List;
    }

    public List getList() {
        return List;
    }

    public void setList(List<String> list) {
        List = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextListSection)) return false;
        TextListSection that = (TextListSection) o;
        return Objects.equals(getList(), that.getList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getList());
    }

    @Override
    public String toString() {
        return "TextListSection{" +
                "List=" + List +
                '}';
    }
}
