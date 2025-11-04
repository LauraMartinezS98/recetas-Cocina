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

@WebServlet("/ProcesarEditarReceta")
public class ProcesarEditarRecetaServlet extends HttpServlet {

    private final RecetaDAO recetaDAO = new RecetaDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String vistaRedireccion = "/ListarRecetas";
        // Declaramos el ID fuera del try para usarlo en la redirección de error
        Integer idReceta = null;

        try {
            // 1. Obtención y Sanitización de datos
            idReceta = Integer.parseInt(request.getParameter("id"));

            // Los valores se recuperan y se limpian directamente.
            String nombreLimpio = getParameterValue(request, "nombre");
            String instruccionesLimpias = getParameterValue(request, "instrucciones");

            String dificultadString = request.getParameter("dificultad");
            String modalidadString = request.getParameter("modalidad");

            // 2. VALIDACIÓN
            if (nombreLimpio.isEmpty() || instruccionesLimpias.isEmpty()) {
                request.getSession().setAttribute("error", "Error: El nombre y las instrucciones no pueden estar vacíos.");
                // Redirige de vuelta al formulario de edición
                response.sendRedirect("MostrarEditarReceta?id=" + idReceta);
                return;
            }

            // 3. Buscar y actualizar
            Receta receta = recetaDAO.buscarPorId(idReceta);

            if (receta != null) {
                receta.setNombre(nombreLimpio);
                receta.setDescripcion(instruccionesLimpias); // setDescripcion
                receta.setDificultad(Dificultad.valueOf(dificultadString));
                receta.setModalidad(Modalidad.valueOf(modalidadString));

                recetaDAO.actualizar(receta);

                request.getSession().setAttribute("mensaje", "Receta actualizada con éxito!");
            } else {
                request.getSession().setAttribute("error", "Error: No se encontró la receta.");
            }

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Error: ID o formato de datos inválido.");
            vistaRedireccion = "ListarRecetas";
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("error", "Error: Los valores de dificultad o modalidad no son válidos.");
            vistaRedireccion = (idReceta != null) ? "MostrarEditarReceta?id=" + idReceta : "ListarRecetas";
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error al actualizar la receta: " + e.getMessage());
            vistaRedireccion = (idReceta != null) ? "MostrarEditarReceta?id=" + idReceta : "ListarRecetas";
        }

        response.sendRedirect(vistaRedireccion);
    }

    /**
     * Método auxiliar para recuperar el valor de un parámetro, limpiarlo y asegurar que no es nulo.
     */
    private String getParameterValue(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        return (value != null) ? value.trim() : "";
    }
}