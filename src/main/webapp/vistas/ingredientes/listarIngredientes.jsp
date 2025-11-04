<%@include file="/vistas/includes/header.jsp" %>
<%@ page import="java.util.List, logica.Ingrediente" %>

<h2>Listado de Ingredientes</h2>

<%
    List<Ingrediente> ingredientes = (List<Ingrediente>) request.getAttribute("ingredientes");
    if (ingredientes != null && !ingredientes.isEmpty()) {
%>
<table class="data-table">
    <thead>
    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Unidad Base</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <% for (Ingrediente ingrediente : ingredientes) { %>
    <tr>
        <td><%= ingrediente.getId_ingrediente() %></td>
        <td><%= ingrediente.getNombre() %></td>

        <td><%= ingrediente.getUnidad().toString() %></td>

        <td>
            <a href="Ingredientes?accion=ver&id=<%= ingrediente.getId_ingrediente() %>">Ver Detalles</a> |
            <a href="MostrarEditarIngrediente?id=<%= ingrediente.getId_ingrediente() %>">Editar</a>
            <%--No lo quiero usar--%>
          <%--  <a href="BorrarIngrediente?id=<%= ingrediente.getId_ingrediente() %>"
               onclick="return confirm('¿Está seguro de que desea eliminar <%= ingrediente.getNombre() %>?');">
                Eliminar
            </a>--%>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
<%
} else {
%>
<p>No hay ingredientes registrados. <a href="CrearIngrediente">¡Añade el primero!</a></p>
<%
    }
%>

<%@include file="/vistas/includes/footer.jsp" %>