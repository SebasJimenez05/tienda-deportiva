package com.deportes.tiendadeportiva.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.deportes.tiendadeportiva.models.ArticuloDeportivo;
import com.deportes.tiendadeportiva.models.Rol;
import com.deportes.tiendadeportiva.models.Usuario;
import com.deportes.tiendadeportiva.repositories.ProductoRepository;
import com.deportes.tiendadeportiva.repositories.RolRepository;
import com.deportes.tiendadeportiva.repositories.UsuarioRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        inicializarRolesYUsuarios();
        inicializarProductos();
    }

    private void inicializarRolesYUsuarios() {
        // Crear roles si no existen
        if (rolRepository.count() == 0) {
            Rol rolAdmin = new Rol("ROLE_ADMIN");
            Rol rolUser = new Rol("ROLE_USER");
            rolRepository.save(rolAdmin);
            rolRepository.save(rolUser);
            System.out.println("Roles creados: ROLE_ADMIN, ROLE_USER");
        }

        // Crear usuario admin si no existe
        if (usuarioRepository.findByEmail("admin@tienda.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setEmail("admin@tienda.com");
            admin.setPassword(passwordEncoder.encode("admin123"));

            Rol rolAdmin = rolRepository.findByNombre("ROLE_ADMIN").orElseThrow();
            Rol rolUser = rolRepository.findByNombre("ROLE_USER").orElseThrow();

            admin.setRoles(Arrays.asList(rolAdmin, rolUser));
            usuarioRepository.save(admin);
            System.out.println("Usuario admin creado: admin@tienda.com / admin123");
        }

        // Crear usuario normal si no existe
        if (usuarioRepository.findByEmail("user@tienda.com").isEmpty()) {
            Usuario user = new Usuario();
            user.setNombre("Usuario Normal");
            user.setEmail("user@tienda.com");
            user.setPassword(passwordEncoder.encode("user123"));

            Rol rolUser = rolRepository.findByNombre("ROLE_USER").orElseThrow();
            user.setRoles(Collections.singletonList(rolUser));

            usuarioRepository.save(user);
            System.out.println("Usuario normal creado: user@tienda.com / user123");
        }
    }

    private void inicializarProductos() {
    if (productoRepository.count() == 0) {
        
        // FÚTBOL (6 productos)
        productoRepository.save(new ArticuloDeportivo(
            "Balón Adidas Champions League",
            "Balón oficial de la Champions League 2024, diseño aerodinámico para máximo control.",
            new BigDecimal("129900"),
            12,
            "Fútbol",
            "Adidas",
            "Equipamiento",
            "/images/productos/futbol/balon-champions.jpg",
            1
        ));

        productoRepository.save(new ArticuloDeportivo(
            "Botines Nike Mercurial Superfly 9",
            "Botines de velocidad con tecnología Flyknit y placa de carbono.",
            new BigDecimal("329900"),
            6,
            "Fútbol",
            "Nike",
            "Calzado",
            "/images/productos/futbol/botines-mercurial.jpg",
            2
        ));

        productoRepository.save(new ArticuloDeportivo(
            "Guantes Reusch Pure Contact",
            "Guantes de portero con látex alemán premium y máxima adherencia.",
            new BigDecimal("149900"),
            8,
            "Fútbol",
            "Reusch",
            "Equipamiento",
            "/images/productos/futbol/guantes-portero.jpg",
            3
        ));

        productoRepository.save(new ArticuloDeportivo(
            "Camiseta Nike Dri-FIT Academy",
            "Camiseta de entrenamiento con tecnología Dri-FIT para máxima transpiración.",
            new BigDecimal("79900"),
            15,
            "Fútbol",
            "Nike",
            "Ropa",
            "/images/productos/futbol/camiseta-entrenamiento.jpg",
            4
        ));

        productoRepository.save(new ArticuloDeportivo(
            "Espinilleras Adidas Predator",
            "Protección ligera con ajuste seguro y diseño ergonómico.",
            new BigDecimal("54900"),
            20,
            "Fútbol",
            "Adidas",
            "Accesorios",
            "/images/productos/futbol/espinilleras.jpg",
            5
        ));

        productoRepository.save(new ArticuloDeportivo(
            "Balón Wilson NFL Official",
            "Balón oficial de fútbol, cuero sintético de alta calidad.",
            new BigDecimal("89900"),
            10,
            "Fútbol",
            "Wilson",
            "Equipamiento",
            "/images/productos/futbol/balon-wilson.jpg",
            6
        ));

        // BALONCESTO (5 productos)
        productoRepository.save(new ArticuloDeportivo(
            "Balón Spalding NBA Official",
            "Balón oficial de la NBA, cuero sintético premium tamaño 7.",
            new BigDecimal("89900"),
            14,
            "Baloncesto",
            "Spalding",
            "Equipamiento",
            "/images/productos/baloncesto/balon-nba.jpg",
            7
        ));

        productoRepository.save(new ArticuloDeportivo(
            "Zapatillas Jordan XXXVII",
            "Zapatillas de baloncesto con amortiguación Zoom Air y unidad de aire.",
            new BigDecimal("349900"),
            4,
            "Baloncesto",
            "Jordan",
            "Calzado",
            "/images/productos/baloncesto/zapatillas-jordan.jpg",
            8
        ));

        productoRepository.save(new ArticuloDeportivo(
            "Jersey Nike NBA Swingman",
            "Jersey oficial edición Swingman de los Golden State Warriors.",
            new BigDecimal("139900"),
            8,
            "Baloncesto",
            "Nike",
            "Ropa",
            "/images/productos/baloncesto/jersey-warriors.jpg",
            9
        ));

        productoRepository.save(new ArticuloDeportivo(
            "Balón Wilson Evolution",
            "Balón de competición con grip superior para máximo control.",
            new BigDecimal("79900"),
            12,
            "Baloncesto",
            "Wilson",
            "Equipamiento",
            "/images/productos/baloncesto/balon-evolution.jpg",
            10
        ));

        productoRepository.save(new ArticuloDeportivo(
            "Shorts Nike Dri-FIT NBA",
            "Shorts oficiales de la NBA con tecnología de secado rápido.",
            new BigDecimal("69900"),
            18,
            "Baloncesto",
            "Nike",
            "Ropa",
            "/images/productos/baloncesto/shorts-nba.jpg",
            11
        ));

        // CICLISMO (5 productos)
        productoRepository.save(new ArticuloDeportivo(
            "Bicicleta Specialized Tarmac SL7",
            "Bicicleta de ruta con cuadro de fibra de carbono FACT y geometría de carrera.",
            new BigDecimal("2199900"),
            2,
            "Ciclismo",
            "Specialized",
            "Equipamiento",
            "/images/productos/ciclismo/bicicleta-tarmac.jpg",
            12
        ));

        productoRepository.save(new ArticuloDeportivo(
            "Casco Giro Aether MIPS",
            "Casco de carretera con tecnología Sphere para ajuste perfecto.",
            new BigDecimal("189900"),
            7,
            "Ciclismo",
            "Giro",
            "Accesorios",
            "/images/productos/ciclismo/casco-giro.jpg",
            13
        ));

        productoRepository.save(new ArticuloDeportivo(
            "Zapatillas Shimano RC9",
            "Zapatillas de competición con suela de carbono y ajuste Dynalast.",
            new BigDecimal("389900"),
            3,
            "Ciclismo",
            "Shimano",
            "Calzado",
            "/images/productos/ciclismo/zapatillas-shimano.jpg",
            14
        ));

        productoRepository.save(new ArticuloDeportivo(
            "Gafas Giro Vanquish MIPS",
            "Gafas deportivas con lentes intercambiables y ventilación mejorada.",
            new BigDecimal("99900"),
            10,
            "Ciclismo",
            "Giro",
            "Accesorios",
            "/images/productos/ciclismo/gafas-giro.jpg",
            15
        ));

        productoRepository.save(new ArticuloDeportivo(
            "Maillot Specialized RBX Comp",
            "Maillot de ciclismo con tecnología Body Geometry para máximo confort.",
            new BigDecimal("89900"),
            12,
            "Ciclismo",
            "Specialized",
            "Ropa",
            "/images/productos/ciclismo/maillot-specialized.jpg",
            16
        ));

        // TENIS (4 productos)
        productoRepository.save(new ArticuloDeportivo(
            "Raqueta Wilson Pro Staff RF97",
            "Raqueta signature de Roger Federer, potencia y control equilibrados.",
            new BigDecimal("459900"),
            4,
            "Tenis",
            "Wilson",
            "Equipamiento",
            "/images/productos/tenis/raqueta-wilson.jpg",
            17
        ));

        productoRepository.save(new ArticuloDeportivo(
            "Zapatillas Nike Court Zoom Vapor Pro 2",
            "Zapatillas de tenis con tecnología Zoom Air para canchas duras.",
            new BigDecimal("239900"),
            8,
            "Tenis",
            "Nike",
            "Calzado",
            "/images/productos/tenis/zapatillas-tenis.jpg",
            18
        ));

        productoRepository.save(new ArticuloDeportivo(
            "Pelotas Wilson US Open Extra Duty",
            "Pack de 4 pelotas oficiales US Open para canchas duras.",
            new BigDecimal("34900"),
            30,
            "Tenis",
            "Wilson",
            "Accesorios",
            "/images/productos/tenis/pelotas-wilson.jpg",
            19
        ));

        productoRepository.save(new ArticuloDeportivo(
            "Mochila Wilson Team Tour",
            "Mochila especializada con compartimento para raquetas y equipo.",
            new BigDecimal("99900"),
            8,
            "Tenis",
            "Wilson",
            "Accesorios",
            "/images/productos/tenis/mochila-wilson.jpg",
            20
        ));

        System.out.println("20 artículos deportivos creados exitosamente");
    }
}
}