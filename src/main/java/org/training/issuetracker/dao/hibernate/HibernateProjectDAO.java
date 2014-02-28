package org.training.issuetracker.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.training.issuetracker.dao.hibernate.entities.Project;
import org.training.issuetracker.dao.hibernate.entities.User;
import org.training.issuetracker.dao.hibernate.util.HibernateUtil;
import org.training.issuetracker.dao.interfaces.ProjectDAO;

public class HibernateProjectDAO implements ProjectDAO {
	
	private static final Logger logger = Logger.getLogger(HibernateProjectDAO.class);

	@Override
	public ArrayList<org.training.issuetracker.dao.transferObjects.Project> getProjects() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Project.class);
        List<Project> result = (List<Project>) criteria.list();
        session.getTransaction().commit();
        
        logger.warn("Projects = " + result.size());
        for(Project project: result){
        	logger.warn("Id = " + project.getProjectId() + " Name=" + project.getName() + " Description=" + project.getDescription()
        			+ " Manager=" + project.getManager().getUserId() + " " + project.getManager().getLastName());
        }
        return null;
	}

	@Override
	public org.training.issuetracker.dao.transferObjects.Project getProjectById(int projectId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Project result = (Project) session.get(Project.class, projectId);
        session.getTransaction().commit();
        
        logger.warn("Id = " + result.getProjectId() + " Name=" + result.getName() + " Description=" + result.getDescription()
    			+ " Manager=" + result.getManager().getUserId() + " " + result.getManager().getLastName());
        return null;
	}

	@Override
	public Integer getProjectIdByName(String name) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Project.class);

        List<Project> result = (List<Project>) criteria.add(Restrictions.like("name", name)).list();
        Project project = result.get(0);
        
        session.getTransaction().commit();
        
        if(project != null){
        	logger.warn("For name =" + name + "projectId = " + project.getProjectId());
        }
           
        return null;
	}

	@Override
	public boolean createProject(org.training.issuetracker.dao.transferObjects.Project project) {
		boolean success = false;
		
		Project newProject = new Project();
		User manager = new User();
		manager.setUserId(project.getManager());
		
		newProject.setProjectId(project.getProjectId());
		newProject.setName(project.getName());
		newProject.setDescription(project.getDescription());
		newProject.setManager(manager);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.saveOrUpdate(newProject);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

	@Override
	public boolean updateProject(org.training.issuetracker.dao.transferObjects.Project project) {
		boolean success = false;
		
		Project newProject = new Project();
		User manager = new User();
		manager.setUserId(project.getManager());
		
		newProject.setProjectId(project.getProjectId());
		newProject.setName(project.getName());
		newProject.setDescription(project.getDescription());
		newProject.setManager(manager);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.saveOrUpdate(newProject);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

	@Override
	public boolean deleteProject(int projectId) {
		boolean success = false;
		
		Project deletingProject = new Project();
		deletingProject.setProjectId(projectId);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.delete(deletingProject);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

}
