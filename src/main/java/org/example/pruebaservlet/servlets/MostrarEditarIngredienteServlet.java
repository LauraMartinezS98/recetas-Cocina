package org.example.pruebaservlet.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.IngredienteDAO;
import logica.Ingrediente;
import java.io.IOException;

@WebServlet("/MostrarEditarIngrediente")
public class MostrarEditarIngredienteServlet extends HttpServlet {

    private final IngredienteDAO ingredienteDAO = new IngredienteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Ingrediente ingrediente = ingredienteDAO.buscarPorId(id);

            if (ingrediente != null) {
                request.setAttribute("ingrediente", ingrediente);
                request.getRequestDispatcher("/vistas/ingredientes/editarIngrediente.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("error", "Error: Ingrediente no encontrado.");
                response.sendRedirect("ListarIngredientes");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Error: ID de ingrediente inválido.");
            response.sendRedirect("ListarIngredientes");
        }
    }
}