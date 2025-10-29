<%
    // Define el título para el header.jsp
   /* pageContext.setAttribute("pageTitle", "Inicio");*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/vistas/includes/header.jsp" %>

<h2>Bienvenido al Gestor de Recetas e Ingredientes</h2>

<p>Una aplicación sencilla para organizar tus creaciones culinarias y los ingredientes necesarios.</p>

<h3>Acciones Rápidas:</h3>
<ul>
    <li><a href="Recetas">Ver listado completo de Recetas</a></li>
    <li><a href="CrearReceta">Crear una Receta</a></li>
    <li><a href="Ingredientes">Ver listado de Ingredientes</a></li>
    <li><a href="CrearIngrediente">Añadir un nuevo Ingrediente</a></li>
</ul>

<%@include file="/vistas/includes/footer.jsp" %>