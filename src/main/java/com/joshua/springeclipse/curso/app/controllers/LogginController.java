package com.joshua.springeclipse.curso.app.controllers;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller	
public class LogginController {

  @GetMapping("/loggin")
  public String loggin(@RequestParam(value="error", required = false) String error,
      @RequestParam(value="logout", required = false) String logout, Model model,
      Principal principal, RedirectAttributes flash) {
    
    if (principal != null) {
      flash.addFlashAttribute("info", "Ya ha iniciado sesion anteriormente");
     
      return "redirect:/listar";
    }

    if (error != null) {
      model.addAttribute("error", "Error en el loggin: Nombre de usuario o contraseña Incorrecta");
    }

    if (logout != null) {
      model.addAttribute("success", "Ha cerrado sesion con exito");
      return "loggin";
      
    }
    
    return "loggin";
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes flash) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    flash.addFlashAttribute("info", "Ha cerrado sesión con éxito");
    return "redirect:/loggin";
  }
}
