package com.appdevsolutions.chat.dao.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appdevsolutions.chat.common.model.FileType;
import com.appdevsolutions.chat.dao.entity.File;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

@Repository
public class FileRepositoryImpl implements FileRepository{

	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(FileRepositoryImpl.class);
	
	
	@Override
	public void persist(File file) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(file);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public List<File> selectAllIn() {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<File> typedQuery=entityManager.createNamedQuery("File.findAllIn",File.class);
		final List<File> files= typedQuery.getResultList();
		entityManager.close();
		return files;
	}

	@Override
	public File selectInById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<File> typedQuery=entityManager.createNamedQuery("File.findInById",File.class);
		typedQuery.setParameter("id", id);
		try{
			final File file= typedQuery.getSingleResult();
			entityManager.close();
			return file;
		}catch(NoResultException noResultException){
			throw new EntityNotFoundException("File Entity not found with id : "+id);
		}
	}

	@Override
	public List<File> selectInByReceiver(User receiver) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<File> typedQuery=entityManager.createNamedQuery("File.findInByReceiver",File.class);
		typedQuery.setParameter("receiver", receiver);
		final List<File> files= typedQuery.getResultList();
		entityManager.close();
		return files;
	}

	@Override
	public List<File> selectInByReceiverWithText(User receiver, String text) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<File> typedQuery=entityManager.createNamedQuery("File.findInByReceiverWithText",File.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("name", "%"+text+"%");
		final List<File> files= typedQuery.getResultList();
		entityManager.close();
		return files;
	}

	@Override
	public List<File> selectInByReceiverWithTime(User receiver, LocalDateTime createTimestamp) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<File> typedQuery=entityManager.createNamedQuery("File.findInByReceiverWithTime",File.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("createTimestamp", createTimestamp);
		final List<File> files= typedQuery.getResultList();
		entityManager.close();
		return files;
	}

	@Override
	public void deleteInById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.deleteInById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			LOGGER.info("deleting file with id : "+id+" with status : "+status);
			throw new EntityNotFoundException("Entity not found with id : "+id);
		}
	}

	@Override
	public void deleteInByReceiver(User receiver) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.deleteInByReceiver");
		query.setParameter("receiver", receiver);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Entity not found with receiverId : "+receiver.getId());
		}
	}

	@Override
	public List<File> selectAllOut() {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<File> typedQuery=entityManager.createNamedQuery("File.findAllOut",File.class);
		final List<File> files= typedQuery.getResultList();
		entityManager.close();
		return files;
	}

	@Override
	public File selectOutById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<File> typedQuery=entityManager.createNamedQuery("File.findOutById",File.class);
		typedQuery.setParameter("id", id);
		try{
			final File file=typedQuery.getSingleResult();
			entityManager.close();
			return file;
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("File Entity not found with id : "+id);
		}
	}

	@Override
	public List<File> selectOutBySender(User sender) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<File> typedQuery=entityManager.createNamedQuery("File.findOutBySender",File.class);
		typedQuery.setParameter("sender", sender);
		final List<File> files= typedQuery.getResultList();
		entityManager.close();
		return files;
	}

	@Override
	public List<File> selectOutBySenderWithText(User sender, String text) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<File> typedQuery=entityManager.createNamedQuery("File.findOutBySenderWithText",File.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("name", "%"+text+"%");
		final List<File> files=typedQuery.getResultList();
		entityManager.close();
		return files;
	}

	@Override
	public List<File> selectOutBySenderWithTime(User sender, LocalDateTime createTimestamp) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<File> typedQuery=entityManager.createNamedQuery("File.findOutBySenderWithTime",File.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("createTimestamp", createTimestamp);
		final List<File> files=typedQuery.getResultList();
		entityManager.close();
		return files;
	}

	@Override
	public void deleteOutById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.deleteOutById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Entity not found with id : "+id);
		}
	}

	@Override
	public void deleteOutBySender(User sender) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.deleteOutBySender");
		query.setParameter("sender", sender);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Entity not found with senderId : "+sender.getId());
		}
	}
	
	@Override
	public void updateInById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.updateInById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("File Entity not found with id : "+id);
		}
	}

	@Override
	public void updateOutBySender(User sender) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.updateOutBySender");
		query.setParameter("sender", sender);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("File Entity not found with senderId : "+sender.getId());
		}
	}
	
	@Override
	public void updateOutById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.updateOutById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("File Entity not found with id : "+id);
		}
	}

	@Override
	public void updateInByReceiver(User receiver) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.updateInByReceiver");
		query.setParameter("receiver", receiver);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("File Entity not found with receiverId : "+receiver.getId());
		}
	}
	
	@Override
	public void updateInByReceiverWithContentType(User receiver,FileType contentType) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.updateInByReceiverWithContentType");
		query.setParameter("receiver", receiver);
		query.setParameter("contentType", contentType);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("File Entity not found with receiverId : "+receiver.getId());
		}
	}
	
	@Override
	public void updateOutBySenderWithContentType(User sender,FileType contentType) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.updateOutBySenderWithContentType");
		query.setParameter("sender", sender);
		query.setParameter("contentType", contentType);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("File Entity not found with receiverId : "+sender.getId());
		}
	}
	
	@Override
	public void updateInByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.updateInByName");
		query.setParameter("name", name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("File Entity not found with name : "+name);
		}
	}
	@Override
	public void updateOutByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.updateOutByName");
		query.setParameter("name", name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("File Entity not found with name : "+name);
		}
	}
	
	@Override
	public void updateInByReceiverWithName(User receiver,String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.updateInByReceiverWithName");
		query.setParameter("receiver", receiver);
		query.setParameter("name",name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("File Entity not found with receiverId : "+receiver.getId());
		}
	}
	
	@Override
	public void updateOutBySenderWithName(User sender,String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.updateOutBySenderWithName");
		query.setParameter("sender",sender);
		query.setParameter("name",name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("File Entity not found with receiverId : "+sender.getId());
		}
	}

	@Override
	public List<File> selectAllInWithContentType(FileType contentType) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<File> typedQuery=entityManager.createNamedQuery("File.findAllInWithContentType",File.class);
		typedQuery.setParameter("contentType", contentType);
		final List<File> files= typedQuery.getResultList();
		entityManager.close();
		return files;
	}

	@Override
	public List<File> selectInByReceiverWithContentType(User receiver, FileType contentType) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<File> typedQuery=entityManager.createNamedQuery("File.findInByReceiverWithContentType",File.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("contentType",contentType);
		final List<File> files= typedQuery.getResultList();
		entityManager.close();
		return files;
	}

	@Override
	public void deleteInByReceiverWithContentType(User receiver, FileType contentType)  throws EntityNotFoundException{
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.deleteInByReceiverWithContentType");
		query.setParameter("receiver", receiver);
		query.setParameter("contentType", contentType);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Entity not found with receiverId : "+receiver.getId()+" contentType : "+contentType);
		}
	}

	@Override
	public List<File> selectAllOutWithContentType(FileType contentType) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<File> typedQuery=entityManager.createNamedQuery("File.findAllOutWithContentType",File.class);
		typedQuery.setParameter("contentType", contentType);
		final List<File> files= typedQuery.getResultList();
		entityManager.close();
		return files;
	}

	@Override
	public List<File> selectOutBySenderWithContentType(User sender, FileType contentType) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<File> typedQuery=entityManager.createNamedQuery("File.findOutBySenderWithContentType",File.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("contentType",contentType);
		final List<File> files= typedQuery.getResultList();
		entityManager.close();
		return files;
	}

	@Override
	public void deleteOutBySenderWithContentType(User sender, FileType contentType) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.deleteOutBySenderWithContentType");
		query.setParameter("sender", sender);
		query.setParameter("contentType", contentType);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Entity not found with senderId : "+sender.getId()+" contentType : "+contentType);
		}
	}

	@Override
	public File selectInByReceiverWithName(User receiver, String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<File> typedQuery=entityManager.createNamedQuery("File.findInByReceiverWithName",File.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("name", name);
		final File file= typedQuery.getSingleResult();
		entityManager.close();
		return file;
	}

	@Override
	public List<File> selectInByName(String name){
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<File> typedQuery=entityManager.createNamedQuery("File.findInByName",File.class);
		typedQuery.setParameter("name", name);
		final List<File> files= typedQuery.getResultList();
		entityManager.close();
		return files;
	}

	@Override
	public void deleteInByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.deleteInByName");
		query.setParameter("id", name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Entity not found with name : "+name);
		}
	}

	@Override
	public void deleteInByReceiverWithName(User receiver, String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.deleteInByReceiverWithName");
		query.setParameter("receiver", receiver);
		query.setParameter("name", name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Entity not found with receiverId : "+receiver.getId()+" name : "+name);
		}
	}

	@Override
	public File selectOutBySenderWithName(User sender, String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<File> typedQuery=entityManager.createNamedQuery("File.findOutBySenderWithName",File.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("name", name);
		final File file= typedQuery.getSingleResult();
		entityManager.close();
		return file;
	}

	@Override
	public List<File> selectOutByName(String name){
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<File> typedQuery=entityManager.createNamedQuery("File.findOutByName",File.class);
		typedQuery.setParameter("name", name);
		final List<File> files= typedQuery.getResultList();
		entityManager.close();
		return files;
	}

	@Override
	public void deleteOutByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.deleteOutByName");
		query.setParameter("name", name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Entity not found with name : "+name);
		}
	}

	@Override
	public void deleteOutBySenderWithName(User sender, String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("File.deleteOutBySenderWithName");
		query.setParameter("sender",sender);
		query.setParameter("name", name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Entity not found with senderId : "+sender.getId()+" name : "+name);
		}
	}
	
}