package com.examen.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.examen.models.Producto;
import com.examen.service.ProductoServiceImpl;

import net.sf.jasperreports.engine.JRException;

@Controller
public class ProductController {

	@Autowired
	private ProductoServiceImpl productService;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}	

	@GetMapping("/registro")
	public String registro(@ModelAttribute("producto") Producto producto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
			return "registro";
		} else {
			return "acceso_denegado";
		}
	}

	@PostMapping("/registro")
    public String registrarProducto(@Validated @ModelAttribute("producto") Producto producto, BindingResult bindingResult,RedirectAttributes redirectAttributes) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
        	if (bindingResult.hasErrors()) {
                return "registro";
            }
            
        	productService.saveProducto(producto);
        	redirectAttributes.addAttribute("success", true);
            
            return "redirect:/registro";
        } else {
        	return "acceso-denegado";
        }    	
    }
	@GetMapping(value = "/reporte", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> reporteProductos(HttpServletRequest request,
			HttpServletResponse response) throws  Exception{
		try {
			InputStream report = this.productService.getReportProductos();
			byte[] data = report.readAllBytes();
			report.close();
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_PDF);
			header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"reporte_productos.pdf\"");
			header.setContentLength(data.length);
			return new ResponseEntity<byte[]>(data,header, HttpStatus.CREATED);			
		} catch (IOException e) {
	        throw new RuntimeException("Error leer o cerrar el informe", e);
	    } catch (JRException e) {
	    	e.printStackTrace();
	        throw new RuntimeException("Error creando el informe", e);
	    }
			//Muchos errores wwww
		}
	}

