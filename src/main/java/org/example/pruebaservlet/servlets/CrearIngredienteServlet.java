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
@WebServlet("/CrearIngrediente")
public class CrearIngredienteServlet extends HttpServlet {

    private final IngredienteDAO ingredienteDAO = new IngredienteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Muestra el formulario (que ahora usa un SELECT)
        request.getRequestDispatcher("/vistas/ingredientes/añadirIngrediente.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String vistaRedireccion = "CrearIngrediente"; //Si falla, vuelve al formulario

        try {
            String nombre = request.getParameter("nombre");
            String unidadString = request.getParameter("unidad"); //Obtiene la cadena del <select>
            //
            if (nombre == null || nombre.trim().isEmpty()) {
                request.getSession().setAttribute("error", "Error: El nombre del ingrediente no puede estar vacío.");
                response.sendRedirect(vistaRedireccion);
                return; // Detiene la ejecución si el nombre está vacío
            }
            //Transforma la cadena (ej: "GRAMO") a un objeto ENUM.
            Unidad unidadEnum = Unidad.valueOf(unidadString);

            Ingrediente nuevoIngrediente = new Ingrediente(nombre, unidadEnum);
            ingredienteDAO.guardar(nuevoIngrediente);

            request.getSession().setAttribute("mensaje", "Ingrediente guardado con éxito!");
            vistaRedireccion = "Ingredientes"; //Redirige al listado

        } catch (IllegalArgumentException e) {
            // Este error ocurre si Unidad.valueOf() falla porque la cadena no coincide con ningún valor ENUM.
            request.getSession().setAttribute("error", "Error: La unidad seleccionada no es válida.");
        }
        catch (RuntimeException e) {
            //Captura errores de DAO (ej: duplicidad de nombre)
            request.getSession().setAttribute("error", "Error al guardar (posiblemente nombre duplicado): " + e.getMessage());
        }

        response.sendRedirect(vistaRedireccion);
    }
}