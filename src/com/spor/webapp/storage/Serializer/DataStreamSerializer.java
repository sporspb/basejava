package com.spor.webapp.storage.Serializer;

import com.spor.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializeStrategy {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, Link> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, Link> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue().getName());
                dos.writeUTF(entry.getValue().getUrl());
            }

            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) entry.getValue()).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> textList = ((TextListSection) entry.getValue()).getList();
                        dos.write(textList.size());
                        for (String text : textList) {
                            dos.writeUTF(text);
                        }
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Organisation> organisationList = ((OrganisationSection) entry.getValue()).getOrganisations();
                        dos.writeInt(organisationList.size());
                        for (Organisation organisation : organisationList) {
                            dos.writeUTF(organisation.getLink().getName());
                            dos.writeUTF(organisation.getLink().getUrl());
                            List<Position> positions = organisation.getPositionList();
                            dos.writeInt(positions.size());
                            for (Position position : positions) {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDescription());
                            }
                        }
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactSize = dis.readInt();
            for (int i = 0; i < contactSize; i++) {
                resume.setContacts(ContactType.valueOf(dis.readUTF()), new Link(dis.readUTF(), dis.readUTF()));
            }

            int sectionSize = dis.readInt();
            for (int i = 0; i < sectionSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.setSections(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int textListSize = dis.readInt();
                        List<String> textList = new ArrayList<>(textListSize);
                        for (int j = 0; j < textListSize; j++) {
                            textList.add(dis.readUTF());
                        }
                        resume.setSections(sectionType, new TextListSection(textList));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        int orgListSize = dis.readInt();
                        List<Organisation> organisationList = new ArrayList<>(orgListSize);
                        for (int j = 0; j < orgListSize; j++) {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            int positionSize = dis.readInt();
                            List<Position> positions = new ArrayList<>(positionSize);
                            for (int k = 0; k < positionSize; k++) {
                                positions.add(
                                        new Position(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
                            }
                            organisationList.add(new Organisation(new Link(name, url), positions));
                        }
                        resume.setSections(sectionType, new OrganisationSection(organisationList));
                        break;
                }
            }
            return resume;
        }
    }
}