package org.training.issuetracker.services.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.issuetracker.dao.entities.Build;
import org.training.issuetracker.dao.interfaces.BuildDAO;
import org.training.issuetracker.services.BuildService;

@Service("buildService") 
@Repository 
@Transactional 
public class SpringBuildService implements BuildService{

	@Autowired
	private BuildDAO buildDAO;
	
	public void setBuildDAO(BuildDAO buildDAO) {
		this.buildDAO = buildDAO;
	}
	
	@Transactional
	@Override
	public List<Build> getBuilds() {
		return buildDAO.getBuilds();
	}

	@Transactional
	@Override
	public List<Build> getBuildsForProject(int projectId) {
		return buildDAO.getBuildsForProject(projectId);
	}

	@Transactional
	@Override
	public Build getBuildById(int buildId) {
		return buildDAO.getBuildById(buildId);
	}

	@Transactional
	@Override
	public boolean createBuild(Build build) {
		return buildDAO.createBuild(build);
	}

	@Transactional
	@Override
	public boolean updateBuild(Build build) {
		return buildDAO.updateBuild(build);
	}

	@Transactional
	@Override
	public boolean deleteBuild(int buildId) {
		return buildDAO.deleteBuild(buildId);
	}

}
