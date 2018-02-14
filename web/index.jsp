<%-- 
    Document   : index
    Created on : 13/01/2018, 18:46:00
    Author     : user
--%>

<%@page import="chatterbot.*"%>
<%@page import="processamentomorfologico.*"%>
<%@page import="robo.*"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ChatterEdu</title>
        <link rel="stylesheet" href="jquery/jquery-ui.css">
        <script src="jquery/jquery.js"></script>
        <script src="jquery/jquery-ui.min.js"></script>
        <script>
            $(function atualizar() {
                $( "#tabs" ).tabs({ active: 1 });
            });
        </script>
    </head>
    <body>
        <h1 style="text-align: center; font-family: 'Courier new';">ChatterEdu 2.0</h1>
        <div id="tabs" style="margin-top: 0px; text-align: center;">
            <ul>
                <li><a href="#tabs-1" class="tab">Processar texto</a></li>
                <li><a href="#tabs-2" class="tab">Chat</a></li>
            </ul>
            <div id="tabs-1">
                <form action="${pageContext.request.contextPath}/Servlet" method="post">
                    <p></p>
                    <div style="text-align: center">
                        <textarea name="process" cols="100" rows="15"></textarea>
                        <p></p>
                        <input type="submit" name="bProcess" value="Processar"/>
                    </div>
                </form>
            </div>
            <div id="tabs-2">
                <form action="${pageContext.request.contextPath}/Servlet" method="post">
                    <p></p>
                    <div style="text-align: center">
                        <p></p>
                        <p></p>
                        <textarea disabled name="chat" cols="100" rows="12">${ textoChat }</textarea>
                        <p></p>
                        <input type="submit" name="bLimpar" value="Limpar" style="text-align: left"/>
                        <p></p>
                        <textarea name="message" cols="100" rows="4"></textarea>
                        <p></p>
                        <input type="submit" name="bChat" value="Enviar mensagem" style="text-align: right"/>
                    </div>
                </form>
            </div>
        </div>        
    </body>
</html>
