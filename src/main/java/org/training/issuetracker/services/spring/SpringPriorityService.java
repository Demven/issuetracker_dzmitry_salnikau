package org.training.issuetracker.services.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.issuetracker.dao.entities.Priority;
import org.training.issuetracker.dao.interfaces.PriorityDAO;
import org.training.issuetracker.services.PriorityService;

@Service("priorityService") 
@Repository 
@Transactional 
public class SpringPriorityService implements PriorityService{

	@Autowired
	private PriorityDAO priorityDAO;
	
	public void setPriorityDAO(PriorityDAO priorityDAO) {
		this.priorityDAO = priorityDAO;
	}
	
	@Transactional
	@Override
	public List<Priority> getPriorities() {
		return priorityDAO.getPriorities();
	}

	@Transactional
	@Override
	public Priority getPriorityById(int priorityId) {
		return priorityDAO.getPriorityById(priorityId);
	}

	@Transactional
	@Override
	public boolean createPriority(Priority priority) {
		return priorityDAO.createPriority(priority);
	}

	@Transactional
	@Override
	public boolean updatePriority(Priority priority) {
		return priorityDAO.updatePriority(priority);
	}

	@Transactional
	@Override
	public boolean deletePriority(int priorityId) {
		return priorityDAO.deletePriority(priorityId);
	}

}
