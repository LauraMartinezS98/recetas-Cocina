<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/vistas/includes/header.jsp" %>
<%@ page import="logica.Ingrediente, logica.DetalleReceta" %>
<%
    // Se asume que el Servlet ya seteó el atributo "ingrediente"
    Ingrediente ingrediente = (Ingrediente) request.getAttribute("ingrediente");
    if (ingrediente != null) {
%>
<h2>Detalle del Ingrediente: <%= ingrediente.getNombre() %></h2>

<p><strong>ID:</strong> <%= ingrediente.getId_ingrediente() %></p>

<p><strong>Unidad Base:</strong> <%= ingrediente.getUnidad() %></p>

<hr>

<h3>Usado en las siguientes Recetas:</h3>

<%
    // Usamos el getter de la lista (que el DAO debe cargar con JOIN FETCH)
    List<DetalleReceta> detalles = ingrediente.getDetallesRecetasDondeSeUsa();
    if (detalles != null && !detalles.isEmpty()) {
%>
<table class="data-table">
    <thead>
    <tr>
        <th>Receta</th>
        <th>Cantidad Requerida</th>
        <th>Ver Detalle</th>
    </tr>
    </thead>
    <tbody>
    <% for (DetalleReceta detalle : detalles) { %>
    <tr>
        <td><%= detalle.getReceta().getNombre() %></td>
        <td><%= detalle.getCantidad() %> <%= detalle.getUnidad() %></td>
        <td>
            <a href="Recetas?accion=ver&id=<%= detalle.getReceta().getId_receta() %>">Ver Receta</a>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
<%
} else {
%>
<p>Este ingrediente no se ha utilizado en ninguna receta aún.</p>
<%
    }
%>

<p><a href="Ingredientes">← Volver al Listado de Ingredientes</a></p>

<%
} else {
%>
<p class="alert alert--error">Ingrediente no encontrado.</p>
<%
    }
%>

<%@include file="/vistas/includes/footer.jsp" %>