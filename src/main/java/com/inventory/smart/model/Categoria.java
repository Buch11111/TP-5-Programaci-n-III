package com.inventory.smart.model;

/**
 * Representa una categoría que agrupa productos dentro del inventario.
 *
 * @author Grupo 3 - Inventario Inteligente
 * @since 1.0
 */
public class Categoria {
    private Long id;
    private String nombre;
    private String descripcion;

    /**
     * Crea una nueva categoría.
     *
     * @param id el identificador único de la categoría (puede ser nulo antes de guardarse)
     * @param nombre el nombre de la categoría (no puede ser nulo ni vacío)
     * @param descripcion la descripción de la categoría
     * @throws IllegalArgumentException si el nombre es nulo o vacío
     */
    public Categoria(Long id, String nombre, String descripcion) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
        }
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }


    /**
     * Obtiene el identificador de la categoría.
     *
     * @return el identificador
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la categoría.
     *
     * @param id el nuevo identificador
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la categoría.
     *
     * @return el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la categoría.
     *
     * @param nombre el nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción de la categoría.
     *
     * @return la descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la categoría.
     *
     * @param descripcion la nueva descripción
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
        /**
     * Devuelve el nombre de la categoría en mayúsculas.
     *
     * @return el nombre en mayúsculas
     */
    public String getNombreMayuscula() {
        return nombre != null ? nombre.toUpperCase() : null;
    }
}
