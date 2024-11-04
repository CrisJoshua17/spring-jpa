package com.joshua.springeclipse.curso.app.controllers;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.joshua.springeclipse.curso.app.models.entity.Cliente;
import com.joshua.springeclipse.curso.app.models.services.IClienteService;
import com.joshua.springeclipse.curso.app.models.services.UploadFileServiceImpl;
import com.joshua.springeclipse.curso.app.util.paginator.PageRendaer;
import com.joshua.springeclipse.curso.app.view.xml.ClienteList;

import jakarta.validation.Valid;

@Controller
@SuppressWarnings("null")
public class ClienteController {

	@Autowired
	@Qualifier("clienteServiceJpa")
	private IClienteService clienteService;
	
	@Autowired
	UploadFileServiceImpl uploadFileService;
	
	@Autowired
	private MessageSource messageSource;
	
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping(value="/uploads/{filename:.+}")
	@SuppressWarnings({"CallToPrintStackTrace"})
	public ResponseEntity<Resource> verFoto(@PathVariable String filename){
		Resource recurso =null; 
		
		try {
			 recurso= uploadFileService.load(filename);
			
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ recurso.getFilename() +"\"")
				.body(recurso);
	}
	
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping("/ver/{id}")
	public String ver (@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Cliente cliente = clienteService.findOne(id);
		if(cliente == null) {
			flash.addFlashAttribute("Error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Detalle cliente: "+ cliente.getName());
		return "ver";
	}
	
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping("/listar-rest")
	@ResponseBody
	public ClienteList listarrRest() {
	return  new ClienteList( clienteService.findAll());
	
	}
	
	
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping("/listar")
	public String listar(@RequestParam(name="page", defaultValue = "0") int page, Model model, Locale locale) {
		
		Pageable pageRequest = PageRequest.of(page, 4);
		Page<Cliente>clientes = clienteService.findAll(pageRequest);
		
		PageRendaer<Cliente> pageRender = new PageRendaer<>("/listar", clientes);
		
		model.addAttribute("titulo",messageSource.getMessage("text.cliente.listar.titulo", null,locale));
		model.addAttribute("clientes", clientes);
		model.addAttribute("page",pageRender);
		
		return"listar";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/form")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("botonEnviar", "Crear");	
		model.put("cliente", cliente);
		model.put("titulo", "Formulario Clientes");
		
		return "form";
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/form")
	public String save(@Valid Cliente cliente, BindingResult result, Model model,@RequestParam("file") MultipartFile foto,
			RedirectAttributes flash) {
		
		if(result.hasErrors()) {
			model.addAttribute("botonEnviar", "Crear");
			model.addAttribute("titulo","Formulario de Cliente");
			return "form";
		}
		if(!foto.isEmpty()) {
		// esto es para ruta dentro del archivo Path directorioRecursos = Paths.get("src//main//resources//static/uploads");
		if(cliente.getId() !=null && cliente.getId()>0 && cliente.getFoto()!=null 
				&& cliente.getFoto().length()>0) {
			
			uploadFileService.delete(cliente.getFoto());
		}
		
		
		}
		
		
		String mensajeFlash = (cliente.getId() != null && cliente.getId() > 0) 
                ? "Cliente actualizado exitosamente." 
                : "Cliente creado exitosamente.";
		flash.addFlashAttribute("info", "Se ha subido correctamente "+ foto.getOriginalFilename());
		cliente.setFoto(foto.getOriginalFilename());
		flash.addFlashAttribute("success",mensajeFlash);
		clienteService.save(cliente);
		return "redirect:listar"; 
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/form/{id}")
	public String update(@PathVariable(value="id") Long id,  Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = null;
		if(id>0) {
			cliente= clienteService.findOne(id);
			if(cliente == null) {
				flash.addFlashAttribute("error","El id del cliente no existe en la base de datos.");
				return "redirect:listar";
			}
		}else {
			flash.addFlashAttribute("error","El id del cliente no puede ser cero.");
			return "redirect:/listar";
		}
		
		model.put("botonEnviar", "Editar");
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		
	return "form";
}
	@Secured("ROLE_ADMIN")
	@GetMapping("/eliminar/{id}")
	public String delete(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		if(id>0) {
			Cliente cliente = clienteService.findOne(id);
			clienteService.delete(id);
			
			
				if(uploadFileService.delete(cliente.getFoto())) {
					flash.addAttribute("info","Foto" + cliente.getFoto() + "eliminada con exito");
				}
			}
		
		flash.addFlashAttribute("success","Cliente eliminado exitosamente.");
		return "redirect:listar";
	}
	
	
	private boolean hasRole(String role) {
		
		SecurityContext contex = SecurityContextHolder.getContext();
		if(contex ==null) {
			return false;
		}
		Authentication auth = contex.getAuthentication();
		if(auth==null) {
			return false;
		}
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		for(GrantedAuthority authority: authorities) {
			if(role.equals(authority.getAuthority())) {
				return true;
			}
		}
		return false;
	}
	
}