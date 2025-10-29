<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/vistas/includes/header.jsp" %>
<%@ page import="logica.Receta, logica.DetalleReceta" %>

<%
    // Se asume que el Servlet ya seteó el atributo "receta"
    Receta receta = (Receta) request.getAttribute("receta");
    if (receta != null) {
%>
<h2>Receta: <%= receta.getNombre() %></h2>

<div style="display: flex; justify-content: space-between; margin-bottom: 20px;">
    <p><strong>Dificultad:</strong> <%= receta.getDificultad().toString() %> (<%= receta.getDificultad().getDescripcion() %>)</p>
    <p><strong>Modalidad:</strong> <%= receta.getModalidad().getDescripcion() %></p>
    <p><strong>ID:</strong> <%= receta.getId_receta() %></p>
</div>

<hr>

<h3>Instrucciones:</h3>
<p style="white-space: pre-wrap;"><%= receta.getDescripcion() %></p>

<hr>

<h3>Ingredientes Requeridos:</h3>

<%
    // Lista de la tabla intermedia que contiene la cantidad, unidad e Ingrediente
    List<DetalleReceta> detalles = receta.getDetalleRecetas();

    if (detalles != null && !detalles.isEmpty()) {
%>
<table class="data-table">
    <thead>
    <tr>
        <th>Ingrediente</th>
        <th>Cantidad</th>
        <th>Ver Detalle</th>
    </tr>
    </thead>
    <tbody>
    <% for (DetalleReceta detalle : detalles) { %>
    <tr>
        <td><%= detalle.getIngrediente().getNombre() %></td>

        <td>
            <span style="font-weight: bold;"><%= detalle.getCantidad() %> <%= detalle.getUnidad() %></span>
        </td>
        <td>
            <a href="Ingredientes?accion=ver&id=<%= detalle.getIngrediente().getId_ingrediente() %>">Ver Ingrediente</a>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
<%
} else {
%>
<p>Esta receta no tiene ingredientes asociados.</p>
<%
    }
%>

<p style="margin-top: 20px;"><a href="Recetas">← Volver al Listado de Recetas</a></p>

<%
} else {
%>
<p class="alert alert--error">Receta no encontrada.</p>
<%
    }
%>

<%@include file="/vistas/includes/footer.jsp" %>