package org.training.issuetracker.beans.converters;

import org.training.issuetracker.beans.IssueBean;
import org.training.issuetracker.beans.UserBean;
import org.training.issuetracker.dao.hibernate.entities.Issue;
import org.training.issuetracker.dao.hibernate.entities.Resolution;
import org.training.issuetracker.dao.hibernate.entities.User;

/**
 * Convenient class to convert transfer objects into beans used in JSP
 * @author Dzmitry Salnikau
 * @since 10.02.2014
 */
public class BeanConverter {
	
	public static UserBean convertToUserBean(User user){
		UserBean userBean = new UserBean();
		
		userBean.setUserId(user.getUserId());
		userBean.setFirstName(user.getFirstName());
		userBean.setLastName(user.getLastName());
		userBean.setEmail(user.getEmail());
		userBean.setPassword(user.getPassword());
		userBean.setRole(user.getRole());
		
		return userBean;
	}
	
	public static IssueBean convertToIssueBean(Issue issue){
		IssueBean issueBean = new IssueBean();
		
		Resolution resolution = null;
		if(issue.getResolution() != null){
			resolution = issue.getResolution();
		}
		
		User assignee = null;
		if(issue.getAssignee() != null){
			assignee = issue.getAssignee();
		}
		
		issueBean.setIssueId(issue.getIssueId());
		issueBean.setCreateDate(issue.getCreateDate());
		issueBean.setCreatedBy(issue.getCreatedBy());
		issueBean.setModifyDate(issue.getModifyDate());
		issueBean.setModifiedBy(issue.getModifiedBy());
		issueBean.setSummary(issue.getSummary());
		issueBean.setDescription(issue.getDescription());
		issueBean.setStatus(issue.getStatus());
		issueBean.setResolution(resolution);
		issueBean.setType(issue.getType());
		issueBean.setPriority(issue.getPriority());
		issueBean.setProject(issue.getProject());
		issueBean.setBuildFound(issue.getBuildFound());
		issueBean.setAssignee(assignee);
		
		return issueBean;
	}
}
