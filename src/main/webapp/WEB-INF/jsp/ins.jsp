<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <!--  layout 使用东南西北中布局
        1、引入 layout-default-latest.css
        2、引入 jquery-1.11.0.min.js 支持脚本
        3、引入 jquery.layout-latest.min.js脚本
        4、启动 $('body').layout({applyDemoStyle:true})
    -->
    <title>组织架构</title>
    <link rel="stylesheet" href="<%=path%>/static/css/layout-default-latest.css">
</head>
<body>

<!-- 东南西北中布局-->
<iframe name="content" class="ui-layout-center"
        src="<%=path%>/welcome" frameborder="0" scrolling="auto"></iframe>

<!-- north-->
<div class="ui-layout-north">欢迎[<shiro:principal/>]<a href="${pageContext.request.contextPath}/logout">退出</a></div>

<!-- west-->
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
            $('body').layout({ applyDemoStyles: true });// 采用默认样式
        });
    });
</script>
</body>
</html>