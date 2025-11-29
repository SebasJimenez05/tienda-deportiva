package com.deportes.tiendadeportiva.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deportes.tiendadeportiva.models.ArticuloDeportivo;

import org.springframework.data.domain.Sort;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<ArticuloDeportivo, Long> {
    List<ArticuloDeportivo> findByDeporte(String deporte);
    List<ArticuloDeportivo> findByStockGreaterThan(Integer stock);
    
    List<ArticuloDeportivo> findByMarca(String marca);
    List<ArticuloDeportivo> findByDeporteAndMarca(String deporte, String marca);

    List<ArticuloDeportivo> findByDeporte(String deporte, Sort sort);
    List<ArticuloDeportivo> findByStockGreaterThan(Integer stock, Sort sort);
    List<ArticuloDeportivo> findByMarca(String marca, Sort sort);
    List<ArticuloDeportivo> findByDeporteAndMarca(String deporte, String marca, Sort sort);
    List<ArticuloDeportivo> findAll(Sort sort);
}