package org.training.issuetracker.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.training.issuetracker.dao.hibernate.entities.Build;
import org.training.issuetracker.dao.hibernate.entities.Project;
import org.training.issuetracker.dao.hibernate.util.HibernateUtil;
import org.training.issuetracker.dao.interfaces.BuildDAO;

public class HibernateBuildDAO implements BuildDAO {
	
	private static final Logger logger = Logger.getLogger(HibernateBuildDAO.class);
	private static final String TAG = HibernateBuildDAO.class.getSimpleName();

	@Override
	public List<Build> getBuilds() {
		List<Build> builds = null;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
	        Criteria criteria = session.createCriteria(Build.class);
	        criteria.addOrder(Order.asc(Build.COLUMN_ID));
	        builds = (List<Build>) criteria.list();
	        transaction.commit();
		} catch (Exception e) {
			logger.error(TAG + " Getting all builds failed!", e);
		    transaction.rollback();
		    throw e;
		}
        
        return builds;
	}

	@Override
	public List<Build> getBuildsForProject(int projectId) {
		List<Build> buildsForProject = null;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
	        Criteria criteria = session.createCriteria(Build.class);
	        criteria.addOrder(Order.asc(Build.COLUMN_ID));
	        
	        Project project = new Project();
	        project.setProjectId(projectId);
	        
	        buildsForProject = (List<Build>) criteria.add(Restrictions.like(Build.COLUMN_PROJECT, project)).list();
	        
	        transaction.commit();
		} catch (Exception e) {
			logger.error(TAG + " Getting builds for project " + projectId + "failed!", e);
		    transaction.rollback();
		    throw e;
		}
		
        return buildsForProject;
	}

	@Override
	public Build getBuildById(int buildId) {
		Build buildById = null;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
	        buildById= (Build) session.get(Build.class, buildId);
	        transaction.commit();
		} catch (Exception e) {
			logger.error(TAG + " Getting Build-object " + buildId + "failed!", e);
		    transaction.rollback();
		    throw e;
		}
        
        return buildById;
	}

	@Override
	public boolean createBuild(Build build) {
		boolean success = false;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
		    session.save(build);
		    transaction.commit();
		    success = true;
		} catch(Exception e) {
			transaction.rollback();
		    logger.error(TAG + " Creating Build-object failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean updateBuild(Build build) {
		boolean success = false;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
		    session.update(build);
		    transaction.commit();
		    success = true;
		} catch(Exception e) {
			transaction.rollback();
		    logger.error(TAG + " Updating Build-object with id=" + build.getBuildId() + " failed!", e);
		}
		
		return success;
	}

	@Override
	public boolean deleteBuild(int buildId) {
		boolean success = false;
		
		Build deletingBuild = new Build();
		deletingBuild.setBuildId(buildId);
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();	
		try {
		    session.delete(deletingBuild);
		    transaction.commit();
		    success = true;
		} catch(Exception e) {
			transaction.rollback();
		    logger.error(TAG + " Deleting Build-object with id=" + buildId + " failed!", e);
		}
		
		return success;
	}

}
