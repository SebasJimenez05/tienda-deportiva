package com.ecommerce.tiendaspring.services;

import com.ecommerce.tiendaspring.models.Producto;
import com.ecommerce.tiendaspring.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll(
                Sort.by(Sort.Direction.ASC, "orden")
        );
    }

    public List<Producto> obtenerProductosPorCategoria(String categoria) {
        return productoRepository.findByCategoria(
                categoria,
                Sort.by(Sort.Direction.ASC, "orden")
        );
    }

    public List<Producto> obtenerProductosEnStock() {
        return productoRepository.findByStockGreaterThan(
                0,
                Sort.by(Sort.Direction.ASC, "orden")
        );
    }

    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public void actualizarStock(Long productoId, int cantidadVendida) {
        Optional<Producto> productoOpt = productoRepository.findById(productoId);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            producto.setStock(producto.getStock() - cantidadVendida);
            productoRepository.save(producto);
        }
    }

    public List<Producto> obtenerProductosDisponibles() {
        return productoRepository.findAll(
                Sort.by(Sort.Direction.ASC, "orden")
        ).stream()
                .filter(p -> p.getStockDisponible() > 0)
                .collect(Collectors.toList());
    }


    public List<Producto> obtenerProductosDisponiblesPorCategoria(String categoria) {
        return productoRepository.findByCategoria(
                categoria,
                Sort.by(Sort.Direction.ASC, "orden")
        ).stream()
                .filter(p -> p.getStockDisponible() > 0)
                .collect(Collectors.toList());
    }
}
