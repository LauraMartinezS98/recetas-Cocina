<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, logica.Ingrediente, logica.Unidad, logica.Dificultad, logica.Modalidad" %>
<%@include file="/vistas/includes/header.jsp" %>

<h2>Añadir Nueva Receta</h2>

<form action="<%= request.getContextPath() %>/CrearReceta" method="post">
    <input type="hidden" name="accion" value="guardar">

    <label for="nombre" class="form-label">Nombre de la Receta:</label>
    <input type="text" id="nombre" name="nombre" class="form-input" required>

    <div style="display: flex; gap: 20px;">
        <div style="flex: 1;">
            <label for="dificultad" class="form-label">Dificultad:</label>
            <select id="dificultad" name="dificultad" class="form-select" required>
                <option value="">-- Selecciona Dificultad --</option>
                <% for (Dificultad dificultad : Dificultad.values()) { %>
                <option value="<%= dificultad.name() %>"><%= dificultad.toString() %></option>
                <% } %>
            </select>
        </div>

        <div style="flex: 1;">
            <label for="modalidad" class="form-label">Modalidad:</label>
            <select id="modalidad" name="modalidad" class="form-select" required>
                <option value="">-- Selecciona Modalidad --</option>
                <% for (Modalidad modalidad : Modalidad.values()) { %>
                <option value="<%= modalidad.name() %>"><%= modalidad.getDescripcion() %></option>
                <% } %>
            </select>
        </div>
    </div>
    <label for="descripcion" class="form-label">Instrucciones:</label>
    <textarea id="descripcion" name="descripcion" rows="5" class="form-textarea" required></textarea>

    <h3>Ingredientes:</h3>
    <div id="ingredientes-container">
        <div class="ingredient-list__item">
            <select name="ingredienteId" class="form-select" required>
                <option value="">-- Selecciona Ingrediente --</option>
                <%
                    //Carga la lista de Ingredientes disponibles
                    List<Ingrediente> ingredientesDisponibles = (List<Ingrediente>) request.getAttribute("ingredientesDisponibles");
                    if (ingredientesDisponibles != null) {
                        for (Ingrediente ing : ingredientesDisponibles) {
                %>
                <option value="<%= ing.getId_ingrediente() %>">
                    <%= ing.getNombre() %> (<%= ing.getUnidad() %>)
                </option>
                <%
                        }
                    }
                %>
            </select>
            <input type="number" name="cantidad" placeholder="Cantidad" min="0.1" step="any" class="form-input" required>


            <button type="button" onclick="eliminarIngrediente(this)" class="btn btn--delete">Quitar</button>
            <button type="button" onclick="añadirIngrediente()" class="btn btn--primary">Añadir Otro Ingrediente</button>
            <button type="submit" class="btn btn--primary">Guardar Receta</button>
        </div>
    </div>

</form>

<script>
    function añadirIngrediente() {
        const container = document.getElementById('ingredientes-container');
        const originalItem = container.querySelector('.ingredient-list__item');

        // Clonar el primer item para replicar los selects y inputs
        const newItem = originalItem.cloneNode(true);

        // Limpiar valores en el clon
        newItem.querySelector('select[name="ingredienteId"]').value = "";
        newItem.querySelector('input[name="cantidad"]').value = "";

        container.appendChild(newItem);
    }

    function eliminarIngrediente(btn) {
        const container = document.getElementById('ingredientes-container');
        const items = container.querySelectorAll('.ingredient-list__item');

        // Evitar eliminar el último formulario base
        if (items.length > 1) {
            btn.closest('.ingredient-list__item').remove();
        } else {
            alert("Una receta debe tener al menos un ingrediente.");
        }
    }
</script>

<%@include file="/vistas/includes/footer.jsp" %>