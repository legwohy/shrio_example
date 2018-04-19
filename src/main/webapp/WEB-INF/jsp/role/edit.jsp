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
 <%-- <form method="post" >
        <div class="form-group">
            <label>角色名:</label>
            <input type="text" name="role" value="${role.role}"/>
        </div>

        <div class="form-group">
            <label>角色描述:</label>
            <input type="text" name="description" value="${role.description}"/>
        </div>

        <div class="form-group">
            <!-- 后端需要传回改角色下的资源列表-->
            <label>角色描述:</label>拥有的资源列表：</label>
            <input type="text" id="resourceName" name="resourceName" value="${zhangfn:resourceNames(role.resourceIds)}" readonly style="width: 300px;">
            <a id="menuBtn" href="#">选择</a>
        </div>
        <button>新增</button>
    </form>--%>

    <!-- commandName 对应实体属性 method 可以支持delete put action非必需-->
 <span id="sb">点击此显示权限树</span>
    <form:form method="post" commandName="role">
        <form:hidden path="id"/>
        <form:hidden path="available"/>


        <div class="form-group">
            <!-- 相当于<<label for="role"/>-->
            <form:label path="role">角色名：</form:label>

            <!-- 相当于<input id='role' name='role' values='Admin' />-->
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

        <!-- 提交表单-->
        <form:button>${op}</form:button>

    </form:form>
    <!-- 权限树-->
    <div id="menuContent" class="menuContent" style="display:none; position: absolute;" >
        <span>我也是权限目录树的一部分</span><br/>
        <ul id="tree" class="ztree" style="margin-top:100px; width:160px;"></ul>
    </div>

    <script src="<%=path%>/static/js/jquery-1.11.0.min.js"></script>
    <script src="<%=path%>/static/JQuery zTree v3.5.15/js/jquery.ztree.all-3.5.min.js"></script>
    <script>
        /** zTree 权限树
         * a、初始化权限树 $.fn.zTree.init($("#tree"), setting, zNodes);
         *  1、树的位置 div布局
         *  2、设置(单复选框,显示样式,节点数据,回调函数)
         *  3、资源节点 简单样式包括(id,pid,name,checked)
         * b、显示或隐藏权限树
         */
        $(function () {
            // 设置 四个(单复选框,显示样式,节点数据,回调函数)
            var setting = {
                check: {// 单复选框
                    enable: true ,// 是否显示radio/checkbox
                    checkboxType: { "Y": "", "N": "" }// YN分别表示复选框勾或取消 ps分别表示操作会影响父子节点
                },
                view: {// tree的显示样式
                    dblClickExpand: true// 双击节点自动展开
                },
                data: {// tree的数据格式
                    simpleData: {
                        enable: true// 使用简单的数据格式 则节点数据必须满足父子关系
                    }
                },
                callback: {
                    onCheck: onCheck
                }
            };

            /**
             * id 当前节点id  唯一
             * pid 父节点id   若为null 则当前节点为顶节点
             * name 当前节点名称
             * checked 检查状态
             */
            // 加载所有的资源节点
            var zNodes =[
                <c:forEach items="${resourceList}" var="r">
                <c:if test="${not r.rootNode}">
                    {
                        id:${r.id},
                        pId:${r.parentId},// 基本格式必须有父节点 相当于根据父节点分组
                        name:"${r.name}",
                        checked:${zhangfn:in(role.resourceIds, r.id)}// 此资源属于角色下的资源 返回true 勾选复选框
                    },
                </c:if>
                </c:forEach>
            ];

            /** 回调函数 获取选择的内容填充数据*/
            function onCheck(e, treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj("tree"),// 必须在init初始化之后方可使用此方法 获取id为tree的对象 该对象是全局的
                    nodes = zTree.getCheckedNodes(true),//输入框 true 勾选的集合 false未勾选的集合
                    id = "",
                    name = "";
                nodes.sort(function compare(a,b){return a.id-b.id;});// 资源id倒叙

                // 取出所有节点的id和name
                for (var i=0, len=nodes.length; i<len; i++) {
                    id += nodes[i].id + ",";
                    name += nodes[i].name + ",";
                }

                // 去掉末尾的 "，"
                if (id.length > 0 ) id = id.substring(0, id.length-1);
                if (name.length > 0 ) name = name.substring(0, name.length-1);

                // 存入相应的id和资源名称集合
                $("#resourceIds").val(id);
                $("#resourceName").val(name);
                // hideMenu();
            }

            // 选择按钮 显示权限目录菜单
            function showMenu() {
                var cityObj = $("#resourceName");
                var cityOffset = $("#resourceName").offset();// 获取坐标值(top,left)

                // 向下展开权限目录
                $("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

                // 鼠标按下事件
                $("body").bind("mousedown", onBodyDown);
            }

            // 鼠标按下隐藏权限树
            function onBodyDown(event) {
                if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
                    hideMenu();
                }
            }

            // 隐藏权限树方法
            function hideMenu() {
                $("#menuContent").fadeOut("fast");
                $("body").unbind("mousedown", onBodyDown);
            }


            $.fn.zTree.init($("#tree"), setting, zNodes);// 初始化(tree布局,设置,节点)
            $("#menuBtn").click(showMenu);// 选择按钮 显示权限目录菜单 总开关

            var status=1;

            $("#sb").click(function () {
                if(status == 1){
                    $("#menuContent").slideDown("fast");
                    status=2;
                }else if(status == 2){
                    $("#menuContent").fadeOut("fast");
                    status = 1;
                }

            });
        });
    </script>


</body>
</html>