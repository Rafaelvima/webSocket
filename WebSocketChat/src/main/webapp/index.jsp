

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Getting Started with JSR 356 - Annotated Endpoint</title>

<script type="text/javascript" src="js/lib/excluded/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="js/lib/aes.js"></script>
<script type="text/javascript" src="js/lib/pbkdf2.js"></script>
<script type="text/javascript" src="js/AesUtil.js"></script>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<meta name="google-signin-scope" content="profile email">
<meta name="google-signin-client_id"
     content="1027953223368-th3hh3onqnevoft8sgliam4kvntmtrvj.apps.googleusercontent.com">
    </head>
    <body>
        <h1>Getting Started with JSR 356 - Annotated Endpoint</h1>
<div class="g-signin2" data-onsuccess="onSignIn"></div>
        <div style="text-align: center;">
            <form action=""> 
                <h2>Text Data</h2>
                <input id="user" value="google" type="text"><br>
                <input onclick="conectar();" value="conectar" type="button"> 
                <h2>Text Data</h2>
                <input onclick="sayHello();" value="Say Hello" type="button"> 
                <input id="destino" value="Juan" type="text">
                <input id="myField" value="WebSocket" type="text"><br>
                <input onclick="getCanales();" value="getCanales" type="button"> 
                <select id="canales">
                    
                </select>
            </form>
            <form action=""> 
                <h2>Binary Data</h2>
                <input onclick="echoBinary();" value="Echo" type="button"> 
                <input id="myField2" value="12345678" type="text"><br>
            </form>
        </div>
        <div id="output"></div>
        <script language="javascript" type="text/javascript" src="websocket.js">
        </script>
        
        <script>
            var idToken;
	function onSignIn(googleUser) {
      var profile = googleUser.getBasicProfile();
      console.log('ID: ' + profile.getId());
      console.log('Name: ' + profile.getName());
      console.log('Image URL: ' + profile.getImageUrl());
      console.log('Email: ' + profile.getEmail());
      console.log('id_token: ' + googleUser.getAuthResponse().id_token);

     //do not post above info to the server because that is not safe.
     //just send the id_token

      var redirectUrl = 'login';
      //using jquery to post data dynamically
      idToken =  googleUser.getAuthResponse().id_token;
      var form = $('<form action="' + redirectUrl + '" method="post">' +
                          '<input type="text" name="id_token" value="' +
                           googleUser.getAuthResponse().id_token + '" />' +
                                                                '</form>');
    //$('body').append(form);
    //form.submit();
    }

	</script>
        <a href="#" onclick="signOut();">Sign out</a>
<script>
  function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
    websocket.close();
  }
</script>
    </body>
</html>