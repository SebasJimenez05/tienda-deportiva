package com.deportes.tiendadeportiva.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.deportes.tiendadeportiva.models.ArticuloDeportivo;
import com.deportes.tiendadeportiva.repositories.ProductoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private StockNotifier stockNotifier;

    @Autowired
    private com.deportes.tiendadeportiva.repositories.CarritoItemRepository carritoItemRepository;

    public List<ArticuloDeportivo> obtenerTodosLosProductos() {
        return productoRepository.findAll(
            Sort.by(Sort.Direction.ASC, "orden")
        ).stream()
            .peek(p -> {
                Integer reservado = carritoItemRepository.sumCantidadByProductoId(p.getId());
                p.setStockReservado(reservado == null ? 0 : reservado);
            })
            .collect(Collectors.toList());
    }

    public List<ArticuloDeportivo> obtenerProductosPorCategoria(String categoria) {
        return productoRepository.findByDeporte(
                categoria,
                Sort.by(Sort.Direction.ASC, "orden")
        );
    }

    public List<ArticuloDeportivo> obtenerProductosEnStock() {
        return productoRepository.findByStockGreaterThan(
                0,
                Sort.by(Sort.Direction.ASC, "orden")
        );
    }

    public Optional<ArticuloDeportivo> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    public ArticuloDeportivo guardarProducto(ArticuloDeportivo producto) {
        return productoRepository.save(producto);
    }
    
    // Método para reponer stock (añadir unidades)
    public ArticuloDeportivo reponerStock(Long productoId, int cantidadAgregar) {
        ArticuloDeportivo producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Limitar la reposición máxima a 30 unidades por petición
        int cantidadReal = Math.min(cantidadAgregar, 30);
        int nuevoStock = (producto.getStock() == null ? 0 : producto.getStock()) + cantidadReal;
        producto.setStock(nuevoStock);
        ArticuloDeportivo guardado = productoRepository.save(producto);

        // Notificar a clientes conectados (WebSocket)
        try {
            stockNotifier.notificarCambioStock(guardado);
        } catch (Exception ignored) {}

        return guardado;
    }

    public void actualizarStock(Long productoId, int cantidadVendida) {
        Optional<ArticuloDeportivo> productoOpt = productoRepository.findById(productoId);
        if (productoOpt.isPresent()) {
            ArticuloDeportivo producto = productoOpt.get();
            int nuevoStock = producto.getStock() - cantidadVendida;
            if (nuevoStock < 0) nuevoStock = 0;
            producto.setStock(nuevoStock);
            productoRepository.save(producto);
        }
    }

    public List<ArticuloDeportivo> obtenerProductosDisponibles() {
        return productoRepository.findAll(
                Sort.by(Sort.Direction.ASC, "orden")
        ).stream()
                // Calcular reservas desde CarritoItem y filtrar por stock disponible
                .peek(p -> {
                    Integer reservado = carritoItemRepository.sumCantidadByProductoId(p.getId());
                    p.setStockReservado(reservado == null ? 0 : reservado);
                })
                .filter(p -> p.getStockDisponible() > 0)
                .collect(Collectors.toList());
    }

    public List<ArticuloDeportivo> obtenerProductosDisponiblesPorCategoria(String categoria) {
        return productoRepository.findByDeporte(
                categoria,
                Sort.by(Sort.Direction.ASC, "orden")
        ).stream()
                .peek(p -> {
                    Integer reservado = carritoItemRepository.sumCantidadByProductoId(p.getId());
                    p.setStockReservado(reservado == null ? 0 : reservado);
                })
                .filter(p -> p.getStockDisponible() > 0)
                .collect(Collectors.toList());
    }

    // ========== NUEVOS MÉTODOS PARA FILTROS ==========

    public List<ArticuloDeportivo> obtenerProductosDisponiblesPorMarca(String marca) {
        return productoRepository.findByMarca(
                marca,
                Sort.by(Sort.Direction.ASC, "orden")
        ).stream()
                .peek(p -> {
                    Integer reservado = carritoItemRepository.sumCantidadByProductoId(p.getId());
                    p.setStockReservado(reservado == null ? 0 : reservado);
                })
                .filter(p -> p.getStockDisponible() > 0)
                .collect(Collectors.toList());
    }

    public List<ArticuloDeportivo> obtenerProductosDisponiblesPorCategoriaYMarca(String categoria, String marca) {
        return productoRepository.findByDeporteAndMarca(
                categoria,
                marca,
                Sort.by(Sort.Direction.ASC, "orden")
        ).stream()
                .peek(p -> {
                    Integer reservado = carritoItemRepository.sumCantidadByProductoId(p.getId());
                    p.setStockReservado(reservado == null ? 0 : reservado);
                })
                .filter(p -> p.getStockDisponible() > 0)
                .collect(Collectors.toList());
    }

    public List<String> obtenerDeportesUnicos() {
        return productoRepository.findAll().stream()
                .map(ArticuloDeportivo::getDeporte)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> obtenerMarcasUnicas() {
        return productoRepository.findAll().stream()
                .map(ArticuloDeportivo::getMarca)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public Map<String, Long> obtenerEstadisticasDeportes() {
        return productoRepository.findAll().stream()
                .filter(p -> p.getStockDisponible() > 0)
                .collect(Collectors.groupingBy(
                    ArticuloDeportivo::getDeporte,
                    Collectors.counting()
                ));
    }

    public int obtenerTotalProductosDisponibles() {
        return (int) productoRepository.findAll().stream()
                .filter(p -> p.getStockDisponible() > 0)
                .count();
    }
}