package org.training.issuetracker.services.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.issuetracker.dao.hibernate.entities.Issue;
import org.training.issuetracker.dao.interfaces.IssueDAO;
import org.training.issuetracker.services.IssueService;

@Service("issueService") 
@Repository 
@Transactional 
public class SpringIssueService implements IssueService{

	@Autowired
	private IssueDAO issueDAO;
	
	public void setIssueDAO(IssueDAO issueDAO) {
		this.issueDAO = issueDAO;
	}
	
	@Transactional
	@Override
	public List<Issue> getIssues() {
		return issueDAO.getIssues();
	}

	@Transactional
	@Override
	public Issue getIssueById(Integer issueId) {
		return issueDAO.getIssueById(issueId);
	}

	@Transactional
	@Override
	public boolean createIssue(Issue issue) {
		return issueDAO.createIssue(issue);
	}

	@Transactional
	@Override
	public boolean updateIssue(Issue issue) {
		return issueDAO.updateIssue(issue);
	}

	@Transactional
	@Override
	public boolean deleteIssue(Integer issueId) {
		return issueDAO.deleteIssue(issueId);
	}

}
