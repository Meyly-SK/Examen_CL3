package com.examen.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.examen.models.Producto;
import java.util.List;


@Repository
public interface ProductoDao extends CrudRepository<Producto, Long>{

	public  List<Producto> findAll();
	
	Producto save(Producto usuario);
}
