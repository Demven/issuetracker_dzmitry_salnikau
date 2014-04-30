package org.training.issuetracker.services;

import java.util.List;

import org.training.issuetracker.dao.entities.Attachment;

public interface AttachmentService {
	
	/**
	 * @return List of all attachments for all issues
	 */
	public List<Attachment> getAttachments();
	
	/**
	 * Returns List of all attachments for a specified issue
	 * @param issueId - unique Id of a issue
	 * @return List<Attachment> 
	 */
	public List<Attachment> getAttachmentsForIssue(int issueId);
	
	/**
	 * Returns List of all attachments for a specified user
	 * @param userId - unique Id of a user
	 * @return List<Attachment> 
	 */
	public List<Attachment> getAttachmentsForUser(int userId);
	
	/**
	 * Returns Attachment object with stated attachmentId
	 * @param attachmentId
	 * @return Attachment 
	 */
	public Attachment getAttachmentById(int attachmentId);
	
	/**
	 * Adds new attachment in a data storage
	 * @param Attachment
	 * @return boolean - true, if it was successful
	 */
	public boolean createAttachment(Attachment attachment);
	
	/**
	 * Update the attachment's data 
	 * @param Attachment
	 * @return boolean - true, if it was successful
	 */
	public boolean updateAttachment(Attachment attachment);
	
	/**
	 * Delete attachment from a data storage by the unique attachmentId
	 * @param attachmentId
	 * @return boolean - true, if it was successful
	 */
	public boolean deleteAttachment(int attachmentId);
}
