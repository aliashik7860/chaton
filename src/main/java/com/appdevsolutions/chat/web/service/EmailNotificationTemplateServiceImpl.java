package com.appdevsolutions.chat.web.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appdevsolutions.chat.dao.entity.EmailNotificationTemplate;
import com.appdevsolutions.chat.dao.entity.EmailTemplate;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;
import com.appdevsolutions.chat.dao.repository.EmailNotificationTemplateRepository;
import com.appdevsolutions.chat.dao.repository.EmailTemplateRepository;
import com.appdevsolutions.chat.dao.repository.UserRepository;
import com.appdevsolutions.chat.web.exception.EmailNotificationTemplateAlreadyExistException;
import com.appdevsolutions.chat.web.exception.EmailNotificationTemplateNotFoundException;
import com.appdevsolutions.chat.web.exception.EmailTemplateNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.EmailNotificationTemplateModel;
import com.appdevsolutions.chat.web.model.EmailTemplateModel;

@Service
public class EmailNotificationTemplateServiceImpl implements EmailNotificationTemplateService{
	
	@Autowired
	EmailNotificationTemplateRepository emailNotificationTemplateRepository;
	
	@Autowired
	EmailTemplateRepository emailTemplateRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void saveById(String emailTemplateId, String userId)
			throws EmailTemplateNotFoundException, UserNotFoundException,EmailNotificationTemplateAlreadyExistException {
		try{
			final EmailTemplate emailTemplate=emailTemplateRepository.findById(emailTemplateId);
			try{
				final User user=userRepository.selectById(userId);
				if(!saveByEmailTemplateAndUser(emailTemplate, user)){
					throw new EmailNotificationTemplateAlreadyExistException("GE_1015", ChatOnWebConstants.EMAIL_TEMPLATE_ID, emailTemplateId);
				}
			}catch(EntityNotFoundException entityNotFoundException){
				throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new EmailTemplateNotFoundException("GE_1014", ChatOnWebConstants.EMAIL_TEMPLATE_ID, emailTemplateId);
		}
	}

	@Override
	public void saveByName(String emailTemplateName, String userId)
			throws EmailTemplateNotFoundException, UserNotFoundException,EmailNotificationTemplateAlreadyExistException {
		try{
			final EmailTemplate emailTemplate=emailTemplateRepository.findByName(emailTemplateName);
			try{
				final User user=userRepository.selectById(userId);
				if(!saveByEmailTemplateAndUser(emailTemplate, user)){
					throw new EmailNotificationTemplateAlreadyExistException("GE_1015", ChatOnWebConstants.EMAIL_TEMPLATE_NAME, emailTemplateName);
				}
			}catch(EntityNotFoundException entityNotFoundException){
				throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new EmailTemplateNotFoundException("GE_1014", ChatOnWebConstants.EMAIL_TEMPLATE_NAME, emailTemplateName);
		}
	}

	@Override
	public void saveBySubject(String emailTemplateSubject, String userId)
			throws EmailTemplateNotFoundException, UserNotFoundException,EmailNotificationTemplateAlreadyExistException {
		try{
			final EmailTemplate emailTemplate=emailTemplateRepository.findBySubject(emailTemplateSubject);
			try{
				final User user=userRepository.selectById(userId);
				if(!saveByEmailTemplateAndUser(emailTemplate, user)){
					throw new EmailNotificationTemplateAlreadyExistException("GE_1015", ChatOnWebConstants.EMAIL_TEMPLATE_SUBJECT, emailTemplateSubject);
				}
			}catch(EntityNotFoundException entityNotFoundException){
				throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new EmailTemplateNotFoundException("GE_1014", ChatOnWebConstants.EMAIL_TEMPLATE_SUBJECT, emailTemplateSubject);
		}
	}
	
	private boolean saveByEmailTemplateAndUser(EmailTemplate emailTemplate,User user)throws EmailNotificationTemplateAlreadyExistException{
		if(emailNotificationTemplateRepository.checkUnique(emailTemplate, user)){
			final EmailNotificationTemplate emailNotificationTemplate=new EmailNotificationTemplate();
			emailNotificationTemplate.setActive(true);
			emailNotificationTemplate.setCreateTimestamp(LocalDateTime.now());
			emailNotificationTemplate.setUpdateTimestamp(LocalDateTime.now());
			emailNotificationTemplate.setUser(user);
			emailNotificationTemplate.setEmailTemplate(emailTemplate);
			emailNotificationTemplateRepository.persist(emailNotificationTemplate);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public EmailNotificationTemplateModel getById(String id) throws EmailNotificationTemplateNotFoundException {
		try{
			final EmailNotificationTemplate emailNotificationTemplate=emailNotificationTemplateRepository.findById(id);
			final EmailTemplate emailTemplate=emailNotificationTemplate.getEmailTemplate();
			final EmailTemplateModel emailTemplateModel=new EmailTemplateModel(emailTemplate.getId(), emailTemplate.getName(), emailTemplate.getSubject(), emailTemplate.getEmailFrom(), emailTemplate.getBody(), emailTemplate.getCreateTimestamp(), emailTemplate.getUpdateTimestamp());
			final EmailNotificationTemplateModel emailNotificationTemplateModel=new EmailNotificationTemplateModel(emailNotificationTemplate.getId(), emailNotificationTemplate.getUser().getId(), emailTemplateModel, emailNotificationTemplate.isActive(), emailNotificationTemplate.getCreateTimestamp(), emailNotificationTemplate.getUpdateTimestamp());
			return emailNotificationTemplateModel;
		}catch(EntityNotFoundException entityNotFoundException){
			throw new EmailNotificationTemplateNotFoundException("GE_1014", ChatOnWebConstants.EMAIL_NOTIFICATION_TEMPLATE_ID, id);
		}
	}
	
	
	@Override
	public Set<EmailNotificationTemplateModel> getByUserId(String userId) throws UserNotFoundException, EmailNotificationTemplateNotFoundException {
		try{
			final User user=userRepository.selectById(userId);
			try{
				final List<EmailNotificationTemplate> emailNotificationTemplates=emailNotificationTemplateRepository.findByUser(user);
				return getEmailNotificationTemplateModels(emailNotificationTemplates);
			}catch(EntityNotFoundException entityNotFoundException){
				throw new EmailNotificationTemplateNotFoundException("GE_1014", ChatOnWebConstants.USER_ID, userId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
		}
	}

	@Override
	public void removeById(String id) throws EmailNotificationTemplateNotFoundException {
		try{
			emailNotificationTemplateRepository.deleteById(id);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new EmailNotificationTemplateNotFoundException("GE_1014", ChatOnWebConstants.EMAIL_NOTIFICATION_TEMPLATE_ID, id);
		}
	}

	@Override
	public void removeByUserId(String userId) throws UserNotFoundException, EmailNotificationTemplateNotFoundException {
		try{
			final User user=userRepository.selectById(userId);
			try{
				emailNotificationTemplateRepository.deleteByUser(user);
			}catch(EntityNotFoundException entityNotFoundException){
				throw new EmailNotificationTemplateNotFoundException("GE_1014", ChatOnWebConstants.USER_ID,userId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
		}
	}


	@Override
	public boolean isActive(String id) throws EmailNotificationTemplateNotFoundException {
		try {
			return emailNotificationTemplateRepository.isActive(id);
		} catch (EntityNotFoundException e) {
			throw new EmailNotificationTemplateNotFoundException("GE_1014", ChatOnWebConstants.EMAIL_NOTIFICATION_TEMPLATE_ID, id);
		}
	}

	@Override
	public void setActiveById(String id, boolean state) throws EmailNotificationTemplateNotFoundException {
		try{
			emailNotificationTemplateRepository.setActiveById(id, state);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new EmailNotificationTemplateNotFoundException("GE_1014", ChatOnWebConstants.EMAIL_NOTIFICATION_TEMPLATE_ID, id);
		}
	}

	@Override
	public void setActiveByUserId(String userId, boolean state) throws UserNotFoundException, EmailNotificationTemplateNotFoundException {
		try{
			final User user=userRepository.selectById(userId);
			try{
				emailNotificationTemplateRepository.setActiveByUser(user, state);
			}catch(EntityNotFoundException entityNotFoundException){
				throw new EmailNotificationTemplateNotFoundException("GE_1014", ChatOnWebConstants.USER_ID,userId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
		}
	}

	
	private Set<EmailNotificationTemplateModel> getEmailNotificationTemplateModels(List<EmailNotificationTemplate> emailNotificationTemplates){
		final Set<EmailNotificationTemplateModel> emailNotificationTemplateModels=new HashSet<EmailNotificationTemplateModel>();
		for(EmailNotificationTemplate emailNotificationTemplate:emailNotificationTemplates){
			final EmailTemplate emailTemplate=emailNotificationTemplate.getEmailTemplate();
			final EmailTemplateModel emailTemplateModel=new EmailTemplateModel(emailTemplate.getId(),emailTemplate.getName() , emailTemplate.getSubject(), emailTemplate.getEmailFrom(), emailTemplate.getBody(), emailTemplate.getCreateTimestamp(), emailTemplate.getUpdateTimestamp());
			final EmailNotificationTemplateModel emailNotificationTemplateModel=new EmailNotificationTemplateModel(emailNotificationTemplate.getId(), emailNotificationTemplate.getUser().getId(), emailTemplateModel, emailNotificationTemplate.isActive(), emailNotificationTemplate.getCreateTimestamp(), emailNotificationTemplate.getUpdateTimestamp());
			emailNotificationTemplateModels.add(emailNotificationTemplateModel);
		}
		return emailNotificationTemplateModels;
	}

	@Override
	public Set<EmailNotificationTemplateModel> getByEmailTemplateId(String emailTemplateId)
			throws EmailTemplateNotFoundException, EmailNotificationTemplateNotFoundException {
		try{
			final EmailTemplate emailTemplate=emailTemplateRepository.findById(emailTemplateId);
			try{
				return getEmailNotificationTemplateModels(emailNotificationTemplateRepository.findByEmailTemplate(emailTemplate));
			}catch(EntityNotFoundException entityNotFoundException){
				throw new EmailNotificationTemplateNotFoundException("GE_1014", ChatOnWebConstants.EMAIL_TEMPLATE_ID,emailTemplateId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_ID, emailTemplateId);
		}
	}

	@Override
	public Set<EmailNotificationTemplateModel> getByEmailTemplateIdWithUserId(String emailTemplateId, String userId)
			throws EmailTemplateNotFoundException, UserNotFoundException, EmailNotificationTemplateNotFoundException {
		try{
			final EmailTemplate emailTemplate=emailTemplateRepository.findById(emailTemplateId);
			try{
				final User user=userRepository.selectById(userId);
				try{
					return getEmailNotificationTemplateModels(emailNotificationTemplateRepository.findByEmailTemplateWithUser(emailTemplate,user));
				}catch(EntityNotFoundException entityNotFoundException){
					throw new EmailNotificationTemplateNotFoundException("GE_1016", ChatOnWebConstants.EMAIL_TEMPLATE_ID,emailTemplateId);
				}
			}catch(EntityNotFoundException entityNotFoundException){
				throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_ID, emailTemplateId);
		}
	}

	@Override
	public Set<EmailNotificationTemplateModel> getByEmailTemplateName(String emailTemplateName)
			throws EmailTemplateNotFoundException, EmailNotificationTemplateNotFoundException {
		try{
			final EmailTemplate emailTemplate=emailTemplateRepository.findByName(emailTemplateName);
			try{
				return getEmailNotificationTemplateModels(emailNotificationTemplateRepository.findByEmailTemplate(emailTemplate));
			}catch(EntityNotFoundException entityNotFoundException){
				throw new EmailNotificationTemplateNotFoundException("GE_1014", ChatOnWebConstants.EMAIL_TEMPLATE_NAME,emailTemplateName);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_NAME, emailTemplateName);
		}
	}

	@Override
	public Set<EmailNotificationTemplateModel> getByEmailTemplateNameWithUserId(String emailTemplateName, String userId)
			throws EmailTemplateNotFoundException, UserNotFoundException, EmailNotificationTemplateNotFoundException {
		try{
			final EmailTemplate emailTemplate=emailTemplateRepository.findByName(emailTemplateName);
			try{
				final User user=userRepository.selectById(userId);
				try{
					return getEmailNotificationTemplateModels(emailNotificationTemplateRepository.findByEmailTemplateWithUser(emailTemplate,user));
				}catch(EntityNotFoundException entityNotFoundException){
					throw new EmailNotificationTemplateNotFoundException("GE_1016", ChatOnWebConstants.EMAIL_TEMPLATE_NAME,emailTemplateName);
				}
			}catch(EntityNotFoundException entityNotFoundException){
				throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_NAME, emailTemplateName);
		}			
	}

	@Override
	public void removeByEmailTemplateId(String emailTemplateId) throws EmailTemplateNotFoundException, EmailNotificationTemplateNotFoundException {
		try{
			final EmailTemplate emailTemplate=emailTemplateRepository.findById(emailTemplateId);
			try{
				emailNotificationTemplateRepository.deleteByEmailTemplate(emailTemplate);
			}catch(EntityNotFoundException entityNotFoundException){
				throw new EmailNotificationTemplateNotFoundException("GE_1014", ChatOnWebConstants.EMAIL_TEMPLATE_ID, emailTemplateId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_ID, emailTemplateId);
		}
	}

	@Override
	public void removeByEmailTemplateIdWithUserId(String emailTemplateId, String userId)
			throws EmailTemplateNotFoundException,UserNotFoundException, EmailNotificationTemplateNotFoundException {
		try{
			final EmailTemplate emailTemplate=emailTemplateRepository.findById(emailTemplateId);
			try{
				final User user=userRepository.selectById(userId);
				try{
					emailNotificationTemplateRepository.deleteByEmailTemplateWithUser(emailTemplate,user);
				}catch(EntityNotFoundException entityNotFoundException){
					throw new EmailNotificationTemplateNotFoundException("GE_1016", ChatOnWebConstants.EMAIL_TEMPLATE_ID, emailTemplateId);
				}
			}catch(EntityNotFoundException entityNotFoundException){
				throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_ID, emailTemplateId);
		}
	}

	@Override
	public void removeByEmailTemplateName(String emailTemplateName) throws EmailTemplateNotFoundException, EmailNotificationTemplateNotFoundException {
		try{
			final EmailTemplate emailTemplate=emailTemplateRepository.findByName(emailTemplateName);
			try{
				emailNotificationTemplateRepository.deleteByEmailTemplate(emailTemplate);
			}catch(EntityNotFoundException entityNotFoundException){
				throw new EmailNotificationTemplateNotFoundException("GE_1014", ChatOnWebConstants.EMAIL_TEMPLATE_NAME, emailTemplateName);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_NAME, emailTemplateName);
		}
	}

	@Override
	public void removeByEmailTemplateNameWithUserId(String emailTemplateName, String userId)
			throws EmailTemplateNotFoundException,UserNotFoundException, EmailNotificationTemplateNotFoundException {
		try{
			final EmailTemplate emailTemplate=emailTemplateRepository.findByName(emailTemplateName);
			try{
				final User user=userRepository.selectById(userId);
				try{
					emailNotificationTemplateRepository.deleteByEmailTemplateWithUser(emailTemplate,user);
				}catch(EntityNotFoundException entityNotFoundException){
					throw new EmailNotificationTemplateNotFoundException("GE_1016", ChatOnWebConstants.EMAIL_TEMPLATE_NAME, emailTemplateName);
				}
			}catch(EntityNotFoundException entityNotFoundException){
				throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_NAME, emailTemplateName);
		}
	}

	@Override
	public Set<EmailNotificationTemplateModel> getByAll() {
		return getEmailNotificationTemplateModels(emailNotificationTemplateRepository.findAll());
	}

	@Override
	public void setActiveByEmailTemplateId(String emailTemplateId, boolean state)
			throws EmailTemplateNotFoundException, EmailNotificationTemplateNotFoundException {
		try{
			final EmailTemplate emailTemplate=emailTemplateRepository.findById(emailTemplateId);
			try{
				emailNotificationTemplateRepository.setActiveByEmailTemplate(emailTemplate,state);
			}catch(EntityNotFoundException entityNotFoundException){
				throw new EmailNotificationTemplateNotFoundException("GE_1014", ChatOnWebConstants.EMAIL_TEMPLATE_ID, emailTemplateId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_ID, emailTemplateId);
		}
	}

	@Override
	public void setActiveByEmailTemplateName(String emailTemplateName, boolean state)
			throws EmailTemplateNotFoundException, EmailNotificationTemplateNotFoundException {
		try{
			final EmailTemplate emailTemplate=emailTemplateRepository.findByName(emailTemplateName);
			try{
				emailNotificationTemplateRepository.setActiveByEmailTemplate(emailTemplate,state);
			}catch(EntityNotFoundException entityNotFoundException){
				throw new EmailNotificationTemplateNotFoundException("GE_1014", ChatOnWebConstants.EMAIL_TEMPLATE_NAME, emailTemplateName);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_NAME, emailTemplateName);
		}
	}

	@Override
	public void setActiveByEmailTemplateIdWithUserId(String emailTemplateId, String userId, boolean state)
			throws EmailTemplateNotFoundException, UserNotFoundException, EmailNotificationTemplateNotFoundException {
		try{
			final EmailTemplate emailTemplate=emailTemplateRepository.findById(emailTemplateId);
			try{
				final User user=userRepository.selectById(userId);
				try{
					emailNotificationTemplateRepository.setActiveByEmailTemplateWithUser(emailTemplate,user,state);
				}catch(EntityNotFoundException entityNotFoundException){
					throw new EmailNotificationTemplateNotFoundException("GE_1016", ChatOnWebConstants.EMAIL_TEMPLATE_ID, emailTemplateId);
				}
			}catch(EntityNotFoundException entityNotFoundException){
				throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_ID, emailTemplateId);
		}
	}

	@Override
	public void setActiveByEmailTemplateNameWithUserId(String emailTemplateName, String userId, boolean state)
			throws EmailTemplateNotFoundException, UserNotFoundException, EmailNotificationTemplateNotFoundException {
		try{
			final EmailTemplate emailTemplate=emailTemplateRepository.findByName(emailTemplateName);
			try{
				final User user=userRepository.selectById(userId);
				try{
					emailNotificationTemplateRepository.setActiveByEmailTemplateWithUser(emailTemplate,user,state);
				}catch(EntityNotFoundException entityNotFoundException){
					throw new EmailNotificationTemplateNotFoundException("GE_1016", ChatOnWebConstants.EMAIL_TEMPLATE_NAME, emailTemplateName);
				}
			}catch(EntityNotFoundException entityNotFoundException){
				throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new EmailTemplateNotFoundException("GE_1010", ChatOnWebConstants.EMAIL_TEMPLATE_NAME, emailTemplateName);
		}
	}
}