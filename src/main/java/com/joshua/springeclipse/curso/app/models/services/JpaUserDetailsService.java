package com.joshua.springeclipse.curso.app.models.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joshua.springeclipse.curso.app.models.dao.IUsuarioDao;
import com.joshua.springeclipse.curso.app.models.entity.Role;
import com.joshua.springeclipse.curso.app.models.entity.Usuario;


@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
	Usuario usuario = usuarioDao.findByUsername(username);
	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	
		for(Role role : usuario.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(),
				true, true, true, authorities);
	
	}	

	
	
	
}
