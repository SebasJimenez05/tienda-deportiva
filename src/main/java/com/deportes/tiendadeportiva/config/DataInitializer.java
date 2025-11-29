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
            
            // FÚTBOL (15 productos)
            productoRepository.save(new ArticuloDeportivo(
                "Balón Nike Premier League",
                "Balón oficial tamaño 5 para partidos profesionales.",
                new BigDecimal("89900"),
                15,
                "Fútbol",
                "Nike",
                "Equipamiento",
                "https://images.unsplash.com/photo-1614632103286-949744ea2db6?w=500",
                1
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Adidas Predator",
                "Zapatillas con tecnología de control total para terrenos firmes.",
                new BigDecimal("249900"),
                8,
                "Fútbol",
                "Adidas",
                "Calzado",
                "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=500",
                2
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Guantes Portero Reusch Attrakt",
                "Guantes profesionales con palmeada de látex alemán.",
                new BigDecimal("129900"),
                6,
                "Fútbol",
                "Reusch",
                "Equipamiento",
                "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=500",
                3
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Balón Adidas Champions League",
                "Balón oficial de la UEFA Champions League 2024.",
                new BigDecimal("129900"),
                10,
                "Fútbol",
                "Adidas",
                "Equipamiento",
                "https://images.unsplash.com/photo-1614632103286-949744ea2db6?w=500",
                4
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Botines Nike Mercurial Vapor",
                "Botines ligeros para máxima velocidad en el campo.",
                new BigDecimal("279900"),
                7,
                "Fútbol",
                "Nike",
                "Calzado",
                "https://images.unsplash.com/photo-1549298916-b41d501d3772?w=500",
                5
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Espinilleras Nike Mercurial",
                "Protección ligera y cómoda para jugadores de fútbol.",
                new BigDecimal("45900"),
                20,
                "Fútbol",
                "Nike",
                "Accesorios",
                "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=500",
                6
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Guantes Reusch Deluxe G3",
                "Guantes de portero premium con máxima adherencia.",
                new BigDecimal("159900"),
                5,
                "Fútbol",
                "Reusch",
                "Equipamiento",
                "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=500",
                7
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Balón Nike Academy",
                "Balón de entrenamiento duradero para uso diario.",
                new BigDecimal("59900"),
                25,
                "Fútbol",
                "Nike",
                "Equipamiento",
                "https://images.unsplash.com/photo-1614632103286-949744ea2db6?w=500",
                8
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Botines Adidas Copa Sense",
                "Botines de cuero para máximo control y confort.",
                new BigDecimal("229900"),
                6,
                "Fútbol",
                "Adidas",
                "Calzado",
                "https://images.unsplash.com/photo-1549298916-b41d501d3772?w=500",
                9
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Uniforme Nike Strike",
                "Uniforme completo para equipo de fútbol.",
                new BigDecimal("89900"),
                12,
                "Fútbol",
                "Nike",
                "Ropa",
                "https://images.unsplash.com/photo-1529900748604-07564a03e7a6?w=500",
                10
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Balón Wilson NFL Official",
                "Balón oficial de fútbol americano Wilson.",
                new BigDecimal("79900"),
                8,
                "Fútbol",
                "Wilson",
                "Equipamiento",
                "https://images.unsplash.com/photo-1614632103286-949744ea2db6?w=500",
                11
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Adidas X Speedportal",
                "Zapatillas para terreno firme con tecnología Speedplate.",
                new BigDecimal("269900"),
                4,
                "Fútbol",
                "Adidas",
                "Calzado",
                "https://images.unsplash.com/photo-1549298916-b41d501d3772?w=500",
                12
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Guantes Reusch Pure Contact",
                "Guantes con tecnología de contacto puro para porteros.",
                new BigDecimal("139900"),
                9,
                "Fútbol",
                "Reusch",
                "Equipamiento",
                "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=500",
                13
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Balón Adidas Training Pro",
                "Balón profesional para entrenamientos intensivos.",
                new BigDecimal("69900"),
                18,
                "Fútbol",
                "Adidas",
                "Equipamiento",
                "https://images.unsplash.com/photo-1614632103286-949744ea2db6?w=500",
                14
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Botines Nike Phantom GT2",
                "Botines con tejido Flyknit para precisión y control.",
                new BigDecimal("259900"),
                5,
                "Fútbol",
                "Nike",
                "Calzado",
                "https://images.unsplash.com/photo-1549298916-b41d501d3772?w=500",
                15
            ));

            // BALONCESTO (15 productos)
            productoRepository.save(new ArticuloDeportivo(
                "Balón Spalding NBA Official",
                "Balón oficial de la NBA, cuero sintético tamaño 7.",
                new BigDecimal("79900"),
                12,
                "Baloncesto",
                "Spalding",
                "Equipamiento",
                "https://images.unsplash.com/photo-1546519638-68e109498ffc?w=500",
                16
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Jordan Jumpman Pro",
                "Zapatillas con amortiguación Air, diseño clásico.",
                new BigDecimal("299900"),
                5,
                "Baloncesto",
                "Jordan",
                "Calzado",
                "https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=500",
                17
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Rodilleras Nike Pro Elite",
                "Protección ligera y transpirable para baloncesto.",
                new BigDecimal("45900"),
                10,
                "Baloncesto",
                "Nike",
                "Accesorios",
                "https://images.unsplash.com/photo-1599058917765-660d3e6fe0d2?w=500",
                18
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Balón Wilson Evolution",
                "Balón de baloncesto premium para competición.",
                new BigDecimal("89900"),
                8,
                "Baloncesto",
                "Wilson",
                "Equipamiento",
                "https://images.unsplash.com/photo-1546519638-68e109498ffc?w=500",
                19
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Nike LeBron 19",
                "Zapatillas de edición limitada LeBron James.",
                new BigDecimal("349900"),
                3,
                "Baloncesto",
                "Nike",
                "Calzado",
                "https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=500",
                20
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Balón Spalding Street",
                "Balón duradero para baloncesto callejero.",
                new BigDecimal("49900"),
                15,
                "Baloncesto",
                "Spalding",
                "Equipamiento",
                "https://images.unsplash.com/photo-1546519638-68e109498ffc?w=500",
                21
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Jordan Why Not?",
                "Zapatillas signature de Russell Westbrook.",
                new BigDecimal("279900"),
                6,
                "Baloncesto",
                "Jordan",
                "Calzado",
                "https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=500",
                22
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Uniforme Nike NBA Swingman",
                "Jersey oficial de NBA edición Swingman.",
                new BigDecimal("129900"),
                9,
                "Baloncesto",
                "Nike",
                "Ropa",
                "https://images.unsplash.com/photo-1612872087720-bb876e2e67d1?w=500",
                23
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Balón Wilson NCAA",
                "Balón oficial de la NCAA para competición universitaria.",
                new BigDecimal("75900"),
                7,
                "Baloncesto",
                "Wilson",
                "Equipamiento",
                "https://images.unsplash.com/photo-1546519638-68e109498ffc?w=500",
                24
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Adidas Dame 8",
                "Zapatillas signature de Damian Lillard.",
                new BigDecimal("239900"),
                8,
                "Baloncesto",
                "Adidas",
                "Calzado",
                "https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=500",
                25
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Mangas Compresión Nike Pro",
                "Mangas de compresión para mejorar el rendimiento.",
                new BigDecimal("29900"),
                20,
                "Baloncesto",
                "Nike",
                "Accesorios",
                "https://images.unsplash.com/photo-1599058917765-660d3e6fe0d2?w=500",
                26
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Balón Spalding TF-1000",
                "Balón profesional con excelente grip y durabilidad.",
                new BigDecimal("99900"),
                6,
                "Baloncesto",
                "Spalding",
                "Equipamiento",
                "https://images.unsplash.com/photo-1546519638-68e109498ffc?w=500",
                27
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Jordan Retro 1",
                "Edición retro de las clásicas Air Jordan 1.",
                new BigDecimal("319900"),
                4,
                "Baloncesto",
                "Jordan",
                "Calzado",
                "https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=500",
                28
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Shorts Baloncesto Nike Dri-FIT",
                "Shorts con tecnología Dri-FIT para máximo confort.",
                new BigDecimal("49900"),
                15,
                "Baloncesto",
                "Nike",
                "Ropa",
                "https://images.unsplash.com/photo-1612872087720-bb876e2e67d1?w=500",
                29
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Balón Wilson Jet",
                "Balón de baloncesto para entrenamiento y recreación.",
                new BigDecimal("45900"),
                22,
                "Baloncesto",
                "Wilson",
                "Equipamiento",
                "https://images.unsplash.com/photo-1546519638-68e109498ffc?w=500",
                30
            ));

            // CICLISMO (15 productos)
            productoRepository.save(new ArticuloDeportivo(
                "Bicicleta Specialized Tarmac",
                "Bicicleta de ruta con cuadro de fibra de carbono.",
                new BigDecimal("1899900"),
                3,
                "Ciclismo",
                "Specialized",
                "Equipamiento",
                "https://images.unsplash.com/photo-1485965120184-e220f721d03e?w=500",
                31
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Casco Giro Montaro MIPS",
                "Casco de montaña con tecnología MIPS.",
                new BigDecimal("159900"),
                7,
                "Ciclismo",
                "Giro",
                "Accesorios",
                "https://images.unsplash.com/photo-1558618047-3c8c76ca7d13?w=500",
                32
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Shimano RC5",
                "Zapatillas de carretera con suela de carbono.",
                new BigDecimal("279900"),
                4,
                "Ciclismo",
                "Shimano",
                "Calzado",
                "https://images.unsplash.com/photo-1535051239488-5a659d61fe1f?w=500",
                33
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Bicicleta Specialized Rockhopper",
                "Bicicleta de montaña para trail y cross country.",
                new BigDecimal("1299900"),
                5,
                "Ciclismo",
                "Specialized",
                "Equipamiento",
                "https://images.unsplash.com/photo-1485965120184-e220f721d03e?w=500",
                34
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Casco Giro Syntax MIPS",
                "Casco de carretera con ventilación optimizada.",
                new BigDecimal("139900"),
                8,
                "Ciclismo",
                "Giro",
                "Accesorios",
                "https://images.unsplash.com/photo-1558618047-3c8c76ca7d13?w=500",
                35
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Shimano XC5",
                "Zapatillas de montaña con cala SPD.",
                new BigDecimal("229900"),
                6,
                "Ciclismo",
                "Shimano",
                "Calzado",
                "https://images.unsplash.com/photo-1535051239488-5a659d61fe1f?w=500",
                36
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Bicicleta Specialized Allez",
                "Bicicleta de ruta con cuadro de aluminio.",
                new BigDecimal("899900"),
                7,
                "Ciclismo",
                "Specialized",
                "Equipamiento",
                "https://images.unsplash.com/photo-1485965120184-e220f721d03e?w=500",
                37
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Gafas Ciclismo Giro Vanquish",
                "Gafas deportivas con lentes intercambiables.",
                new BigDecimal("89900"),
                12,
                "Ciclismo",
                "Giro",
                "Accesorios",
                "https://images.unsplash.com/photo-1571068316344-75bc76f77890?w=500",
                38
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Shimano RC7",
                "Zapatillas de carretera de alta gama.",
                new BigDecimal("349900"),
                3,
                "Ciclismo",
                "Shimano",
                "Calzado",
                "https://images.unsplash.com/photo-1535051239488-5a659d61fe1f?w=500",
                39
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Bicicleta Specialized Stumpjumper",
                "Bicicleta de montaña full suspension.",
                new BigDecimal("2599900"),
                2,
                "Ciclismo",
                "Specialized",
                "Equipamiento",
                "https://images.unsplash.com/photo-1485965120184-e220f721d03e?w=500",
                40
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Casco Giro Aether MIPS",
                "Casco de carretera con tecnología Sphere.",
                new BigDecimal("199900"),
                4,
                "Ciclismo",
                "Giro",
                "Accesorios",
                "https://images.unsplash.com/photo-1558618047-3c8c76ca7d13?w=500",
                41
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Shimano S-Phyre",
                "Zapatillas de competición de máxima gama.",
                new BigDecimal("419900"),
                2,
                "Ciclismo",
                "Shimano",
                "Calzado",
                "https://images.unsplash.com/photo-1535051239488-5a659d61fe1f?w=500",
                42
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Maillot Ciclismo Specialized",
                "Maillot de ciclismo con tecnología Body Geometry.",
                new BigDecimal("79900"),
                10,
                "Ciclismo",
                "Specialized",
                "Ropa",
                "https://images.unsplash.com/photo-1558618047-3c8c76ca7d13?w=500",
                43
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Guantes Ciclismo Giro Monaco",
                "Guantes de ciclismo con gel antividración.",
                new BigDecimal("49900"),
                15,
                "Ciclismo",
                "Giro",
                "Accesorios",
                "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=500",
                44
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Shimano SH-RC300",
                "Zapatillas de carretera para entrenamiento.",
                new BigDecimal("189900"),
                8,
                "Ciclismo",
                "Shimano",
                "Calzado",
                "https://images.unsplash.com/photo-1535051239488-5a659d61fe1f?w=500",
                45
            ));

            // TENIS (15 productos)
            productoRepository.save(new ArticuloDeportivo(
                "Raqueta Wilson Pro Staff v14",
                "Raqueta profesional con marco de grafito.",
                new BigDecimal("399900"),
                6,
                "Tenis",
                "Wilson",
                "Equipamiento",
                "https://images.unsplash.com/photo-1587280501635-68ba0a61d46f?w=500",
                46
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Nike Court Air Zoom Vapor",
                "Zapatillas con tecnología Zoom Air para canchas duras.",
                new BigDecimal("219900"),
                9,
                "Tenis",
                "Nike",
                "Calzado",
                "https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=500",
                47
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Pelotas Wilson US Open",
                "Pack de 3 pelotas oficiales US Open.",
                new BigDecimal("29900"),
                20,
                "Tenis",
                "Wilson",
                "Accesorios",
                "https://images.unsplash.com/photo-1622279457486-62bcc4bb10b8?w=500",
                48
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Raqueta Wilson Blade 98",
                "Raqueta con control preciso y potencia equilibrada.",
                new BigDecimal("329900"),
                8,
                "Tenis",
                "Wilson",
                "Equipamiento",
                "https://images.unsplash.com/photo-1587280501635-68ba0a61d46f?w=500",
                49
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Adidas Barricade",
                "Zapatillas de tenis con máxima estabilidad.",
                new BigDecimal("239900"),
                7,
                "Tenis",
                "Adidas",
                "Calzado",
                "https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=500",
                50
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Pelotas Wilson Championship",
                "Pelotas de tenis para competición.",
                new BigDecimal("24900"),
                25,
                "Tenis",
                "Wilson",
                "Accesorios",
                "https://images.unsplash.com/photo-1622279457486-62bcc4bb10b8?w=500",
                51
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Raqueta Wilson Clash 100",
                "Raqueta con tecnología FreeFlex para mayor comodidad.",
                new BigDecimal("359900"),
                5,
                "Tenis",
                "Wilson",
                "Equipamiento",
                "https://images.unsplash.com/photo-1587280501635-68ba0a61d46f?w=500",
                52
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas NikeCourt Air Zoom GP",
                "Zapatillas inspiradas en Naomi Osaka.",
                new BigDecimal("269900"),
                4,
                "Tenis",
                "Nike",
                "Calzado",
                "https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=500",
                53
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Overgrips Wilson Pro Comfort",
                "Overgrips absorbentes para mejor grip.",
                new BigDecimal("14900"),
                30,
                "Tenis",
                "Wilson",
                "Accesorios",
                "https://images.unsplash.com/photo-1599058917765-660d3e6fe0d2?w=500",
                54
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Raqueta Wilson Ultra 100",
                "Raqueta para jugadores que buscan potencia.",
                new BigDecimal("319900"),
                6,
                "Tenis",
                "Wilson",
                "Equipamiento",
                "https://images.unsplash.com/photo-1587280501635-68ba0a61d46f?w=500",
                55
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Adidas Adizero Ubersonic",
                "Zapatillas ligeras para jugadores rápidos.",
                new BigDecimal("229900"),
                8,
                "Tenis",
                "Adidas",
                "Calzado",
                "https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=500",
                56
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Pelotas Wilson Triniti",
                "Pelotas con mayor durabilidad y consistencia.",
                new BigDecimal("34900"),
                18,
                "Tenis",
                "Wilson",
                "Accesorios",
                "https://images.unsplash.com/photo-1622279457486-62bcc4bb10b8?w=500",
                57
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Raqueta Wilson Burn 100",
                "Raqueta diseñada para generar máximo spin.",
                new BigDecimal("299900"),
                7,
                "Tenis",
                "Wilson",
                "Equipamiento",
                "https://images.unsplash.com/photo-1587280501635-68ba0a61d46f?w=500",
                58
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Zapatillas Nike Court Lite 2",
                "Zapatillas versátiles para tenis y entrenamiento.",
                new BigDecimal("159900"),
                12,
                "Tenis",
                "Nike",
                "Calzado",
                "https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=500",
                59
            ));

            productoRepository.save(new ArticuloDeportivo(
                "Mochila Tenis Wilson Tour",
                "Mochila especializada para equipo de tenis.",
                new BigDecimal("89900"),
                10,
                "Tenis",
                "Wilson",
                "Accesorios",
                "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=500",
                60
            ));

            System.out.println("60 artículos deportivos creados exitosamente");
        }
    }
}