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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="friend_ship")
@NamedQueries({
	@NamedQuery(name="FriendShip.findById",query="select fs from FriendShip fs where fs.id= :id"),
	@NamedQuery(name="FriendShip.findAll",query="select fs from FriendShip fs"),
	@NamedQuery(name="FriendShip.findAllUnFriends",query="select fs from FriendShip fs where fs.friendShipState= :friendShipState"),
	@NamedQuery(name="FriendShip.findAllFriends",query="select fs from FriendShip fs where fs.friendShipState= :friendShipState and fs.blockState= :blockState"),
	@NamedQuery(name="FriendShip.findAllSentRequestsByUser",query="select fs from FriendShip fs where fs.sender= :sender and fs.friendShipState= :friendShipState"),
	@NamedQuery(name="FriendShip.findAllReceivedRequestsByUser",query="select fs from FriendShip fs where fs.receiver= :receiver and fs.friendShipState= :friendShipState"),
	@NamedQuery(name="FriendShip.findAllFriendsByUser",query="select fs from FriendShip fs where (fs.sender= :sender or fs.receiver= :receiver) and fs.friendShipState= :friendShipState and fs.blockState= :blockState"),
	@NamedQuery(name="FriendShip.findAllBlockedFriends",query="select fs from FriendShip fs where fs.friendShipState= :friendShipState and fs.blockState= :blockState"),
	@NamedQuery(name="FriendShip.findAllBlockedFriendsBySender",query="select fs from FriendShip fs where fs.sender= :sender and fs.friendShipState= :friendShipState and fs.blockState= :blockState"),
	@NamedQuery(name="FriendShip.findAllBlockedFriendsByReceiver",query="select fs from FriendShip fs where fs.receiver= :receiver and fs.friendShipState= :friendShipState and fs.blockState= :blockState"),
	@NamedQuery(name="FriendShip.deleteById",query="delete from FriendShip fs where fs.id= :id"),
	@NamedQuery(name="FriendShip.deleteAllSentRequestByUser",query="delete from FriendShip fs where fs.sender= :sender and fs.friendShipState= :friendShipState"),
	@NamedQuery(name="FriendShip.deleteAllReceiverRequestByUser",query="delete from FriendShip fs where fs.receiver= :receiver and fs.friendShipState= :friendShipState"),
	@NamedQuery(name="FriendShip.updateFriendById",query="update FriendShip fs set fs.friendShipState= :friendShipState where fs.id= :id"),
	@NamedQuery(name="FriendShip.updateFriendByUser",query="update FriendShip fs set fs.friendShipState= :friendShipState where fs.receiver= :receiver")
})
public class FriendShip {
	
	@Id
	@GenericGenerator(name="seq_friend_ship_id", strategy="com.appdevsolutions.chat.dao.generator.FriendShipIdGenerator")
	@GeneratedValue(generator="seq_friend_ship_id")
	@Basic(fetch=FetchType.LAZY)
	@Column(name="id",unique=true,nullable=false,updatable=false)
    private String id;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "sender_id")
    private User sender;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "receiver_id")
    private User receiver;
	
	@Column(name="comment")
    private String comment;
	
	@Column(name="friendship_state")
	@Enumerated(EnumType.STRING)
	private FriendShipState friendShipState;
	
	@Column(name="block_state")
	private BlockState blockState;
	
	@Column(name="create_timestamp")
	private LocalDateTime createTimestamp;
	
	@Column(name="update_timestamp")
	private LocalDateTime updateTimestamp;
	
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
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getComment() {
		return comment;
	}
	public void setFriendShipState(FriendShipState friendShipState) {
		this.friendShipState = friendShipState;
	}
	public FriendShipState getFriendShipState() {
		return friendShipState;
	}
	public void setBlockState(BlockState blockState) {
		this.blockState = blockState;
	}
	public BlockState getBlockState() {
		return blockState;
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
}