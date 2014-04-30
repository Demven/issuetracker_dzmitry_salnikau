package org.training.issuetracker.services.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.issuetracker.dao.entities.Resolution;
import org.training.issuetracker.dao.interfaces.ResolutionDAO;
import org.training.issuetracker.services.ResolutionService;

@Service("resolutionService") 
@Repository 
@Transactional 
public class SpringResolutionService implements ResolutionService{

	@Autowired
	private ResolutionDAO resolutionDAO;
	
	public void setResolutionDAO(ResolutionDAO resolutionDAO) {
		this.resolutionDAO = resolutionDAO;
	}
	
	@Transactional
	@Override
	public List<Resolution> getResolutions() {
		return resolutionDAO.getResolutions();
	}

	@Transactional
	@Override
	public Resolution getResolutionById(int resolutionId) {
		return resolutionDAO.getResolutionById(resolutionId);
	}

	@Transactional
	@Override
	public boolean createResolution(Resolution resolution) {
		return resolutionDAO.createResolution(resolution);
	}

	@Transactional
	@Override
	public boolean updateResolution(Resolution resolution) {
		return resolutionDAO.updateResolution(resolution);
	}

	@Transactional
	@Override
	public boolean deleteResolution(int resolutionId) {
		return resolutionDAO.deleteResolution(resolutionId);
	}

}
