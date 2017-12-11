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
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <script type="text/javascript" src="Js/CustomerJS.js"></script>

        <title>Customer Page</title>
    </head>
    <body>

   
        <script type="text/x-handlebars-template" id="orderTemplate">
            
            <TABLE border="1">
            <tr>
            <th>Order Number</th>
            <th>Produit description</th>
            <th>Quantite</th>
            <th>Shipping cost</th>
            <th>Sales date</th>
            <th>Shipping date</th>
            <th>Freight company</th>
            <th>Action</th>
            </tr>
                 {{#orders}}
                <TR>
                    <TD>{{ordernumber}}</TD><TD>{{product.description}}</TD>                    
                    <TD>{{quantity}}</TD><TD>{{shippingcost}}</TD>
                    <TD>{{saledate}}</TD><TD>{{shippingdate}}</TD>
                    <TD>{{freight}}</TD>
                     
                    <TD><form action="UpdateOrder" method="post">
                         <input type="hidden" name="ordernumber" value="{{ordernumber}}" />
                         <input type="hidden" name="action" value="get" />
                         <input type="submit" value="Update" />
                         
                        </form>
                        <button onclick="deleteOrder({{ordernumber}})">Delete</button>
                    </TD>
                </TR>
                {{/orders}}
            </TABLE>
            </script>

        <div class="w3-top">
            <div class="w3-bar w3-white w3-card" id="myNavbar">

                <div class="w3-dropdown-hover w3-hide-small w3-right">
                    <button class="w3-button " title="Notifications"><i class="fa fa-user"></i>   ${user.name}</button>     
                    <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px">      
                        <a href="${pageContext.request.contextPath}/LoginController?act=out" class="w3-bar-item w3-button"><i class="fa fa-sign-out" aria-hidden="true"></i> Signeout</a>
                    </div>
                </div>

                <div class="w3-left w3-hide-small">
                    <a href="${pageContext.request.contextPath}/AddOrder" class="w3-bar-item w3-button"><i class="fa fa-th"></i> New Order </a>
                    <a href="#team" class="w3-bar-item w3-button"><i class="fa fa-user" title="Connected Users"></i> ${applicationScope.numberConnected}</a>
                    <a href="#pricing" class="w3-bar-item w3-button"><i class="fa fa-usd"></i> PRICING</a>
                    <a href="${pageContext.request.contextPath}/contact.jsp" class="w3-bar-item w3-button">Contact us</a>
                </div>

            </div>
        </div>
        <div id="customerorders" style="padding-top:100px; padding-left: 48px; font-size: 28px;"> Welcome back ${user.name} </div>
        <div id="home">
            
        </div>
        <div style="padding-top:20px;">
            
        </div>
    </body>
</html>
