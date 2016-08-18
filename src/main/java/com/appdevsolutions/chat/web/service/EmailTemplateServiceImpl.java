package com.appdevsolutions.chat.web.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appdevsolutions.chat.dao.entity.EmailTemplate;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;
import com.appdevsolutions.chat.dao.repository.EmailTemplateRepository;
import com.appdevsolutions.chat.web.exception.EmailTemplateAlreadyExistException;
import com.appdevsolutions.chat.web.exception.EmailTemplateNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.EmailTemplateModel;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

	@Autowired
	EmailTemplateRepository emailTemplateRepository;
	
	@Override
	public void save(EmailTemplateModel emailTemplateModel) throws EmailTemplateAlreadyExistException {
		try{
			emailTemplateRepository.findByName(emailTemplateModel.getName());
			throw new EmailTemplateAlreadyExistException("GE_1011", ChatOnWebConstants.EMAIL_TEMPLATE_NAME, emailTemplateModel.getName());
		}catch(EntityNotFoundException entityNotFoundException){
			try{
				emailTemplateRepository.findBySubject(emailTemplateModel.getSubject());
				throw new EmailTemplateAlreadyExistException("GE_1011", ChatOnWebConstants.EMAIL_TEMPLATE_SUBJECT, emailTemplateModel.getSubject());
			}catch(EntityNotFoundException entityNotFoundException2){
				final EmailTemplate emailTemplate=new EmailTemplate();
				emailTemplate.setName(emailTemplateModel.getName());
				emailTemplate.setSubject(emailTemplateModel.getSubject());
				emailTemplate.setEmailFrom(emailTemplateModel.getFrom());
				emailTemplate.setBody(emailTemplateModel.getBody());
				emailTemplate.setActive(true);
				emailTemplate.setCreateTimestamp(emailTemplateModel.getCreateTimestamp());
				emailTemplate.setUpdateTimestamp(emailTemplateModel.getUpdateTimestamp());
				emailTemplateRepository.persist(emailTemplate);
			}
		}
	}
	@Override
	public Set<EmailTemplateModel> getAll() {
		final List<EmailTemplate> emailTemplates=emailTemplateRepository.findAll();
		final Set<EmailTemplateModel> emailTemplateModels=new HashSet<EmailTemplateModel>(emailTemplates.size());
		for(EmailTemplate emailTemplate:emailTemplates){
			final EmailTemplateModel emailTemplateModel=new EmailTemplateModel(emailTemplate.getId(), emailTemplate.getName(), emailTemplate.getSubject(), emailTemplate.getEmailFrom(), emailTemplate.getBody(),emailTemplate.getCreateTimestamp(), emailTemplate.getUpdateTimestamp());
			emailTemplateModels.add(emailTemplateModel);
		}
		return emailTemplateModels;
	}

	@Override
	public EmailTemplateModel getById(String id) throws EmailTemplateNotFoundException {
		try {
			final EmailTemplate emailTemplate=emailTemplateRepository.findById(id);
			final EmailTemplateModel emailTemplateModel=new EmailTemplateModel(emailTemplate.getId(), emailTemplate.getName(), emailTemplate.getSubject(), emailTemplate.getEmailFrom(), emailTemplate.getBody(),emailTemplate.getCreateTimestamp(),emailTemplate.getUpdateTimestamp());
			return emailTemplateModel;
		} catch (EntityNotFoundException e) {
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_ID,id);
		}
	}

	@Override
	public EmailTemplateModel getByName(String name) throws EmailTemplateNotFoundException {
		try {
			final EmailTemplate emailTemplate=emailTemplateRepository.findByName(name);
			final EmailTemplateModel emailTemplateModel=new EmailTemplateModel(emailTemplate.getId(), emailTemplate.getName(), emailTemplate.getSubject(), emailTemplate.getEmailFrom(), emailTemplate.getBody(),emailTemplate.getCreateTimestamp(),emailTemplate.getUpdateTimestamp());
			return emailTemplateModel;
		} catch (EntityNotFoundException e) {
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_NAME,name);
		}
	}

	@Override
	public EmailTemplateModel getBySubject(String subject) throws EmailTemplateNotFoundException {
		try {
			final EmailTemplate emailTemplate=emailTemplateRepository.findBySubject(subject);
			final EmailTemplateModel emailTemplateModel=new EmailTemplateModel(emailTemplate.getId(), emailTemplate.getName(), emailTemplate.getSubject(), emailTemplate.getEmailFrom(), emailTemplate.getBody(),emailTemplate.getCreateTimestamp(),emailTemplate.getUpdateTimestamp());
			return emailTemplateModel;
		} catch (EntityNotFoundException e) {
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_SUBJECT,subject);
		}
	}
	@Override
	public void updateEmailFromById(String id, String from) throws EmailTemplateNotFoundException {
		try {
			emailTemplateRepository.updateEmailFromById(id, from);
		} catch (EntityNotFoundException e) {
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_ID, id);
		}
	}

	@Override
	public void updateEmailFromByName(String name, String from) throws EmailTemplateNotFoundException {
		try {
			emailTemplateRepository.updateEmailFromByName(name, from);
		} catch (EntityNotFoundException e) {
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_NAME, name);
		}
	}

	@Override
	public void updateEmailFromBySubject(String subject, String from) throws EmailTemplateNotFoundException {
		try {
			emailTemplateRepository.updateEmailFromBySubject(subject, from);
		} catch (EntityNotFoundException e) {
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_SUBJECT, subject);
		}
	}

	@Override
	public void updateName(String oldName, String newName)
			throws EmailTemplateNotFoundException, EmailTemplateAlreadyExistException {
		try {
			emailTemplateRepository.findByName(newName);
			throw new EmailTemplateAlreadyExistException("GE_1011", ChatOnWebConstants.EMAIL_TEMPLATE_NEW_NAME,newName);
		} catch (EntityNotFoundException e) {
			try {
				emailTemplateRepository.updateName(oldName, newName);
			} catch (EntityNotFoundException e1) {
				throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_OLD_NAME,oldName);
			}
		}
	}

	@Override
	public void updateSubject(String oldSubject, String newSubject)
			throws EmailTemplateNotFoundException, EmailTemplateAlreadyExistException {
		try {
			emailTemplateRepository.findBySubject(newSubject);
			throw new EmailTemplateAlreadyExistException("GE_1011", ChatOnWebConstants.EMAIL_TEMPLATE_NEW_SUBJECT,newSubject);
		} catch (EntityNotFoundException e) {
			try {
				emailTemplateRepository.updateSubject(oldSubject, newSubject);
			} catch (EntityNotFoundException e1) {
				throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_OLD_SUBJECT,oldSubject);
			}
		}
	}

	@Override
	public void updateBodyById(String id, String body) throws EmailTemplateNotFoundException {
		try {
			emailTemplateRepository.updateBodyById(id, body);
		} catch (EntityNotFoundException e) {
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_ID, id);
		}
	}

	@Override
	public void updateBodyByName(String name, String body) throws EmailTemplateNotFoundException {
		try {
			emailTemplateRepository.updateBodyByName(name, body);
		} catch (EntityNotFoundException e) {
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_NAME,name);
		}
	}

	@Override
	public void updateBodyBySubject(String subject, String body) throws EmailTemplateNotFoundException {
		try {
			emailTemplateRepository.updateBodyBySubject(subject, body);
		} catch (EntityNotFoundException e) {
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_SUBJECT,subject);
		}
	}

	@Override
	public void deleteById(String id) throws EmailTemplateNotFoundException {
		try {
			emailTemplateRepository.deleteById(id);
		} catch (EntityNotFoundException e) {
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_ID, id);
		}
	}

	@Override
	public void deleteBySubject(String subject) throws EmailTemplateNotFoundException {
		try {
			emailTemplateRepository.deleteBySubject(subject);
		} catch (EntityNotFoundException e) {
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_SUBJECT, subject);
		}
	}

	@Override
	public void deleteByName(String name) throws EmailTemplateNotFoundException {
		try {
			emailTemplateRepository.deleteByName(name);
		} catch (EntityNotFoundException e) {
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_NAME, name);
		}
	}
}