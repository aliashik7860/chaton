package com.appdevsolutions.chat.web.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appdevsolutions.chat.dao.entity.Message;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;
import com.appdevsolutions.chat.dao.repository.MessageRepository;
import com.appdevsolutions.chat.dao.repository.UserRepository;
import com.appdevsolutions.chat.web.exception.MessageNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.MessageModel;

@Service
public class MessageServiceImpl implements MessageService{

	//private static final Logger LOGGER=LoggerFactory.getLogger(MessageServiceImpl.class);
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public void save(MessageModel messageModel) throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(messageModel.getSenderId());
			try{
				final User receiver=userRepository.selectById(messageModel.getReceiverId());
				final Message message=new Message();
				message.setSender(sender);
				message.setReceiver(receiver);
				message.setMessage(messageModel.getMessage());
				message.setSenderFlag(true);
				message.setReceiverFlag(true);
				message.setCreateTimestamp(LocalDateTime.now());
				messageRepository.persist(message);
			}catch(EntityNotFoundException entityNotFoundException){
				throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, messageModel.getReceiverId());
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, messageModel.getSenderId());
		}
	}

	private List<MessageModel> getMessageModels(List<Message> messages){
		final List<MessageModel> messageModels=new ArrayList<MessageModel>(messages.size());
		for(Message message:messages){
			final MessageModel messageModel=new MessageModel(message.getId(), message.getSender().getId(), message.getReceiver().getId(), message.getMessage(), message.getCreateTimestamp());
			messageModels.add(messageModel);
		}
		return messageModels;
	}
	@Override
	public List<MessageModel> getAllIn() {
		return getMessageModels(messageRepository.selectAllIn());
	}

	@Override
	public MessageModel getInById(String id) throws MessageNotFoundException {
		try{
			final Message message=messageRepository.selectInById(id);
			final MessageModel messageModel=new MessageModel(message.getId(), message.getSender().getId(), message.getReceiver().getId(), message.getMessage(), message.getCreateTimestamp());
			return messageModel;
		}catch(EntityNotFoundException entityNotFoundException){
			throw new MessageNotFoundException("GE_1009", ChatOnWebConstants.MESSAGE_ID,id);
		}
	}

	@Override
	public List<MessageModel> getOutBySenderId(String senderId) throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getMessageModels(messageRepository.selectOutBySender(sender));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public List<MessageModel> getOutBySenderIdWithText(String senderId, String text) throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getMessageModels(messageRepository.selectOutBySenderWithText(sender,text));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public List<MessageModel> getOutBySenderIdWithTime(String senderId, LocalDateTime createTimestamp)
			throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getMessageModels(messageRepository.selectOutBySenderWithTime(sender,createTimestamp));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public void deleteInById(String id) throws MessageNotFoundException {
		try{
			messageRepository.deleteInById(id);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new MessageNotFoundException("GE_1009", ChatOnWebConstants.MESSAGE_ID,id);
		}
	}

	@Override
	public void deleteOutBySenderId(String senderId) throws UserNotFoundException, MessageNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			try{
				messageRepository.deleteOutBySender(sender);
			}catch(EntityNotFoundException entityNotFoundException){
				throw new MessageNotFoundException("GE_1009", ChatOnWebConstants.SENDER_ID, senderId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public List<MessageModel> getAllOut() {
		return getMessageModels(messageRepository.selectAllOut());
	}

	@Override
	public MessageModel getOutById(String id) throws MessageNotFoundException {
		try{
			final Message message=messageRepository.selectOutById(id);
			final MessageModel messageModel=new MessageModel(message.getId(), message.getSender().getId(), message.getReceiver().getId(), message.getMessage(), message.getCreateTimestamp());
			return messageModel;
		}catch(EntityNotFoundException entityNotFoundException){
			throw new MessageNotFoundException("GE_1009", ChatOnWebConstants.MESSAGE_ID,id);
		}
	}

	@Override
	public List<MessageModel> getInByReceiverId(String receiverId) throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getMessageModels(messageRepository.selectInByReceiver(receiver));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public List<MessageModel> getInByReceiverIdWithText(String receiverId, String text) throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getMessageModels(messageRepository.selectInByReceiverWithText(receiver,text));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public List<MessageModel> getInByReceiverIdWithTime(String receiverId, LocalDateTime createTimestamp)
			throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getMessageModels(messageRepository.selectInByReceiverWithTime(receiver,createTimestamp));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public void deleteOutById(String id) throws MessageNotFoundException {
		try{
			messageRepository.deleteOutById(id);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new MessageNotFoundException("GE_1009", ChatOnWebConstants.MESSAGE_ID,id);
		}
	}

	@Override
	public void deleteInByReceiverId(String receiverId) throws UserNotFoundException, MessageNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			try{
				messageRepository.deleteInByReceiver(receiver);
			}catch(EntityNotFoundException entityNotFoundException){
				throw new MessageNotFoundException("GE_1009", ChatOnWebConstants.RECEIVER_ID, receiverId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}
}