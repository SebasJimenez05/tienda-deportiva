package com.ecommerce.tiendaspring.repositories;

import com.ecommerce.tiendaspring.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria(String categoria);
    List<Producto> findByStockGreaterThan(Integer stock);
}