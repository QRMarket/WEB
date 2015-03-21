


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