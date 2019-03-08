<%--
  Created by IntelliJ IDEA.
  User: fyq
  Date: 2018/9/25
  Time: 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript" src="/demo/js/jquery-3.3.1.min.js"></script>

<html>
<head>
    <title>Title</title>
</head>
<body>
<form name="test" method="post" action="/demo/mysev">
    <input type="submit" value="get ip address">
</form>

<form name="test1" method="post" action="/demo/createAccount">
    <input type="text" name="name" />
    <input type="submit" value="create account">
</form>

<form name="logout" method="post" action="/demo/logout">
    <input type="button" value="strengthLevel" onclick="strengthLevel()">
    <input type="submit" value="logout">
</form>

<script>
    function strengthLevel() {
        $.ajax({
            type: "get",
            url: "<%=request.getContextPath()%>/getStrengthLevel",
            data: {password: $("#pwd").val()},
            dataType: "json",
            success: function (data) {
                if (data == 0) {
                    alert("0");
                }
                if (data == 1) {
                    alert("1");

                }
                if (data == 2) {
                   alert("2");

                }
                if (data == -1) {
                    alert("false");
                }
            },
            error: function (msg) {
                alert("error");
            }
        });
    }
</script>

</body>
</html>
