package com.ecommerce.tiendaspring.models;

import java.math.BigDecimal;

public class CarritoItem {
    private Producto producto;
    private int cantidad;
    private BigDecimal subtotal;

    public CarritoItem(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(cantidad));
    }

    // Getters y Setters
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(cantidad));
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}