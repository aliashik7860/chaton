package com.appdevsolutions.chat.dao.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="email_template")
@NamedQueries({
	@NamedQuery(name="EmailTemplate.findById",query="select et from EmailTemplate et where et.id= :id"),
	@NamedQuery(name="EmailTemplate.findByName",query="select et from EmailTemplate et where et.name= :name"),
	@NamedQuery(name="EmailTemplate.findBySubject",query="select et from EmailTemplate et where et.subject= :subject"),
	@NamedQuery(name="EmailTemplate.findAll",query="select et from EmailTemplate et"),
	@NamedQuery(name="EmailTemplate.updateFromById",query="update EmailTemplate et set et.emailFrom= :emailFrom where et.id= :id"),
	@NamedQuery(name="EmailTemplate.updateFromByName",query="update EmailTemplate et set et.emailFrom= :emailFrom where et.name= :name"),
	@NamedQuery(name="EmailTemplate.updateFromBySubject",query="update EmailTemplate et set et.emailFrom= :emailFrom where et.subject= :subject"),
	@NamedQuery(name="EmailTemplate.updateName",query="update EmailTemplate et set et.name= :newName where et.name= :oldName"),
	@NamedQuery(name="EmailTemplate.updateSubject",query="update EmailTemplate et set et.subject= :newSubject where et.subject= :oldSubject"),
	@NamedQuery(name="EmailTemplate.updateBodyById",query="update EmailTemplate et set et.body= :body where et.id= :id"),
	@NamedQuery(name="EmailTemplate.updateBodyByName",query="update EmailTemplate et set et.body= :body where et.name= :name"),
	@NamedQuery(name="EmailTemplate.updateBodyBySubject",query="update EmailTemplate et set et.body= :body where et.subject= :subject"),
	@NamedQuery(name="EmailTemplate.deleteById",query="delete from EmailTemplate et where et.id= :id"),
	@NamedQuery(name="EmailTemplate.deleteByName",query="delete from EmailTemplate et where et.name= :name"),
	@NamedQuery(name="EmailTemplate.deleteBySubject",query="delete from EmailTemplate et where et.subject= :subject"),
})
public class EmailTemplate implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public EmailTemplate() {
	}
	@Id
	@GenericGenerator(name="seq_email_template_id", strategy="com.appdevsolutions.chat.dao.generator.EmailTemplateIdGenerator")
	@GeneratedValue(generator="seq_email_template_id")
	@Basic(fetch=FetchType.LAZY)
	@Column(name="id",updatable=false,unique=true,nullable=false)
	private String id;
	
	@Column(length=16,name="name")
	private String name;
	
	@Column(length=50,name="email_from")
	private String emailFrom;
	
	@Column(length=16,name="subject")
	private String subject;
	
	@Column(name="body")
	private String body;
	
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
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}
	public String getEmailFrom() {
		return emailFrom;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSubject() {
		return subject;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getBody() {
		return body;
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