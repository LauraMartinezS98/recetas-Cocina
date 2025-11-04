<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="logica.Ingrediente" %>
<%@ page import="logica.Unidad" %>

<%@include file="/vistas/includes/header.jsp" %>

<main>
    <%-- 1. Recupera el objeto Ingrediente enviado por MostrarEditarIngredienteServlet --%>
    <jsp:useBean id="ingrediente" scope="request" type="logica.Ingrediente"/>

    <h2>Editar Ingrediente: <%= ingrediente.getNombre() %></h2>

    <%-- Muestra errores/mensajes de estado --%>
    <% if (request.getSession().getAttribute("error") != null) { %>
    <p class="error"><%= request.getSession().getAttribute("error") %></p>
    <% request.getSession().removeAttribute("error"); %>
    <% } %>

    <form action="ProcesarEditarIngrediente" method="post">

        <%-- Campo OCULTO: ID del ingrediente --%>
        <input type="hidden" name="id" value="<%= ingrediente.getId_ingrediente() %>">

        <div>
            <label for="nombre">Nombre del Ingrediente:</label>
            <input type="text" id="nombre" name="nombre"
                   value="<%= ingrediente.getNombre() %>" required>
        </div>

        <div>
            <label for="unidad">Unidad de Medida Base:</label>
            <select id="unidad" name="unidad" required>
                <option value="">-- Selecciona Unidad --</option>
                <%
                    for (Unidad unidad : Unidad.values()) {
                        String selected = (unidad.equals(ingrediente.getUnidad())) ? "selected" : "";
                %>
                <option value="<%= unidad.name() %>" <%= selected %>>
                    <%= unidad.toString() %>
                </option>
                <%
                    }
                %>
            </select>
        </div>

        <button type="submit">Guardar Cambios</button>
        <a href="Ingredientes" style="margin-left: 15px;">Cancelar</a>
    </form>
</main>

<%@include file="/vistas/includes/footer.jsp" %>