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

import com.appdevsolutions.chat.common.model.VideoType;

@Entity
@Table(name="video")
@NamedQueries({
	@NamedQuery(name="Video.findAllIn",query="select v from Video v where v.receiverFlag=true"),
	@NamedQuery(name="Video.findAllInByContentType",query="select v from Video v where v.contentType= :contentType and v.receiverFlag=true"),
	@NamedQuery(name="Video.findInById",query="select v from Video v where v.id= :id and v.receiverFlag=true"),
	@NamedQuery(name="Video.findInByName",query="select v from Video v where v.name= :name and v.receiverFlag=true"),
	@NamedQuery(name="Video.findInByReceiver",query="select v from Video v where v.receiver= :receiver and v.receiverFlag=true"),
	@NamedQuery(name="Video.findInByReceiverWithName",query="select v from Video v where v.receiver= :receiver and v.name= :name and v.receiverFlag=true"),
	@NamedQuery(name="Video.findInByReceiverWithText",query="select v from Video v where v.receiver= :receiver and v.receiverFlag=true and v.name like :name"),
	@NamedQuery(name="Video.findInByReceiverWithTime",query="select v from Video v where v.receiver= :receiver and v.createTimestamp= :createTimestamp and v.receiverFlag=true"),
	@NamedQuery(name="Video.findInByReceiverWithContentType",query="select v from Video v where v.receiver= :receiver and v.contentType= :contentType and v.receiverFlag=true"),
	@NamedQuery(name="Video.updateInById",query="update Video v set v.receiverFlag=false where v.id= :id and v.receiverFlag=true"),
	@NamedQuery(name="Video.updateInByName",query="update Video v set v.receiverFlag=false where v.name= :name and v.receiverFlag=true"),
	@NamedQuery(name="Video.updateInByReceiver",query="update Video v set v.receiverFlag=false where v.receiver= :receiver and v.receiverFlag=true"),
	@NamedQuery(name="Video.updateInByReceiverWithName",query="update Video v set v.receiverFlag=false where v.receiver= :receiver and v.name= :name and v.receiverFlag=true"),
	@NamedQuery(name="Video.updateInByReceiverWithContentType",query="update Video v set v.receiverFlag=false where v.receiver= :receiver and v.contentType= :contentType and v.receiverFlag=true"),
	@NamedQuery(name="Video.deleteInById",query="delete from Video v where v.id= :id and v.senderFlag=false"),
	@NamedQuery(name="Video.deleteInByName",query="delete from Video v where v.name= :name and v.senderFlag=false"),
	@NamedQuery(name="Video.deleteInByReceiver",query="delete from Video v where v.receiver= :receiver and v.senderFlag=false"),
	@NamedQuery(name="Video.deleteInByReceiverWithName",query="delete from Video v where v.receiver= :receiver and v.name= :name and v.senderFlag=false"),
	@NamedQuery(name="Video.deleteInByReceiverWithContentType",query="delete from Video v where v.receiver= :receiver and v.contentType= :contentType and v.senderFlag=false"),
	@NamedQuery(name="Video.findAllOut",query="select v from Video v where v.senderFlag=true"),
	@NamedQuery(name="Video.findAllOutByContentType",query="select v from Video v where v.contentType= :contentType and v.senderFlag=true"),
	@NamedQuery(name="Video.findOutById",query="select v from Video v where v.id= :id and v.senderFlag=true"),
	@NamedQuery(name="Video.findOutByName",query="select v from Video v where v.name= :name and v.senderFlag=true"),
	@NamedQuery(name="Video.findOutBySender",query="select v from Video v where v.sender= :sender and v.senderFlag=true"),
	@NamedQuery(name="Video.findOutBySenderWithName",query="select v from Video v where v.sender= :sender and v.name= :name and v.senderFlag=true"),
	@NamedQuery(name="Video.findOutBySenderWithText",query="select v from Video v where v.sender= :sender and v.senderFlag=true and v.name like :name"),
	@NamedQuery(name="Video.findOutBySenderWithTime",query="select v from Video v where v.sender= :sender and v.createTimestamp= :createTimestamp and v.senderFlag=true"),
	@NamedQuery(name="Video.findOutBySenderWithContentType",query="select v from Video v where v.sender= :sender and v.contentType= :contentType and v.senderFlag=true"),
	@NamedQuery(name="Video.updateOutById",query="update Video v set v.senderFlag=false where v.id= :id and v.senderFlag=true"),
	@NamedQuery(name="Video.updateOutByName",query="update Video v set v.senderFlag=false where v.name= :name and v.senderFlag=true"),
	@NamedQuery(name="Video.updateOutBySender",query="update Video v set v.senderFlag=false where v.sender= :sender and v.senderFlag=true"),
	@NamedQuery(name="Video.updateOutBySenderWithName",query="update Video v set v.senderFlag=false where v.sender= :sender and v.name= :name and v.senderFlag=true"),
	@NamedQuery(name="Video.updateOutBySenderWithContentType",query="update Video v set v.senderFlag=false where v.sender= :sender and v.contentType= :contentType and v.senderFlag=true"),
	@NamedQuery(name="Video.deleteOutById",query="delete from Video v where v.id= :id and v.receiverFlag=false"),
	@NamedQuery(name="Video.deleteOutByName",query="delete from Video v where v.name= :name and v.receiverFlag=false"),
	@NamedQuery(name="Video.deleteOutBySender",query="delete from Video v where v.sender= :sender and v.receiverFlag=false"),
	@NamedQuery(name="Video.deleteOutBySenderWithName",query="delete from Video v where v.sender= :sender and v.name= :name and v.receiverFlag=false"),
	@NamedQuery(name="Video.deleteOutBySenderWithContentType",query="delete from Video v where v.sender= :sender and v.contentType= :contentType and v.receiverFlag=false")
})
public class Video {
	
	@Id
	@GenericGenerator(name="seq_video_id", strategy="com.appdevsolutions.chat.dao.generator.VideoIdGenerator")
	@GeneratedValue(generator="seq_video_id")
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
	
	@Column(length=20,nullable=false,updatable=false)
	private long size;
	
	@Column(name="content_type",length=20,nullable=false,updatable=false)
	@Enumerated(EnumType.STRING)
    private VideoType contentType;
	
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
	
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	public User getReceiver() {
		return receiver;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getSender() {
		return sender;
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
	public void setContentType(VideoType contentType) {
		this.contentType = contentType;
	}
	public VideoType getContentType() {
		return contentType;
	}
	
	public void setReceiverFlag(boolean receiverFlag) {
		this.receiverFlag = receiverFlag;
	}
	public boolean isReceiverFlag() {
		return receiverFlag;
	}
	public void setSenderFlag(boolean senderFlag) {
		this.senderFlag = senderFlag;
	}
	public boolean isSenderFlag() {
		return senderFlag;
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