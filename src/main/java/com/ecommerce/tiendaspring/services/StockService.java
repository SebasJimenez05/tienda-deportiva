package com.ecommerce.tiendaspring.services;

import com.ecommerce.tiendaspring.models.Producto;
import com.ecommerce.tiendaspring.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StockService {

    @Autowired
    private ProductoRepository productoRepository;

    // Reservar stock cuando se agrega al carrito
    public synchronized boolean reservarStock(Long productoId, int cantidad) {
        Optional<Producto> productoOpt = productoRepository.findById(productoId);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            int stockDisponible = producto.getStock() - producto.getStockReservado();
            
            if (stockDisponible >= cantidad) {
                producto.setStockReservado(producto.getStockReservado() + cantidad);
                productoRepository.save(producto);
                return true;
            }
        }
        return false;
    }

    // Liberar stock cuando se elimina del carrito
    public synchronized void liberarStock(Long productoId, int cantidad) {
        Optional<Producto> productoOpt = productoRepository.findById(productoId);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            int nuevoStockReservado = producto.getStockReservado() - cantidad;
            producto.setStockReservado(Math.max(0, nuevoStockReservado));
            productoRepository.save(producto);
        }
    }

    // Actualizar reserva cuando se modifica la cantidad en el carrito
    public synchronized boolean actualizarReserva(Long productoId, int cantidadAnterior, int cantidadNueva) {
        Optional<Producto> productoOpt = productoRepository.findById(productoId);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            int stockDisponible = producto.getStock() - producto.getStockReservado();
            int diferencia = cantidadNueva - cantidadAnterior;
            
            if (stockDisponible >= diferencia) {
                producto.setStockReservado(producto.getStockReservado() + diferencia);
                productoRepository.save(producto);
                return true;
            }
        }
        return false;
    }

    // Confirmar venta - convertir stock reservado en vendido
    public synchronized void confirmarVenta(Long productoId, int cantidad) {
        Optional<Producto> productoOpt = productoRepository.findById(productoId);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            producto.setStockReservado(producto.getStockReservado() - cantidad);
            producto.setStock(producto.getStock() - cantidad);
            productoRepository.save(producto);
        }
    }
}