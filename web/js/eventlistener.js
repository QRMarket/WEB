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
    
    $(document).on('click' , 'tr' , function(event){
        
        orderId = $(this).attr('orderID');
        if(orderId){
            console.log(orderId);        
        }
        
    });
        
    
    
});