package com.appdevsolutions.chat.dao.entity;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.appdevsolutions.chat.common.model.ImageType;

@Entity
@Table(name="profile_photo")
@NamedQueries({
	@NamedQuery(name="ProfilePhoto.findById",query="select pp from ProfilePhoto pp where pp.id= :id"),
	@NamedQuery(name="ProfilePhoto.findByCount",query="select count(pp) from ProfilePhoto pp"),
	@NamedQuery(name="ProfilePhoto.findByUser",query="select pp from ProfilePhoto pp where pp.user= :user"),
	@NamedQuery(name="ProfilePhoto.deleteById",query="delete from ProfilePhoto pp where pp.id= :id"),
	@NamedQuery(name="ProfilePhoto.deleteByUser",query="delete from ProfilePhoto pp where pp.user= :user")
})
public class ProfilePhoto {

	@Id
	@GenericGenerator(name="seq_profile_photo_id", strategy="com.appdevsolutions.chat.dao.generator.ProfilePhotoIdGenerator")
	@GeneratedValue(generator="seq_profile_photo_id")
	@Basic(fetch=FetchType.LAZY)
	@Column(name="id",unique=true,nullable=false,updatable=false)
    private String id;
	
	@OneToOne(fetch=FetchType.LAZY,orphanRemoval=true)
	@JoinColumn(name="user_id")
    private User user;
	
	@Column(name="file_name",length=100,nullable=false,updatable=false)
    private String name;
	
	@Column(name="content_type",length=20,nullable=false,updatable=false)
	@Enumerated(EnumType.STRING)
    private ImageType contentType;
	
	@Column(name="size",length=20,nullable=false,updatable=false)
	private long size;
	
	/*@Convert(converter=LocalDateTimeToTimestampConverter.class)*/
	@Column(name="create_timestamp",length=100,nullable=false,updatable=false)
	private LocalDateTime createTimestamp;
	
	@Basic(optional = false)
    @Column(nullable = false)
	@Version
	private int version;
	
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
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setSize(long size) {
		this.size = size;
	}
	public long getSize() {
		return size;
	}
	public void setContentType(ImageType contentType) {
		this.contentType = contentType;
	}
	public ImageType getContentType() {
		return contentType;
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
	
}