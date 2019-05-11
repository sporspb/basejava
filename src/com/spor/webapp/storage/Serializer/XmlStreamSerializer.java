package com.spor.webapp.storage.Serializer;

import com.spor.webapp.model.*;
import com.spor.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements SerializeStrategy {

    private XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(
                Resume.class, Organisation.class, Link.class,
                OrganisationSection.class, TextSection.class, TextListSection.class, Position.class);
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, w);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}
