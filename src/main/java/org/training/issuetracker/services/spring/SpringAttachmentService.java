package org.training.issuetracker.services.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.issuetracker.dao.hibernate.entities.Attachment;
import org.training.issuetracker.dao.interfaces.AttachmentDAO;
import org.training.issuetracker.services.AttachmentService;

@Service("attachmentService") 
@Repository 
@Transactional 
public class SpringAttachmentService implements AttachmentService{

	@Autowired
	private AttachmentDAO attachmentDAO;
	
	public void setAttachmentDAO(AttachmentDAO attachmentDAO) {
		this.attachmentDAO = attachmentDAO;
	}
	
	@Transactional
	@Override
	public List<Attachment> getAttachments() {
		return attachmentDAO.getAttachments();
	}

	@Transactional
	@Override
	public List<Attachment> getAttachmentsForIssue(int issueId) {
		return attachmentDAO.getAttachmentsForIssue(issueId);
	}

	@Transactional
	@Override
	public List<Attachment> getAttachmentsForUser(int userId) {
		return attachmentDAO.getAttachmentsForUser(userId);
	}

	@Transactional
	@Override
	public Attachment getAttachmentById(int attachmentId) {
		return attachmentDAO.getAttachmentById(attachmentId);
	}

	@Transactional
	@Override
	public boolean createAttachment(Attachment attachment) {
		return attachmentDAO.createAttachment(attachment);
	}

	@Transactional
	@Override
	public boolean updateAttachment(Attachment attachment) {
		return attachmentDAO.updateAttachment(attachment);
	}

	@Transactional
	@Override
	public boolean deleteAttachment(int attachmentId) {
		return attachmentDAO.deleteAttachment(attachmentId);
	}

}
