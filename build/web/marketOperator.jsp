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
/**
********************************************************************************
********************************************************************************
                            MARKET OPERATION
******************************************************************************** 
********************************************************************************
*/
                       
                                  
            $(document).on('click', '#cp_Operations' , function(event){
                hideOperationRows();
                id = $(event.target).attr("id");
                $('#'+ id + '_ROW').fadeIn("fast");
                
            });
            
            function hideOperationRows(){                
                $('#cp_MARKET_ADD_ROW').hide();
                $('#cp_MARKET_GET_LIST_ROW').hide();
                $('#cp_MARKET_EDIT_ROW').hide();
                $('#cp_MARKET_DELETE_ROW').hide();
            }
 
            //**************************************************************
            //**************************************************************
            //**                ADRESS HANDLER
            //**************************************************************
            //**************************************************************
            $(document).on('change click', 'select' , function(event){
                                                            
                    if($(this).attr('id')=='address_city'){                        
                        // GET BOROUGHS FROM ADDRESS SERVLET
                        $.post('AddressServlet', {                         
                            "cdasDO":"getBoroughList",
                            "cdaCity":$( "#address_city option:selected").val()}, function(data) {

                            var jsonObj = jQuery.parseJSON( data );                          
                            if(jsonObj['resultCode']==='GUPPY.001'){                                
                                $("#address_borough").children().remove();
                                $("#address_locality").children().remove();
                                for(m=0; m<jsonObj['content'].length; m++){
                                    $("#address_borough").append('<option value="'+jsonObj['content'][m]+'">'+jsonObj['content'][m]+'</option>"');
                                }                        
                            }
                        });
                        
                    }else if($(this).attr('id')=='address_borough'){                        
                        // GET LOCALITIES FROM ADDRESS SERVLET
                        $.post('AddressServlet', {                         
                            "cdasDO":"getLocalityList",
                            "cdaCity":$( "#address_city option:selected").val(),
                            "cdaBorough":$( "#address_borough option:selected").val()}, function(data) {

                            var jsonObj = jQuery.parseJSON( data );                          
                            if(jsonObj['resultCode']==='GUPPY.001'){
                                $("#address_locality").children().remove();
                                for(m=0; m<jsonObj['content'].length; m++){
                                    $("#address_locality").append('<option value="'+jsonObj['content'][m]+'">'+jsonObj['content'][m]+'</option>"');
                                }                        
                            }
                        });
                    }                    
            });
            
            
            //**************************************************************
            //**************************************************************
            //**                PREPARE ADD MARKET PANEL
            //**************************************************************
            //**************************************************************
            $(document).on('click', '#cp_MARKET_ADD' , function(event){
                    
                    // GET CITIES FROM ADDRESS SERVLET
                    $.post('AddressServlet', {                         
                        "cdasDO":"getCityList"}, function(data) {

                        var jsonObj = jQuery.parseJSON( data );                          
                        if(jsonObj['resultCode']==='GUPPY.001'){  
                            $("#address_city").children().remove();
                            for(m=0; m<jsonObj['content'].length; m++){
                                $("#address_city").append('<option value="'+jsonObj['content'][m]+'">'+jsonObj['content'][m]+'</option>"');
                            }                        
                        }
                    });
                    
                    
                    // GET CITIES FROM ADDRESS SERVLET
                    $.post('CompanyServlet', {                         
                        "cdcsDO":"getCompanyList"}, function(data) {

                        var jsonObj = jQuery.parseJSON( data );                          
                        if(jsonObj['resultCode']==='GUPPY.001'){    
                            for(m=0; m<jsonObj['content'].length; m++){
                                $("#distirbuter_company").append('<option value="'+jsonObj['content'][m]+'">'+jsonObj['content'][m]+'</option>"');
                            }                        
                        }
                    });
                                                               
            }); 
            
            
            
            //**************************************************************
            //**************************************************************
            //**                PREPARE GET MARKET PANEL
            //**************************************************************
            //**************************************************************
            $(document).on('click', '#cp_MARKET_GET_LIST' , function(event){
                                        
                    // GET CITIES FROM ADDRESS SERVLET
                    $.post('MarketServlet', {
                        "cdmsDO":"getMarketList"}, function(data) {
                        
                        var jsonObj = jQuery.parseJSON( data );                          
                        if(jsonObj['resultCode']==='GUPPY.001'){    
                            $("#marketList").children().remove();
                            for(m=0; m<jsonObj['content'].length; m++){
                                $("#marketList").append('<option value="'+jsonObj['content'][m]+'">'+jsonObj['content'][m]+'</option>"');
                            }                        
                        }
                    });                                                                                                       
            }); 
            
            
            //**************************************************************
            //**************************************************************
            //**                ACTION AREA
            //**************************************************************
            //**************************************************************
            
            $(document).on('click', '#action_market_add' , function(event){
                
                $.post('MarketServlet', {                         
                        "cdmsDO":"addMarket"}, function(data) {
                        
                        var jsonObj = jQuery.parseJSON( data );  
                        console.log(jsonObj);
                        if(jsonObj['resultCode']==='GUPPY.001'){                                                             
                            alert("Market add called");
                        
                        }
                    }); 
            });
                                                
        </script>
        
    </head>
    <body>
                                
        
        <div class="container">                        
            
        <!--
        ********************************************************************
        ********************************************************************
                                HEADER AREA (ROW)
        ********************************************************************
        ********************************************************************
        -->
            <div id="cp_Operations" class="row" >
                <div class="btn-group btn-group-justified" role="group" aria-label="...">
                    <div class="btn-group" role="group">
                        <button id="cp_MARKET_ADD" type="button" class="btn btn-default">Add New Market</button>
                    </div>                    
                    <div class="btn-group" role="group">
                        <button id="cp_MARKET_GET_LIST" type="button" class="btn btn-default">Get Market List</button>
                    </div>
                    <div class="btn-group" role="group">
                        <button id="cp_MARKET_EDIT" type="button" class="btn btn-default">Get & Edit Market</button>
                    </div>
                </div>
            </div>
            
            
            
            <!--
            ********************************************************************
            ********************************************************************
                                    MARKET ADD (ROW)
            ********************************************************************
            ********************************************************************
            -->
            <div id="cp_MARKET_ADD_ROW" class="row" style="display: none;" >
                                               
                <div class="panel panel-default">
                    <div class="panel-heading">
                        Address
                    </div>
                    
                    <div class="panel-body">
                        <label for="address_city">Select City</label>
                        <select id="address_city" class="form-control"></select>

                        <label for="address_borough">Select Borough</label>
                        <select id="address_borough" class="form-control"></select>

                        <label for="address_locality">Select Locality</label>
                        <select id="address_locality" class="form-control"></select>

                    </div>
                </div>
                
                
                <div class="panel panel-default">
                    <div class="panel-heading">
                        Company
                    </div>
                    
                    <div class="panel-body">
                        <label for="distirbuter_company">Company Name</label>
                        <select id="distirbuter_company" class="form-control"></select>                        
                    </div>
                </div>
                
                <button id="action_market_add" type="button" class="btn btn-primary">ADD</button>
                
            </div>
                                    
            
            
            
            
            <!--
            ********************************************************************
            ********************************************************************
                                    MARKET GET LIST (ROW)
            ********************************************************************
            ********************************************************************
            -->
            <div id="cp_MARKET_GET_LIST_ROW" class="row" style="display: none;">
                
                
                <div class="panel panel-default panel-primary">
                    <div class="panel-heading">
                        Market List
                    </div>
                    
                    <div class="panel-body">                        
                        
                        <label for="marketList">Select Locality</label>
                        <select id="marketList" class="form-control"></select>
                        
                    </div>
                    
                    <div class="panel-footer">
                        <button id="marketGet" type="button" class="btn btn-primary">GET MARKET</button>  
                    </div>
                </div>
                
                
                <div class="panel panel-default panel-success">
                    <div class="panel-heading panel-title">
                        Market INFO PANEL
                    </div>
                    
                    <div class="panel-body">                        
                        
                        
                        
                        <label for="marketList">Address List</label>                        
                        <table class="table table-condensed">
                            <tr>
                                <td>...</td>
                                <td>...</td>
                                <td>...</td>
                                <td>...</td>
                            </tr>
                            <tr>
                                <td>...</td>
                                <td>...</td>
                                <td>...</td>
                                <td>...</td>
                            </tr>
                        </table>                        
                        
                        <br>
                        <label for="marketList">Worker List</label>
                        <table class="table table-condensed">
                            <tr>
                                <td>...</td>
                                <td>...</td>
                                <td>...</td>
                                <td>...</td>
                            </tr>
                            <tr>
                                <td>...</td>
                                <td>...</td>
                                <td>...</td>
                                <td>...</td>
                            </tr>
                        </table>
                        
                        <br>
                        <label for="marketList">Order List</label>
                        <table class="table table-condensed">
                            <tr>
                                <td>...</td>
                                <td>...</td>
                                <td>...</td>
                                <td>...</td>
                            </tr>
                            <tr>
                                <td>...</td>
                                <td>...</td>
                                <td>...</td>
                                <td>...</td>
                            </tr>
                        </table>
                        
                    </div>
                    
                    <div class="panel-footer">
                        <label for="marketList">Select Locality</label>
                        
                    </div>
                </div>
                
            </div>
            
            
            
            
            
            <!--
            ********************************************************************
            ********************************************************************
                                    MARKET UPDATE (ROW)
            ********************************************************************
            ********************************************************************
            -->
            <div id="cp_MARKET_EDIT_ROW" class="row" style="display: none;">
                Market EDIT
            </div>
                        
            
        </div>
        
        
    </body>
</html>
