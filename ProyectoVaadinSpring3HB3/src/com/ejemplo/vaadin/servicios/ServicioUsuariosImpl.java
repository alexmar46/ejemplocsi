package com.ejemplo.vaadin.servicios;

import com.ejemplo.vaadin.dao.DaoUsuario;
import com.ejemplo.vaadin.entidades.Usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

/**
 * 
 * @author
 */
public class ServicioUsuariosImpl implements ServicioUsuarios, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private DaoUsuario daoUsuarios;

	/**
	 * @{inheritDoc
	 */
	@Override
	public List<Usuario> usuarios() {
		List<Usuario> retorno = null;
		retorno = daoUsuarios.getActivos();
		if (retorno == null) {
			retorno = new ArrayList<Usuario>();
		}
		return retorno;
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	public Integer guardarUsuario(Usuario usuario) {
		Integer retorno = 1;
		try {
			daoUsuarios.guardar(usuario);
			retorno = 0;
		} catch (ConstraintViolationException e) {
			retorno = 4;
		} catch (DataIntegrityViolationException e) {
			retorno = 4;
		} catch (Exception e) {
			e.printStackTrace();
			retorno = 3;
		}
		return retorno;

	}

}
