
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
    var hoy= new Date();

    var object = {
        "destino": canal.value,
        "tipo": "texto",
        "contenido": texto,
        "guardar": guarda.checked,
        "fecha":hoy
    };

    writeToScreen("SENT (textito): " + JSON.stringify(object));
    websocket.send(JSON.stringify(object));

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
function cargarMensajes(evt) {
    console.log("fechas");

    var fi = fechaInicial.value;
    var ff = fechaFinal.value;
//    var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
//    var dateini = new Date(fi.replace(pattern, '$3-$2-$1'));
//    var datefinal = new Date(ff.replace(pattern, '$3-$2-$1'));

  console.log(fi + ff);
    var object = {
        "tipo": "carga",
        "contenido": null,
        "fechaInicial": fi,
        "fechaFinal": ff
    };

    writeToScreen("SENT (textito): " + JSON.stringify(object));
    websocket.send(JSON.stringify(object));
}

