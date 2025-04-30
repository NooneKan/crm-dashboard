package com.noonekan.crm.service.ejb;

import com.noonekan.crm.dto.UsuarioDTO;
import com.noonekan.crm.entity.Empresa;
import com.noonekan.crm.entity.Usuario;
import com.noonekan.crm.service.UsuarioService;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UsuarioServiceEJB implements UsuarioService {

	@PersistenceContext(unitName = "crmPU")
	private EntityManager em;

	public void cadastrarUsuario(UsuarioDTO dto) {
		Usuario usuario = new Usuario();
		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setSenha(dto.getSenha());

		if (dto.getEmpresaId() != null) {
			Empresa empresa = em.find(Empresa.class, dto.getEmpresaId());
			usuario.setEmpresa(empresa);
		}

		em.persist(usuario);
		em.flush();

	}

	public UsuarioDTO obterUsuario(Long id) throws Exception {
		Usuario usuario = em.find(Usuario.class, id);
		if (usuario != null) {
			UsuarioDTO dto = new UsuarioDTO();
			dto.setId(usuario.getId());
			dto.setEmail(usuario.getEmail());
			dto.setEmpresaId(usuario.getEmpresa().getId());
			dto.setNome(usuario.getNome());
			dto.setSenha(usuario.getSenha());
			return dto;
		}

		return null;
	}

	public void removerUsuario(Long id) throws Exception {
		
		Usuario usuario = em.find(Usuario.class, id);
		if (usuario != null) {
			em.remove(usuario);
		}

	}

	public void atualizarUsuario(UsuarioDTO dto) throws Exception {
		Usuario usuario = em.find(Usuario.class, dto.getId());

		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setSenha(dto.getSenha());

		if (dto.getEmpresaId() != null) {
			Empresa empresa = em.find(Empresa.class, dto.getEmpresaId());
			usuario.setEmpresa(empresa);
		}

		em.merge(usuario);
	}

	
	public boolean possuiUsuario(Long id) {
	    Usuario usuario = em.find(Usuario.class, id);
	    return usuario != null;
	}

	


}