<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="zhagfn" uri="http://www.springframework.org/tags" %>

<!-- 引入自定义的tld文件-->
<%@taglib prefix="zhangfn" uri="http://github.com/zhangkaitao/tags/zhang-functions" %>

<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="<%=path%>/static/css/css.css">
    <link rel="stylesheet" href="<%=path%>/static/JQuery zTree v3.5.15/css/zTreeStyle/zTreeStyle.css">
    <style>
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:220px;height:200px;overflow-y:scroll;overflow-x:auto;}
    </style>

</head>
<body>
    <form:form method="post" commandName="role">
        <form:hidden path="id"/>
        <form:hidden path="available"/>

        <div class="form-group">
            <form:label path="role">角色名：</form:label>
            <form:input path="role"/>
        </div>

        <div class="form-group">
            <form:label path="description">角色描述：</form:label>
            <form:input path="description"/>
        </div>


        <div class="form-group">
            <form:label path="resourceIds">拥有的资源列表：</form:label>
            <form:hidden path="resourceIds"/>
            <input type="text" id="resourceName" name="resourceName" value="${zhangfn:resourceNames(role.resourceIds)}" readonly>
            <a id="menuBtn" href="#">选择</a>
        </div>

        <form:button>${op}</form:button>

    </form:form>

    <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
        <ul id="tree" class="ztree" style="margin-top:0; width:160px;"></ul>
    </div>

    <script src="<%=path%>/static/js/jquery-1.11.0.min.js"></script>
    <script src="<%=path%>/static/JQuery zTree v3.5.15/js/jquery.ztree.all-3.5.min.js"></script>
    <script>
        $(function () {
            var setting = {
                check: {// tree在点击时的相关设置
                    enable: true ,// 是否显示单选框radio还是复选框checkBox
                    checkboxType: { "Y": "", "N": "" }// YN分别表示复选框勾或取消 ps分别表示操作会影响父子节点
                },
                view: {// tree的显示状态
                    dblClickExpand: false// 双击节点不自动展开
                },
                data: {// tree的数据格式
                    simpleData: {
                        enable: true// 使用简单的数据格式
                    }
                },
                callback: {
                    onCheck: onCheck// 捕获checkbox和radiod勾选或取消时的回调函数
                }
            };

            /**
             * id 当前节点id  唯一
             * pid 父节点id   若为null 则当前节点为顶节点
             * name 当前节点名称
             * childred 子节点(子节点也是json格式) 通常情况四个属性
             * @type {[*]}
             */
            var zNodes =[
                <c:forEach items="${resourceList}" var="r">
                <c:if test="${not r.rootNode}">
                { id:${r.id}, pId:${r.parentId}, name:"${r.name}", checked:${zhangfn:in(role.resourceIds, r.id)}},
                </c:if>
                </c:forEach>
            ];

            function onCheck(e, treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj("tree"),// 必须在init之后方可使用此方法 获取id为tree的对象 该对象是全局的
                        nodes = zTree.getCheckedNodes(true),//
                        id = "",
                        name = "";
                nodes.sort(function compare(a,b){return a.id-b.id;});
                for (var i=0, l=nodes.length; i<l; i++) {
                    id += nodes[i].id + ",";
                    name += nodes[i].name + ",";
                }
                if (id.length > 0 ) id = id.substring(0, id.length-1);
                if (name.length > 0 ) name = name.substring(0, name.length-1);
                $("#resourceIds").val(id);
                $("#resourceName").val(name);
//                hideMenu();
            }

            function showMenu() {
                var cityObj = $("#resourceName");
                var cityOffset = $("#resourceName").offset();
                $("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

                $("body").bind("mousedown", onBodyDown);
            }
            function hideMenu() {
                $("#menuContent").fadeOut("fast");
                $("body").unbind("mousedown", onBodyDown);
            }
            function onBodyDown(event) {
                if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
                    hideMenu();
                }
            }

            $.fn.zTree.init($("#tree"), setting, zNodes);// 初始化
            $("#menuBtn").click(showMenu);
        });
    </script>


</body>
</html>