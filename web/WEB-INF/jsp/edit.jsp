<%@ page import="com.spor.webapp.model.ContactType" %>
<%@ page import="com.spor.webapp.model.SectionType" %>
<%@ page import="com.spor.webapp.util.DateUtil" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type).name}"></dd>
            </dl>
        </c:forEach>

        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${sectionType.equals(SectionType.PERSONAL) || sectionType.equals(SectionType.OBJECTIVE)}">
                    <dl>
                        <dt><b>${sectionType.title}</b></dt>
                        <dd><input type="text" id="${sectionType.name()}" size=70 name="${sectionType.name()}"
                                   value="${resume.getSection(sectionType)}"></dd>
                    </dl>
                </c:when>

                <c:when test="${sectionType.equals(SectionType.ACHIEVEMENT) || sectionType.equals(SectionType.QUALIFICATIONS)}">
                    <dl>
                        <dt><b>${sectionType.title}</b></dt>
                        <dd>
                            <textarea rows="4" cols="70" id="${sectionType.name()}"
                                      name="${sectionType.name()}">${resume.getSection(sectionType)}</textarea>
                        </dd>
                    </dl>
                </c:when>

                <c:when test="${sectionType.equals(SectionType.EXPERIENCE) || sectionType.equals(SectionType.EDUCATION)}">
                    <dl>
                        <dt><b>${sectionType.title}</b></dt>
                        <c:forEach items="${(resume.getSection(sectionType)).getOrganisations()}" var="organisation"
                                   varStatus="counter">
                            <br>

                            <dl>
                                <dt>Название</dt>
                                <dt><input type="text" value="${organisation.getLink().getName()}"
                                           name="${sectionType}"></dt>
                            </dl>
                            <dl>
                                <dt>Сайт</dt>
                                <dt><input type="text" value="${organisation.getLink().getUrl()}"
                                           name="${sectionType}url"></dt>
                            </dl>

                            <c:forEach items="${organisation.getPositionList()}" var="position">
                                <jsp:useBean id="position" type="com.spor.webapp.model.Position"/>
                                <dl>
                                    <dt>От / До</dt>
                                    <dd>
                                        <input class="DATE" type="text" value="${DateUtil.format(position.startDate)}"
                                               name="${sectionType}${counter.index}startDate" placeholder="MM/yyyy">
                                        <input class="DATE" type="text" value="${DateUtil.format(position.endDate)}"
                                               name="${sectionType}${counter.index}endDate" placeholder="MM/yyyy">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Должность</dt>
                                    <dt><input type="text" value="${position.getTitle()}"
                                               name="${sectionType}${counter.index}title"></dt>
                                </dl>
                                <dl>
                                    <dt>Описание</dt>
                                    <dd>
                                        <textarea name="${sectionType}${counter.index}description"
                                                  rows=5>${activity.getDescription()}</textarea>
                                    </dd>
                                </dl>
                            </c:forEach>

                        </c:forEach>
                    </dl>

                </c:when>
            </c:choose>
        </c:forEach>

        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back(); return false">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>