<%-- 
    Document   : pintarListaAlumnos
    Created on : Oct 28, 2017, 8:02:42 PM
    Author     : oscar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alumnos</title>
        <script>

            function cargarAlumno(id, nombre, fecha, mayor) {
                document.getElementById("nombre").value = nombre;
                document.getElementById("idalumno").value = id;
                document.getElementById("fecha").value = fecha;
                document.getElementById("mayorEdad").checked = mayor;

            }
        </script>
    </head>
    <body>
        <h1>ALUMNOS</h1>
        <br>
        <table border="1">
            <c:forEach items="${alumnos}" var="alumno">  
                <tr>
                    <td>
                        <input type="button" value="cargar ${alumno.id}" 
                               onclick="cargarAlumno('${alumno.id}',
                                               '${fn:escapeXml(fn:replace(alumno.nombre,"'", "\\'"))}',
                                               '<fmt:formatDate value="${alumno.fecha_nacimiento}" pattern="dd-MM-yyyy"/>',
                               ${alumno.mayor_edad});"/>
                    </td> 
                    <td>
                        ${alumno.nombre}
                    </td>

                    <td>
                        <fmt:formatDate value="${alumno.fecha_nacimiento}" pattern="dd-MM-yyyy"/>
                    </td>
                    <td>
                        <input type="checkbox" <c:if test="${alumno.mayor_edad}" >checked</c:if> />
                        </td>
                    </tr>


            </c:forEach> 

        </table>
        <form action ="alumnos?op=" method="get">
            <input type="hidden" id="idalumno" name="id" />
            <input type="text" id="nombre" name="nombre" size="12"/>
            <input type="text" id="fecha" name="fecha" size="12"/>
            <input type="checkbox"  name="mayor" id="mayorEdad"/>
            <input type="hidden" id="op" name="op"/>
            <button value="update" onclick="updForm();"> actu</button>
            <button value="delete" onclick="delForm();"> delete</button>
            <button value="insert" onclick="insForm();"> insertar</button>
        </form>
        <script>
            function updForm() {
                document.getElementById("op").value = "update";
            }
            function delForm() {
                document.getElementById("op").value = "delete";
            }
            function insForm() {
                document.getElementById("op").value = "insert";
            }
        </script>
    </body>
</html>