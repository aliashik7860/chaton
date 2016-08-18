
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

import com.appdevsolutions.chat.common.model.AudioType;

@Entity
@Table(name="audio")
@NamedQueries({
	@NamedQuery(name="Audio.findAllIn",query="select a from Audio a where a.receiverFlag=true"),
	@NamedQuery(name="Audio.findAllInByContentType",query="select a from Audio a where a.contentType= :contentType and a.receiverFlag=true"),
	@NamedQuery(name="Audio.findInById",query="select a from Audio a where a.id= :id and a.receiverFlag=true"),
	@NamedQuery(name="Audio.findInByName",query="select a from Audio a where a.name= :name and a.receiverFlag=true"),
	@NamedQuery(name="Audio.findInByReceiver",query="select a from Audio a where a.receiver= :receiver and a.receiverFlag=true"),
	@NamedQuery(name="Audio.findInByReceiverWithName",query="select a from Audio a where a.receiver= :receiver and a.name= :name and a.receiverFlag=true"),
	@NamedQuery(name="Audio.findInByReceiverWithText",query="select a from Audio a where a.receiver= :receiver and a.receiverFlag=true and a.name like :name"),
	@NamedQuery(name="Audio.findInByReceiverWithTime",query="select a from Audio a where a.receiver= :receiver and a.createTimestamp= :createTimestamp and a.receiverFlag=true"),
	@NamedQuery(name="Audio.findInByReceiverWithContentType",query="select a from Audio a where a.receiver= :receiver and a.contentType= :contentType and a.receiverFlag=true"),
	@NamedQuery(name="Audio.updateInById",query="update Audio a set a.receiverFlag=false, a.updateTimestamp= current_timestamp where a.id= :id and a.receiverFlag=true"),
	@NamedQuery(name="Audio.updateInByName",query="update Audio a set a.receiverFlag=false, a.updateTimestamp= current_timestamp where a.name= :name and a.receiverFlag=true"),
	@NamedQuery(name="Audio.updateInByReceiver",query="update Audio a set a.receiverFlag=false, a.updateTimestamp= current_timestamp where a.receiver= :receiver and a.receiverFlag=true"),
	@NamedQuery(name="Audio.updateInByReceiverWithName",query="update Audio a set a.receiverFlag=false, a.updateTimestamp= current_timestamp where a.receiver= :receiver and a.name= :name and a.receiverFlag=true"),
	@NamedQuery(name="Audio.updateInByReceiverWithContentType",query="update Audio a set a.receiverFlag=false, a.updateTimestamp= current_timestamp where a.receiver= :receiver and a.contentType= :contentType and a.receiverFlag=true"),
	@NamedQuery(name="Audio.deleteInById",query="delete from Audio a where a.id= :id and a.senderFlag=false"),
	@NamedQuery(name="Audio.deleteInByName",query="delete from Audio a where a.name= :name and a.senderFlag=false"),
	@NamedQuery(name="Audio.deleteInByReceiver",query="delete from Audio a where a.receiver= :receiver and a.senderFlag=false"),
	@NamedQuery(name="Audio.deleteInByReceiverWithName",query="delete from Audio a where a.receiver= :receiver and a.name= :name and a.senderFlag=false"),
	@NamedQuery(name="Audio.deleteInByReceiverWithContentType",query="delete from Audio a where a.receiver= :receiver and a.contentType= :contentType and a.senderFlag=false"),
	@NamedQuery(name="Audio.findAllOut",query="select a from Audio a where a.senderFlag=true"),
	@NamedQuery(name="Audio.findAllOutByContentType",query="select a from Audio a where a.contentType= :contentType and a.senderFlag=true"),
	@NamedQuery(name="Audio.findOutById",query="select a from Audio a where a.id= :id and a.senderFlag=true"),
	@NamedQuery(name="Audio.findOutByName",query="select a from Audio a where a.name= :name and a.senderFlag=true"),
	@NamedQuery(name="Audio.findOutBySender",query="select a from Audio a where a.sender= :sender and a.senderFlag=true"),
	@NamedQuery(name="Audio.findOutBySenderWithName",query="select a from Audio a where a.sender= :sender and a.name= :name and a.senderFlag=true"),
	@NamedQuery(name="Audio.findOutBySenderWithText",query="select a from Audio a where a.sender= :sender and a.senderFlag=true and a.name like :name"),
	@NamedQuery(name="Audio.findOutBySenderWithTime",query="select a from Audio a where a.sender= :sender and a.createTimestamp= :createTimestamp and a.senderFlag=true"),
	@NamedQuery(name="Audio.findOutBySenderWithContentType",query="select a from Audio a where a.sender= :sender and a.contentType= :contentType and a.senderFlag=true"),
	@NamedQuery(name="Audio.updateOutById",query="update Audio a set a.senderFlag=false, a.updateTimestamp= current_timestamp where a.id= :id and a.senderFlag=true"),
	@NamedQuery(name="Audio.updateOutByName",query="update Audio a set a.senderFlag=false, a.updateTimestamp= current_timestamp where a.name= :name and a.senderFlag=true"),
	@NamedQuery(name="Audio.updateOutBySender",query="update Audio a set a.senderFlag=false, a.updateTimestamp= current_timestamp where a.sender= :sender and a.senderFlag=true"),
	@NamedQuery(name="Audio.updateOutBySenderWithName",query="update Audio a set a.senderFlag=false, a.updateTimestamp= current_timestamp where a.sender= :sender and a.name= :name and a.senderFlag=true"),
	@NamedQuery(name="Audio.updateOutBySenderWithContentType",query="update Audio a set a.senderFlag=false, a.updateTimestamp= current_timestamp where a.sender= :sender and a.contentType= :contentType and a.senderFlag=true"),
	@NamedQuery(name="Audio.deleteOutById",query="delete from Audio a where a.id= :id and a.receiverFlag=false"),
	@NamedQuery(name="Audio.deleteOutByName",query="delete from Audio a where a.name= :name and a.receiverFlag=false"),
	@NamedQuery(name="Audio.deleteOutBySender",query="delete from Audio a where a.sender= :sender and a.receiverFlag=false"),
	@NamedQuery(name="Audio.deleteOutBySenderWithName",query="delete from Audio a where a.sender= :sender and a.name= :name and a.receiverFlag=false"),
	@NamedQuery(name="Audio.deleteOutBySenderWithContentType",query="delete from Audio a where a.sender= :sender and a.contentType= :contentType and a.receiverFlag=false")
})
public class Audio {
	
	@Id
	@GenericGenerator(name="seq_audio_id", strategy="com.appdevsolutions.chat.dao.generator.AudioIdGenerator")
	@GeneratedValue(generator="seq_audio_id")
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
    private AudioType contentType;
	
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
	public void setContentType(AudioType contentType) {
		this.contentType = contentType;
	}
	public AudioType getContentType() {
		return contentType;
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