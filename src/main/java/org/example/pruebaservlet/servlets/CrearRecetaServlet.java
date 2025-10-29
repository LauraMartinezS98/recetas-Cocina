package org.example.pruebaservlet.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.RecetaDAO; // DAO para guardar la Receta
import dao.IngredienteDAO; // DAO para buscar el Ingrediente existente
import logica.*;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/CrearReceta")
public class CrearRecetaServlet extends HttpServlet {

    private final RecetaDAO recetaDAO = new RecetaDAO();
    private final IngredienteDAO ingredienteDAO = new IngredienteDAO();

    /**
     * Maneja la solicitud GET para mostrar el formulario de creación de receta.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Carga la lista de ingredientes disponibles para el campo <select> del formulario
            request.setAttribute("ingredientesDisponibles", ingredienteDAO.listarTodos());
            request.getRequestDispatcher("/vistas/recetas/añadirReceta.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error al cargar los ingredientes disponibles: " + e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    /**
     * Maneja la solicitud POST para procesar y guardar la nueva receta.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        String vistaRedireccion = "CrearReceta"; // URL de fallback

        if ("guardar".equals(accion)) {
            try {
                guardarReceta(request);
                request.getSession().setAttribute("mensaje", "Receta guardada con éxito!");
                vistaRedireccion = "Recetas"; // Redirige al Servlet de listado
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("error", "Error de formato: Asegúrese de usar números válidos en las cantidades.");
                vistaRedireccion = "CrearReceta";
            } catch (RuntimeException e) {
                // Captura errores de DAO (ej. problemas de conexión o base de datos)
                request.getSession().setAttribute("error", "Error al guardar la receta: " + e.getMessage());
                vistaRedireccion = "CrearReceta";
            }
        }

        response.sendRedirect(vistaRedireccion);
    }

    // --- MÉTODO DE LÓGICA DE NEGOCIO (Donde se ensambla la relación N:M) ---
    private void guardarReceta(HttpServletRequest request) {

        // 1. Obtener parámetros de campos de texto estándar
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");

        // 2. Obtener y convertir parámetros ENUM
        // Los valores vienen como String del formulario y deben convertirse a los tipos ENUM.
        String dificultadString = request.getParameter("dificultad");
        String modalidadString = request.getParameter("modalidad");

        Dificultad dificultad = Dificultad.valueOf(dificultadString);
        Modalidad modalidad = Modalidad.valueOf(modalidadString);

        // 3. Crear la nueva Receta con el constructor actualizado
        Receta nuevaReceta = new Receta(nombre, descripcion, dificultad, modalidad);

        // 4. Obtener parámetros de la relación N:M (Ingredientes)
        String[] idsIngrediente = request.getParameterValues("ingredienteId");
        String[] cantidades = request.getParameterValues("cantidad");
        String[] unidades = request.getParameterValues("unidad");

        if (idsIngrediente != null && idsIngrediente.length > 0) {

            for (int i = 0; i < idsIngrediente.length; i++) {

                // Obtiene y valida los datos
                Integer idIng = Integer.parseInt(idsIngrediente[i]);
                BigDecimal cantidad = new BigDecimal(cantidades[i].trim().replace(",", "."));
                String unidad = unidades[i];

                // Busca la entidad Ingrediente existente
                Ingrediente ingExistente = ingredienteDAO.buscarPorId(idIng);

                if (ingExistente != null) {
                    // Crea la entidad DetalleReceta
                    DetalleReceta detalle = new DetalleReceta(
                            nuevaReceta,
                            ingExistente,
                            cantidad,
                            unidad
                    );

                    // ENSAMBLA la relación N:M (LÍNEA CLAVE)
                    nuevaReceta.addDetalleReceta(detalle);
                }
            }
        }

        // 5. Guarda la Receta (la cascada se encarga de DetalleReceta)
        recetaDAO.guardar(nuevaReceta);
    }
}