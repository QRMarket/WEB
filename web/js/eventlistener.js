$(function(){
    
    //************************************************************************//
    //************************************************************************//
    //                      INITIALIZATION
    //************************************************************************//
    //************************************************************************//
    
    
    setInterval(function(){
            
            getOrderList();
            
    }, 30000);
    
    
    //************************************************************************//
    //************************************************************************//
    //                      MARKET PANEL FUNCTIONs
    //************************************************************************//
    //************************************************************************//
    $(document).on('click' , '#mp_container_home_button' , function(){
        $('.container-tab').css('display' , 'none');
        $('#containerHOME').fadeIn("fast");
    });
    
    $(document).on('click' , '#mp_container_order_button' , function(){
        getOrderList();
        $('.container-tab').css('display' , 'none');
        $('#containerORDER').fadeIn("fast");
    });
    
    $(document).on('click' , '#marketPanel_orderTable > tr' , function(event){
        
        orderId = $(this).attr('orderID');                
                                               
        $.post('OrderServlet', { 
            "cdosDo":"getOrderInfo",
            "cdoID" : orderId }, function(data) {             

            var jsonObj = jQuery.parseJSON( data );  
            console.log(jsonObj);
            if(jsonObj['resultCode']==='GUPPY.001'){
                // Gerekli değişiklikler yapılacak
            }
        }); 
        
        
    });
        
    
    
});