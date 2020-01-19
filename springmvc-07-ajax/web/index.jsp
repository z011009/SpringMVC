<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/jquery-3.4.1.min.js"></script>
    <script>
        function a() {
            $.post({
                url: "${pageContext.request.contextPath}/a1",
                data: {"name": $("#username").val()},
                success: function (data) {
                    alert(data);
                }
            });
        }
    </script>
</head>
<body>
<%--失去焦点的时候，发起一个请求到后台--%>
用户名：<input type="text" id="username" onblur="a()"/>
</body>
</html>
