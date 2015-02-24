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
            
            $(document).on("click" , "#login" , function(){                  
                $.post('Auth', { 
                    "authDo":"carpeLogin",
                    "cduMail":$('#userMail').val(),
                    "cduPass":$('#userPass').val()}, function(data) {             

                    var jsonObj = jQuery.parseJSON( data );  
                    console.log(jsonObj);
                    if(jsonObj['resultCode']==='GUPPY.001'){                           
                        $('#productServletTest').css('display','visible');
                        window.location.href = "testPanel.jsp";
                    }else{
                        $('#loginInfo').html("Username or Password Wrong!!!");                        
                    }
                });                
            });
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
                    <h1>Login Form</h1>
                    <span id="loginInfo"></span>
                    
                </div>
                <!--END HEADER-->

                <!--CONTENT-->
                <div class="content">
                    <input id="userMail" name="username" type="text" class="input username" value="Username" onfocus="this.value = ''" />
                    <input id="userPass" name="password" type="password" class="input password" value="Password" onfocus="this.value = ''" />
                </div>
                <!--END CONTENT-->

                <!--FOOTER-->
                <div class="footer">
                    <input id="login" type="submit" name="submit" value="Login" class="button" />
                    <input id="register" type="submit" name="submit" value="Register" class="register" />
                </div>
                <!--END FOOTER-->

            </div>
            <!--END LOGIN FORM-->

        </div>
        <!--END WRAPPER-->

        <!--GRADIENT--><div class="gradient"></div><!--END GRADIENT-->

    </body>
</html>