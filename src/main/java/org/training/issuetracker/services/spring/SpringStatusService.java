package org.training.issuetracker.services.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.issuetracker.dao.entities.Status;
import org.training.issuetracker.dao.interfaces.StatusDAO;
import org.training.issuetracker.services.StatusService;

@Service("statusService") 
@Repository 
@Transactional 
public class SpringStatusService implements StatusService{

	@Autowired
	private StatusDAO statusDAO;
	
	public void setStatusDAO(StatusDAO statusDAO) {
		this.statusDAO = statusDAO;
	}
	
	@Transactional
	@Override
	public List<Status> getStatuses() {
		return statusDAO.getStatuses();
	}

	@Transactional
	@Override
	public Status getStatusById(int statusId) {
		return statusDAO.getStatusById(statusId);
	}

	@Transactional
	@Override
	public boolean createStatus(Status status) {
		return statusDAO.createStatus(status);
	}

	@Transactional
	@Override
	public boolean updateStatus(Status status) {
		return statusDAO.updateStatus(status);
	}

	@Transactional
	@Override
	public boolean deleteStatus(int statusId) {
		return statusDAO.deleteStatus(statusId);
	}

}
