<!DOCTYPE html>
<html>
    <head>
        <title>GUPPY Test Panel</title>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">                   
        
        
        
        <!-- IMPORT Bootstarp LIBRARIES -->        
        <link href="com/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="com/dist/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css" />        
        
        
        <!-- IMPORT JS LIBRARIES -->        
        <script src="com/js/lib/jquery-1.11.0.js"></script> 
        <script src="com/dist/js/bootstrap.min.js"></script>
        <script src="js/httprequest.js"></script> 
        
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
            
            
            $(document).on("click" , "ul#profile_menu_tab li" , function(){
                
                $("ul#profile_menu_tab li.active").removeClass("active");
                $(this).addClass("active");
                
            });
            
            
            function goToHomePage() {
                window.location.href = "testPanel.jsp"
            }

                                           
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
                            <button type="button" class="btn btn-default navbar-btn "  onclick="goToHomePage()" >Home</button>
                            
                        </li>
                        
                    </ul>
                    
                    <ul class="nav navbar-nav navbar-right">                        
                        <li class="active">                            
                            
                            <div class="dropdown" style="margin-top:8px; margin-bottom:8px;">
                                <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
                                    <span class="caret"></span></button>
                                <ul class="dropdown-menu" role="menu" aria-labelledby="menu1">
                                    <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Profile</a></li>
                                    <li role="presentation" class="divider"></li>                                    
                                    <li role="presentation"><a id="logout" role="menuitem" tabindex="-1" href="#">Logout</a></li>
                                </ul>
                            </div>
                        </li>
                        
                        
                        
                    </ul>  
                     
                </div>
                
            </div>                       
        </nav>
                
        
        <div class="container">
                        
            <!--
            ********************************************************************
            ********************************************************************
                                    PROFILE AREA (ROW)
            ********************************************************************
            ********************************************************************
            -->
            <div id="productServletTest" class="row" >
                
                <div id="userCart" class="col-md-12" style="padding: 5px;" >

                    <div class="container">
                        <h3>Profile Page</h3>
                        <ul id="profile_menu_tab" class="nav nav-tabs nav-justified">
                            <li class="active" id="profile-tab-order"><a href="#">Orders</a></li>
                            <li id="profile-tab-favorite" ><a href="#">Favorite</a></li>
                            <li id="profile-tab-address" ><a href="#">Address</a></li>
                            <li id="profile-tab-setting" ><a href="#">Settings</a></li>
                        </ul>                        
                    </div>

                    <div class="order-items">
                        
                        <div class="order-item">
                            <div class="order-header">
                                <div class="row">
                                    <div class="col-md-10 order-restaurant">
                                        <a href="/yildiz-aspava-emek-ankara">
                                            siraris yeri
                                        </a>
                                    </div>
                                    <div class="col-md-6 text-right order-date">
                                        tarih
                                    </div>
                                </div>
                            </div>
                            <div class="order-body">
                                <div class="row order-details">
                                    <div class="col-md-10">
                                        <div class="order-info">
                                            <strong>Siparis meta data</strong>
                                        </div>
                                        <div class="amount-info">
                                            Tutar: <strong>36,00 TL</strong>
                                        </div>
                                        <div class="order-detail-link">
                                            <a href="/siparis/bilgi?trackingNumber=231041284&amp;oh=1" target="_blank">Siparis Detaylari</a>
                                        </div>
                                    </div>
                                    <div class="col-md-6 repeat-order">

                                        <button data-order-group-id="d9353ccd-7625-440e-a432-a8691987d41d" class="ys-btn ys-btn-primary ys-btn-lg middle repeat-order-button">
                                            SIPARIS TEKRAR
                                        </button>

                                    </div>
                                </div>
                                <div class="ys-panel order-history-detail rating-comment">
                                    <div class="panel-body">

                                        <div class="no-comment">
                                            <i class="ys-icons ys-icons-comment"></i>Yorum ??
                                            <button data-category-name="51de9a75-deeb-4f09-b156-d0fd08bdd9c5" data-tracking-number="231041284" data-flavour="8.76" data-speed="8.88" data-serving="8.82" data-flavour-slider="9" data-speed-slider="9" data-serving-slider="9" data-catalog-name="TR_ANKARA" class="ys-btn ys-btn-default comment-button">
                                                Puan-Yorum
                                            </button>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div> 
                        
                    </div>
                    
                        
                </div>                                                        

            </div>
            
        </div>
        
        
    </body>
</html>
