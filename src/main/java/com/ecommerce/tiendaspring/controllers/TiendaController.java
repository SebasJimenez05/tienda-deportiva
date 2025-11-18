package com.ecommerce.tiendaspring.controllers;

import com.ecommerce.tiendaspring.models.CarritoItem;
import com.ecommerce.tiendaspring.models.Producto;
import com.ecommerce.tiendaspring.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("carrito")
public class TiendaController {

    @Autowired
    private ProductoService productoService;

    // Inicializar carrito en la sesión
    @ModelAttribute("carrito")
    public List<CarritoItem> inicializarCarrito() {
        return new ArrayList<>();
    }

    @GetMapping("/tienda")
    public String mostrarTienda(@RequestParam(defaultValue = "todos") String categoria, Model model) {
        List<Producto> productos;
        
        if ("todos".equals(categoria)) {
            productos = productoService.obtenerProductosEnStock();
        } else {
            productos = productoService.obtenerProductosPorCategoria(categoria);
        }
        
        model.addAttribute("productos", productos);
        model.addAttribute("categoriaSeleccionada", categoria);
        return "tienda";
    }

    @PostMapping("/agregar-carrito")
    public String agregarAlCarrito(@RequestParam Long productoId, 
                                  @RequestParam(defaultValue = "1") int cantidad,
                                  @ModelAttribute("carrito") List<CarritoItem> carrito,
                                  Model model) {
        
        Producto producto = productoService.obtenerProductoPorId(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Verificar stock
        if (producto.getStock() < cantidad) {
            model.addAttribute("error", "No hay suficiente stock disponible");
            return "redirect:/tienda?categoria=" + model.getAttribute("categoriaSeleccionada");
        }

        // Verificar si el producto ya está en el carrito
        boolean productoExistente = false;
        for (CarritoItem item : carrito) {
            if (item.getProducto().getId().equals(productoId)) {
                item.setCantidad(item.getCantidad() + cantidad);
                productoExistente = true;
                break;
            }
        }

        // Si no existe, agregarlo al carrito
        if (!productoExistente) {
            carrito.add(new CarritoItem(producto, cantidad));
        }

        model.addAttribute("success", "Producto agregado al carrito");
        return "redirect:/tienda?categoria=" + model.getAttribute("categoriaSeleccionada");
    }

    @GetMapping("/carrito")
    public String verCarrito(@ModelAttribute("carrito") List<CarritoItem> carrito, Model model) {
        BigDecimal total = carrito.stream()
                .map(CarritoItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        model.addAttribute("total", total);
        return "carrito";
    }

    @PostMapping("/actualizar-carrito")
    public String actualizarCarrito(@RequestParam Long productoId,
                                   @RequestParam int cantidad,
                                   @ModelAttribute("carrito") List<CarritoItem> carrito) {
        
        // Buscar el item y actualizar cantidad
        for (CarritoItem item : carrito) {
            if (item.getProducto().getId().equals(productoId)) {
                if (cantidad <= 0) {
                    carrito.remove(item);
                } else {
                    item.setCantidad(cantidad);
                }
                break;
            }
        }
        
        return "redirect:/carrito";
    }

    @PostMapping("/eliminar-carrito")
    public String eliminarDelCarrito(@RequestParam Long productoId,
                                    @ModelAttribute("carrito") List<CarritoItem> carrito) {
        
        carrito.removeIf(item -> item.getProducto().getId().equals(productoId));
        return "redirect:/carrito";
    }

    @GetMapping("/vaciar-carrito")
    public String vaciarCarrito(@ModelAttribute("carrito") List<CarritoItem> carrito) {
        carrito.clear();
        return "redirect:/carrito";
    }
}