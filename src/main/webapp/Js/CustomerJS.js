/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



            $(document).ready(// Exécuté à la fin du chargement de la page
                    function () {
                        // On montre la liste des codes
                        showCodes();
                    }
            );

            function showError(xhr, status, message) {
                alert(JSON.parse(xhr.responseText).message);
            }

            function showCodes() {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "ShowCustomerOrders",
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                var source = $("#orderTemplate").html(); 
                                var template = Handlebars.compile(source);
                                $('#home').html(template(result));
                            }
                });
            }

            function deleteOrder(ordernumber) {
              $.ajax({
                    type: "POST",
                    url: "DeleteOrder",
                    data: {"ordernumber":ordernumber},
                    success: showCodes,
                    error: showError
                  });
                
            };