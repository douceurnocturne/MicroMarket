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
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="css/CustomerStyle.css"> 
        <title>Customer Page</title>
    </head>
    <body>

        <div class="w3-top">
            <div class="w3-bar w3-white w3-card" id="myNavbar">

                <div class="w3-dropdown-hover w3-hide-small w3-right">
                    <button class="w3-button " title="Notifications"><i class="fa fa-user"></i>   ${user.name}</button>     
                    <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px">      
                        <a href="#" class="w3-bar-item w3-button">blablaa</a>
                        <a href="#" class="w3-bar-item w3-button">do blabla</a>
                        <a href="#" class="w3-bar-item w3-button"><i class="fa fa-sign-out" aria-hidden="true"></i> Signeout</a>
                    </div>
                </div>
                
                <div class="w3-left w3-hide-small">
                    <a href="#work" class="w3-bar-item w3-button"><i class="fa fa-th"></i> WORK</a>
                    <a href="#team" class="w3-bar-item w3-button"><i class="fa fa-user"></i> TEAM</a>
                    <a href="#pricing" class="w3-bar-item w3-button"><i class="fa fa-usd"></i> PRICING</a>
                    <a href="#contact" class="w3-bar-item w3-button"><i class="fa fa-envelope"></i> CONTACT</a>
                    <a href="#about" class="w3-bar-item w3-button">ABOUT</a>
                </div>
 
            </div>
        </div>

        <div id="home">
            <h1> Customer : ${user.name} </h1>
            <table border="1">

                <tr><th>Order Number</th><th>Produit description</th>
                    <th>Quantite</th><th>Shipping cost</th>
                    <th>Sales date</th><th>Freight company</th>
                    <th>Action</th>
                </tr>

                <c:forEach var="record" items="${orders}">

                    <tr><td>${record.ordernumber}</td><td>${record.product.description}</td>
                        <td>${record.quantity}</td><td>${record.shippingcost}</td>
                        <td>${record.date}</td><td>${record.freight}</td>
                        <td><button name="${record.ordernumber}" method="Post" type="button" >Update</button>
                            <button name="${record.ordernumber}" method="Post" type="button" >Delete</button></td>
                    </tr>
                </c:forEach>

            </table>
        </div>
    </body>
</html>
