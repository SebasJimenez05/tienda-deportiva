package com.deportes.tiendadeportiva.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deportes.tiendadeportiva.repositories.ProductoRepository;
import com.deportes.tiendadeportiva.repositories.UsuarioRepository;
import com.deportes.tiendadeportiva.repositories.VentaRepository;
import com.deportes.tiendadeportiva.models.ArticuloDeportivo;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Comparator;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class StatsService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private VentaRepository ventaRepository;
    
    public Map<String, Object> getStatsEnTiempoReal() {
        Map<String, Object> stats = new HashMap<>();
        
        // Obtener todos los productos
        List<ArticuloDeportivo> productos = productoRepository.findAll();
        
        // 1. TOTAL DE USUARIOS REGISTRADOS (REAL)
        long totalUsuarios = usuarioRepository.count();
        stats.put("usuariosRegistrados", totalUsuarios);
        
        // 2. VENTAS DE HOY (REALES - números bajos como pediste)
        // Usamos LocalDateTime para coincidir con tu repositorio
        LocalDateTime inicioHoy = LocalDate.now().atStartOfDay();
        LocalDateTime finHoy = LocalDate.now().atTime(23, 59, 59);
        long ventasHoy = ventaRepository.countByFechaVentaBetween(inicioHoy, finHoy);
        stats.put("ventasHoy", ventasHoy);
        
        // 3. PRODUCTO TRENDING (REAL - producto con menos stock disponible)
        ArticuloDeportivo productoTrending = productos.stream()
                .filter(p -> p.getStockDisponible() > 0)
                .min(Comparator.comparing(ArticuloDeportivo::getStockDisponible))
                .orElse(null);
        
        if (productoTrending != null) {
            stats.put("productoTrendingNombre", productoTrending.getNombre());
            stats.put("productoTrendingId", productoTrending.getId());
            stats.put("productoTrendingDeporte", productoTrending.getDeporte());
        } else {
            stats.put("productoTrendingNombre", "Sin productos");
            stats.put("productoTrendingId", 0L);
            stats.put("productoTrendingDeporte", "todos");
        }
        
        // 4. OFERTAS ACTIVAS (REAL - productos con stock bajo < 3)
        long ofertasActivas = productos.stream()
                .filter(p -> p.getStockDisponible() > 0 && p.getStockDisponible() < 3)
                .count();
        stats.put("ofertasActivas", ofertasActivas);
        
        return stats;
    }
}