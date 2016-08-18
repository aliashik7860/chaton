package com.appdevsolutions.chat.dao.repository;

import java.util.List;

import com.appdevsolutions.chat.dao.entity.EmailNotificationTemplate;
import com.appdevsolutions.chat.dao.entity.EmailTemplate;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

public interface EmailNotificationTemplateRepository
{
	public void persist(EmailNotificationTemplate emailNotificationTemplate);
	public EmailNotificationTemplate findById(String id)throws EntityNotFoundException;
	public List<EmailNotificationTemplate> findAll();
	public List<EmailNotificationTemplate> findByEmailTemplate(EmailTemplate emailTemplate)throws EntityNotFoundException;
	public List<EmailNotificationTemplate> findByEmailTemplateWithUser(EmailTemplate emailTemplate, User user)throws EntityNotFoundException;
	public List<EmailNotificationTemplate> findByUser(User user)throws EntityNotFoundException;
	public void deleteById(String id)throws EntityNotFoundException;
	public void deleteByEmailTemplate(EmailTemplate emailTemplate)throws EntityNotFoundException;
	public void deleteByEmailTemplateWithUser(EmailTemplate emailTemplate,User user)throws EntityNotFoundException;
	public void deleteByUser(User user)throws EntityNotFoundException;
	public boolean isActive(String id)throws EntityNotFoundException;
	public void setActiveById(String id,boolean state) throws EntityNotFoundException;
	public void setActiveByUser(User user,boolean state) throws EntityNotFoundException;
	public void setActiveByEmailTemplate(EmailTemplate emailTemplate, boolean active) throws EntityNotFoundException;
	public void setActiveByEmailTemplateWithUser(EmailTemplate emailTemplate, User user, boolean active) throws EntityNotFoundException;
	public boolean checkUnique(EmailTemplate emailTemplate,User user);
}
