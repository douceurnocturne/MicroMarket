/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



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
                                var def=document.createElement('option');
                                def.value="";
                                def.innerHTML="Chose";
                                s.appendChild(def);
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