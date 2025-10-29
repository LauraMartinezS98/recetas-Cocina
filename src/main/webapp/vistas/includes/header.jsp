<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="logica.Ingrediente" %>
<%@ page import="logica.Receta" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">

    <%
        // --- LÓGICA DEL TÍTULO SIMPLE (Robusta contra errores de pageContext) ---
        String title;
        // Obtiene la acción de la URL (Ej: "listar", "crear")
        String accion = request.getParameter("accion");

        if (accion != null && !accion.isEmpty()) {
            // Capitaliza la acción para el título (Ej: "listar" -> "Listar")
            title = accion.substring(0, 1).toUpperCase() + accion.substring(1).toLowerCase();
        } else {
            // Título predeterminado si no hay acción
            title = "Inicio";
        }
    %>
    <title><%= title %> | Gestor de Recetas</title>

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<header class="app-header">
    <h1>Gestor de Recetas e Ingredientes</h1>
    <nav>
        <ul class="app-nav__list">
            <li><a href="Recetas" class="app-nav__link">Ver Recetas</a></li>
            <li><a href="CrearReceta" class="app-nav__link">Añadir Receta</a></li>
            <li>|</li>
            <li><a href="Ingredientes" class="app-nav__link">Ver Ingredientes</a></li>
            <li><a href="CrearIngrediente" class="app-nav__link">Añadir Ingrediente</a></li>
        </ul>
    </nav>
</header>
<main>
        <%
        // Mostrar mensajes de sesión (para notificaciones POST-Redirect-Get)
        String mensaje = (String) request.getSession().getAttribute("mensaje");
        String error = (String) request.getSession().getAttribute("error");

        if (mensaje != null) {
        %>
    <p class="alert alert--success"><%= mensaje %></p>
        <%
            request.getSession().removeAttribute("mensaje"); // Limpiar después de mostrar
        }
        if (error != null) {
        %>
    <p class="alert alert--error"><%= error %></p>
<%
        request.getSession().removeAttribute("error"); // Limpiar después de mostrar
    }
%>