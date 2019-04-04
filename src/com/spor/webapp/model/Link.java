package com.spor.webapp.model;

import java.util.Objects;

public class Link {

    private String text;
    private String value;

    public Link(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link)) return false;
        Link link = (Link) o;
        return Objects.equals(getText(), link.getText()) &&
                Objects.equals(getValue(), link.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), getValue());
    }
}
