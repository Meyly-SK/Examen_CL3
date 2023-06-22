package com.examen.service;
import com.examen.models.Producto;

import java.io.InputStream;
import java.util.List;

public interface ProductoService {

	public List<Producto> getAllProducts();
	
	public InputStream getReportProductos() throws Exception;
	
	void saveProducto(Producto producto);
}
