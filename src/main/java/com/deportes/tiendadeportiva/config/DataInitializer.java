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

    // productos
    private void inicializarProductos() {
        if (productoRepository.count() == 0) {

            // FÚTBOL
            productoRepository.save(new ArticuloDeportivo(
                "Balón de Fútbol Nike Premier League",
                "Balón oficial tamaño 5, perfecto para partidos profesionales y entrenamientos.",
                new BigDecimal("89900"),
                15,
                "Fútbol",
                "Nike",
                "Equipamiento",
                "/images/balon-futbol.jpg",
                1
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas de Fútbol Adidas Predator",
                "Zapatillas con tecnología de control total, ideal para terrenos firmes.",
                new BigDecimal("249900"),
                8,
                "Fútbol",
                "Adidas",
                "Calzado",
                "/images/zapatillas-futbol.jpg",
                2
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Guantes de Portero Reusch",
                "Guantes profesionales con palmeada de látex alemán, máxima adherencia.",
                new BigDecimal("129900"),
                6,
                "Fútbol",
                "Reusch",
                "Equipamiento",
                "/images/guantes-portero.jpg",
                3
            ));

            // BALONCESTO
            productoRepository.save(new ArticuloDeportivo(
                "Balón de Baloncesto Spalding NBA",
                "Balón oficial de la NBA, cuero sintético, tamaño 7.",
                new BigDecimal("79900"),
                12,
                "Baloncesto",
                "Spalding",
                "Equipamiento",
                "/images/balon-baloncesto.jpg",
                4
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Jordan Jumpman",
                "Zapatillas de baloncesto con amortiguación Air, diseño clásico.",
                new BigDecimal("299900"),
                5,
                "Baloncesto",
                "Jordan",
                "Calzado",
                "/images/zapatillas-baloncesto.jpg",
                5
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Rodilleras Nike Pro",
                "Protección ligera y transpirable para máximo rendimiento.",
                new BigDecimal("45900"),
                10,
                "Baloncesto",
                "Nike",
                "Accesorios",
                "/images/rodilleras-baloncesto.jpg",
                6
            ));

            // CICLISMO
            productoRepository.save(new ArticuloDeportivo(
                "Bicicleta de Ruta Specialized",
                "Cuadro de fibra de carbono, grupo Shimano 105, 11 velocidades.",
                new BigDecimal("1899900"),
                3,
                "Ciclismo",
                "Specialized",
                "Equipamiento",
                "/images/bicicleta-ruta.jpg",
                7
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Casco Giro Montaro MIPS",
                "Casco de montaña con tecnología MIPS, ventilación optimizada.",
                new BigDecimal("159900"),
                7,
                "Ciclismo",
                "Giro",
                "Accesorios",
                "/images/casco-ciclismo.jpg",
                8
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Ciclismo Shimano RC5",
                "Zapatillas de carretera con suela de carbono, cala SPD-SL.",
                new BigDecimal("279900"),
                4,
                "Ciclismo",
                "Shimano",
                "Calzado",
                "/images/zapatillas-ciclismo.jpg",
                9
            ));

            // TENIS
            productoRepository.save(new ArticuloDeportivo(
                "Raqueta Tenis Wilson Pro Staff",
                "Raqueta profesional, marco de grafito, control y potencia equilibrados.",
                new BigDecimal("399900"),
                6,
                "Tenis",
                "Wilson",
                "Equipamiento",
                "/images/raqueta-tenis.jpg",
                10
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Tenis Nike Court Air Zoom",
                "Zapatillas con tecnología Zoom Air, ideal para canchas duras.",
                new BigDecimal("219900"),
                9,
                "Tenis",
                "Nike",
                "Calzado",
                "/images/zapatillas-tenis.jpg",
                11
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Pelotas Tenis Wilson US Open",
                "Pack de 3 pelotas oficiales US Open, durabilidad excepcional.",
                new BigDecimal("29900"),
                20,
                "Tenis",
                "Wilson",
                "Accesorios",
                "/images/pelotas-tenis.jpg",
                12
            ));

            // ROPA DEPORTIVA
            productoRepository.save(new ArticuloDeportivo(
                "Camiseta Entrenamiento Nike Dri-FIT",
                "Tecnología Dri-FIT para máxima transpiración, diseño ergonómico.",
                new BigDecimal("69900"),
                15,
                "Fútbol",
                "Nike",
                "Ropa",
                "/images/camiseta-entrenamiento.jpg",
                13
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Shorts Running Adidas Aeroready",
                "Shorts ligeros con tecnología Aeroready, bolsillo para llaves.",
                new BigDecimal("49900"),
                12,
                "Ciclismo",
                "Adidas",
                "Ropa",
                "/images/shorts-running.jpg",
                14
            ));

            System.out.println("Artículos deportivos creados exitosamente");
        }
    }
}