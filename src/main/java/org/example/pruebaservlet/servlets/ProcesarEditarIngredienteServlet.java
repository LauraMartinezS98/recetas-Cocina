package org.example.pruebaservlet.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.IngredienteDAO;
import logica.Ingrediente;
import logica.Unidad;
import java.io.IOException;

@WebServlet("/ProcesarEditarIngrediente")
public class ProcesarEditarIngredienteServlet extends HttpServlet {

    private final IngredienteDAO ingredienteDAO = new IngredienteDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String vistaRedireccion = "/ListarIngredientes";
        Integer id = null;

        try {
            id = Integer.parseInt(request.getParameter("id"));
            String nuevoNombre = request.getParameter("nombre");
            String unidadString = request.getParameter("unidad");

            // Validación de Nulidad y Vacío
            if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
                request.getSession().setAttribute("error", "El nombre no puede estar vacío.");
                response.sendRedirect("/MostrarEditarIngrediente?id=" + id);
                return;
            }

            Ingrediente ingrediente = ingredienteDAO.buscarPorId(id);
            if (ingrediente != null) {
                ingrediente.setNombre(nuevoNombre.trim());
                ingrediente.setUnidad(Unidad.valueOf(unidadString));

                ingredienteDAO.actualizar(ingrediente);

                request.getSession().setAttribute("mensaje", "Ingrediente actualizado con éxito.");
            } else {
                request.getSession().setAttribute("error", "Error: Ingrediente no encontrado.");
            }

        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error al actualizar: " + e.getMessage());
            // Si el error ocurrió después de obtener el ID, volvemos a la edición
            if (id != null) vistaRedireccion = "MostrarEditarIngrediente?id=" + id;
        }

        response.sendRedirect(vistaRedireccion);
    }
}