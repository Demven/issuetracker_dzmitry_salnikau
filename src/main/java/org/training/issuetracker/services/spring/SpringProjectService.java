package org.training.issuetracker.services.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.issuetracker.dao.entities.Project;
import org.training.issuetracker.dao.interfaces.ProjectDAO;
import org.training.issuetracker.services.ProjectService;

@Service("projectService") 
@Repository 
@Transactional 
public class SpringProjectService implements ProjectService{

	@Autowired
	private ProjectDAO projectDAO;
	
	public void setProjectDAO(ProjectDAO projectDAO) {
		this.projectDAO = projectDAO;
	}
	
	@Transactional
	@Override
	public List<Project> getProjects() {
		return projectDAO.getProjects();
	}

	@Transactional
	@Override
	public Project getProjectById(int projectId) {
		return projectDAO.getProjectById(projectId);
	}

	@Transactional
	@Override
	public Integer getProjectIdByName(String name) {
		return projectDAO.getProjectIdByName(name);
	}

	@Transactional
	@Override
	public boolean createProject(Project project) {
		return projectDAO.createProject(project);
	}

	@Transactional
	@Override
	public boolean updateProject(Project project) {
		return projectDAO.updateProject(project);
	}

	@Transactional
	@Override
	public boolean deleteProject(int projectId) {
		return projectDAO.deleteProject(projectId);
	}

}
