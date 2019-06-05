<%@ page import="com.spor.webapp.model.Organisation" %>
<%@ page import="com.spor.webapp.model.OrganisationSection" %>
<%@ page import="com.spor.webapp.model.TextListSection" %>
<%@ page import="com.spor.webapp.model.TextSection" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.spor.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/tobackheader.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.spor.webapp.model.ContactType, com.spor.webapp.model.Link>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>

        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.spor.webapp.model.SectionType, com.spor.webapp.model.AbstractSection>"/>
            <c:set var="sectionType" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.spor.webapp.model.AbstractSection"/>

        <b>${sectionType.title}</b><br>

        <c:choose>
        <c:when test="${sectionType=='PERSONAL'|| sectionType=='OBJECTIVE' }">
                <%=((TextSection) section).getContent()%>
        <br>
        </c:when>

        <c:when test="${sectionType=='ACHIEVEMENT'|| sectionType=='QUALIFICATIONS' }">
    <ul>
        <c:forEach var="item" items="<%=((TextListSection) section).getList()%>">
            <li>${item}</li>
        </c:forEach>
    </ul>
    <br>
    </c:when>

    <c:when test="${sectionType=='EXPERIENCE' || sectionType=='EDUCATION'}">

        <ul>
            <% List<Organisation> organizations = ((OrganisationSection) sectionEntry.getValue()).getOrganisations(); %>
            <c:if test="<%=organizations.size() != 0%>">
                <c:forEach var="organization" items="<%=organizations%>">
                    <c:set var="organizationName" value="${organization.link.name}"/>
                    <c:if test="${organization.link.url.length() != 0}">
                        <c:set var="organizationName"
                               value="<a href='${organization.link.url}'>${organization.link.name}</a>"/>
                    </c:if>
                    <c:forEach var="position" items="${organization.positionList}">
                        <jsp:useBean id="position" type="com.spor.webapp.model.Position"/>
                        <c:set var="startDate" value="${position.startDate}"/>
                        <c:set var="endDate" value="${position.endDate}"/>
                        <li>
                                ${organizationName}<b> : ${startDate} ... ${endDate}</b><br>
                            <b>${position.title}</b><br>
                                ${position.description}<p>
                        </li>
                    </c:forEach>
                </c:forEach>
            </c:if>
        </ul>
    </c:when>

    </c:choose>
    </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>