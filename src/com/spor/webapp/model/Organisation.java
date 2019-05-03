package com.spor.webapp.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organisation implements Serializable {

    private static final long serialVersionUID = 1L;
    private Link link;
    private List<Position> positionList;

    public Organisation(Link link, Position... positions) {
        Objects.requireNonNull(positions, "activities must not be null");
        this.link = link;
        this.positionList = Arrays.asList(positions);
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<Position> positionList) {
        this.positionList = positionList;
    }
}
