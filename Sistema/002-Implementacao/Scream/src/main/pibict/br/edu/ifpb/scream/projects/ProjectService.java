package br.edu.ifpb.scream.projects;

import static br.edu.ifpb.scream.generic.GenericDAO.getLogger;
import java.util.List;
import java.util.logging.Level;

import javax.inject.Inject;

import br.edu.ifpb.scream.projects.dao.ProjectDao;
import br.ifpb.monteiro.scream.util.jpa.Transactional;

/**
 * @author Mauricio Aguiar
 *
 */
public class ProjectService {

	@Inject
	private ProjectDao projectDao;

	public int count() {
		return projectDao.count();
	}

	@Transactional
	public boolean create(Project entity) {
		try {
			this.projectDao.create(entity);
			return true;
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, "Erro no Service ", e);
			return false;
		}
	}

	@Transactional
	public boolean update(Project entity){
		try{
			projectDao.update(entity);
			return true;
		}catch(Exception e){
			getLogger().log(Level.SEVERE, "Erro no Service ", e);
			return false;
		}
	}

	public Project find(Long id) {
		return projectDao.findById(id);
	}

	public List<Project> findAll() {
		return projectDao.findAll();
	}

	public List<Project> findRange(int[] range) {
		return projectDao.findRange(range);
	}

	public void remove(Project entity) {
		this.projectDao.delete(entity);
	}
	
	public List<Project> findByIdProduct(Product product){
		return projectDao.findByIdProduct(product);
	}
	

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}