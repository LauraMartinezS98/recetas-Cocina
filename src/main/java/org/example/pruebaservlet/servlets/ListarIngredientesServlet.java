package org.example.pruebaservlet.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.IngredienteDAO;
import logica.Ingrediente;

import java.io.IOException;
import java.util.List;

@WebServlet("/Ingredientes")
public class ListarIngredientesServlet extends HttpServlet {

    private final IngredienteDAO ingredienteDAO = new IngredienteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        String vista = "/vistas/ingredientes/listarIngredientes.jsp"; //Vista por defecto

        try {
            if ("ver".equals(accion)) {
                //Si la URL es /Ingredientes?accion=ver&id=X
                Integer idIngrediente = Integer.parseInt(request.getParameter("id"));
                Ingrediente ingrediente = ingredienteDAO.buscarPorId(idIngrediente);
                request.setAttribute("ingrediente", ingrediente);
                vista = "/vistas/ingredientes/verIngrediente.jsp";
            } else {
                //Si la URL es /Ingredientes (o accion=listar)
                List<Ingrediente> ingredientes = ingredienteDAO.listarTodos();
                request.setAttribute("ingredientes", ingredientes);
                // La vista por defecto es listarIngredientes.jsp
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Error: El ID proporcionado no es un número válido.");
            vista = "/vistas/ingredientes/listarIngredientes.jsp"; // Volver al listado si falla el ID
        } catch (Exception e) {
            request.setAttribute("error", "Error al cargar los datos de ingredientes: " + e.getMessage());
            e.printStackTrace();
            vista = "/index.jsp"; //Ir a la página principal si hay un fallo crítico
        }

        //Envía la petición a la vista JSP
        request.getRequestDispatcher(vista).forward(request, response);
    }
}