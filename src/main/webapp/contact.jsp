<%-- 
    Document   : customer
    Created on : 18 nov. 2017, 01:45:14
    Author     : Ehsan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script src="http://builds.handlebarsjs.com.s3.amazonaws.com/handlebars-v4.0.11.js"></script>

        <link rel="stylesheet" href="css/W3Style.css">
         <link rel="stylesheet" href="css/CustomerStyle.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
        <link rel="stylesheet" href="css/contactStyle.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

        <title>Contact us!</title>
    </head>
    <body>

        <div class="w3-top">
            <div class="w3-bar w3-white w3-card" id="myNavbar">

                <div class="w3-dropdown-hover w3-hide-small w3-right">
                    <button class="w3-button " title="Notifications"><i class="fa fa-user"></i>   ${user.name}</button>     
                    <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px">      
                        <a href="${pageContext.request.contextPath}/LoginController?act=out" class="w3-bar-item w3-button"><i class="fa fa-sign-out" aria-hidden="true"></i> Signeout</a>
                    </div>
                </div>

                <div class="w3-left w3-hide-small">
                    <a href="${pageContext.request.contextPath}/customer.jsp" class="w3-bar-item w3-button"><i class="fa fa-th"></i> Back to customer </a>
                    <a href="#team" class="w3-bar-item w3-button"><i class="fa fa-user" title="Connected Users"></i> ${applicationScope.numberConnected}</a>
                </div>

            </div>
        </div>

        <div id="form-main">
  <div id="form-div">
    <form class="form" id="form1">
      
      <p class="name">
        <input name="name" type="text" class="validate[required,custom[onlyLetter],length[0,100]] feedback-input" value="${user.name}" id="name" />
      </p>
      
      <p class="email">
        <input name="email" type="text" class="validate[required,custom[email]] feedback-input" id="email" value="${user.email}" />
      </p>
      
      <p class="text">
        <textarea name="text" class="validate[required,length[6,300]] feedback-input" id="comment" placeholder="Comment"></textarea>
      </p>
      
      
      <div class="submit">
        <input type="submit" value="SEND" id="button-blue"/>
        <div class="ease"></div>
      </div>
    </form>
  </div>

    </body>
</html>

  
