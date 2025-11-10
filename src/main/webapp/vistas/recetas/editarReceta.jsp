<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="logica.Receta" %>
<%@ page import="logica.Dificultad" %>
<%@ page import="logica.Modalidad" %>

<%@include file="/vistas/includes/header.jsp" %>

<main>
    <%-- 1. Recupera el objeto Receta enviado por ProcesarEditarRecetaServlet --%>
    <jsp:useBean id="receta" scope="request" type="logica.Receta"/>

    <h2>Editar Receta: <%= receta.getNombre() %></h2>

    <%-- Muestra errores/mensajes de estado --%>
    <% if (request.getSession().getAttribute("error") != null) { %>
    <p class="error"><%= request.getSession().getAttribute("error") %></p>
    <% request.getSession().removeAttribute("error"); %>
    <% } %>

    <%--Context Path en la acción del formulario --%>
    <form action="<%= request.getContextPath() %>/editarRecetas" method="post">

        <%-- Campo OCULTO: ID de la receta --%>
        <input type="hidden" name="id" value="<%= receta.getId_receta() %>">

        <div>
            <label for="nombre">Nombre de la Receta:</label>
            <input type="text" id="nombre" name="nombre"
                   value="<%= receta.getNombre() %>" required>
        </div>

        <div>
            <label for="instrucciones">Instrucciones:</label>
            <%-- Usando getDescripcion() para el textarea --%>
            <textarea id="instrucciones" name="instrucciones" rows="10" required><%= receta.getDescripcion() %></textarea>
        </div>

        <div>
            <label for="dificultad">Dificultad:</label>
            <select id="dificultad" name="dificultad" required>
                <% for (Dificultad dif : Dificultad.values()) {
                    String selected = (dif.equals(receta.getDificultad())) ? "selected" : "";
                %>
                <option value="<%= dif.name() %>" <%= selected %>><%= dif.toString() %></option>
                <% } %>
            </select>
        </div>

        <div>
            <label for="modalidad">Modalidad:</label>
            <select id="modalidad" name="modalidad" required>
                <% for (Modalidad mod : Modalidad.values()) {
                    String selected = (mod.equals(receta.getModalidad())) ? "selected" : "";
                %>
                <option value="<%= mod.name() %>" <%= selected %>><%= mod.toString() %></option>
                <% } %>
            </select>
        </div>

        <button type="submit">Guardar Cambios</button>
        <%-- Context Path en el enlace de Cancelar --%>
        <a href="<%= request.getContextPath() %>/Recetas" style="margin-left: 15px;">Cancelar</a>
    </form>
</main>

<%@include file="/vistas/includes/footer.jsp" %>