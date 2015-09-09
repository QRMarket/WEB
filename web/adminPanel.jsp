<!DOCTYPE html>
<html>
    <head>
        <title>GUPPY Test Panel </title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">                   
        
       
        
        <!-- IMPORT Bootstarp LIBRARIES -->        
        <link href="com/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="com/dist/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css" />        
                
        <!-- IMPORT JS LIBRARIES -->        
        <script src="com/js/lib/jquery-1.11.0.js"></script> 
        <script src="com/dist/js/bootstrap.min.js"></script>
                        
        <script>
    
            $(document).ready(function(){                
                for(i=0 ; i<$('#productSelected').children().size(); i++){                 
                    $($('#productSelected').children()[i]).html(window.location.origin + $($('#productSelected').children()[i]).html());
                }
            });
    
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
            
/**
********************************************************************************
********************************************************************************
                            MAIN OPERATION
******************************************************************************** 
********************************************************************************
*/
            $(document).on('click', '#productMain' , function(){       
                $('.containerMain:visible').fadeOut("fast" , function(){                    
                    $('#containerMain').fadeIn("fast");                    
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
                        $('#tr_productUID').html(jsonObj['content']['productID'])
                        $('#tr_productName').html(jsonObj['content']['productName'])
                        $('#orderServletTest').css('display','visible');
                        $('#cartNoproduct').css('display','none');
                        
                    }
                });                
            });
            
            
            $(document).on("click" , "#showProductInfo" , function(){                                                  
                alert("Go to production INFO page -- onProgress");
            });
            
            
            $(document).on("click" , "#addToOrderList" , function(){                                  
                                                
                if($('#orderServletTest').css('display')=="none"){
                    alert("It is not selected any Product");
                }else{
                    
                    $.post('OrderServlet', { 
                        "cdosDo":"productUpdate",
                        "cdpUID":$('#tr_productUID').text(),
                        "cdpAmount":$( "#productAmount option:selected" ).val()}, function(data) {             

                        var jsonObj = jQuery.parseJSON( data );  
                        console.log(jsonObj);
                        if(jsonObj['resultCode']==='GUPPY.001'){                                                             
                            alert("Product added successfully");
                            $('#orderServletTest').css('display','none');
                            $('#cartNoproduct').css('display','visible');
                        }
                    }); 
                    
                }
                
                
                                
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
            
            
            /**
             *******************************************************************
             *******************************************************************
                                        ORDER OPERATION
             ******************************************************************* 
             *******************************************************************
             */
            $(document).on('click', '#getCurrentBasket' , function(){                
                $.post('OrderServlet', {
                    "cdosDo":"getCurrentOrderInfo" }, function(data) {             

                    var jsonObj = jQuery.parseJSON( data );                      
                    console.log(jsonObj);
                    if(jsonObj['resultCode']=='GUPPY.001'){
                        $("#cartProductInfo").children().remove();
                        
                        $("#confirmCart").show();
                        for(i=0; i<jsonObj['content'].length; i++){                                    
                            row = '<tr>' + '<td class="info">' + jsonObj['content'][i]['productID'] +'</td>';
                            row = row + '<td class="info">' + jsonObj['content'][i]['productName'] +'</td>';
                            row = row + '<td class="info">' + jsonObj['content'][i]['price'] +'</td>';
                            row = row + '<td class="info">' + jsonObj['content'][i]['priceType'] +'</td>';
                            row = row + '<td class="info">' + jsonObj['content'][i]['amount'] +'</td>';
                            row = row + '<td class="info">' + 'TOTAL ::' + (jsonObj['content'][i]['price']*jsonObj['content'][i]['amount']) +'</td>';
                            row = row + '</tr>'
                            $("#cartProductInfo").append(row);                                
                        }
                    }else{
                        alert('Current Basket empty');
                    }                                                            
                    
                }); 
            });
            
            $(document).on('click', '#getOrderList' , function(){                
                $.post('OrderServlet', {
                    "cdosDo":"getOrderLists" }, function(data) {             

                    var jsonObj = jQuery.parseJSON( data );                      
                    console.log(jsonObj);                    
                    if(jsonObj['resultCode']=='GUPPY.001'){
                        $("#orderSelected").children().remove();
                        for(i=0; i<jsonObj['content'].length; i++){                            
                            option = '<option>'+jsonObj['content'][i]+'</div>';
                            $("#orderSelected").append(option);
                        }
                    }                                                            
                    
                }); 
            });
            
            $(document).on('click', '#getOrderInfo' , function(){       
                $.post('OrderServlet', {
                    "cdosDo":"getOrderInfo",
                    "cdoID":$( "#orderSelected option:selected" ).val()}, function(data) {             

                    var jsonObj = jQuery.parseJSON( data );                      
                    console.log(jsonObj);
                    if(jsonObj['resultCode']=='GUPPY.001'){
                        $("#cartProductInfo").children().remove();
                        $("#confirmCart").css('display' , 'none');
                        for(i=0; i<jsonObj['content'].length; i++){                                    
                            row = '<tr>' + '<td class="info">' + jsonObj['content'][i]['productID'] +'</td>';
                            row = row + '<td class="info">' + jsonObj['content'][i]['productName'] +'</td>';
                            row = row + '<td class="info">' + jsonObj['content'][i]['price'] +'</td>';
                            row = row + '<td class="info">' + jsonObj['content'][i]['priceType'] +'</td>';
                            row = row + '<td class="info">' + jsonObj['content'][i]['amount'] +'</td>';
                            row = row + '<td class="info">' + 'TOTAL ::' + (jsonObj['content'][i]['price']*jsonObj['content'][i]['amount']) +'</td>';
                            row = row + '</tr>'
                            $("#cartProductInfo").append(row);                                
                        }
                    }                                                                                
                }); 
            });
            
            
            /**
             *******************************************************************
             *******************************************************************
                                CONFIRM CART OPERATION
             ******************************************************************* 
             *******************************************************************
             */
            
            $(document).on('click', '#confirmCart' , function(){       
                $.post('OrderServlet', {
                    "cdosDo":"confirmOrderList"}, function(data) {             

                    var jsonObj = jQuery.parseJSON( data );                      
                    console.log(jsonObj);
                    if(jsonObj['resultCode']=='GUPPY.001'){
                        
                    }                                                                                
                });
            });
            
/**
********************************************************************************
********************************************************************************
                            PRODUCT OPERATION
******************************************************************************** 
********************************************************************************
*/
            $(document).on('click', '#productOperation' , function(){       
                $('.containerMain:visible').fadeOut("fast" , function(){                    
                    $('#containerProduct').fadeIn("fast");                    
                });                
            });
            
            $(document).on('click', '#cp_Operations' , function(event){       
                
                hideOperationRows();
                id = $(event.target).attr("id");
                $('#'+ id + '_ROW').fadeIn("fast");
                
            });
            
            function hideOperationRows(){
                
                $('#cp_PRODUCTION_ADD_ROW').hide();
                $('#cp_PRODUCTION_GET_LIST_ROW').hide();
                $('#cp_PRODUCTION_EDIT_ROW').hide();
                $('#cp_PRODUCTION_DELETE_ROW').hide();
            }
 
  
/**
********************************************************************************
********************************************************************************
                            MARKET OPERATION 
******************************************************************************** 
********************************************************************************
*/
            $(document).on('click', '#marketOperation' , function(){       
                $('.containerMain:visible').fadeOut("fast" , function(){                    
                    $('#containerMarket').fadeIn("fast");                       
                });                
            });               
        </script>
        
        
        <!--RESIZE OPERATIONS-->
        <script language="javascript" type="text/javascript">
            
            function resizeIframe(obj) {
                obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
            }
            
            $(document).mousemove(function(event){                
                resizeIframe($('#marketFrame').get(0));
                resizeIframe($('#productFrame').get(0));
            });
            
            
        </script>
        
    </head>
    <body>
        
        <nav class="navbar navbar-inverse">
            <div class="container">
                <div class="navbar-header">                    
                    <a class="navbar-brand" href="#">Guppy Project</a>
                </div>
                <div id="navbar" class="collapse navbar-collapse">
                    
                    <ul class="nav navbar-nav navbar-left">                        
                        <li class="active">
                            <button id="productMain" type="button" class="btn btn-default navbar-btn" style="margin-left: 3px;">Home</button>
                        </li>
                        <li class="active">
                            <button id="productOperation" type="button" class="btn btn-default navbar-btn " style="margin-left: 3px;">Product Operation</button>
                        </li>
                        
                        <li class="active">
                            <button id="marketOperation" type="button" class="btn btn-default navbar-btn " style="margin-left: 3px;">Market Operation</button>
                        </li>
                    </ul>
                    
                    <ul class="nav navbar-nav navbar-right">                        
                        <li class="active">
                            <button id="logout" type="button" class="btn btn-default navbar-btn ">Logout</button>
                        </li>
                    </ul>  
                     
                </div>
                
            </div>                       
        </nav>
        
        
<!--
********************************************************************************
********************************************************************************
                                MARKET CONTAINER
********************************************************************************
********************************************************************************
-->

        <div id="containerMarket" class="container containerMain" style="display: none"  >
            <iframe id="marketFrame" src="marketOperator.jsp" frameBorder=0  height="100%" width="100%" ></iframe>
        </div>
        
                
<!--
********************************************************************************
********************************************************************************
                                PRODUCT CONTAINER
********************************************************************************
********************************************************************************
-->
        <div id="containerProduct" class="container containerMain" style="display: none;">                      
            <iframe id="productFrame" src="productOperator.jsp" frameBorder=0  height="100%" width="100%" ></iframe>                         
        </div>
        
        
        
        
<!--
********************************************************************************
********************************************************************************
                                MAIN CONTAINER
********************************************************************************
********************************************************************************
-->
        <div id="containerMain" class="container containerMain">
            
            
            <!--
            ********************************************************************
            ********************************************************************
                                    PRODUCT AREA (ROW)
            ********************************************************************
            ********************************************************************
            -->
            <div id="productServletTest" class="row" >
                
                <div id="userCart" class="col-md-4" style="padding: 5px;" >
                    
                    <div id="orderServletTest" style="display: none;">
                        <div class="row"> 
                            <table class="table table-hover" id="productInfo" style="margin: 5px;">                                
                                <tr><td class="info">ID</td><td id="tr_productUID"></td></tr>
                                <tr><td class="info">Name</td><td id="tr_productName"></td></tr>
                                <tr><td class="info">Price</td><td id="tr_price"></td></tr>
                                <tr><td class="info">Price T</td><td id="tr_priceType"></td></tr>
                                <tr><td class="info">Image</td><td id="tr_productImage">Product Image will come</td></tr>
                            </table>  
                            
                        </div>

                        <div>                            
                            <select id="productAmount" class="form-control" style="margin: 5px;">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                            </select>
                        </div>
                    </div>
                    
                    <div id="cartNoproduct">
                        No Product
                    </div>
                    
                </div> 
                
                <div id="ProductList" class="col-md-8">
                    
                    <select id="productSelected" class="form-control" style="margin: 5px;">
                        <option value="xxx">/QR_Market_Web/ProductServlet?cdpUID=cpr_001</option>
                        <option value="xxx">/QR_Market_Web/ProductServlet?cdpUID=cpr_002</option>
                        <option value="xxx">/QR_Market_Web/ProductServlet?cdpUID=cpr_003</option>
                        <option value="xxx">/QR_Market_Web/ProductServlet?cdpUID=cpr_004</option>
                        <option value="xxx">/QR_Market_Web/ProductServlet?cdpUID=cpr_005</option>                
                    </select>
                    <input style="margin: 5px;" id="getProductInfo" class="btn btn-default" type="button" value="Get Production Info">
                    <input style="margin: 5px;" id="addToOrderList" class="btn btn-default" type="button" value="Add Product To Cart">
                    <input style="margin: 5px;" id="showProductInfo" class="btn btn-default" type="button" value="Production Info Page">                     
                </div>                                               
                           
            </div>

            
            
            <!--
            ********************************************************************
            ********************************************************************
                                    ORDER AREA (ROW)
            ********************************************************************
            ********************************************************************
            -->            
            <div id="cartArea" class="row" style="margin-top: 20px;" >
                
                
                <div id="cartInfo" class="col-md-4" style="padding: 5px;" >
                    <table class="table table-hover" id="cartProductInfo" style="margin: 5px;">
                        
                    </table> 
                    <input style="margin: 5px; display: none;" id="confirmCart" class="btn btn-default" type="button" value="Confirm Cart">
                </div>
                                
                
                
                <div id="cartList" class="col-md-8"  >
                    
                    <select id="orderSelected" class="form-control" style="margin: 5px;">
                        
                        
                    </select>
                    <input style="margin: 5px;" id="getOrderInfo" class="btn btn-default" type="button" value="Get Order Info">
                    <input style="margin: 5px;" id="getOrderList" class="btn btn-default" type="button" value="Get Order List">
                    <input style="margin: 5px;" id="getCurrentBasket" class="btn btn-default" type="button" value="Current Basket">
                </div>
                
            </div>
                                    
            
        </div>
        
        
    </body>
</html>
