<%-- 
    Document   : index
    Created on : 18 nov. 2017, 00:55:28
    Author     : Ehsan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Form</title>

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
        <link rel="stylesheet" href="css/style.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"></script>
        <link rel="shortcut icon" type="image/png" href="css/market.png"/>
    </head>

    <body>
        <div class="login">
            <h1>Login</h1>

            <form action = "LoginController" method="post">
                <input type="text" name="user" placeholder="Username" required="required" />
                <input type="password" name="pass" placeholder="Password" required="required" />
                <button type="submit" class="btn btn-primary btn-block btn-large">Let me in.</button>
            </form>
        </div>
        <c:choose>
            <%-- On n'a pas trouvé le client --%>
            <c:when test="${empty message}">

            </c:when>
            <c:otherwise> <%-- On a trouvé --%>
                <h2>${message}</h2>

            </c:otherwise>
        </c:choose>


    </body>
</html>

