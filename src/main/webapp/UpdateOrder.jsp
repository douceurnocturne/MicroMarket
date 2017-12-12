<%-- 
    Document   : UpdateOrder
    Created on : 20 nov. 2017, 17:36:09
    Author     : Ehsan
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/W3Style.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="css/AddOrderStyle.css"> 
        <link rel="shortcut icon" type="image/png" href="css/market.png"/>
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script type="text/javascript" src="Js/UpdateJS.js"></script>
        <title>Update Order</title>
    </head>
    <body>

        <div class="w3-top">
            <div class="w3-bar w3-white w3-card" id="myNavbar">

                <div class="w3-dropdown-hover w3-hide-small w3-right">
                    <button class="w3-button"><i class="fa fa-user"></i>   ${user.name}</button>     
                    <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px">      
                        <a href="${pageContext.request.contextPath}/LoginController?act=out" class="w3-bar-item w3-button"><i class="fa fa-sign-out" aria-hidden="true"></i> Signeout</a>
                    </div>
                </div>

                <div class="w3-left w3-hide-small">
                    <a href="${pageContext.request.contextPath}/customer.jsp" class="w3-bar-item w3-button"><i class="fa fa-th"></i> Go Back</a>
                    <a href="#team" class="w3-bar-item w3-button"><i class="fa fa-user" title="Connected Users"></i> ${applicationScope.numberConnected}</a>
                                        <a href="#team" class="w3-bar-item w3-button"><i class="fa fa-desktop" title="Number of active sessions"></i> ${applicationScope.numberSession}</a>
                    <a href="#pricing" class="w3-bar-item w3-button"><i class="fa fa-usd"></i> PRICING</a>
                    <a href="${pageContext.request.contextPath}/contact.jsp" class="w3-bar-item w3-button">Contact us</a>
                </div>

            </div>
        </div>

        <div id="title"> Update Order number : ${order.ordernumber}</div>

        <div id="#home">

            <form method="post">
                <input type="hidden" name="action" value="update" readonly>
                <div class="col-2">
                    <label>
                        Customer:
                        <input tabindex="2" type="text" value="${user.name}" readonly>
                        <input name="user" tabindex="2" type="hidden" value=${user} readonly tabindex="1">
                    </label>
                </div>
                <div class="col-2">
                    <label>
                        Order Number:
                        <input type="text" type="number" name="ordernumber" required tabindex="2" value="${order.ordernumber}" readonly>
                    </label>
                </div>


                <div class="col-3">
                    <label>
                        Quantity
                        <input type="number" value="${order.quantity}" required placeholder="Min: 1, Max: 100 " min="1" max="100" id="quantity" name="quantity" tabindex="3">
                    </label>
                </div>
                <div class="col-3">
                    <label>
                        Shipping Cost
                        <input type="number" step="0.01" required name="shipping_cost" tabindex="4" value="${order.shippingcost}">
                    </label>
                </div>
                <div class="col-i">
                    <label>
                        Products:
                        
                        <input id="productCat" name="productCat" required type="hidden" value="${order.product.code}">
                         <select required id ="product_Cat_selector" name='product_Cat_selector' tabindex="5" onchange="getProduct()">
                             <option selected disabled>${order.product.code}</option>
                            <c:forEach var="pro" items="${productCodeslist}">
                                <option value="${pro}">
                                    ${pro}
                                </option>
                            </c:forEach>
                        </select>
                        
                         <input id="productid" name="productid" type="hidden" value="${order.product.productid}">
                         <select id ="product_selector" required name='productlist' tabindex="5" onchange="
                                var e = document.getElementById('product_selector');
                                document.getElementById('productid').value = e.options[e.selectedIndex].value;
                                    console.log(e.options[e.selectedIndex].value);
                                ">
                            <option selected disabled>${order.product.description}</option>

                        </select>
                        

                    </label>
                </div>

                <div class="col-4">
                    <label>
                        Sale Date:
                        <input required name="sale_date" type="date" tabindex="6" value="${order.saledate}">

                    </label>
                </div>
                <div class="col-4">
                    <label>
                        Shipping Date:
                        <input required name="shipping_date" type="date" tabindex="7" value="${order.shippingdate}">

                    </label>
                </div>
                <div class="col-4">
                    <label>
                        Freight Company:
                        <input name="freight" required type="text" tabindex="8" value="${order.freight}">

                    </label>
                </div>

                <div class="col-submit">
                    <button id="addorder_but" type="submit" class="submitbtn">Update order</button>
                </div>

            </form>
        </div> 
                        <div id="msg" style="padding-left: 48px;"> ${message} </div>

    </body>
</html>
