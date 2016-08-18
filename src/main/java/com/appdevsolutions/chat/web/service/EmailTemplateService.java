package com.appdevsolutions.chat.web.service;

import java.util.Set;

import com.appdevsolutions.chat.web.exception.EmailTemplateAlreadyExistException;
import com.appdevsolutions.chat.web.exception.EmailTemplateNotFoundException;
import com.appdevsolutions.chat.web.model.EmailTemplateModel;

public interface EmailTemplateService {
	public void save(EmailTemplateModel emailTemplateModel) throws EmailTemplateAlreadyExistException;
	public Set<EmailTemplateModel> getAll();
	public EmailTemplateModel getById(String id) throws EmailTemplateNotFoundException;
	public EmailTemplateModel getByName(String name) throws EmailTemplateNotFoundException;
	public EmailTemplateModel getBySubject(String subject)throws EmailTemplateNotFoundException;
	public void updateEmailFromById(String id,String from) throws EmailTemplateNotFoundException;
	public void updateEmailFromByName(String name,String from) throws EmailTemplateNotFoundException;
	public void updateEmailFromBySubject(String subject,String from) throws EmailTemplateNotFoundException;
	public void updateName(String oldName,String newName) throws EmailTemplateNotFoundException,EmailTemplateAlreadyExistException;
	public void updateSubject(String oldSubject,String newSubject) throws EmailTemplateNotFoundException,EmailTemplateAlreadyExistException;
	public void updateBodyById(String id,String body) throws EmailTemplateNotFoundException;
	public void updateBodyByName(String name,String body) throws EmailTemplateNotFoundException;
	public void updateBodyBySubject(String subject,String body)throws EmailTemplateNotFoundException;
	public void deleteById(String id)throws EmailTemplateNotFoundException;
	public void deleteBySubject(String subject)throws EmailTemplateNotFoundException;
	public void deleteByName(String name)throws EmailTemplateNotFoundException;
}