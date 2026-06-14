package com.inventory.smart.exception;

/**
 * Excepción lanzada cuando se rompe una regla de negocio.
 *
 * @author Grupo 3 - Inventario Inteligente
 * @since 1.0
 */
public class BusinessRuleException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    /**
     * Crea una nueva excepción con el mensaje especificado.
     *
     * @param message el detalle del error
     */
    public BusinessRuleException(String message) {
        super(message);
    }
}
