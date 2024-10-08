package com.joshua.springeclipse.curso.app.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.joshua.springeclipse.curso.app.models.entity.Cliente;
import com.joshua.springeclipse.curso.app.models.entity.Factura;
import com.joshua.springeclipse.curso.app.models.entity.Producto;

public interface IClienteService {

	
	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);
	
	public void save(Cliente cliente);
	
	public Cliente findOne(Long id);
	
	public void delete(Long id);
	
	public List<Producto> findByNombre(String term);
	
	public void saveFactura(Factura factutra);
	
	public Producto findProductoById(Long id);
	
	public Factura findFacturaById(Long id);
	
	public void deleteFactura(Long id);
	
	public Factura fetchFacturaByIddWithClienteWithItemFacturaWithProducto(Long id);
	
	public Cliente fetchByIdWithFactura(Long id);
}
