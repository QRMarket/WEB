<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <script src="com/js/lib/jquery-1.11.0.js"></script>        
        <script>
            
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
                    }
                });                
            });
                        
            
        </script>
        
    </head>
    <body>
        
        <div>
            User name: <input id="userMail" type="text" name="cduMail"><br>
            User pass: <input id="userPass" type="password" name="cduPass"><br>
            <input id="login" type="submit" value="Submit">
        </div>
        
        
    </body>
</html>
