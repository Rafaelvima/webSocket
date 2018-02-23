
var wsUri = "ws://localhost:8080/WebSocketChat/endpoint";
console.log("Connecting to " + wsUri);
var token = "Token";
//var user=document.getElementById("user");
//var options = {
//  headers: {
//    "X-Auth-Token" : token
//  }
//};
var websocket;

function conectar() {
    websocket = new WebSocket(wsUri + "/" + user.value + "/" + pass.value, []);
    websocket.onopen = function (evt) {
        onOpen(evt)
    };
    websocket.onmessage = function (evt) {
        onMessage(evt)
    };
    websocket.onerror = function (evt) {
        onError(evt)
    };
    websocket.onclose = function (evt) {
        onClose(evt)
    };
}
var output = document.getElementById("output");
var iterationCount = 1000;
var keySize = 128;
var aesUtil = new AesUtil(keySize, iterationCount);


function getCanales(){
     var iv = CryptoJS.lib.WordArray.random(128 / 8).toString(CryptoJS.enc.Hex);
    var salt = CryptoJS.lib.WordArray.random(128 / 8).toString(CryptoJS.enc.Hex);

    var passphrase = "temp";

    var object = {
        "destino": destino.value,
        "tipo": "canales",
        "contenido": "",
        "key": passphrase,
        "salt" : salt,
        "iv": iv
    };


    websocket.send(JSON.stringify(object));
   
}
function sayHello() {
    console.log("sayHello: " + myField.value);

    
    var iv = CryptoJS.lib.WordArray.random(128 / 8).toString(CryptoJS.enc.Hex);
    var salt = CryptoJS.lib.WordArray.random(128 / 8).toString(CryptoJS.enc.Hex);

    var passphrase = "temp";
     
    var texto = aesUtil.encrypt(salt, iv,passphrase, myField.value);
    
    writeToScreen("SENT (text): " + aesUtil.decrypt(salt, iv,passphrase, texto));

    var object = {
        "destino": destino.value,
        "tipo": "texto",
        "contenido": texto,
        "key": passphrase,
        "salt" : salt,
        "iv": iv
    };


    websocket.send(JSON.stringify(object));
    writeToScreen("SENT (text): " + JSON.stringify(object));
}

function echoBinary() {
//                alert("Sending " + myField2.value.length + " bytes")
    var buffer = new ArrayBuffer(myField2.value.length);
    var bytes = new Uint8Array(buffer);
    for (var i = 0; i < bytes.length; i++) {
        bytes[i] = i;
    }
//                alert(buffer);
    websocket.send(buffer);
    writeToScreen("SENT (binary): " + buffer.byteLength + " bytes");
}

function onOpen() {
    console.log("onOpen");
    writeToScreen("CONNECTED");
    if (user.value == "google")
    {
        var object = {
        "destino": destino.value,
        "tipo": "texto",
        "contenido": idToken
    };
        websocket.send(JSON.stringify(object));
    }
}
function onClose() {

    writeToScreen("Server close conection");
}

function onMessage(evt) {
    if (typeof evt.data == "string") {
        var mensaje = JSON.parse(evt.data);
       
        var texto = aesUtil.decrypt(mensaje.salt,mensaje.iv,mensaje.key, mensaje.contenido);
        switch (mensaje.tipo)
        {
            case "texto": 
                writeToScreen("RECEIVED (text): " + texto);
                break;
            case "canales": 
                var canales = JSON.parse(texto);
                for (var canal in canales)
                {
                    $("#canales").append(new Option(canales[canal], canales[canal]));
                }
                writeToScreen("RECEIVED (text): " + texto);
                
                break;
        }
        
       
    } else {
        writeToScreen("RECEIVED (binary): " + evt.data);
    }
}

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function writeToScreen(message) {
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
}
