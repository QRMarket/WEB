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
            
            
            
            
            // UPLOAD IMAGE OPERATION
            function readURL(input) {                                                
                if (input.files && input.files[0]) {
                    var reader = new FileReader();

                    reader.onload = function (e) {
                        console.log(e.target.result)
                        $('#imagePreviev').attr('src', e.target.result);
                    }

                    reader.readAsDataURL(input.files[0]);
                }
            }

            $(document).on('change' , '#po_product_images' , function(){                
                readURL(this);
            }); 
            
            
            $(document).on('click' , '#po_product_add' , function(){                                
//                $.ajax({
//                    url: 'http://localhost:8080/Sample_WebApplication_3_Upload/SampleServlet',
//                    data: $('#po_product_images').attr('files'),
//                    cache: false,
//                    contentType: 'multipart/form-data',
//                    processData: false,
//                    type: 'POST',
//                    success: function(data){
//
//                        alert(data);
//                    }
//                });
    
                alert("Button clicked");
            
                var jForm = new FormData();
                jForm.append("cdpsDo", "addProduct");
                jForm.append("cdpName", $('#cp_add_productName').val());
                jForm.append("cdpBranch", $('#cp_add_productBranch').val());
                jForm.append("cdpBarcode", $('#cp_add_productBarcode').val());
                jForm.append("cdpDesc", $('#cp_add_productDescription').val());                 
                jForm.append("cdpImages", $('#po_product_images').get(0).files[0]);
 
                url = "http://localhost:8080/QR_Market_Web/ProductServlet"
                url = "http://localhost:8080/Sample_WebApplication_3_Upload/SampleServlet"
 
                $.ajax({
                    url: "http://localhost:8080/QR_Market_Web/ProductAddServlet",
                    type: "POST",
                    data: jForm,
                    mimeType: "multipart/form-data",
                    contentType: false,
                    cache: false,
                    processData: false,
                    success: function () {
                        alert("POST SUCCESS");
                    },
                    error: function () {
                        alert("POST FAIL");
                    }
                });

            });
            
            

            
            
                                                
        </script>
        
    </head>
    <body>
                                
<!--
********************************************************************************
********************************************************************************
                                PRODUCT CONTAINER
********************************************************************************
********************************************************************************
-->
        <div id="containerProduct" class="container">                      
            
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
            
            
            <!--
            ********************************************************************
            ********************************************************************
                                    PRODUCT ADD (ROW)
            ********************************************************************
            ********************************************************************
            -->            
            <div id="cp_PRODUCTION_ADD_ROW" class="row" style="display: none;" >                
                
                <div id="cp_PRODUCTION_ADD_PANEL" class="panel panel-primary">
                    <div class="panel-heading">
                        Product addition
                    </div>
                    
                    <div class="panel-body">
                        
                        <div>
                            <div class="form-group">
                                <label for="cp_add_productName">Product Name </label>
                                <input id="cp_add_productName" name="cdpName" type="text" class="form-control" placeholder="Enter Product Name">
                            </div>
                            <div class="form-group">
                                <label for="cp_add_productBranch">Product Branch </label>
                                <input id="cp_add_productBranch" name="cdpBranch" type="text" class="form-control" placeholder="Enter Product Branch">
                            </div>                            
                            <div class="form-group">
                                <label for="cp_add_productBarcode">Product Barcode</label>
                                <input id="cp_add_productBarcode" name="cdpBarcode" type="text" class="form-control"  placeholder="Barcode of product">
                            </div>
                            <div class="form-group">
                                <label for="cp_add_productDescription">Product Description</label>
                                <textarea id="cp_add_productDescription" name="cdpDesc" class="form-control" rows="3" placeholder="Description"></textarea>                        
                            </div>
                            <div class="form-group">
                                <label for="po_product_images">File input</label>
                                <input type="file" id="po_product_images" name="cdpImages">                                
                            </div>
                                                                                    
                            <button id="po_product_add" type="submit" class="btn btn-primary">ADD PRODUCT</button>
                        </div>
                                                                        
                    </div>
                    
                    <div class="panel-footer">
                        <img width="100" height="100" id="imagePreviev" />
                    </div>
                    
                </div>
                
            </div>
            
            
            <!--
            ********************************************************************
            ********************************************************************
                                    PRODUCT ADD (ROW)
            ********************************************************************
            ********************************************************************
            --> 
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
