
var wsUri = "ws://localhost:8083/WebSocketChat/endpoint";
console.log("Connecting to " + wsUri);
var token = "Token";
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



function getCanales() {

    var object = {
        "destino": destino.value,
        "tipo": "canales",
        "contenido": ""
    };


    websocket.send(JSON.stringify(object));

}
function sayHello() {
    console.log("sayHello: " + myField.value);
    var texto = myField.value;
    writeToScreen("SENT (text): " + texto);
    var hoy = new Date();

    var object = {
        "destino": canal.value,
        "tipo": "texto",
        "contenido": texto,
        "guardar": guarda.checked,
        "fecha": hoy
    };

    writeToScreen("SENT (textito): " + JSON.stringify(object));
    websocket.send(JSON.stringify(object));

}
writeToScreen("SENT (binary): " + buffer.byteLength + " bytes");
function onOpen() {
    console.log("onOpen");
    writeToScreen("CONNECTED");
    if (user.value == "google")
    {
        var object = {
            "destino": canal.value,
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

        var texto = mensaje.contenido;
        switch (mensaje.tipo)
        {
            case "texto":
                writeToScreen("RECEIVED (textoes): " + texto);
                break;
            case "canales":
                var canales = JSON.parse(texto);
                for (var canal in canales)
                {
                    $("#destino").append(new Option(canales[canal], canales[canal]));
                }
                break;
            case "pedirPCanal":
                var permisito = confirm(mensaje.contenido);
                if (permisito) {
                    var object = {
                        "tipo":"darPCanal",
                        "id_canal": mensaje.destino,
                        "user": mensaje.user
                        
                    };
                    websocket.send(JSON.stringify(object));
                } 
                break;
            case "darPCanal":
                writeToScreen("RECEIVED (textoes): " + mensaje.contenido);
                break;
            case "darMensajes":
                var mensajesFechas = JSON.parse(texto);
                for (var mensajitos in mensajesFechas)
                {
                    writeToScreen("RECEIVED (textoes): " + mensajitos.fecha + "  "+mensajitos.contenido);
                }
                
                break;
        }


    } else {
        writeToScreen("Error al enviar el mensaje");
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
function cargarMensajes() {
    var object = {
        "tipo": "cargaMensajes",
        "contenido": null,
        "fechaInicial": fechaInicial.value,
        "fechaFinal": fechaFinal.value
    };

    writeToScreen("SENT (textito): " + JSON.stringify(object));
    websocket.send(JSON.stringify(object));
}

