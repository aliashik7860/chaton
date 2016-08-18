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

import com.appdevsolutions.chat.common.model.FileType;

@Entity
@Table(name="file")
@NamedQueries({
	@NamedQuery(name="File.findAllIn",query="select f from File f where f.receiverFlag=true"),
	@NamedQuery(name="File.findAllInByContentType",query="select f from File f where f.contentType= :contentType and f.receiverFlag=true"),
	@NamedQuery(name="File.findInById",query="select f from File f where f.id= :id and f.receiverFlag=true"),
	@NamedQuery(name="File.findInByName",query="select f from File f where f.name= :name and f.receiverFlag=true"),
	@NamedQuery(name="File.findInByReceiver",query="select f from File f where f.receiver= :receiver and f.receiverFlag=true"),
	@NamedQuery(name="File.findInByReceiverWithName",query="select f from File f where f.receiver= :receiver and f.name= :name and f.receiverFlag=true"),
	@NamedQuery(name="File.findInByReceiverWithText",query="select f from File f where f.receiver= :receiver and f.receiverFlag=true and f.name like :name"),
	@NamedQuery(name="File.findInByReceiverWithTime",query="select f from File f where f.receiver= :receiver and f.createTimestamp= :createTimestamp and f.receiverFlag=true"),
	@NamedQuery(name="File.findInByReceiverWithContentType",query="select f from File f where f.receiver= :receiver and f.contentType= :contentType and f.receiverFlag=true"),
	@NamedQuery(name="File.updateInById",query="update File f set f.receiverFlag=false where f.id= :id and f.receiverFlag=true"),
	@NamedQuery(name="File.updateInByName",query="update File f set f.receiverFlag=false where f.name= :name and f.receiverFlag=true"),
	@NamedQuery(name="File.updateInByReceiver",query="update File f set f.receiverFlag=false where f.receiver= :receiver and f.receiverFlag=true"),
	@NamedQuery(name="File.updateInByReceiverWithName",query="update File f set f.receiverFlag=false where f.receiver= :receiver and f.name= :name and f.receiverFlag=true"),
	@NamedQuery(name="File.updateInByReceiverWithContentType",query="update File f set f.receiverFlag=false where f.receiver= :receiver and f.contentType= :contentType and f.receiverFlag=true"),
	@NamedQuery(name="File.deleteInById",query="delete from File f where f.id= :id and f.senderFlag=false"),
	@NamedQuery(name="File.deleteInByName",query="delete from File f where f.name= :name and f.senderFlag=false"),
	@NamedQuery(name="File.deleteInByReceiver",query="delete from File f where f.receiver= :receiver and f.senderFlag=false"),
	@NamedQuery(name="File.deleteInByReceiverWithName",query="delete from File f where f.receiver= :receiver and f.name= :name and f.senderFlag=false"),
	@NamedQuery(name="File.deleteInByReceiverWithContentType",query="delete from File f where f.receiver= :receiver and f.contentType= :contentType and f.senderFlag=false"),
	@NamedQuery(name="File.findAllOut",query="select f from File f where f.senderFlag=true"),
	@NamedQuery(name="File.findAllOutByContentType",query="select f from File f where f.contentType= :contentType and f.senderFlag=true"),
	@NamedQuery(name="File.findOutById",query="select f from File f where f.id= :id and f.senderFlag=true"),
	@NamedQuery(name="File.findOutByName",query="select f from File f where f.name= :name and f.senderFlag=true"),
	@NamedQuery(name="File.findOutBySender",query="select f from File f where f.sender= :sender and f.senderFlag=true"),
	@NamedQuery(name="File.findOutBySenderWithName",query="select f from File f where f.sender= :sender and f.name= :name and f.senderFlag=true"),
	@NamedQuery(name="File.findOutBySenderWithText",query="select f from File f where f.sender= :sender and f.senderFlag=true and f.name like :name"),
	@NamedQuery(name="File.findOutBySenderWithTime",query="select f from File f where f.sender= :sender and f.createTimestamp= :createTimestamp and f.senderFlag=true"),
	@NamedQuery(name="File.findOutBySenderWithContentType",query="select f from File f where f.sender= :sender and f.contentType= :contentType and f.senderFlag=true"),
	@NamedQuery(name="File.updateOutById",query="update File f set f.senderFlag=false where f.id= :id and f.senderFlag=true"),
	@NamedQuery(name="File.updateOutByName",query="update File f set f.senderFlag=false where f.name= :name and f.senderFlag=true"),
	@NamedQuery(name="File.updateOutBySender",query="update File f set f.senderFlag=false where f.sender= :sender and f.senderFlag=true"),
	@NamedQuery(name="File.updateOutBySenderWithName",query="update File f set f.senderFlag=false where f.sender= :sender and f.name= :name and f.senderFlag=true"),
	@NamedQuery(name="File.updateOutBySenderWithContentType",query="update File f set f.senderFlag=false where f.sender= :sender and f.contentType= :contentType and f.senderFlag=true"),
	@NamedQuery(name="File.deleteOutById",query="delete from File f where f.id= :id and f.receiverFlag=false"),
	@NamedQuery(name="File.deleteOutByName",query="delete from File f where f.name= :name and f.receiverFlag=false"),
	@NamedQuery(name="File.deleteOutBySender",query="delete from File f where f.sender= :sender and f.receiverFlag=false"),
	@NamedQuery(name="File.deleteOutBySenderWithName",query="delete from File f where f.sender= :sender and f.name= :name and f.receiverFlag=false"),
	@NamedQuery(name="File.deleteOutBySenderWithContentType",query="delete from File f where f.sender= :sender and f.contentType= :contentType and f.receiverFlag=false")
})
public class File {

	@Id
	@GenericGenerator(name="seq_file_id", strategy="com.appdevsolutions.chat.dao.generator.FileIdGenerator")
	@GeneratedValue(generator="seq_file_id")
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
    private FileType contentType;
	
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
	public void setContentType(FileType contentType) {
		this.contentType = contentType;
	}
	public FileType getContentType() {
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