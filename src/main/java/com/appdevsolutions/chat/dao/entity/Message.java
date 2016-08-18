package com.appdevsolutions.chat.dao.entity;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Table(name="message")
@NamedQueries({
	@NamedQuery(name="Message.findAllIn",query="select im from Message im where im.receiverFlag=true"),
	@NamedQuery(name="Message.findInById",query="select im from Message im where im.id= :id and im.receiverFlag=true"),
	@NamedQuery(name="Message.findInByReceiver",query="select im from Message im where im.receiver= :receiver and im.receiverFlag=true"),
	@NamedQuery(name="Message.findInByReceiverWithText",query="select im from Message im where im.receiver= :receiver and im.receiverFlag=true and im.message like :message"),
	@NamedQuery(name="Message.findInByReceiverWithTime",query="select im from Message im where im.receiver= :receiver and im.createTimestamp= :createTimestamp and im.receiverFlag=true"),
	@NamedQuery(name="Message.updateInById",query="update Message im set im.receiverFlag=false, im.updateTimestamp=current_timestamp where im.id= :id and im.receiverFlag=true"),
	@NamedQuery(name="Message.updateInByReceiver",query="update Message im set im.receiverFlag=false, im.updateTimestamp=current_timestamp where im.receiver= :receiver and im.receiverFlag=true"),
	@NamedQuery(name="Message.deleteInById",query="delete from Message im where im.id= :id and im.senderFlag=false"),
	@NamedQuery(name="Message.deleteInByReceiver",query="delete from Message im where im.receiver= :receiver and im.senderFlag=false"),
	@NamedQuery(name="Message.findAllOut",query="select im from Message im where im.senderFlag=true"),
	@NamedQuery(name="Message.findOutById",query="select im from Message im where im.id= :id and im.senderFlag=true"),
	@NamedQuery(name="Message.findOutBySender",query="select im from Message im where im.sender= :sender and im.senderFlag=true"),
	@NamedQuery(name="Message.findOutBySenderWithText",query="select im from Message im where im.sender= :sender and im.senderFlag=true and im.message like :message"),
	@NamedQuery(name="Message.findOutBySenderWithTime",query="select im from Message im where im.sender= :sender and im.createTimestamp= :createTimestamp and im.senderFlag=true"),
	@NamedQuery(name="Message.updateOutById",query="update Message im set im.senderFlag=false, im.updateTimestamp=current_timestamp where im.id= :id and im.senderFlag=true"),
	@NamedQuery(name="Message.updateOutBySender",query="update Message im set im.senderFlag=false, im.updateTimestamp=current_timestamp where im.sender= :sender and im.senderFlag=true"),
	@NamedQuery(name="Message.deleteOutById",query="delete from Message im where im.id= :id and im.receiverFlag=false"),
	@NamedQuery(name="Message.deleteOutBySender",query="delete from Message im where im.sender= :sender and im.receiverFlag=false")	
})
public class Message {
	public Message() {
	}
	@Id
	@GenericGenerator(name="seq_message_id", strategy="com.appdevsolutions.chat.dao.generator.MessageIdGenerator")
	@GeneratedValue(generator="seq_message_id")
	@Basic(fetch=FetchType.LAZY)
	@Column(name="id",updatable=false,unique=false,nullable=false)
	private String id;
	
	@OneToOne(fetch=FetchType.EAGER,orphanRemoval=true)
	@JoinColumn(name="sender_id")
    private User sender;
	
	@OneToOne(fetch=FetchType.EAGER,orphanRemoval=true)
	@JoinColumn(name="receiver_id")
    private User receiver;
	
	private String message;
	
	@Column(name="sender_flag")
	private boolean senderFlag;
	
	@Column(name="receiver_flag")
	private boolean receiverFlag;
	
	@Column(name="create_timestamp")
	private LocalDateTime createTimestamp;
	
	@Column(name="updateTimestamp")
	private LocalDateTime updateTimestamp;
	
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}    
	
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getReceiver() {
		return receiver;
	}
	
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	public boolean isSenderFlag() {
		return senderFlag;
	}
	public void setSenderFlag(boolean senderFlag) {
		this.senderFlag = senderFlag;
	}
	public boolean isReceiverFlag() {
		return receiverFlag;
	}
	public void setReceiverFlag(boolean receiverFlag) {
		this.receiverFlag = receiverFlag;
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
	
	public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}
	public LocalDateTime getUpdateTimestamp() {
		return updateTimestamp;
	}

}