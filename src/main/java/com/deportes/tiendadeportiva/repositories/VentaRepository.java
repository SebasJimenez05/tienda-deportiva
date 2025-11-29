package com.deportes.tiendadeportiva.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deportes.tiendadeportiva.models.Venta;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByUsuarioIdOrderByFechaVentaDesc(Long usuarioId);
    List<Venta> findAllByOrderByFechaVentaDesc();
}