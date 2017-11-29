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
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="css/CustomerStyle.css"> 
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        
        <title>Administrator Page</title>
    </head>
    <body>
        
        <script type="text/javascript"> 
            
            function getRandomColor() {
                var letters = '0123456789ABCDEF';
                var color = '#';
                for (var i = 0; i < 6; i++) {
                  color += letters[Math.floor(Math.random() * 16)];
                }
                return color;
              }
            
        google.charts.load("current", {packages:["corechart"]});
        //google.charts.setOnLoadCallback(drawChart);
      
        function showError(xhr, status, message) {
                alert(JSON.parse(xhr.responseText).message);
            }    
            
        function getEarningsProdCode(){
          var sdate = document.getElementById('start_date').value;
          var edate = document.getElementById('end_date').value;
          console.log(sdate);
          console.log(edate);
          $.ajax({
                    url: "GraphicsController",
                    data: {"graphic":"codes","start_date":sdate,"end_date":edate},
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                             
                               // data.addColumn('string', 'Name');
                               // data.addColumn('number', 'Salary');
                               var map = [];
                          
                               map.push(['Cat','Benefits',{ role: 'style' }]);
                                
                                for (var key in result) {
                                    var val = result[key];
                                    map.push([key,val,'color:'+getRandomColor()]);
                                  
                                    console.log([key,val]);

                                }
                                var data = google.visualization.arrayToDataTable(map);
                                    /*
                                    var options = {
                                        title: 'Earnings By Products Codes',
                                        pieHole: 0.3
                                        }; */
        
                                    var options = {
                                    title: 'Earnings By Products Codes',
                                    
                                    vAxis: {
                                      title: 'Earnings scale'
                                    }
                                  };
                                        
                                        
                                    var chart = new google.visualization.ColumnChart(document.getElementById('graphics'));
                                    chart.draw(data, options);
                           
                                
                            }
                });
        };
        
        </script>

        <div class="w3-top">
            <div class="w3-bar w3-white w3-card" id="myNavbar">

                <div class="w3-dropdown-hover w3-hide-small w3-right">
                    <button class="w3-button" ><i class="fa fa-user"></i>   ${user.name}</button>     
                    <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px">      
                        <a href="#" class="w3-bar-item w3-button">blablaa</a>
                        <a href="#" class="w3-bar-item w3-button">do blabla</a>
                        <a href="${pageContext.request.contextPath}/LogoutController" class="w3-bar-item w3-button"><i class="fa fa-sign-out" aria-hidden="true"></i> Signeout</a>
                    </div>
                </div>
                
                <div class="w3-left w3-hide-small">
                    <a href="#work" class="w3-bar-item w3-button"><i class="fa fa-th"></i> Earnings By Zone</a>
                    <a href="#team" class="w3-bar-item w3-button"><i class="fa fa-user"></i> Earnings By Customer</a>
                    <a href="#" onclick="getEarningsProdCode()" class="w3-bar-item w3-button"><i class="fa fa-usd"></i>  Earnings By Product Cat</a>

                </div>
                    <div class="w3-right w3-hide-small">
                        <a class="w3-bar-item w3-button"> <i class="fa fa-calendar"> </i> Start Date :
                     <input  id="start_date" required name="start_date" type="date"></a>                  
                    <a class="w3-bar-item w3-button"> <i class="fa fa-calendar"> </i> End Date : 
                     <input id="end_date" required name="end_date" type="date"></a>              
                    </div>
            </div>
        </div>
                    
        <div id="graphics" style="width:800px; height: 800px; margin:0 auto;"></div>
    </body>
</html>
