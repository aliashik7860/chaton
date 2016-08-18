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
@Table(name="photo")
@NamedQueries({
	@NamedQuery(name="Photo.findAllIn",query="select p from Photo p where p.receiverFlag=true"),
	@NamedQuery(name="Photo.findAllInByContentType",query="select p from Photo p where p.contentType= :contentType and p.receiverFlag=true"),
	@NamedQuery(name="Photo.findInById",query="select p from Photo p where p.id= :id and p.receiverFlag=true"),
	@NamedQuery(name="Photo.findInByName",query="select p from Photo p where p.name= :name and p.receiverFlag=true"),
	@NamedQuery(name="Photo.findInByReceiver",query="select p from Photo p where p.receiver= :receiver and p.receiverFlag=true"),
	@NamedQuery(name="Photo.findInByReceiverWithName",query="select p from Photo p where p.receiver= :receiver and p.name= :name and p.receiverFlag=true"),
	@NamedQuery(name="Photo.findInByReceiverWithText",query="select p from Photo p where p.receiver= :receiver and p.receiverFlag=true and p.name like :name"),
	@NamedQuery(name="Photo.findInByReceiverWithTime",query="select p from Photo p where p.receiver= :receiver and p.createTimestamp= :createTimestamp and p.receiverFlag=true"),
	@NamedQuery(name="Photo.findInByReceiverWithContentType",query="select p from Photo p where p.receiver= :receiver and p.contentType= :contentType and p.receiverFlag=true"),
	@NamedQuery(name="Photo.updateInById",query="update Photo p set p.receiverFlag=false where p.id= :id and p.receiverFlag=true"),
	@NamedQuery(name="Photo.updateInByName",query="update Photo p set p.receiverFlag=false where p.name= :name and p.receiverFlag=true"),
	@NamedQuery(name="Photo.updateInByReceiver",query="update Photo p set p.receiverFlag=false where p.receiver= :receiver and p.receiverFlag=true"),
	@NamedQuery(name="Photo.updateInByReceiverWithName",query="update Photo p set p.receiverFlag=false where p.receiver= :receiver and p.name= :name and p.receiverFlag=true"),
	@NamedQuery(name="Photo.updateInByReceiverWithContentType",query="update Photo p set p.receiverFlag=false where p.receiver= :receiver and p.contentType= :contentType and p.receiverFlag=true"),
	@NamedQuery(name="Photo.deleteInById",query="delete from Photo p where p.id= :id and p.senderFlag=false"),
	@NamedQuery(name="Photo.deleteInByName",query="delete from Photo p where p.name= :name and p.senderFlag=false"),
	@NamedQuery(name="Photo.deleteInByReceiver",query="delete from Photo p where p.receiver= :receiver and p.senderFlag=false"),
	@NamedQuery(name="Photo.deleteInByReceiverWithName",query="delete from Photo p where p.receiver= :receiver and p.name= :name and p.senderFlag=false"),
	@NamedQuery(name="Photo.deleteInByReceiverWithContentType",query="delete from Photo p where p.receiver= :receiver and p.contentType= :contentType and p.senderFlag=false"),
	@NamedQuery(name="Photo.findAllOut",query="select p from Photo p where p.senderFlag=true"),
	@NamedQuery(name="Photo.findAllOutByContentType",query="select p from Photo p where p.contentType= :contentType and p.senderFlag=true"),
	@NamedQuery(name="Photo.findOutById",query="select p from Photo p where p.id= :id and p.senderFlag=true"),
	@NamedQuery(name="Photo.findOutByName",query="select p from Photo p where p.name= :name and p.senderFlag=true"),
	@NamedQuery(name="Photo.findOutBySender",query="select p from Photo p where p.sender= :sender and p.senderFlag=true"),
	@NamedQuery(name="Photo.findOutBySenderWithName",query="select p from Photo p where p.sender= :sender and p.name= :name and p.senderFlag=true"),
	@NamedQuery(name="Photo.findOutBySenderWithText",query="select p from Photo p where p.sender= :sender and p.senderFlag=true and p.name like :name"),
	@NamedQuery(name="Photo.findOutBySenderWithTime",query="select p from Photo p where p.sender= :sender and p.createTimestamp= :createTimestamp and p.senderFlag=true"),
	@NamedQuery(name="Photo.findOutBySenderWithContentType",query="select p from Photo p where p.sender= :sender and p.contentType= :contentType and p.senderFlag=true"),
	@NamedQuery(name="Photo.updateOutById",query="update Photo p set p.senderFlag=false where p.id= :id and p.senderFlag=true"),
	@NamedQuery(name="Photo.updateOutByName",query="update Photo p set p.senderFlag=false where p.name= :name and p.senderFlag=true"),
	@NamedQuery(name="Photo.updateOutBySender",query="update Photo p set p.senderFlag=false where p.sender= :sender and p.senderFlag=true"),
	@NamedQuery(name="Photo.updateOutBySenderWithName",query="update Photo p set p.senderFlag=false where p.sender= :sender and p.name= :name and p.senderFlag=true"),
	@NamedQuery(name="Photo.updateOutBySenderWithContentType",query="update Photo p set p.senderFlag=false where p.sender= :sender and p.contentType= :contentType and p.senderFlag=true"),
	@NamedQuery(name="Photo.deleteOutById",query="delete from Photo p where p.id= :id and p.receiverFlag=false"),
	@NamedQuery(name="Photo.deleteOutByName",query="delete from Photo p where p.name= :name and p.receiverFlag=false"),
	@NamedQuery(name="Photo.deleteOutBySender",query="delete from Photo p where p.sender= :sender and p.receiverFlag=false"),
	@NamedQuery(name="Photo.deleteOutBySenderWithName",query="delete from Photo p where p.sender= :sender and p.name= :name and p.receiverFlag=false"),
	@NamedQuery(name="Photo.deleteOutBySenderWithContentType",query="delete from Photo p where p.sender= :sender and p.contentType= :contentType and p.receiverFlag=false")
})
public class Photo {

