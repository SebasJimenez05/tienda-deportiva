package com.deportes.tiendadeportiva.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.deportes.tiendadeportiva.models.ArticuloDeportivo;

import java.util.HashMap;
import java.util.Map;

@Service
public class StockNotifier {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void notificarCambioStock(ArticuloDeportivo producto) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("productoId", producto.getId());
        payload.put("stock", producto.getStock());
        payload.put("stockReservado", producto.getStockReservado());

        messagingTemplate.convertAndSend("/topic/stock", payload);
    }
}