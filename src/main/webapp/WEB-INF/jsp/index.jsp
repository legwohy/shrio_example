<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>组织架构</title>
    <link rel="stylesheet" href="<%=path%>/static/css/layout-default-latest.css">
</head>
<body>

<iframe name="content" class="ui-layout-center"
        src="<%=path%>/welcome" frameborder="0" scrolling="auto"></iframe>
<div class="ui-layout-north">欢迎[<shiro:principal/>]<a href="${pageContext.request.contextPath}/logout">退出</a></div>
<div class="ui-layout-west">
    功能菜单<br/>
    <c:forEach items="${menus}" var="m">
        <a href="<%=path%>/${m.url}" target="content">${m.name}</a><br/>
    </c:forEach>
</div>


<script src="<%=path%>/static/js/jquery-1.11.0.min.js"></script>
<script src="<%=path%>/static/js/jquery.layout-latest.min.js"></script>
<script>
    $(function () {
        $(document).ready(function () {
            $('body').layout({ applyDemoStyles: true });
        });
    });
</script>
</body>
</html>