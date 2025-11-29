package com.deportes.tiendadeportiva.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class ArticuloDeportivo {
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
    private String deporte;  // Cambiado de categoria a deporte

    @Column(nullable = false, length = 50)
    private String marca;    // Nuevo campo marca

    @Column(nullable = false, length = 50)
    private String tipo;     // Nuevo campo tipo

    @Column(length = 255)
    private String imagen;

    // Nuevo campo para el orden
    @Column(nullable = false)
    private Integer orden = 0;

    // Constructores
    public ArticuloDeportivo() {}

    public ArticuloDeportivo(String nombre, String descripcion, BigDecimal precio, Integer stock,
                            String deporte, String marca, String tipo, String imagen, Integer orden) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.stockReservado = 0;
        this.deporte = deporte;
        this.marca = marca;
        this.tipo = tipo;
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

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

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