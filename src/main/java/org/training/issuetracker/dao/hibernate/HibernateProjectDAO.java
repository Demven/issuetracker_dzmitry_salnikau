package org.training.issuetracker.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.training.issuetracker.dao.hibernate.entities.Project;
import org.training.issuetracker.dao.hibernate.util.HibernateUtil;
import org.training.issuetracker.dao.interfaces.ProjectDAO;

public class HibernateProjectDAO implements ProjectDAO {
	
	private static final Logger logger = Logger.getLogger(HibernateProjectDAO.class);
	private static final String TAG = HibernateProjectDAO.class.getSimpleName();
	
	@Override
	public List<Project> getProjects() {
		List<Project> projects = null;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
	        Criteria criteria = session.createCriteria(Project.class);
	        criteria.addOrder(Order.asc(Project.COLUMN_ID));
	        projects = (List<Project>) criteria.list();
	        transaction.commit();
		} catch (Exception e) {
			logger.error(TAG + " Getting all projects failed!", e);
		    transaction.rollback();
		    throw e;
		}
        
        return projects;
	}

	@Override
	public Project getProjectById(int projectId) {
		Project projectById = null;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
	        projectById = (Project) session.get(Project.class, projectId);
	        transaction.commit();
		} catch (Exception e) {
			logger.error(TAG + " Getting Project-object " + projectId + " failed!", e);
		    transaction.rollback();
		    throw e;
		}
		
        return projectById;
	}

	@Override
	public Integer getProjectIdByName(String name) {
		Integer projectId = null;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
	        Criteria criteria = session.createCriteria(Project.class);
	
	        List<Project> result = (List<Project>) criteria.add(Restrictions.like(Project.COLUMN_NAME, name)).list();
	        Project project = result.get(0);
	        
	        transaction.commit();
	        
	        if(project != null){
	        	projectId = project.getProjectId();
	        }
		} catch (Exception e) {
			logger.error(TAG + " Getting projectId by name=" + name +" failed!", e);
		    transaction.rollback();
		    throw e;
		}
           
        return projectId;
	}

	@Override
	public boolean createProject(Project project) {
		boolean success = false;

		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
		    session.save(project);
		    transaction.commit();
		    success = true;
		} catch(Exception e) {
			transaction.rollback();
		    logger.error(TAG + " Creating Project-object failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean updateProject(Project project) {
		boolean success = false;

		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
		    session.update(project);
		    transaction.commit();
		    success = true;
		} catch(Exception e) {
			transaction.rollback();
		    logger.error(TAG + " Updating Project-object with id=" + project.getProjectId() + " failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean deleteProject(int projectId) {
		boolean success = false;
		
		Project deletingProject = new Project();
		deletingProject.setProjectId(projectId);
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
		    session.delete(deletingProject);
		    transaction.commit();
		    success = true;
		} catch(Exception e) {
			transaction.rollback();
		    logger.error(TAG + " Deleting Build-object with id=" + projectId + " failed!", e);
		}
		
		return success;
	}

}
