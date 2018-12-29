<%--
  Created by IntelliJ IDEA.
  User: fyq
  Date: 2018/9/25
  Time: 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript" src="/WEB-INF/js/jquery-3.3.1.min.js"></script>

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
                    $("#level").html("");
                    $("#level").attr("class", "pwd-meter-label-strength-weak");
                    $("#p1").attr("class", "pwd-meter-indicator-strength-weak");
                }
                if (data == 1) {
                    $("#level").html("");
                    $("#level").attr("class", "pwd-meter-label-strength-medium");
                    $("#p1").attr("class", "pwd-meter-indicator-strength-medium");

                }
                if (data == 2) {
                    $("#level").html("");
                    $("#level").attr("class", "pwd-meter-label-strength-strong");
                    $("#p1").attr("class", "pwd-meter-indicator-strength-strong");

                }
                if (data == -1) {
                    $("#level").html("-");
                    $("#p1").removeClass();
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
