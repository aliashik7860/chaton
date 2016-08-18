package com.appdevsolutions.chat.dao.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="role")
@NamedQueries({
	@NamedQuery(name="Role.findAll",query="select r from Role r"),
	@NamedQuery(name="Role.findById",query="select r from Role r where r.id= :id"),
	@NamedQuery(name="Role.findByCount",query="select count(r) from Role r"),
	@NamedQuery(name="Role.findByName",query="select r from Role r where r.name= :name"),
	@NamedQuery(name="Role.findByUser",query="select r from Role r where r.user= :user"),
	@NamedQuery(name="Role.deleteAll",query="delete from Role r"),
	@NamedQuery(name="Role.deleteById",query="delete from Role r where r.id= :id"),
	@NamedQuery(name="Role.deleteByName",query="delete from Role r where r.name= :name"),
	@NamedQuery(name="Role.deleteByUser",query="delete from Role r where r.user = :user"),
	@NamedQuery(name="Role.isEntityExist",query="select count(r) from Role r where r.name= :name and r.user= :user"),
	@NamedQuery(name="Role.updateNameById",query="update Role r set r.name= :newName where r.id= :id and r.name= :oldName")
})
public class Role implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="seq_role_id", strategy="com.appdevsolutions.chat.dao.generator.RoleIdGenerator")
	@GeneratedValue(generator="seq_role_id")
	@Basic(fetch=FetchType.LAZY)
	@Column(name="id",unique=true,updatable=false)
    private String id;
	
	@Column(name="name")
    private String name;
    
	@OneToMany(mappedBy="role",cascade={CascadeType.PERSIST,CascadeType.REMOVE},fetch=FetchType.EAGER,orphanRemoval=true)
    private Set<Privilege> privileges=new HashSet<Privilege>();
    
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "user_id")
    private User user;
    
    @Column(name="create_timestamp")
	private LocalDateTime createTimestamp;
    
    @Basic(optional = false)
    @Column(nullable = false)
	@Version
	private int version;
    
    public Role() {
    	
    }
    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public User getUser() {
		return user;
	}
    public void setUser(User user) {
		this.user = user;
	}
    
    public Set<Privilege> getPrivileges() {
		return privileges;
	}
    public void addPrivilege(Privilege privilege) {
    	privilege.setRole(this);
		privileges.add(privilege);
	}
    
    public LocalDateTime getCreateTimestamp() {
		return createTimestamp;
	}
    public void setCreateTimestamp(LocalDateTime createTimestamp) {
		this.createTimestamp = createTimestamp;
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((privileges == null) ? 0 : privileges.hashCode());
		//result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Role other = (Role) obj;
		if (createTimestamp == null) {
			if (other.createTimestamp != null)
				return false;
		} else if (!createTimestamp.equals(other.createTimestamp))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (privileges == null) {
			if (other.privileges != null)
				return false;
		} else if (!privileges.equals(other.privileges))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	@Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Role [");
        builder.append("id:"+id);
        builder.append(",");
        builder.append("name:"+name);
        builder.append("privileges:"+privileges);
        builder.append("users:"+user);
        builder.append("createTimestamp:"+createTimestamp);
        builder.append("]");
        return builder.toString();
    }
	
}