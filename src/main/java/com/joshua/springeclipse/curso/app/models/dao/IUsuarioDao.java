package com.joshua.springeclipse.curso.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.joshua.springeclipse.curso.app.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

	
	public Usuario findByUsername(String username);
}
