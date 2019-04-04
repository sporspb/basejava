package com.spor.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;
    private final String fullName;

    private Map<ContactType, Link> contacts = new EnumMap<>(ContactType.class);
    private Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setContacts(ContactType type, Link data) {
        contacts.put(type, data);
    }

    public void setSections(SectionType type, AbstractSection data) {
        sections.put(type, data);
    }

    public Map<ContactType, Link> getContacts() {
        return contacts;
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return fullName.equals(resume.fullName) && (uuid.equals(resume.uuid));
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid) + Objects.hash(fullName);
    }

    @Override
    public String toString() {
        return uuid + " " + fullName + " " + contacts + " " + sections;
    }
}