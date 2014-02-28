package org.training.issuetracker.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.training.issuetracker.dao.hibernate.entities.Build;
import org.training.issuetracker.dao.hibernate.entities.Project;
import org.training.issuetracker.dao.hibernate.util.HibernateUtil;
import org.training.issuetracker.dao.interfaces.BuildDAO;

public class HibernateBuildDAO implements BuildDAO {
	
	private static final Logger logger = Logger.getLogger(HibernateBuildDAO.class);

	@Override
	public ArrayList<org.training.issuetracker.dao.transferObjects.Build> getBuilds() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Build.class);
        List<Build> result = (List<Build>) criteria.list();
        session.getTransaction().commit();
        
        logger.warn("Builds = " + result.size());
        for(Build build: result){
        	logger.warn("Id = " + build.getBuildId() + " Version=" + build.getVersion() + " Project=" + build.getProject().getProjectId() + " " + build.getProject().getName());
        }
        return null;
	}

	@Override
	public ArrayList<org.training.issuetracker.dao.transferObjects.Build> getBuildsForProject(int projectId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Build.class);
        
        Project project = new Project();
        project.setProjectId(projectId);
        
        List<Build> result = (List<Build>) criteria.add(Restrictions.like("project", project)).list();
        logger.warn("Builds = " + result.size());
        for(Build build: result){
        	logger.warn("Build with projectId: id=" + build.getBuildId() + " version=" + build.getVersion()
        			+ " project=" + build.getProject().getProjectId()  + " " + build.getProject().getName());
        }
        return null;
	}

	@Override
	public org.training.issuetracker.dao.transferObjects.Build getBuildById(int buildId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Build result = (Build) session.get(Build.class, buildId);
        session.getTransaction().commit();
        
        logger.warn("Build: id=" + result.getBuildId() + " version=" + result.getVersion() + " project=" + result.getProject().getProjectId() + " " + result.getProject().getName());
        return null;
	}

	@Override
	public boolean createBuild(org.training.issuetracker.dao.transferObjects.Build build) {
		boolean success = false;
		
		Build newBuild = new Build();
		Project project = new Project();
		project.setProjectId(build.getProject());
		
		newBuild.setBuildId(build.getBuildId());
		newBuild.setProject(project);
		newBuild.setVersion(build.getVersion());
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.saveOrUpdate(newBuild);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

	@Override
	public boolean updateBuild(org.training.issuetracker.dao.transferObjects.Build build) {
		boolean success = false;
		
		Build newBuild = new Build();
		Project project = new Project();
		project.setProjectId(build.getProject());
		
		newBuild.setBuildId(build.getBuildId());
		newBuild.setProject(project);
		newBuild.setVersion(build.getVersion());
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.saveOrUpdate(newBuild);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

	@Override
	public boolean deleteBuild(int buildId) {
		boolean success = false;
		
		Build deletingBuild = new Build();
		deletingBuild.setBuildId(buildId);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
		    tx = session.beginTransaction();
		    session.delete(deletingBuild);
		    tx.commit();
		    success = true;
		} catch(Exception e) {
		    tx.rollback();
		}
		
		return success;
	}

}
