package org.example.pruebaservlet.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.RecetaDAO;
import logica.Receta;
import logica.Dificultad;
import logica.Modalidad;
import java.io.IOException;

@WebServlet("/editarRecetas")
public class ProcesarEditarRecetaServlet extends HttpServlet {

    private final RecetaDAO recetaDAO = new RecetaDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        // 1. Inicializamos con el mapping de éxito (Listado), usando /
        String vistaRedireccion = "/Recetas"; // ASUMIMOS que el listado está en /Recetas
        Integer idReceta = null;

        try {
            // 1. Obtención y Sanitización de datos
            idReceta = Integer.parseInt(request.getParameter("id"));

            String nombreLimpio = getParameterValue(request, "nombre");
            String instruccionesLimpias = getParameterValue(request, "instrucciones");

            String dificultadString = request.getParameter("dificultad");
            String modalidadString = request.getParameter("modalidad");

            // 2. VALIDACIÓN
            if (nombreLimpio.isEmpty() || instruccionesLimpias.isEmpty()) {
                request.getSession().setAttribute("error", "Error: El nombre y las instrucciones no pueden estar vacíos.");
                // Redirige de vuelta al formulario de edición (USANDO /)
                response.sendRedirect(request.getContextPath() + "/MostrarEditarReceta?id=" + idReceta);
                return;
            }

            // 3. Buscar y actualizar
            Receta receta = recetaDAO.buscarPorId(idReceta);

            if (receta != null) {
                receta.setNombre(nombreLimpio);
                receta.setDescripcion(instruccionesLimpias);
                receta.setDificultad(Dificultad.valueOf(dificultadString));
                receta.setModalidad(Modalidad.valueOf(modalidadString));

                recetaDAO.actualizar(receta);

                request.getSession().setAttribute("mensaje", "Receta actualizada con éxito!");
            } else {
                request.getSession().setAttribute("error", "Error: No se encontró la receta.");
            }

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Error: ID o formato de datos inválido.");
            vistaRedireccion = "/Recetas"; // Si el ID falla, volvemos al listado
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("error", "Error: Los valores de dificultad o modalidad no son válidos.");
            // 2. Usamos rutas absolutas (con /) para el catch
            vistaRedireccion = (idReceta != null) ? "/MostrarEditarReceta?id=" + idReceta : "/Recetas";
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error al actualizar la receta: " + e.getMessage());
            // 3. Usamos rutas absolutas (con /) para el catch
            vistaRedireccion = (idReceta != null) ? "/MostrarEditarReceta?id=" + idReceta : "/Recetas";
        }

        // 4. Redirección final segura usando getContextPath()
        response.sendRedirect(request.getContextPath() + vistaRedireccion);
    }
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
    /**
     * Método auxiliar para recuperar el valor de un parámetro, limpiarlo y asegurar que no es nulo.
     */
    private String getParameterValue(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        return (value != null) ? value.trim() : "";
    }
}