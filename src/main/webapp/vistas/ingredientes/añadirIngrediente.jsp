<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="logica.Unidad" %>

<%@include file="/vistas/includes/header.jsp" %>

<h2>Añadir Nuevo Ingrediente</h2>

<form action="<%= request.getContextPath() %>/CrearIngrediente" method="post">
    <input type="hidden" name="accion" value="guardar">

    <label for="nombreIngrediente" class="form-label">Nombre del Ingrediente:</label>
    <input type="text" id="nombreIngrediente" name="nombre" class="form-input" required>

    <label for="unidadBase" class="form-label">Unidad de Medida Base:</label>

    <select id="unidadBase" name="unidad" class="form-select" required>
        <option value="">-- Selecciona Unidad --</option>
        <%
            // Itera sobre todos los valores del ENUM Unidad
            for (Unidad unidad : Unidad.values()) {
        %>
        <option value="<%= unidad.name() %>"><%= unidad.toString() %> (<%= unidad.getAbreviatura() %>)</option>
        <%
            }
        %>
    </select>

    <button type="submit" class="btn btn--primary">Guardar Ingrediente</button>
</form>

<%@include file="/vistas/includes/footer.jsp" %>