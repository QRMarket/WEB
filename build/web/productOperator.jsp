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
            
                                                
        </script>
        
    </head>
    <body>
                                
        
        <div class="container">
                
            <div id="cp_Operations" class="row" >
                <div class="btn-group btn-group-justified" role="group" aria-label="...">
                    <div class="btn-group" role="group">
                        <button id="cp_PRODUCTION_ADD" type="button" class="btn btn-default">Production Add</button>
                    </div>
                    <div class="btn-group" role="group">
                        <button id="cp_PRODUCTION_GET_LIST" type="button" class="btn btn-default">Get Production List</button>
                    </div>
                    <div class="btn-group" role="group">
                        <button id="cp_PRODUCTION_EDIT" type="button" class="btn btn-default">Edit Production</button>
                    </div>
                    <div class="btn-group" role="group">
                        <button id="cp_PRODUCTION_DELETE" type="button" class="btn btn-default">Delete Production</button>
                    </div>
                </div>
            </div>
            
            <div id="cp_PRODUCTION_ADD_ROW" class="row" style="display: none;" >
                <form action="/QR_Market_Web/ProductServlet?cdpsDo=addProduct" method="POST">                    
                    <div class="form-group">
                        <label for="cp_add_productName">Product Name </label>
                        <input id="cp_add_productName" type="text" class="form-control" placeholder="Enter Product Name">
                    </div>
                    <div class="form-group">
                        <label for="cp_add_productBarcode">Product Barcode</label>
                        <input id="cp_add_productBarcode" type="text" class="form-control"  placeholder="Barcode">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputFile">File input</label>
                        <input type="file" id="exampleInputFile">
                        <p class="help-block">Add image/s for product</p>
                    </div>                    
                    <button id="cp_add_submit" type="submit" class="btn btn-default">Submit</button>
              </form>
            </div>
            <div id="cp_PRODUCTION_GET_LIST_ROW" class="row" style="display: none;">
                production get list
            </div>
            <div id="cp_PRODUCTION_EDIT_ROW" class="row" style="display: none;">
                production EDIT
            </div>
            <div id="cp_PRODUCTION_DELETE_ROW" class="row" style="display: none;">
                production delete
            </div>
            
        </div>
        
        
    </body>
</html>
