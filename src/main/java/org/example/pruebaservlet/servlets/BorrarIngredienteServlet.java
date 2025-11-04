package org.example.pruebaservlet.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.IngredienteDAO;
import java.io.IOException;

@WebServlet("/BorrarIngrediente")
public class BorrarIngredienteServlet extends HttpServlet {

    private final IngredienteDAO ingredienteDAO = new IngredienteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String vistaRedireccion = "Ingredientes";

        try {
            Integer id = Integer.parseInt(request.getParameter("id"));

            // 1. Intentar la eliminación (la DB lo bloqueará si hay referencias)
            ingredienteDAO.eliminar(id);

            request.getSession().setAttribute("mensaje", "Ingrediente eliminado con éxito.");

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Error: ID de ingrediente inválido.");
        } catch (RuntimeException e) {
            // 2. Capturar el error de la Base de Datos

            // Las excepciones de la DB se envuelven en RuntimeException.
            // Para el borrado bloqueado, el mensaje suele contener 'constraint', 'foreign key' o 'cannot delete'.
            String errorMsg = e.getMessage();

            if (errorMsg != null && (errorMsg.contains("constraint") || errorMsg.contains("foreign key") || errorMsg.contains("cannot delete"))) {
                // 3. Mostrar el mensaje de control
                request.getSession().setAttribute("error", "⛔ No se puede eliminar el ingrediente porque está **siendo utilizado en una o más recetas**.");
            } else {
                // Si es otro tipo de RuntimeException
                request.getSession().setAttribute("error", "Error inesperado al eliminar el ingrediente: " + errorMsg);
            }
        }

        response.sendRedirect(vistaRedireccion);
    }
}