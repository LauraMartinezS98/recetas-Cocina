package org.example.pruebaservlet.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.RecetaDAO;
import logica.Receta;
import java.io.IOException;

// Mapeado para mostrar el formulario de edición
@WebServlet("/MostrarEditarReceta")
public class MostrarEditarRecetaServlet extends HttpServlet {

    private final RecetaDAO recetaDAO = new RecetaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. Obtener el ID de la receta a editar
            Integer id = Integer.parseInt(request.getParameter("id"));

            // 2. Buscar la Receta (con detalles, gracias al FETCH JOIN en tu DAO)
            Receta receta = recetaDAO.buscarPorId(id);

            if (receta != null) {
                // 3. Poner el objeto en el request y enviar al formulario
                request.setAttribute("receta", receta);
                request.getRequestDispatcher("/vistas/recetas/editarReceta.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("error", "Error: Receta con ID " + id + " no encontrada.");
                response.sendRedirect("ListarRecetas"); // Redirige al listado
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Error: ID de receta inválido.");
            response.sendRedirect("ListarRecetas");
        }
    }
}