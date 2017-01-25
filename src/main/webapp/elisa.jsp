<!--
 * Copyright 2017 ArtisTech, Inc.
-->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <jsp:useBean scope="request" class="com.artistech.ee.web.DataManager" id="dataBean" type="com.artistech.ee.web.DataManager">
        <jsp:setProperty name="dataBean" property="*" />
    </jsp:useBean>
    <jsp:useBean scope="request" class="com.artistech.ee.web.ElisaBean" id="elisaBean" type="com.artistech.ee.web.ElisaBean">
        <jsp:setProperty name="elisaBean" property="*" />
    </jsp:useBean>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>ELISA</title>
        <link rel='stylesheet' href='style.css' type='text/css'>
        <script type='text/javascript'>
            var pipeline_id = "<c:out value="${dataBean.pipeline_id}" />";
        </script>
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    </head>
    <body>
        <h1>ELISA</h1>
        Your pipeline_id is: <c:out value="${dataBean.pipeline_id}" />
        <form method="post" action="Elisa" enctype="multipart/form-data">
            <label for="outputFormat">Output Format</label>
            <select id="outputFormat" name="outputFormat">
                <c:forEach var="outputFormat" items="${elisaBean.outputFormats}">
                    <option value="${outputFormat}">${outputFormat}</option>
                </c:forEach>
            </select>
            <label for="model">Model</label>
            <select id="model" name="model">
                <c:forEach var="model" items="${elisaBean.models}">
                    <option value="${model}">${model}</option>
                </c:forEach>
            </select><br />
            <input type="hidden" name="step" id="step" value="/hub.jsp" />
            <input type="hidden" name="pipeline_id" id="pipeline_id" value="<c:out value="${dataBean.pipeline_id}" />"/>
            <input type="submit" value="ELISA" />
        </form>
    </body>
</html>