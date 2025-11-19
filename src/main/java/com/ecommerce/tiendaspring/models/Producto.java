package com.ecommerce.tiendaspring.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Integer stockReservado = 0;

    @Column(nullable = false, length = 50)
    private String categoria;

    @Column(length = 255)
    private String imagen;

    //  Nuevo campo para el orden
    @Column(nullable = false)
    private Integer orden = 0;

    // Constructores
    public Producto() {}

    public Producto(String nombre, String descripcion, BigDecimal precio, Integer stock,
                String categoria, String imagen, Integer orden) {

    this.nombre = nombre;
    this.descripcion = descripcion;
    this.precio = precio;
    this.stock = stock;
    this.stockReservado = 0;
    this.categoria = categoria;
    this.imagen = imagen;
    this.orden = orden;
}


    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStockReservado() {
        return stockReservado;
    }

    public void setStockReservado(Integer stockReservado) {
        this.stockReservado = stockReservado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    //  Getters y setters del campo orden
    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    // Método helper para obtener stock disponible
    public Integer getStockDisponible() {
        return this.stock - this.stockReservado;
    }
}
