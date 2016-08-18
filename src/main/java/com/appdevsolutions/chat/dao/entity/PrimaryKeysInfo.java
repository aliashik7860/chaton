package com.appdevsolutions.chat.dao.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
	@NamedQuery(name="PrimaryKeysInfo.findAll",query="select pki from PrimaryKeysInfo pki"),
	@NamedQuery(name="PrimaryKeysInfo.findById",query="select pki from PrimaryKeysInfo pki where pki.id = :id"),
	@NamedQuery(name="PrimaryKeysInfo.findByKeyName",query="select pki from PrimaryKeysInfo pki where pki.keyName = :keyName")
})
@Table(name="primary_keys_info")
public class PrimaryKeysInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Basic(fetch=FetchType.LAZY)
	@Column(name="id",unique=true,nullable=false,updatable=false)
    private int id;
	
	@Column(length=32,name="key_name")
	private String keyName;
	
	@Column(length=16,name="key_count")
	private int keyCount;
	
	
	
	public int getId() {
		return id;
	}
	public String getKeyName() {
		return keyName;
	}
	public int getKeyCount() {
		return keyCount;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public void setKeyCount(int keyCount) {
		this.keyCount = keyCount;
	}
	
}
