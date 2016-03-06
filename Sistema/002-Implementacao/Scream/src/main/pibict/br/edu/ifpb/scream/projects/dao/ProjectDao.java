package br.edu.ifpb.scream.projects.dao;


import java.util.List;

import javax.persistence.Query;

import br.edu.ifpb.scream.generic.GenericDAO;
import br.edu.ifpb.scream.projects.Product;
import br.edu.ifpb.scream.projects.Project;
import br.ifpb.monteiro.scream.util.jpa.Transactional;

public class ProjectDao extends GenericDAO<Project>{

	private static final long serialVersionUID = 1L;

	public ProjectDao() {
		super(Project.class);
	}

	@Transactional
	@Override
	public void delete(Project entity) {
		if (getEntityManager().getTransaction().isActive()) {

		} else {

			getEntityManager().getTransaction().begin();

		}
//		Query queryAltera = getEntityManager().createQuery("UPDATE table definicao_pronto DROP CONSTRAINT fk_definicao_pronto_projeto_id");
//		queryAltera.executeUpdate();
//
//		Query queryDef = getEntityManager().createNativeQuery("DELETE FROM definicao_pronto WHERE projeto_id = " + entity.getId());
//		queryDef.executeUpdate();
		
		Query queryProjeto = getEntityManager().createNativeQuery("DELETE FROM Project WHERE id = " + entity.getId());
		queryProjeto.executeUpdate();	

	}
	
    public List<Project> findByIdProduct(Product product) {
		
		List<Project> project = query("Select p From Project p Where p.product=?1",product);
		
		return project;
    }

}