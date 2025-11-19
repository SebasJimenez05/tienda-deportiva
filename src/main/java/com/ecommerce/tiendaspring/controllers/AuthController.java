package com.ecommerce.tiendaspring.controllers;

import com.ecommerce.tiendaspring.dto.PasswordChangeDto;
import com.ecommerce.tiendaspring.models.Rol;
import com.ecommerce.tiendaspring.models.Usuario;
import com.ecommerce.tiendaspring.repositories.RolRepository;
import com.ecommerce.tiendaspring.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {

        // Email ya registrado
        if (usuarioService.existeUsuarioPorEmail(usuario.getEmail())) {
            model.addAttribute("error", "El email ya está registrado");
            model.addAttribute("usuario", usuario);
            return "register";
        }

        // Aceptar solo correos Gmail
        if (!usuario.getEmail().toLowerCase().endsWith("@gmail.com")) {
            model.addAttribute("error", "Solo se aceptan cuentas de Gmail (@gmail.com)");
            model.addAttribute("usuario", usuario);
            return "register";
        }

        // Validar campos
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()
                || usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()
                || usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {

            model.addAttribute("error", "Todos los campos son obligatorios");
            model.addAttribute("usuario", usuario);
            return "register";
        }

        try {
            // Codificar Contraseña
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

            // Asignar rol por defecto ROLE_USER
            Rol rolUsuario = rolRepository.findByNombre("ROLE_USER")
                    .orElseGet(() -> rolRepository.save(new Rol("ROLE_USER")));

            usuario.setRoles(Collections.singletonList(rolUsuario));

            usuarioService.guardarUsuario(usuario);

            model.addAttribute("success", "Registro exitoso. Ahora puedes iniciar sesión.");
            return "login";

        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar usuario: " + e.getMessage());
            model.addAttribute("usuario", usuario);
            return "register";
        }
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("passwordChangeDto", new PasswordChangeDto());
        return "change-password";
    }

    @PostMapping("/change-password")
    public String processPasswordChange(@ModelAttribute PasswordChangeDto passwordChangeDto,
                                        Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Validar coincidencia de nuevas contraseñas
        if (!passwordChangeDto.getNewPassword().equals(passwordChangeDto.getConfirmPassword())) {
            model.addAttribute("error", "Las nuevas contraseñas no coinciden");
            return "change-password";
        }

        // Mínimo 8 caracteres
        if (passwordChangeDto.getNewPassword().length() < 8) {
            model.addAttribute("error", "La nueva contraseña debe tener al menos 8 caracteres");
            return "change-password";
        }

        try {
            boolean passwordChanged = usuarioService.changePassword(
                    email,
                    passwordChangeDto.getCurrentPassword(),
                    passwordChangeDto.getNewPassword(),
                    passwordEncoder
            );

            if (passwordChanged) {
                model.addAttribute("success", "Contraseña cambiada exitosamente");
            } else {
                model.addAttribute("error", "La contraseña actual es incorrecta");
            }

        } catch (Exception e) {
            model.addAttribute("error", "Error al cambiar la contraseña: " + e.getMessage());
        }

        return "change-password";
    }
}
