package com.examen.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examen.models.Producto;
import com.examen.models.ProductoReport;
import com.examen.repositories.ProductoDao;

import java.util.*;
import net.sf.jasperreports.engine.JRException;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ProductoServiceImpl implements ProductoService{
	
	@Autowired
	private ProductoDao productRepository;

	@Override
	public List<Producto> getAllProducts() {
		return this.productRepository.findAll();
	}
	 @Override
	    public void saveProducto(Producto producto) {
	        productRepository.save(producto);
	}
	@Override
	public InputStream getReportProductos() throws Exception {
		
		try {
			List<Producto> listaProductos = this.getAllProducts();
			List<ProductoReport> listaData = new ArrayList<ProductoReport>();
			listaData.add(new ProductoReport());
			listaData.get(0).setProductoList(listaProductos);
			JRBeanCollectionDataSource dts = new JRBeanCollectionDataSource(listaData);
			Map<String,Object> parameters = new HashMap<>();
			parameters.put("IMAGE_PATH", "https://www.dafont.com/forum/attach/orig/9/2/928497.png");
			JasperReport jasperReportObj = getJasperReportCompiled();
			JasperPrint jPrint = JasperFillManager.fillReport(jasperReportObj, parameters, dts);
			InputStream result = new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jPrint));
			return result;			
		} catch (JRException ex) {
			throw ex;
		}
	}
	private JasperReport getJasperReportCompiled() {
		try {
			InputStream productoReportStream = getClass().getResourceAsStream("/jasper/producto_report.jrxml");
			JasperReport jasper = JasperCompileManager.compileReport(productoReportStream);
			return jasper;
		} catch (Exception e) {
			return null;
		}
	}
}
