        


        function getOrderList(){                        
            $.post('MarketServlet', { 
                "cdmsDO":"getMarketOrderList"}, function(data) {             

                var jsonObj = jQuery.parseJSON( data );  
                console.log(jsonObj);
                if(jsonObj['resultCode']==='GUPPY.001'){
                    
                    $('#marketPanel_orderTable').children().remove();
                    
                    columns = "";
                    row="";
                    for(i=0; i<jsonObj['content'].length; i++){                        
                        columns =  "<td>"+(i+1)+"</td>" +  "<td>"+ jsonObj['content'][i] + "</td>";                         
                        columns = columns + '<td><input style="margin: 5px;" class="btn btn-default" type="button" value="Finish Order"></td>'
                        row = row + "<tr orderID='"+jsonObj['content'][i]+"'>" + columns + "</tr>";                           
                    }
                    
                    $('#marketPanel_orderTable').append(row);
                }
            }); 
        }
        
        
        
        function getProductList(){
            $.post('ProductServlet', { 
                "cdpsDo":"getProductList",
                "cdpmID":"c_34567" }, function(data) {

                var jsonObj = jQuery.parseJSON( data );  
                console.log(jsonObj);
                if(jsonObj['resultCode']==='GUPPY.001'){
                    
                    for(i=0; i<jsonObj['content'].length; i++){
                        
                        myValue = jsonObj['content'][i];
                        myElem = "<option value=\"" + myValue + "\"> " + myValue +"  </option>";                        
                        $('#productSelected').append(myElem);
                                                
                    }
                    
                }
            }); 
        }
        
        
        function getProductInfo(productID){
            
            $.post('ProductServlet', { 
                "cdpsDo":"getProduct",
                "cdpUID":productID}, function(data) {

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
        }