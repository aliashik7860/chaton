package com.appdevsolutions.chat.dao.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="email_notification_template")
@NamedQueries({
	@NamedQuery(name="EmailNotificationTemplate.findAll",query="select ent from EmailNotificationTemplate ent"),
	@NamedQuery(name="EmailNotificationTemplate.findById",query="select ent from EmailNotificationTemplate ent where ent.id= :id"),
	@NamedQuery(name="EmailNotificationTemplate.findByUser",query="select ent from EmailNotificationTemplate ent where ent.user= :user"),
	@NamedQuery(name="EmailNotificationTemplate.findByEmailTemplate",query="select ent from EmailNotificationTemplate ent where ent.emailTemplate= :emailTemplate"),
	@NamedQuery(name="EmailNotificationTemplate.findByEmailTemplateWithUser",query="select ent from EmailNotificationTemplate ent where ent.emailTemplate= :emailTemplate and ent.user= :user"),
	@NamedQuery(name="EmailNotificationTemplate.deleteById",query="delete from EmailNotificationTemplate ent where ent.id= :id"),
	@NamedQuery(name="EmailNotificationTemplate.deleteByUser",query="delete from EmailNotificationTemplate ent where ent.user= :user"),
	@NamedQuery(name="EmailNotificationTemplate.deleteByEmailTemplate",query="delete from EmailNotificationTemplate ent where ent.emailTemplate= :emailTemplate"),
	@NamedQuery(name="EmailNotificationTemplate.deleteByEmailTemplateWithUser",query="delete from EmailNotificationTemplate ent where ent.emailTemplate= :emailTemplate and ent.user= :user"),
	@NamedQuery(name="EmailNotificationTemplate.isActive",query="select ent from EmailNotificationTemplate ent where ent.id= :id"),
	@NamedQuery(name="EmailNotificationTemplate.setActiveById",query="update EmailNotificationTemplate ent set ent.active= :active where ent.id= :id"),
	@NamedQuery(name="EmailNotificationTemplate.setActiveByUser",query="update EmailNotificationTemplate ent set ent.active= :active where ent.user= :user"),
	@NamedQuery(name="EmailNotificationTemplate.setActiveByEmailTemplate",query="update EmailNotificationTemplate ent set ent.active= :active where ent.emailTemplate= :emailTemplate"),
	@NamedQuery(name="EmailNotificationTemplate.setActiveByEmailTemplateWithUser",query="update EmailNotificationTemplate ent set ent.active= :active where ent.emailTemplate= :emailTemplate and ent.user= :user"),
	@NamedQuery(name="EmailNotificationTemplate.checkUnique",query="select ent from EmailNotificationTemplate ent where ent.emailTemplate= :emailTemplate and ent.user= :user")
})
public class EmailNotificationTemplate implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public EmailNotificationTemplate() {
	}
	@Id
	@GenericGenerator(name="seq_email_notification_template_id", strategy="com.appdevsolutions.chat.dao.generator.EmailNotificationTemplateIdGenerator")
	@GeneratedValue(generator="seq_email_notification_template_id")
	@Basic(fetch=FetchType.LAZY)
	@Column(name="id",updatable=false,unique=true,nullable=false)
	private String id;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "user_id")
    private User user;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "email_template_id")
    private EmailTemplate emailTemplate;
	
	@Column(name="active")
	private boolean active;
	
	@Column(name="create_timestamp")
	private LocalDateTime createTimestamp;
	
	@Column(name="update_timestamp")
	private LocalDateTime updateTimestamp;
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	public void setEmailTemplate(EmailTemplate emailTemplate) {
		this.emailTemplate = emailTemplate;
	}
	public EmailTemplate getEmailTemplate() {
		return emailTemplate;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isActive() {
		return active;
	}
	public void setCreateTimestamp(LocalDateTime createTimestamp) {
		this.createTimestamp = createTimestamp;
	}
	public LocalDateTime getCreateTimestamp() {
		return createTimestamp;
	}
	public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}
	public LocalDateTime getUpdateTimestamp() {
		return updateTimestamp;
	}
}