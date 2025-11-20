package com.ecommerce.tiendaspring.repositories;

import com.ecommerce.tiendaspring.models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByUsuarioIdOrderByFechaVentaDesc(Long usuarioId);
    List<Venta> findAllByOrderByFechaVentaDesc();
}