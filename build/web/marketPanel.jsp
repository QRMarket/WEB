<!DOCTYPE html>
<html>
    <head>
        <title>GUPPY Test Panel</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">                   
        
        
        
        <!-- IMPORT Bootstarp LIBRARIES -->        
        <link href="com/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="com/dist/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css" />        
                
        <!-- IMPORT JS LIBRARIES -->        
        <script src="com/js/lib/jquery-1.11.0.js"></script> 
        <script src="com/dist/js/bootstrap.min.js"></script>
                        
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
                                                
        </script>
        
        <style>
            
            .navButton{
                margin-left: 5px;
            }
            
        </style>
        
    </head>
    <body>
            <%@ page import="java.util.List" %>
            <%! int x = 5; %> 
        
            <!--
            ********************************************************************
            ********************************************************************
                                    NAVBAR AREA 
            ********************************************************************
            ********************************************************************
            -->
            <nav class="navbar navbar-inverse">
                <div class="container">
                    <div class="navbar-header">                    
                        <a class="navbar-brand" href="#">Guppy Project <%=request.getSession(false).getAttribute("cduType")%> </a>
                    </div>
                    <div id="navbar" class="collapse navbar-collapse">

                        <ul class="nav navbar-nav navbar-left">                        
                            <li class="navButton">
                                <button type="button" class="btn btn-default navbar-btn ">Home</button>
                            </li>
                            <li class="navButton">
                                <button type="button" class="btn btn-default navbar-btn ">Taken Orders </button>
                            </li>
                            <%                                 
                                if( ((String)request.getSession(false).getAttribute("cduType")).equalsIgnoreCase("MARKETADMIN")){
                                    out.write("<li class=\"navButton\">");
                                    out.write("<button type=\"button\" class=\"btn btn-default navbar-btn \">ADMIN PAGE</button>");
                                    out.write("</li>");
                                }                                                                
                            
                            %>                            
                        </ul>

                        <ul class="nav navbar-nav navbar-right">                        
                            <li class="active">
                                <button id="logout" type="button" class="btn btn-default navbar-btn ">Logout</button>
                            </li>
                        </ul>  

                    </div>

                </div>                       
            </nav>


            <div class="container">
            
            
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

            
            
            
        
        
    </body>
</html>
