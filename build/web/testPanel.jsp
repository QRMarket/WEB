<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <script src="com/js/lib/jquery-1.11.0.js"></script>        
        <script>
                                    
            $(document).on("click" , "#logout" , function(){                                                  
                $.post('Auth', { 
                    "authDo":"carpeLogout"}, function(data) {             

                    var jsonObj = jQuery.parseJSON( data );  
                    console.log(jsonObj);
                    if(jsonObj['resultCode']==='GUPPY.090'){                           
                        window.location.href = "login.jsp";
                    }
                });                
            
            });
            
            $(document).on("click" , "#getProductInfo" , function(){                                  
                $.post( $( "#productSelected option:selected" ).text() ,
                        {
                            "cdpsDo":"getProduct"
                        } , 
                        function(data) {             

                    var jsonObj = jQuery.parseJSON( data );  
                    console.log(jsonObj);
                    if(jsonObj['resultCode']==='GUPPY.001'){            
                        $('#tr_price').html(jsonObj['content']['price'])
                        $('#tr_priceType').html(jsonObj['content']['priceType'])
                        $('#tr_productID').html(jsonObj['content']['productID'])
                        $('#tr_productName').html(jsonObj['content']['productName'])
                        $('#orderServletTest').css('display','visible');
                    }
                });                
            });
            
            
            $(document).on("click" , "#showProductInfo" , function(){                                  
                alert("Production sayfasına yönlendirilecek")
            });
            
            
            $(document).on("click" , "#addToOrderList" , function(){                                  
                $.post('OrderServlet', { 
                    "cdosDo":"addToOrderList",
                    "cdpUID":"cpr_001",
                    "cdpAmount":"5"}, function(data) {             

                    var jsonObj = jQuery.parseJSON( data );  
                    console.log(jsonObj);
                    if(jsonObj['resultCode']==='GUPPY.001'){                                                             
                        alert("Product added successfully")
                    }
                });                 
            });
            $(document).on("click" , "#addToOrderListAlter" , function(){                                  
                $.post('OrderServlet', { 
                    "cdosDo":"addToOrderList",
                    "cdoID":"bb899262-be93-4f48-846e-a665c53de476",
                    "cdpUID":"cpr_002"}, function(data) {             

                    var jsonObj = jQuery.parseJSON( data );  
                    console.log(jsonObj);
                    if(jsonObj['resultCode']==='GUPPY.001'){                           
                        $('#productServletTest').css('display','visible');
                    }
                });                 
            });
            
        </script>
        
    </head>
    <body>
        
        <div>
            <input id="logout" type="submit" value="LOGOUT">
        </div>
        
        <div id="productServletTest" >
            <select id="productSelected">
                <option value="xxx">http://localhost:8080/QR_Market_Web/ProductServlet?cdpUID=cpr_001</option>
                <option value="xxx">http://localhost:8080/QR_Market_Web/ProductServlet?cdpUID=cpr_002</option>
                <option value="xxx">http://localhost:8080/QR_Market_Web/ProductServlet?cdpUID=cpr_003</option>
                <option value="xxx">http://localhost:8080/QR_Market_Web/ProductServlet?cdpUID=cpr_004</option>
                <option value="xxx">http://localhost:8080/QR_Market_Web/ProductServlet?cdpUID=cpr_005</option>                
            </select>
            <input id="getProductInfo" type="submit" value="Get Production Info">
            <input id="showProductInfo" type="submit" value="Production Info Page">            
        </div>
        
        
        
        <div id="orderServletTest" style="display: none;"> 
            <table id="productInfo" style="border: 2px; border: 1px solid black; padding: 15px;">                                
                <tr><td id="tr_productID"></td></tr>
                <tr><td id="tr_productName"></td></tr>
                <tr><td id="tr_price"></td></tr>
                <tr><td id="tr_priceType"></td></tr>
                <tr><td id="tr_productImage">Product Image will come</td></tr>
            </table>
            <input id="addToOrderList" type="submit" value="Add Product To OrderList">
            <input id="addToOrderListAlter" type="submit" value="Add Product To OrderList (bb899262-be93-4f48-846e-a665c53de476)">
        </div>
        
        
    </body>
</html>
