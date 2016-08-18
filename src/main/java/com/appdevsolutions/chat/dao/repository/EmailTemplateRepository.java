package com.appdevsolutions.chat.dao.repository;

import java.util.List;

import com.appdevsolutions.chat.dao.entity.EmailTemplate;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

public interface EmailTemplateRepository {
	public void persist(EmailTemplate emailTemplate);
	public List<EmailTemplate> findAll();
	public EmailTemplate findById(String id)throws EntityNotFoundException;
	public EmailTemplate findByName(String name)throws EntityNotFoundException;
	public EmailTemplate findBySubject(String subject)throws EntityNotFoundException;
	public void updateEmailFromById(String id,String eamilFrom)throws EntityNotFoundException;
	public void updateEmailFromByName(String name,String from)throws EntityNotFoundException;
	public void updateEmailFromBySubject(String subject,String from) throws EntityNotFoundException;
	public void updateName(String oldName,String newName) throws EntityNotFoundException;
	public void updateSubject(String oldSubject,String newSubject)throws EntityNotFoundException;
	public void updateBodyById(String id,String body)throws EntityNotFoundException;
	public void updateBodyBySubject(String subject,String body) throws EntityNotFoundException;
	public void updateBodyByName(String name,String body)throws EntityNotFoundException;
	public void deleteById(String id)throws EntityNotFoundException;
	public void deleteBySubject(String subject)throws EntityNotFoundException;
	public void deleteByName(String name)throws EntityNotFoundException;
}
