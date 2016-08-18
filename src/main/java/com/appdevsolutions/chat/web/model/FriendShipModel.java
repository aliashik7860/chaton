package com.appdevsolutions.chat.web.model;

import java.time.LocalDateTime;

import com.appdevsolutions.chat.dao.entity.BlockState;
import com.appdevsolutions.chat.dao.entity.FriendShipState;

public class FriendShipModel {
	private final String id;
	private final String senderId;
	private final String receiverId;
	private final String comment;
	private final FriendShipState friendShipState;
	private final BlockState blockState;
	private final LocalDateTime createTimestamp;
	private final LocalDateTime updateTimestamp;
	public FriendShipModel(String id,String senderId,String receiverId, String comment, FriendShipState friendShipState, BlockState blockState, LocalDateTime createTimestamp, LocalDateTime updateTimestamp) {
		this.id=id;
		this.senderId=senderId;
		this.receiverId=receiverId;
		this.comment=comment;
		this.friendShipState=friendShipState;
		this.blockState=blockState;
		this.createTimestamp=createTimestamp;
		this.updateTimestamp=updateTimestamp;
	}
	public String getId() {
		return id;
	}
	public String getSenderId() {
		return senderId;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public String getComment() {
		return comment;
	}
	public FriendShipState getFriendShipState() {
		return friendShipState;
	}
	public BlockState getBlockState() {
		return blockState;
	}
	public LocalDateTime getCreateTimestamp() {
		return createTimestamp;
	}
	public LocalDateTime getUpdateTimestamp() {
		return updateTimestamp;
	}
}