	@Id
	@GenericGenerator(name="seq_photo_id", strategy="com.appdevsolutions.chat.dao.generator.PhotoIdGenerator")
	@GeneratedValue(generator="seq_photo_id")
	@Basic(fetch=FetchType.LAZY)
	@Column(name="id",unique=true,nullable=false,updatable=false)
    private String id;
	
	@OneToOne(fetch=FetchType.EAGER,orphanRemoval=true)
	@JoinColumn(name="sender_id")
    private User sender;
	
	@OneToOne(fetch=FetchType.EAGER,orphanRemoval=true)
	@JoinColumn(name="receiver_id")
    private User receiver;
	
	@Column(name="file_name",length=100,nullable=false,updatable=false)
    private String name;
	
	@Column(length=255,nullable=false,unique=true)
	private String senderPath;
	
	@Column(length=255,nullable=false,unique=true)
	private String receiverPath;
	
	@Column(name="content_type",length=20,nullable=false,updatable=false)
	@Enumerated(EnumType.STRING)
    private ImageType contentType;
	
	@Column(name="size",length=20,nullable=false,updatable=false)
	private long size;
	
	@Column(name="sender_flag")
	private boolean senderFlag;
	
	@Column(name="receiver_flag")
	private boolean receiverFlag;
	
	@Column(name="create_timestamp",nullable=false,updatable=false)
	private LocalDateTime createTimestamp;
	
	@Column(name="update_timestamp",nullable=false,updatable=true)
	private LocalDateTime updateTimestamp;
	
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
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getSender() {
		return sender;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	public User getReceiver() {
		return receiver;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setSenderPath(String senderPath) {
		this.senderPath = senderPath;
	}
	public String getSenderPath() {
		return senderPath;
	}
	public void setReceiverPath(String receiverPath) {
		this.receiverPath = receiverPath;
	}
	public String getReceiverPath() {
		return receiverPath;
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
	public void setSenderFlag(boolean senderFlag) {
		this.senderFlag = senderFlag;
	}
	
	public boolean isSenderFlag() {
		return senderFlag;
	}
	public void setReceiverFlag(boolean receiverFlag) {
		this.receiverFlag = receiverFlag;
	}
	
	public boolean isReceiverFlag() {
		return receiverFlag;
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
	
	public void setVersion(int version) {
		this.version = version;
	}
	public int getVersion() {
		return version;
	}
	
}