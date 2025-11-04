package org.example.pruebaservlet.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.RecetaDAO;
import java.io.IOException;

@WebServlet("/BorrarReceta")
public class BorrarRecetaServlet extends HttpServlet {

    private final RecetaDAO recetaDAO = new RecetaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String vistaRedireccion = "Recetas";

        try {
            Integer id = Integer.parseInt(request.getParameter("id"));

            recetaDAO.eliminar(id);

            request.getSession().setAttribute("mensaje", "Receta eliminada con éxito.");

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Error: ID de receta inválido.");
        } catch (RuntimeException e) {
            request.getSession().setAttribute("error", "Error al eliminar la receta: " + e.getMessage());
        }

        response.sendRedirect(vistaRedireccion);
    }
}