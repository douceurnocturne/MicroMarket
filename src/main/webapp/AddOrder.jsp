<%-- 
    Document   : AddOrder
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
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="css/AddOrderStyle.css"> 
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <title>New Order</title>
    </head>
    <body>
        <script> 
               function showError(xhr, status, message) {
                alert(JSON.parse(xhr.responseText).message);
            }
            
                function getProduct() {
                // On fait un appel AJAX pour chercher les codes
                var e = document.getElementById('product_Cat_selector');
                document.getElementById('productCat').value = e.options[e.selectedIndex].value;
                var code = document.getElementById('productCat').value;
                $.ajax({
                    url: "ProductByCat",
                    data: {"cat":code},
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                var s = document.getElementById('product_selector');
                                s.innerHTML='';
                                var i;  
                                for (i in result) {
                                       var opt = document.createElement('option');
                                        opt.value = result[i].productid;
                                        opt.innerHTML = result[i].description;
                                        s.appendChild(opt);
                                }
                           
                            }
                });
            }
        </script>
        <div class="w3-top">
            <div class="w3-bar w3-white w3-card" id="myNavbar">

                <div class="w3-dropdown-hover w3-hide-small w3-right">
                    <button class="w3-button"><i class="fa fa-user"></i>   ${user.name}</button>     
                    <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px">      
                        <a href="#" class="w3-bar-item w3-button">blablaa</a>
                        <a href="#" class="w3-bar-item w3-button">do blabla</a>
                        <a href="${pageContext.request.contextPath}/LogoutController" class="w3-bar-item w3-button"><i class="fa fa-sign-out" aria-hidden="true"></i> Signeout</a>
                    </div>
                </div>

                <div class="w3-left w3-hide-small">
                    <a href="${pageContext.request.contextPath}/customer.jsp" class="w3-bar-item w3-button"><i class="fa fa-th"></i> Go Back</a>
                    <a href="#team" class="w3-bar-item w3-button"><i class="fa fa-user"></i> TEAM</a>
                    <a href="#pricing" class="w3-bar-item w3-button"><i class="fa fa-usd"></i> PRICING</a>
                    <a href="#contact" class="w3-bar-item w3-button"><i class="fa fa-envelope"></i> CONTACT</a>
                    <a href="#about" class="w3-bar-item w3-button">ABOUT</a>
                </div>

            </div>
        </div>

        <div id="title"> New Order : </div>

        <div id="#home">

            <form method="post">
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
                        <input type="text" name="ordernumber_input" required tabindex="2">
                    </label>
                </div>


                <div class="col-3">
                    <label>
                        Quantity
                        <input type="number" required placeholder="Min: 1, Max: 100 " min="1" max="100" id="quantity" name="quantity" tabindex="3">
                    </label>
                </div>
                <div class="col-3">
                    <label>
                        Shipping Cost
                        <input type="number" step="0.01" required name="shipping_cost" tabindex="4">
                    </label>
                </div>
                <div class="col-i">
                    <label>
                        Products:
                        
                        <input id="productCat" name="productCat" type="hidden" value="">
                         <select id ="product_Cat_selector" name='product_Cat_selector' tabindex="5" onchange="getProduct()">
                             <option selected disabled>Choose Product Cat here</option>
                            <c:forEach var="pro" items="${productCodeslist}">
                                <option value="${pro}">
                                    ${pro}
                                </option>
                            </c:forEach>
                        </select>
                        
                         <input id="product" name="product" type="hidden" value="">
                        <select id ="product_selector" name='productlist' tabindex="5" onchange="
                                var e = document.getElementById('product_selector');
                                document.getElementById('product').value = e.options[e.selectedIndex].value;
                                    console.log(e.options[e.selectedIndex].value);
                                ">
                            <option selected disabled>Choose Product Cat First</option>

                        </select>
                        

                    </label>
                </div>

                <div class="col-4">
                    <label>
                        Sale Date:
                        <input required name="sale_date" type="date" tabindex="6">

                    </label>
                </div>
                <div class="col-4">
                    <label>
                        Shipping Date:
                        <input required name="shipping_date" type="date" tabindex="7">

                    </label>
                </div>
                <div class="col-4">
                    <label>
                        Freight Company:
                        <input name="freight" required type="text" tabindex="8">

                    </label>
                </div>

                <div class="col-submit">
                    <button id="addorder_but" type="submit" class="submitbtn">Place order</button>
                </div>

            </form>
        </div> 
                        <div id="msg" style="padding-left: 48px;"> ${message} </div>

    </body>
</html>
