package com.ecommerce.tiendaspring.controllers;

import com.ecommerce.tiendaspring.models.CarritoItem;
import com.ecommerce.tiendaspring.models.Producto;
import com.ecommerce.tiendaspring.services.ProductoService;
import com.ecommerce.tiendaspring.services.StockService;
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

    @Autowired
    private StockService stockService;

    // Inicializar carrito en la sesión
    @ModelAttribute("carrito")
    public List<CarritoItem> inicializarCarrito() {
        return new ArrayList<>();
    }

    @GetMapping("/tienda")
    public String mostrarTienda(@RequestParam(defaultValue = "todos") String categoria, Model model) {
        List<Producto> productos;

        if ("todos".equals(categoria)) {
            productos = productoService.obtenerProductosDisponibles();
        } else {
            productos = productoService.obtenerProductosDisponiblesPorCategoria(categoria);
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

        int stockDisponible = producto.getStockDisponible();
        if (stockDisponible < cantidad) {
            model.addAttribute("error", "No hay suficiente stock disponible. Solo quedan " + stockDisponible + " unidades.");
            return "redirect:/tienda?categoria=" + getCategoriaSeleccionada(model);
        }

        if (!stockService.reservarStock(productoId, cantidad)) {
            model.addAttribute("error", "Error al reservar el stock. Intenta nuevamente.");
            return "redirect:/tienda?categoria=" + getCategoriaSeleccionada(model);
        }

        boolean productoExistente = false;
        for (CarritoItem item : carrito) {
            if (item.getProducto().getId().equals(productoId)) {

                stockService.liberarStock(productoId, item.getCantidad());

                if (!stockService.reservarStock(productoId, item.getCantidad() + cantidad)) {
                    model.addAttribute("error", "Error al actualizar la reserva de stock.");
                    return "redirect:/tienda?categoria=" + getCategoriaSeleccionada(model);
                }

                item.setCantidad(item.getCantidad() + cantidad);
                productoExistente = true;
                break;
            }
        }

        if (!productoExistente) {
            carrito.add(new CarritoItem(producto, cantidad));
        }

        model.addAttribute("success", "Producto agregado al carrito");
        return "redirect:/tienda?categoria=" + getCategoriaSeleccionada(model);
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
                                    @ModelAttribute("carrito") List<CarritoItem> carrito,
                                    Model model) {

        for (CarritoItem item : carrito) {
            if (item.getProducto().getId().equals(productoId)) {
                int cantidadAnterior = item.getCantidad();

                if (cantidad <= 0) {
                    stockService.liberarStock(productoId, cantidadAnterior);
                    carrito.remove(item);
                    model.addAttribute("success", "Producto eliminado del carrito");
                } else {

                    if (!stockService.actualizarReserva(productoId, cantidadAnterior, cantidad)) {
                        model.addAttribute("error", "No hay suficiente stock para actualizar la cantidad");
                        return "redirect:/carrito";
                    }

                    item.setCantidad(cantidad);
                    model.addAttribute("success", "Cantidad actualizada");
                }
                break;
            }
        }

        return "redirect:/carrito";
    }

    @PostMapping("/eliminar-carrito")
    public String eliminarDelCarrito(@RequestParam Long productoId,
                                     @ModelAttribute("carrito") List<CarritoItem> carrito,
                                     Model model) {

        for (CarritoItem item : carrito) {
            if (item.getProducto().getId().equals(productoId)) {
                stockService.liberarStock(productoId, item.getCantidad());
                carrito.remove(item);
                model.addAttribute("success", "Producto eliminado del carrito");
                break;
            }
        }

        return "redirect:/carrito";
    }

    @GetMapping("/vaciar-carrito")
    public String vaciarCarrito(@ModelAttribute("carrito") List<CarritoItem> carrito,
                                Model model) {

        for (CarritoItem item : carrito) {
            stockService.liberarStock(item.getProducto().getId(), item.getCantidad());
        }

        carrito.clear();
        model.addAttribute("success", "Carrito vaciado");
        return "redirect:/carrito";
    }

    private String getCategoriaSeleccionada(Model model) {
        return model.getAttribute("categoriaSeleccionada") != null ?
                model.getAttribute("categoriaSeleccionada").toString() : "todos";
    }
}
