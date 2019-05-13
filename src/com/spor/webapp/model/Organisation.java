package com.spor.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Organisation implements Serializable {

    private static final long serialVersionUID = 1L;
    private Link link;
    private List<Position> positionList;

    public Organisation() {
    }

    public Organisation(Link link, Position... positions) {
        this(link, Arrays.asList(positions));
    }

    public Organisation(Link link, List<Position> positionList) {
        Objects.requireNonNull(positionList, "positions must not be null");
        this.link = link;
        this.positionList = positionList;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organisation)) return false;
        Organisation that = (Organisation) o;
        return Objects.equals(getLink(), that.getLink()) &&
                Objects.equals(getPositionList(), that.getPositionList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLink(), getPositionList());
    }

    @Override
    public String toString() {
        return "Organisation{" +
                "link=" + link +
                ", positionList=" + positionList +
                '}';
    }
}
