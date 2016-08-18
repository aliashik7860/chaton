package com.appdevsolutions.chat.dao.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="user")
@NamedQueries({
	@NamedQuery(name="User.findAll",query="select u from User u"),
	@NamedQuery(name="User.findByCount",query="select count(u) from User u"),
	@NamedQuery(name="User.findByUsername",query="select u from User u where u.username= :username"),
	@NamedQuery(name="User.findById",query="select u from User u where u.id= :id"),
	@NamedQuery(name="User.findByIds",query="select u from User u where u.id in :ids"),
	@NamedQuery(name="User.findByUsernameAndPassword",query="select u from User u where u.username= :username and u.password= :password"),
	//@NamedQuery(name="User.findByName",query="select u from User u where u.name.firstName like %firstName%"),
	@NamedQuery(name="User.findByCity",query="select u from User u where u.address.city= :city"),
	@NamedQuery(name="User.findByState",query="select u from User u where u.address.state= :state"),
	@NamedQuery(name="User.findByCountry",query="select u from User u where u.address.country= :country"),
	@NamedQuery(name="User.updateLastLoginByUserId",query="update User u set u.lastLoginTimestamp= :lastLoginTimestamp where u.id= :id"),
	@NamedQuery(name="User.updateLastLoginByUsername",query="update User u set u.lastLoginTimestamp= :lastLoginTimestamp where u.username= :username"),
	@NamedQuery(name="User.deleteById",query="delete from User u where u.id= :id"),
	@NamedQuery(name="User.deleteByUsername",query="delete from User u where u.username= :username"),
})
public class User implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	public User(){
		
	}
	@Id
	@GenericGenerator(name="seq_user_id", strategy="com.appdevsolutions.chat.dao.generator.UserIdGenerator")
	@GeneratedValue(generator="seq_user_id")
	@Column(name="id",updatable=false,unique=true)
	private String id;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="firstName",column=@Column(name="first_name",length=20)),
		@AttributeOverride(name="middleName",column=@Column(name="middle_name",length=20)),
		@AttributeOverride(name="lastName",column=@Column(name="last_name",length=20))})
	private Name name;
	
	@Column(length=25,name="username",unique=true)
	private String username;
	
	@Column(length=64,name="password")
	private String password;
	
	@Column(length=1,name="is_email_verified")
	private boolean emailVerified;
	
	@Column(name="date_of_birth",updatable=false)
	private LocalDate dateOfBirth;
	
	@Column(name="create_timestamp",updatable=false)
	private LocalDateTime createTimestamp;
	
	@Column(name="password_change_timestamp")
	private LocalDateTime passwordChangeTimestamp;
	
	@Column(name="last_login_timestamp")
	private LocalDateTime lastLoginTimestamp;
	
	@Column(length=10,name="mobile_number")
	private String mobileNumber;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="houseNumber",column=@Column(name="house_number")),
		@AttributeOverride(name="street",column=@Column(name="street")),
		@AttributeOverride(name="city",column=@Column(name="city")),
		@AttributeOverride(name="state",column=@Column(name="state")),
		@AttributeOverride(name="country",column=@Column(name="country"))})
	private Address address;
	
	@OneToMany(mappedBy="user",cascade={CascadeType.PERSIST,CascadeType.REMOVE},fetch=FetchType.EAGER,orphanRemoval=true)
    private Set<Role> roles=new HashSet<Role>();
	
	/*@OneToOne(mappedBy="user",cascade={CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval=true,fetch=FetchType.EAGER)
	private ProfilePhoto profilePhoto;*/
	
	@Column(name="status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    
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

	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDateTime getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(LocalDateTime createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public LocalDateTime getPasswordChangeTimestamp() {
		return passwordChangeTimestamp;
	}

	public void setPasswordChangeTimestamp(LocalDateTime passwordChangeTimestamp) {
		this.passwordChangeTimestamp = passwordChangeTimestamp;
	}

	public LocalDateTime getLastLoginTimestamp() {
		return lastLoginTimestamp;
	}

	public void setLastLoginTimestamp(LocalDateTime lastLoginTimestamp) {
		this.lastLoginTimestamp = lastLoginTimestamp;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void addRole(Role role) {
		role.setUser(this);
		roles.add(role);
	}

	public UserStatus getStatus() {
		return status;
	}
	public void setStatus(UserStatus status) {
		this.status = status;
	}
	
	/*public void setProfilePhoto(ProfilePhoto profilePhoto) {
		this.profilePhoto = profilePhoto;
	}
	public ProfilePhoto getProfilePhoto() {
		return profilePhoto;
	}*/
	
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
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((createTimestamp == null) ? 0 : createTimestamp.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + (emailVerified ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastLoginTimestamp == null) ? 0 : lastLoginTimestamp.hashCode());
		result = prime * result + ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((passwordChangeTimestamp == null) ? 0 : passwordChangeTimestamp.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof User)) {
			return false;
		}
		User user=(User)obj;
		if(id.equals(user.id)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		/*builder.append("User [id=");
		builder.append(id);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", rePassword=");
		builder.append(rePassword);
		builder.append(", emailVerified=");
		builder.append(emailVerified);
		builder.append(", dateOfBirth=");
		builder.append(dateOfBirth);
		builder.append(", createTimestamp=");
		builder.append(createTimestamp);
		builder.append(", passwordChangeTimestamp=");
		builder.append(passwordChangeTimestamp);
		builder.append(", lastLoginTimestamp=");
		builder.append(lastLoginTimestamp);
		builder.append(", mobileNumber=");
		builder.append(mobileNumber);
		builder.append(", captchaText=");
		builder.append(captchaText);
		builder.append(", generatedCaptcha=");
		builder.append(generatedCaptcha);
		builder.append(", houseNumber=");
		builder.append(houseNumber);
		builder.append(", street=");
		builder.append(street);
		builder.append(", city=");
		builder.append(city);
		builder.append(", state=");
		builder.append(state);
		builder.append(", country=");
		builder.append(country);
		builder.append(", roles=");
		builder.append(roles);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");*/
		return builder.toString();
	}
	
}