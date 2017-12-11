/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
            
        function getEarningsProdCode(code){
          var sdate = document.getElementById('start_date').value;
          var edate = document.getElementById('end_date').value;
          
          if (sdate === "" || edate === "" ) {
              alert("Please Pick a date");
          } else {

          $.ajax({
                    url: "GraphicsController",
                    data: {"graphic":code,"start_date":sdate,"end_date":edate},
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {

                               var map = [];
                          
                               map.push(['Cat','Benefits',{ role: 'style' }]);
                                
                                for (var key in result) {
                                    var val = result[key];
                                    map.push([key,val,'color:'+getRandomColor()]);
                                  
                             

                                }
                                var data = google.visualization.arrayToDataTable(map);
        
        
                                    var options = {
                                    title: 'Earnings By '+code,
                                    
                                    vAxis: {
                                      title: 'Earnings scale'
                                    }
                                  };
                                      var chart;  
                                   switch(code) {
                                        case "codes":
                                            chart = new google.visualization.ColumnChart(document.getElementById('graphics'));
                                            break;
                                        case "geo":
                                            chart = new google.visualization.PieChart(document.getElementById('graphics'));
                                            break;
                                        case "client":
                                            chart = new google.visualization.BarChart(document.getElementById('graphics'));
                                            break;
                                     
                                    }   
                                    
                                
                                    chart.draw(data, options);
                           
                                
                            }
                });
        }};