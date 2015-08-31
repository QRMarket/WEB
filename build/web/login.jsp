<html>
    <head>
        
        <!--META-->
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Login Form</title>

        <!--STYLESHEETS-->
        <link href="com/css/style.css" rel="stylesheet" type="text/css" />       
        
    </head>
    <body>
        
        <!--WRAPPER-->
        <div id="wrapper">

            <!--SLIDE-IN ICONS-->
            <div class="user-icon"></div>
            <div class="pass-icon"></div>
            <!--END SLIDE-IN ICONS-->

            <div id="loginForm">
            <!--LOGIN FORM-->
                <form action="Auth?authDo=carpeLogin" method="POST" class="login-form" >

                        <!--HEADER-->
                        <div class="header">
                            <h1>Login Form</h1>
                            <span id="loginInfo"></span>

                        </div>
                        <!--END HEADER-->

                        <!--CONTENT-->
                        <div class="content">
                            <input id="userMail" name="cduMail" type="text" class="input username" value="Username" onfocus="this.value = ''" />
                            <input id="userPass" name="cduPass" type="password" class="input password" value="Password" onfocus="this.value = ''" />
                        </div>
                        <!--END CONTENT-->


                    <!--FOOTER-->
                    <div class="footer">
                        <input id="login" type="submit" name="submit" value="Login" class="button" />
                        <input id="openRegisterForm" type="button" name="submit" value="Register" class="register" />
                    </div>
                    <!--END FOOTER-->

                </form>
            </div>  
            <!--END LOGIN FORM-->
            
            
            
            <!--END REGISTER FORM-->
            <div id="registerForm" style="display: none;">
                <form action="Auth?authDo=carpeRegister" method="POST" class="login-form" >

                        <!--HEADER-->
                        <div class="header">
                            <h1>Register Form</h1>
                            <span id="loginInfo"></span>

                        </div>
                        <!--END HEADER-->

                        <!--CONTENT-->
                        <div class="content">
                            <input id="userMail" name="cduMail" type="text" class="input username" value="Username" onfocus="this.value = ''" />
                            <input id="userPass" name="cduPass" type="password" class="input password" value="Password" onfocus="this.value = ''" />
                            <input id="userPassConf" name="cduPassConf" type="password" class="input password" value="Password" onfocus="this.value = ''" />
                        </div>
                        <!--END CONTENT-->


                    <!--FOOTER-->
                    <div class="footer">
                        <input id="confirmRegister" type="submit" name="submit" value="Register" class="button" />
                        <input id="openLoginForm" type="button" name="submit" value="<<" class="register" />
                    </div>
                    <!--END FOOTER-->

                </form>                   
            </div>
            <!--END REGISTER FORM-->
            
        </div>
        <!--END WRAPPER-->

        <!--GRADIENT--><div class="gradient"></div><!--END GRADIENT-->
        
        
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
                         
            // -- close login form and open register form
            $(document).on("click" , "#openRegisterForm" , function(){
                    $("#loginForm").hide();
                    $("#registerForm").show();
            });
            
            
            $(document).on("click" , "#openLoginForm" , function(){                    
                    $("#registerForm").hide();
                    $("#loginForm").show();
            });
            
        </script>
        
        

    </body>
</html>