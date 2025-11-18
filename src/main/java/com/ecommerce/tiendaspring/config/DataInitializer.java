package com.ecommerce.tiendaspring.config;

import com.ecommerce.tiendaspring.models.Producto;
import com.ecommerce.tiendaspring.models.Rol;
import com.ecommerce.tiendaspring.models.Usuario;
import com.ecommerce.tiendaspring.repositories.ProductoRepository;
import com.ecommerce.tiendaspring.repositories.RolRepository;
import com.ecommerce.tiendaspring.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
            
            // Obtener roles desde la base de datos (managed entities)
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
            // MOUSES
            productoRepository.save(new Producto(
                "Mouse Gamer RGB 7200 DPI",
                "Sensor óptico de alta precisión, 7 botones programables y luces RGB dinámicas.",
                new BigDecimal("39990"),
                8,
                "mouses",
                "/images/mouse1.jpg"
            ));

            productoRepository.save(new Producto(
                "Mouse Inalámbrico Silent Click",
                "Diseño ergonómico, conexión 2.4 GHz y clic silencioso para oficina o estudio.",
                new BigDecimal("24990"),
                12,
                "mouses",
                "/images/mouse2.jpg"
            ));

            productoRepository.save(new Producto(
                "Mouse Vertical Ergonómico",
                "Reduce fatiga en la muñeca, apto para uso prolongado y DPI ajustable.",
                new BigDecimal("49990"),
                5,
                "mouses",
                "/images/mouse3.jpg"
            ));

            productoRepository.save(new Producto(
                "Mouse Básico USB Económico",
                "Ideal para tareas básicas, diseño liviano y compatible con cualquier PC.",
                new BigDecimal("15990"),
                20,
                "mouses",
                "/images/mouse4.jpg"
            ));

            // TECLADOS
            productoRepository.save(new Producto(
                "Teclado Mecánico Blue Switch",
                "Interruptores azules táctiles, retroiluminación RGB y construcción metálica.",
                new BigDecimal("119900"),
                6,
                "teclados",
                "/images/teclado1.jpg"
            ));

            productoRepository.save(new Producto(
                "Teclado de Membrana Retroiluminado",
                "Silencioso, luces multicolor fijas y diseño resistente al agua.",
                new BigDecimal("59900"),
                10,
                "teclados",
                "/images/teclado2.jpg"
            ));

            productoRepository.save(new Producto(
                "Teclado Inalámbrico Compacto",
                "Conexión Bluetooth, carga por USB-C y tamaño reducido para mayor movilidad.",
                new BigDecimal("69900"),
                7,
                "teclados",
                "/images/teclado3.jpg"
            ));

            productoRepository.save(new Producto(
                "Teclado Numérico USB",
                "Ideal para portátiles y contabilidad, plug & play.",
                new BigDecimal("29900"),
                15,
                "teclados",
                "/images/teclado4.jpg"
            ));

            // ALFOMBRILLAS
            productoRepository.save(new Producto(
                "Alfombrilla XL Extendida",
                "Ideal para teclado y mouse, superficie suave y base antideslizante.",
                new BigDecimal("24900"),
                14,
                "alfombrillas",
                "/images/alfombrilla1.jpg"
            ));

            productoRepository.save(new Producto(
                "Mousepad RGB de 10 Modos",
                "Iluminación LED alrededor, control táctil y superficie microtexturizada.",
                new BigDecimal("49900"),
                6,
                "alfombrillas",
                "/images/alfombrilla2.jpg"
            ));

            productoRepository.save(new Producto(
                "Alfombrilla Antideslizante Básica",
                "Tamaño estándar, ligera y con bordes reforzados para mayor durabilidad.",
                new BigDecimal("9900"),
                25,
                "alfombrillas",
                "/images/alfombrilla3.jpg"
            ));

            productoRepository.save(new Producto(
                "Mousepad Gamer XXL",
                "Ideal para setups grandes, diseño resistente y superficie waterproof.",
                new BigDecimal("34900"),
                9,
                "alfombrillas",
                "/images/alfombrilla4.jpg"
            ));

            // AUDÍFONOS
            productoRepository.save(new Producto(
                "Audífonos Gamer con Micrófono RGB",
                "Sonido envolvente, micrófono flexible y almohadillas de espuma suave.",
                new BigDecimal("89900"),
                7,
                "audifonos",
                "/images/audifonos1.jpg"
            ));

            productoRepository.save(new Producto(
                "Audífonos Inalámbricos Bluetooth",
                "20 horas de batería, carga USB-C y reducción de ruido pasiva.",
                new BigDecimal("69900"),
                11,
                "audifonos",
                "/images/audifonos2.jpg"
            ));

            productoRepository.save(new Producto(
                "Diadema Económica con Micrófono",
                "Livianos, cómodos y perfectos para clases virtuales y reuniones.",
                new BigDecimal("29900"),
                18,
                "audifonos",
                "/images/audifonos3.jpg"
            ));

            productoRepository.save(new Producto(
                "Headset Profesional para Streaming",
                "Sonido Hi-Fi, micrófono con cancelación activa y diseño premium.",
                new BigDecimal("149900"),
                4,
                "audifonos",
                "/images/audifonos4.jpg"
            ));

            System.out.println("Productos creados exitosamente");
        }
    }
}