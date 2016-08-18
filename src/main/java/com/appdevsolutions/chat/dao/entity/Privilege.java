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
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="privilege")
@NamedQueries({
	@NamedQuery(name="Privilege.findAll",query="select p from Privilege p"),
	@NamedQuery(name="Privilege.findByCount",query="select count(p) from Privilege p"),
	@NamedQuery(name="Privilege.findById",query="select p from Privilege p where p.id= :id"),
	@NamedQuery(name="Privilege.findByName",query="select p from Privilege p where p.name= :name"),
	@NamedQuery(name="Privilege.findByRole",query="select p from Privilege p where p.role= :role"),
	@NamedQuery(name="Privilege.deleteAll",query="delete from Privilege p"),
	@NamedQuery(name="Privilege.deleteById",query="delete from Privilege p where p.id= :id"),
	@NamedQuery(name="Privilege.deleteByName",query="delete from Privilege p where p.name= :name"),
	@NamedQuery(name="Privilege.deleteByRole",query="delete from Privilege p where p.role = :role"),
	@NamedQuery(name="Privilege.updateNameById",query="update Privilege p set p.name= :newName where p.id= :id and p.name= :oldName"),
	@NamedQuery(name="Privilege.isEntityExist",query="select count(p) from Privilege p where p.name= :name and p.role= :role"),
})
public class Privilege implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public Privilege() {
	}
	@Id
	@GenericGenerator(name="seq_privilege_id", strategy="com.appdevsolutions.chat.dao.generator.PrivilegeIdGenerator")
	@GeneratedValue(generator="seq_privilege_id")
	@Basic(fetch=FetchType.LAZY)
	@Column(name="id",unique=true,updatable=false)
	private String id;
	
	@Column(name="name")
	private String name;
	
	/*@Convert(converter=LocalDateTimeToTimestampConverter.class)*/
	@Column(name="create_timestamp")
	private LocalDateTime createTimestamp;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role role;
	
	@Basic(optional = false)
    @Column(nullable = false)
	@Version
	private int version;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public Role getRole() {
		return role;
	}
    public void setRole(Role role) {
		this.role = role;
	}
    
    public void setCreateTimestamp(LocalDateTime createTimestamp) {
		this.createTimestamp = createTimestamp;
	}
    public LocalDateTime getCreateTimestamp() {
		return createTimestamp;
	}
    
    public void setVersion(int version) {
		this.version = version;
	}
    public int getVersion() {
		return version;
	}
    
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTimestamp == null) ? 0 : createTimestamp.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		//result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Privilege other = (Privilege) obj;
		if (createTimestamp == null) {
			if (other.createTimestamp != null)
				return false;
		} else if (!createTimestamp.equals(other.createTimestamp))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		/*if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;*/
		return true;
	}
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Privilege [");
		sb.append("id:"+id);
		sb.append(",");
        sb.append("name:"+name);
        sb.append(",");
        sb.append("roles:");
        sb.append(",");
        sb.append("createTimestamp:"+createTimestamp);
        sb.append("]");
        return sb.toString();
    }
}