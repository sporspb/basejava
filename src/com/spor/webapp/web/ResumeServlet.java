package com.spor.webapp.web;

import com.spor.webapp.Config;
import com.spor.webapp.model.*;
import com.spor.webapp.storage.Storage;
import com.spor.webapp.util.DateUtil;
import com.spor.webapp.util.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends javax.servlet.http.HttpServlet {
    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        Resume r;
        final boolean isNewResume = uuid.isEmpty();
        if (isNewResume) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.setContacts(type, new Link(value));
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType sectionType : SectionType.values()) {
            String value = request.getParameter(sectionType.name());
            String[] values = request.getParameterValues(sectionType.name());
            if (value == null || value.trim().length() == 0 && values.length < 2) {
                r.getSections().remove(sectionType);
            } else {
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        r.setSections(sectionType, new TextSection(request.getParameter(sectionType.name())));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.setSections(sectionType, new TextListSection(value.split("\r\n")));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organisation> organisations = new ArrayList<>();
                        String[] urls = request.getParameterValues(sectionType.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!StringUtil.isEmpty(name)) {
                                List<Position> positions = new ArrayList<>();
                                String pfx = sectionType.name() + i;
                                String[] startDates = request.getParameterValues(pfx + "startDate");
                                String[] endDates = request.getParameterValues(pfx + "endDate");
                                String[] allPositions = request.getParameterValues(pfx + "title");
                                String[] allInformation = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < allPositions.length; j++) {
                                    if (!StringUtil.isEmpty(allPositions[j])) {
                                        positions.add(new Position(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), allPositions[j], allInformation[j]));
                                    }
                                }
                                organisations.add(new Organisation(new Link(name, urls[i]), positions));
                            }
                        }
                        r.setSections(sectionType, new OrganisationSection(organisations));
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }

        if (isNewResume) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "add":
                r = Resume.EMPTY;
                break;
            case "view":
                r = storage.get(uuid);
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType sectionType : SectionType.values()) {
                    AbstractSection section = r.getSection(sectionType);
                    switch (sectionType) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = TextListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganisationSection organisationsSection = (OrganisationSection) section;
                            List<Organisation> emptyFirstOrganisations = new ArrayList<>();
                            emptyFirstOrganisations.add(Organisation.EMPTY);
                            if (organisationsSection != null) {
                                for (Organisation org : organisationsSection.getOrganisations()) {
                                    List<Position> emptyFirstPositions = new ArrayList<>();
                                    emptyFirstPositions.add(Position.EMPTY);
                                    emptyFirstPositions.addAll(org.getPositionList());
                                    emptyFirstOrganisations.add(new Organisation(org.getLink(), emptyFirstPositions));
                                }
                            }
                            section = new OrganisationSection(emptyFirstOrganisations);
                            break;
                    }
                    r.setSections(sectionType, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);

    }
}