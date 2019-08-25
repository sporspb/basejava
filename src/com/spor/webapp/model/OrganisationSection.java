package com.spor.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganisationSection extends AbstractSection {

    private List<Organisation> organisations;

    public OrganisationSection() {
    }

    public OrganisationSection(Organisation... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganisationSection(List<Organisation> organisations) {
        Objects.requireNonNull(organisations, "organizations must not be null");
        this.organisations = organisations;
    }

    public List<Organisation> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(List<Organisation> organisations) {
        this.organisations = organisations;
    }

    @Override
    public String toString() {
        return "OrganisationSection{" +
                "organisations=" + organisations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganisationSection)) return false;
        OrganisationSection that = (OrganisationSection) o;
        return Objects.equals(getOrganisations(), that.getOrganisations());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrganisations());
    }
}
