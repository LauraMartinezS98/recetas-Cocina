package org.example.pruebaservlet.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.RecetaDAO;
import logica.Receta;

import java.io.IOException;
import java.util.List;

@WebServlet("/Recetas")
public class ListarRecetasServlet extends HttpServlet {

    private final RecetaDAO recetaDAO = new RecetaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        String vista = "vistas/recetas/listarRecetas.jsp";

        try {
            if ("ver".equals(accion)) {
                // 1. ACCIÓN VER DETALLE
                Integer idReceta = Integer.parseInt(request.getParameter("id"));
                Receta receta = recetaDAO.buscarPorId(idReceta);
                request.setAttribute("receta", receta);
                vista = "/vistas/recetas/verReceta.jsp";
            }
            else {
                // 2. ACCIÓN LISTAR o BUSCAR (Procesa solo Modalidad y Dificultad)

                String modalidad = request.getParameter("modalidad");
                String dificultad = request.getParameter("dificultad");

                List<Receta> recetas;

                // Si al menos un filtro está presente, llamamos al método de búsqueda avanzada
                if ((modalidad != null && !modalidad.isEmpty() && !"".equals(modalidad)) ||
                        (dificultad != null && !dificultad.isEmpty() && !"".equals(dificultad))) {

                    // Llama al método del DAO SÓLO con los filtros
                    recetas = recetaDAO.buscarPorFiltros(modalidad, dificultad);
                }
                else {
                    // Si no hay filtros, listar todos
                    recetas = recetaDAO.listarTodos();
                }

                request.setAttribute("recetas", recetas);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Error: El ID proporcionado no es válido.");
        } catch (Exception e) {
            request.setAttribute("error", "Error al cargar o buscar recetas: " + e.getMessage());
            e.printStackTrace();
        }
        //Envía la petición a la vista JSP
        request.getRequestDispatcher(vista).forward(request, response);
    }
}