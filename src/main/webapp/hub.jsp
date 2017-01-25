<!--
 * Copyright 2017 ArtisTech, Inc.
-->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:useBean scope="request" class="com.artistech.ee.web.DataManager" id="dataBean" type="com.artistech.ee.web.DataManager">
        <jsp:setProperty name="dataBean" property="*" />
    </jsp:useBean>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Hub</title>
        <link rel='stylesheet' href='style.css' type='text/css'>
        <script type='text/javascript'>
            var pipeline_id = "<c:out value="${dataBean.pipeline_id}" />";
        </script>
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    </head>
    <body>
        <h1>Hub:</h1>
        Your pipeline_id is: <c:out value="${dataBean.pipeline_id}" />
        <h2>Pipeline Done</h2>
        <c:forEach var="dataDir" items="${dataBean.data.keys}">
            <c:if test="${fn:length(dataBean.data.getFiles(dataDir)) gt 0}">
                <ul><kbd><c:out value="${dataDir}"/></kbd> Files:
                    <c:forEach var="dataFile" items="${dataBean.data.getFiles(dataDir)}">
                        <li><a target="_blank" href="ViewRaw?stage=<c:out value="${dataDir}"/>&pipeline_id=<c:out value="${dataBean.pipeline_id}"/>&file=<c:out value="${dataFile}"/>"><c:out value="${dataFile}"/></a></li>
                        </c:forEach>
                </ul>
            </c:if>
        </c:forEach>
        <h2>Pipeline To Do</h2>
        <ul>
            <c:if test="${fn:length(dataBean.data.camrFiles) eq 0}">
                <li><a href="camr.jsp?pipeline_id=<c:out value="${dataBean.pipeline_id}" />">tok</a></li>
                </c:if>
                <c:if test="${fn:length(dataBean.data.camrFiles) gt 0}">
                <li><a href="elisa.jsp?pipeline_id=<c:out value="${dataBean.pipeline_id}" />">ELISA IE</a></li>
                </c:if>
        </ul>
    </body>
</html>
