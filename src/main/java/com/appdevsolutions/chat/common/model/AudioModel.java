package com.appdevsolutions.chat.common.model;

import java.io.InputStream;
import java.time.LocalDateTime;

public final class AudioModel {
	private final String id;
	private final String senderId;
	private final String receiverId;
	private final String name;
	private final InputStream data;
	private final long size;
	private final AudioType contentType;
	private final LocalDateTime createTimestamp;
	private final LocalDateTime updateTimestamp;
	public AudioModel(final String id,final String senderId,final String receiverId, final String name,final InputStream data, final long size, final AudioType contentType, final LocalDateTime createTimestamp, LocalDateTime updateTimestamp) {
		this.id=id;
		this.senderId=senderId;
		this.receiverId=receiverId;
		this.name=name;
		this.data=data;
		this.size=size;
		this.contentType=contentType;
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
	public String getName() {
		return name;
	}
	public InputStream getData() {
		return data;
	}
	public long getSize() {
		return size;
	}
	public AudioType getContentType() {
		return contentType;
	}
	public LocalDateTime getCreateTimestamp() {
		return createTimestamp;
	}
	public LocalDateTime getUpdateTimestamp() {
		return updateTimestamp;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result + ((createTimestamp == null) ? 0 : createTimestamp.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((receiverId == null) ? 0 : receiverId.hashCode());
		result = prime * result + ((senderId == null) ? 0 : senderId.hashCode());
		result = prime * result + (int) (size ^ (size >>> 32));
		result = prime * result + ((updateTimestamp == null) ? 0 : updateTimestamp.hashCode());
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
		final AudioModel other = (AudioModel) obj;
		if (contentType != other.contentType)
			return false;
		if (createTimestamp == null) {
			if (other.createTimestamp != null)
				return false;
		} else if (!createTimestamp.equals(other.createTimestamp))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (receiverId == null) {
			if (other.receiverId != null)
				return false;
		} else if (!receiverId.equals(other.receiverId))
			return false;
		if (senderId == null) {
			if (other.senderId != null)
				return false;
		} else if (!senderId.equals(other.senderId))
			return false;
		if (size != other.size)
			return false;
		if (updateTimestamp == null) {
			if (other.updateTimestamp != null)
				return false;
		} else if (!updateTimestamp.equals(other.updateTimestamp))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AudioModel [id=" + id + ", senderId=" + senderId + ", receiverId=" + receiverId + ", name=" + name
				+ ", data=" + data + ", size=" + size + ", contentType=" + contentType + ", createTimestamp="
				+ createTimestamp + ", updateTimestamp=" + updateTimestamp + "]";
	}
	
}
