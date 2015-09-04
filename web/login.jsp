<html>
    <head>
        
        <!--META-->
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Login Form</title>

        <!--STYLESHEETS-->
        <!--<link href="com/css/style.css" rel="stylesheet" type="text/css" />-->       
        
        <!-- IMPORT Bootstarp LIBRARIES -->        
        <link href="com/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="com/dist/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css" />        
        <link href="login.css" rel="stylesheet" type="text/css" />
        
        <!-- IMPORT JS LIBRARIES -->        
        <script src="com/js/lib/jquery-1.11.0.js"></script> 
        <script src="com/dist/js/bootstrap.min.js"></script>
        <script src="js/httprequest.js"></script> 
        
    </head>
    <body>
        
        <div class="container">
            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    <div class="panel panel-login">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-6">
                                    <a href="#" class="active" id="login-form-link">Login</a>
                                </div>
                                <div class="col-xs-6">
                                    <a href="#" id="register-form-link">Register</a>
                                </div>
                            </div>
                            <hr>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    
                                    
                                    <form id="login-form" action="Auth?authDo=carpeLogin" method="post" role="form" style="display: block;">
                                        <div class="form-group">
                                            <input id="userMail" name="cduMail" type="text" tabindex="1" class="form-control" value="Username" onfocus="this.value = ''" />
                            
                                        </div>
                                        <div class="form-group">
                                            <input id="userPass" name="cduPass" type="password" tabindex="2" class="form-control" value="Password" onfocus="this.value = ''" />
                                        </div>
                                        <div class="form-group text-center">
                                            <input type="checkbox" tabindex="3" class="" name="remember" id="remember">
                                            <label for="remember"> Remember Me</label>
                                        </div>
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-sm-6 col-sm-offset-3">
                                                    <input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Log In">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-lg-12">
                                                    <div class="text-center">
                                                        <a href="http://phpoll.com/recover" tabindex="5" class="forgot-password">Forgot Password?</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                    
                                    
                                    <form id="register-form" action="Auth?authDo=carpeRegister" method="post" role="form" style="display: none;">                                        
                                        <div class="form-group">
                                            <input type="email" name="cduMail" id="email" tabindex="1" class="form-control" placeholder="Email Address" value="">
                                        </div>
                                        <div class="form-group">
                                            <input type="password" name="cduPass" id="password" tabindex="2" class="form-control" placeholder="Password">
                                        </div>
                                        <div class="form-group">
                                            <input type="password" name="cduPassConfirm" id="confirm-password" tabindex="3" class="form-control" placeholder="Confirm Password">
                                        </div>
                                        <div class="form-group">
                                            <input type="text" name="cduname" id="username" tabindex="4" class="form-control" placeholder="Username" value="">
                                        </div>
                                        <div class="form-group">
                                            <input type="text" name="cdusurname" id="username" tabindex="5" class="form-control" placeholder="Surname" value="">
                                        </div>
                                        <div class="form-group">
                                            <input type="text" name="cduphone" id="username" tabindex="6" class="form-control" placeholder="Phone" value="">
                                        </div>
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-sm-6 col-sm-offset-3">
                                                    <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Register Now">
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                    
                                    
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        
        <script src="com/js/lib/jquery-1.11.0.js"></script>
        <script type="text/javascript">                        
            
            $(function() {
                $('#login-form-link').click(function(e) {
                            $("#login-form").delay(100).fadeIn(100);
                            $("#register-form").fadeOut(100);
                            $('#register-form-link').removeClass('active');
                            $(this).addClass('active');
                            e.preventDefault();
                    });
                    $('#register-form-link').click(function(e) {
                            $("#register-form").delay(100).fadeIn(100);
                            $("#login-form").fadeOut(100);
                            $('#login-form-link').removeClass('active');
                            $(this).addClass('active');
                            e.preventDefault();
                    });
            });
            
        </script>
        
<!--        
        
        
        WRAPPER
        <div id="wrapper">

            SLIDE-IN ICONS
            <div class="user-icon"></div>
            <div class="pass-icon"></div>
            END SLIDE-IN ICONS

            <div id="loginForm">
            LOGIN FORM
                <form action="Auth?authDo=carpeLogin" method="POST" class="login-form" >

                        HEADER
                        <div class="header">
                            <h1>Login Form</h1>
                            <span id="loginInfo"></span>

                        </div>
                        END HEADER

                        CONTENT
                        <div class="content">
                            <input id="userMail" name="cduMail" type="text" class="input username" value="Username" onfocus="this.value = ''" />
                            <input id="userPass" name="cduPass" type="password" class="input password" value="Password" onfocus="this.value = ''" />
                        </div>
                        END CONTENT


                    FOOTER
                    <div class="footer">
                        <input id="login" type="submit" name="submit" value="Login" class="button" />
                        <input id="openRegisterForm" type="button" name="submit" value="Register" class="register" />
                    </div>
                    END FOOTER

                </form>
            </div>  
            END LOGIN FORM
            
            
            
            END REGISTER FORM
            <div id="registerForm" style="display: none;">
                <form action="Auth?authDo=carpeRegister" method="POST" class="login-form" >

                        HEADER
                        <div class="header">
                            <h1>Register Form</h1>
                            <span id="loginInfo"></span>

                        </div>
                        END HEADER

                        CONTENT
                        <div class="content">
                            <input id="userMail" name="cduMail" type="text" class="input username" value="Username" onfocus="this.value = ''" />
                            <input id="userPass" name="cduPass" type="password" class="input password" value="Password" onfocus="this.value = ''" />
                            <input id="userPassConf" name="cduPassConf" type="password" class="input password" value="Password" onfocus="this.value = ''" />
                        </div>
                        END CONTENT


                    FOOTER
                    <div class="footer">
                        <input id="confirmRegister" type="submit" name="submit" value="Register" class="button" />
                        <input id="openLoginForm" type="button" name="submit" value="<<" class="register" />
                    </div>
                    END FOOTER

                </form>                   
            </div>
            END REGISTER FORM
            
        </div>
        END WRAPPER

        GRADIENT<div class="gradient"></div>END GRADIENT
        
        
        SCRIPTS
        <script src="com/js/lib/jquery-1.11.0.js"></script>
        Slider-in icons
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
        -->
        

    </body>
</html>