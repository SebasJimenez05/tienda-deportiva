package com.deportes.tiendadeportiva.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deportes.tiendadeportiva.models.CarritoItem;

@Repository
public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {
    void deleteByCarritoId(Long carritoId);

    @Query("SELECT COALESCE(SUM(ci.cantidad), 0) FROM CarritoItem ci WHERE ci.producto.id = :productoId")
    Integer sumCantidadByProductoId(@Param("productoId") Long productoId);
}