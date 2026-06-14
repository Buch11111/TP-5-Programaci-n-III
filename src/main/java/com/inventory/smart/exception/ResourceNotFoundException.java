package com.inventory.smart.exception;

/**
 * Excepción lanzada cuando no se encuentra un recurso solicitado.
 *
 * @author Grupo 3 - Inventario Inteligente
 * @since 1.0
 */
public class ResourceNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    /**
     * Crea una nueva excepción con el mensaje especificado.
     *
     * @param message el detalle del error
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
