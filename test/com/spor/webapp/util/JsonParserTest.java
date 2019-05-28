package com.spor.webapp.util;

import com.spor.webapp.ResumeTestData;
import com.spor.webapp.model.AbstractSection;
import com.spor.webapp.model.Resume;
import com.spor.webapp.model.TextSection;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserTest {
    @Test
    public void testResume() throws Exception {
        String json = JsonParser.write(ResumeTestData.RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(resume, resume);
    }

    @Test
    public void write() throws Exception {
        AbstractSection section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(section1, section2);
    }

}