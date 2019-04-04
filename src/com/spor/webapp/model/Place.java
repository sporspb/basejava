package com.spor.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Place {

    private Link link;
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String description;

    public Place(Link link, LocalDate startDate, LocalDate endDate, String title, String description) {
        this.link = link;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Place)) return false;
        Place place = (Place) o;
        return Objects.equals(getLink(), place.getLink()) &&
                Objects.equals(getStartDate(), place.getStartDate()) &&
                Objects.equals(getEndDate(), place.getEndDate()) &&
                Objects.equals(getTitle(), place.getTitle()) &&
                Objects.equals(getDescription(), place.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLink(), getStartDate(), getEndDate(), getTitle(), getDescription());
    }
}
