<%-- 
    Document   : admin
    Created on : 18 nov. 2017, 01:45:01
    Author     : Ehsan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/W3Style.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="css/CustomerStyle.css"> 
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript" src="Js/adminJS.js"></script>
        
        <title>Administrator Page</title>
    </head>
    <body>

        <div class="w3-top">
            <div class="w3-bar w3-white w3-card" id="myNavbar">

                <div class="w3-dropdown-hover w3-hide-small w3-right">
                    <button class="w3-button" ><i class="fa fa-user"></i>   ${user.name}</button>     
                    <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px">      
                        <a href="${pageContext.request.contextPath}/LoginController?act=out" class="w3-bar-item w3-button"><i class="fa fa-sign-out" aria-hidden="true"></i> Signeout</a>
                    </div>
                </div>
                
                <div class="w3-left w3-hide-small">
                    <a href="#" onclick="getEarningsProdCode('geo')" class="w3-bar-item w3-button"><i class="fa fa-th"></i> Earnings By Zone</a>
                    <a href="#" onclick="getEarningsProdCode('client')" class="w3-bar-item w3-button"><i class="fa fa-user"></i> Earnings By Customer</a>
                    <a href="#" onclick="getEarningsProdCode('codes')" class="w3-bar-item w3-button"><i class="fa fa-usd"></i>  Earnings By Product Cat</a>

                </div>
                    <div class="w3-right w3-hide-small">
                        <a class="w3-bar-item w3-button"> <i class="fa fa-calendar"> </i> Start Date :
                     <input  id="start_date" required name="start_date" type="date" value="2010-01-01"></a>                  
                    <a class="w3-bar-item w3-button"> <i class="fa fa-calendar"> </i> End Date : 
                     <input id="end_date" required name="end_date" type="date" value="2012-01-01"></a>  
                                         <a href="#" class="w3-bar-item w3-button"><i class="fa fa-user" title="Connected Users"></i> ${applicationScope.numberConnected}</a>
                                                             <a href="#team" class="w3-bar-item w3-button"><i class="fa fa-desktop" title="Number of active sessions"></i> ${applicationScope.numberSession}</a>
                    </div>
            </div>
        </div>
                    
        <div id="graphics" style="width:800px; height: 800px; margin:0 auto;"></div>
    </body>
</html>
