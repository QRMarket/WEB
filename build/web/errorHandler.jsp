<html>
    <head>
        
        <!--META-->
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Login Form</title>

        <!--STYLESHEETS-->
        <link href="com/css/style.css" rel="stylesheet" type="text/css" />

        <!--SCRIPTS-->
        <script src="com/js/lib/jquery-1.11.0.js"></script>
        <!--Slider-in icons-->
        <script type="text/javascript">
            $(document).ready(function () {
                    $(".username").focus(function () {
                        $(".user-icon").css("left", "-48px");
                    });
                    $(".username").blur(function () {
                        $(".user-icon").css("left", "0px");
                    });

                    $(".password").focus(function () {
                        $(".pass-icon").css("left", "-48px");
                    });
                    $(".password").blur(function () {
                        $(".pass-icon").css("left", "0px");
                    });
            });
            
            $(document).on('click' , '#goMainPage' , function(){                
                
                    window.location.href = "testPanel.jsp";
            }) 
            
        </script>

    </head>
    <body>

        
        
        <!--WRAPPER-->
        <div id="wrapper">

            <!--SLIDE-IN ICONS-->
            <div class="user-icon"></div>
            <div class="pass-icon"></div>
            <!--END SLIDE-IN ICONS-->

            <!--LOGIN FORM-->
            <div class="login-form" >

                <!--HEADER-->
                <div class="header">
                    <h1>Sorry, I couldn't find your page :(</h1>
                    
                    
                </div>
                <!--END HEADER-->

                <!--CONTENT-->
                <div class="content">
                    
                </div>
                <!--END CONTENT-->

                <!--FOOTER-->
                <div class="footer">
                    <input id="goMainPage" type="submit" name="submit" value="Let go to Main Page" class="button" />                    
                </div>
                <!--END FOOTER-->

            </div>
            <!--END LOGIN FORM-->

        </div>
        <!--END WRAPPER-->

        <!--GRADIENT--><div class="gradient"></div><!--END GRADIENT-->

    </body>
</html>