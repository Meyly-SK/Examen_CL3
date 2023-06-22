package com.examen.models;

import java.util.List;
import java.util.ArrayList;

public class ProductoReport {

	private List<Producto> productoList;
	
	//No le mueva que sin eso no funciona
	//No es seguro del porque , solo dejelo 
	private String seccion;
	
	private String aula;

	/////////////////////
	//Tampoco sus setters y getters
	
	public ProductoReport() {
		super();
		this.productoList = new ArrayList<>();
	}
	
	public List<Producto> getProductoList() {
		return productoList;
	}
	public void setProductoList(List<Producto> productoList) {
		this.productoList = productoList;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public String getAula() {
		return aula;
	}

	public void setAula(String aula) {
		this.aula = aula;
	}
	
	
	
}
