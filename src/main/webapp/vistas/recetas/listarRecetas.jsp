<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, logica.Receta, logica.Dificultad, logica.Modalidad" %>

<%@include file="/vistas/includes/header.jsp" %>

<h2>Buscar Recetas</h2>

<form action="Recetas" method="get" style="margin-bottom: 20px;">
    <div style="display: flex; gap: 15px; align-items: flex-end;">
        <div style="flex: 1;">
            <label for="modalidad" class="form-label" style="margin-top: 0;">Modalidad:</label>
            <select id="modalidad" name="modalidad" class="form-select">
                <option value="">-- Todas --</option>
                <% for (Modalidad mod : Modalidad.values()) { %>
                <option value="<%= mod.name() %>"><%= mod.getDescripcion() %></option>
                <% } %>
            </select>
        </div>

        <div style="flex: 1;">
            <label for="dificultad" class="form-label" style="margin-top: 0;">Dificultad:</label>
            <select id="dificultad" name="dificultad" class="form-select">
                <option value="">-- Todas --</option>
                <% for (Dificultad dif : Dificultad.values()) { %>
                <option value="<%= dif.name() %>"><%= dif.toString() %></option>
                <% } %>
            </select>
        </div>

        <button type="submit" class="btn btn--primary">Buscar</button>
    </div>
</form>

<hr>

<h2>Listado de Recetas</h2>

<%
    // Obtener la lista del request (Seteada por ListarRecetasServlet)
    List<Receta> recetas = (List<Receta>) request.getAttribute("recetas");

    if (recetas != null && !recetas.isEmpty()) {
%>
<table class="data-table">
    <thead>
    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Modalidad</th>
        <th>Dificultad</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <% for (Receta receta : recetas) { %>
    <tr>
        <td><%= receta.getId_receta() %></td>
        <td><%= receta.getNombre() %></td>
        <td><%= receta.getModalidad().getDescripcion() %></td>
        <td><%= receta.getDificultad().toString() %></td>
        <td>
            <a href="Recetas?accion=ver&id=<%= receta.getId_receta() %>">Ver Detalles</a>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
<%
} else {
%>
<p class="alert alert--error">No se encontraron recetas que coincidan con los filtros aplicados.
    <a href="CrearReceta">¡Añade la primera!</a></p>
<%
    }
%>

<%@include file="/vistas/includes/footer.jsp" %>