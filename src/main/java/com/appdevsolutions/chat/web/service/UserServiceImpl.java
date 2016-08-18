package com.appdevsolutions.chat.web.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appdevsolutions.chat.dao.entity.Privilege;
import com.appdevsolutions.chat.dao.entity.Role;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.entity.UserStatus;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;
import com.appdevsolutions.chat.dao.repository.UserRepository;
import com.appdevsolutions.chat.web.exception.UserAlreadyExistException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.exception.UserUpdatePasswordException;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.PrivilegeModel;
import com.appdevsolutions.chat.web.model.RoleModel;
import com.appdevsolutions.chat.web.model.SecureUserModel;
import com.appdevsolutions.chat.web.model.UserModel;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	SessionRegistry sessionRegistry;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	DirectoryService userDirectoryService;
	
	@Autowired
	Md5PasswordEncoder md5PasswordEncoder;
	
	@Override
	public void deleteUserById(final String userId) throws UserNotFoundException{
		try {
			userRepository.deleteById(userId);
		}catch(EntityNotFoundException entityNotFoundException) {
			final UserNotFoundException userNotFoundException=new UserNotFoundException("GE_1000",ChatOnWebConstants.USER_ID,userId);
			LOGGER.error("Delete user could not performed, user not found with id : "+userId,userNotFoundException);
			throw userNotFoundException;
		}
	}
	@Override
	public void deleteUserByUsername(final String username) throws UserNotFoundException {
		try {
			userRepository.deleteByUsername(username);
		}catch(EntityNotFoundException entityNotFoundException) {
			final UserNotFoundException userNotFoundException=new UserNotFoundException("GE_1000",ChatOnWebConstants.USERNAME,username);
			LOGGER.error("Delete user could not performed, user not found with username : "+username,userNotFoundException);
			throw userNotFoundException;
		}
	}

	@Override
	public UserModel findUserById(final String userId) throws UserNotFoundException{
		try {
			final User user= userRepository.selectById(userId);
			boolean status=false;
			if(isLiveByUserId(user.getId())){
				status=true;
			}
			return getUserModel(user, status);
		}catch(EntityNotFoundException entityNotFoundException) {
			final UserNotFoundException userNotFoundException=new UserNotFoundException("GE_1000",ChatOnWebConstants.USER_ID,userId);
			LOGGER.error("User not found with id : "+userId,userNotFoundException);
			throw userNotFoundException;
		}
	}

	@Override
	public UserModel findUserByUsername(final String username) throws UserNotFoundException{
		try {
			final User user= userRepository.selectByUsername(username);
			boolean status=false;
			if(isLiveByUserId(user.getId())){
				status=true;
			}
			return getUserModel(user, status);
		}catch(EntityNotFoundException entityNotFoundException) {
			final UserNotFoundException userNotFoundException=new UserNotFoundException("GE_1000",ChatOnWebConstants.USERNAME,username);
			LOGGER.error("User not found with username : "+username,userNotFoundException);
			throw userNotFoundException;
		}
	}

	@Override
	@Cacheable("isRegistered")
	public boolean isUsernameRegistered(final String username) {
		try {
			userRepository.selectByUsername(username);
			return true;
		}catch(EntityNotFoundException entityNotFoundException) {
			return false;
		}
	}

	@Override
	public void saveUser(final UserModel userModel) throws UserAlreadyExistException{
		try{
			userRepository.selectByUsername(userModel.getUsername());
			throw new UserAlreadyExistException("GE_1004",ChatOnWebConstants.USERNAME,userModel.getUsername());
		}catch(EntityNotFoundException entityNotFoundException){
			final Privilege privilege=new Privilege();
			privilege.setCreateTimestamp(LocalDateTime.now());
			privilege.setName("READ_PRIVILEGE");
			final Role role=new Role();
			role.setCreateTimestamp(LocalDateTime.now());
			role.addPrivilege(privilege);
			role.setName("ROLE_USER");
			//final Set<Role> userRoles=new HashSet<Role>();
			final User user=new User();
			user.setName(userModel.getName());
			user.setUsername(userModel.getUsername());
			user.setPassword(userModel.getPassword());
			user.setDateOfBirth(userModel.getDateOfBirth());
			user.setAddress(userModel.getAddress());
			user.setEmailVerified(false);
			user.setCreateTimestamp(LocalDateTime.now());
			user.setLastLoginTimestamp(LocalDateTime.now());
			user.setStatus(UserStatus.ACTIVE);
			user.setPasswordChangeTimestamp(LocalDateTime.now());
			//final String encodedPassword=md5PasswordEncoder.encodePassword(user.getPassword(), "");
			user.setPassword(userModel.getPassword());
			user.addRole(role);
			/*final ProfilePhoto profilePhoto=new ProfilePhoto();
			profilePhoto.setContentType(ImageType.IMAGE_PNG);
			profilePhoto.setCreateTimestamp(LocalDateTime.now());
			profilePhoto.setName("ProfilePhoto.png");
			profilePhoto.setSize(6965);
			profilePhoto.setUser(user);*/
			//user.setProfilePhoto(profilePhoto);
			userRepository.persist(user);
			//userDirectoryService.createUserDirectoryTree(user.getUsername());
		}
	}

	
	@Override
	public void updateUserPassword(String userId, String oldPassword, String newPassword) throws UserNotFoundException,UserUpdatePasswordException{
		try {
			final User user=userRepository.selectById(userId);
			if(user.getPassword().equals(oldPassword)){
				user.setPassword(newPassword);
				userRepository.update(user);
			}else{
				throw new UserUpdatePasswordException("GE_1008", ChatOnWebConstants.OLD_PASSWORD, oldPassword);
			}
		} catch (EntityNotFoundException EntityNotFoundException) {
			LOGGER.error("User not found Error : ",EntityNotFoundException);
			throw new UserNotFoundException("GE_1000",ChatOnWebConstants.USER_ID,userId);
		}
	}

	@Override
	public UserModel liveMe() throws UserNotFoundException{
		final Object auth=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(auth instanceof SecureUserModel){
			UserDetails userDetails=(UserDetails)auth;
			return findUserByUsername(userDetails.getUsername());
		}else{
			throw new UserNotFoundException("GE_1000", "userId", "");
		}
	}
	@Override
	public List<UserModel> liveUsers() {
		return null;
	}

	@Override
	public List<UserModel> eachUserUsers(final String id) {
		final UserModel user=new UserModel(id, "", null, "", null, null,null, "", null, true, null);
		final List<UserModel> eachUserUsers=allUsers();
		eachUserUsers.remove(user);
		return eachUserUsers;
	}

	@Override
	public List<UserModel> allUsers() {
		try {
			final List<User> users=userRepository.selectAll();
			List<UserModel> allUsers=new ArrayList<UserModel>(users.size());
			for(User user:users) {
				boolean status=false;
				if(isLiveByUserId(user.getId())){
					status=true;
				}
				allUsers.add(getUserModel(user, status));
			}
			return allUsers;
		}catch(EntityNotFoundException entityNotFoundException) {
			LOGGER.info("Users not found : "+entityNotFoundException);
			return new ArrayList<UserModel>();
		}
		
	}
	private List<String> getLiveUserIds(){
		final List<String> liveUserIds=new ArrayList<String>(sessionRegistry.getAllPrincipals().size());
		for(final Object principal:sessionRegistry.getAllPrincipals()){
			if(principal instanceof SecureUserModel){
				final SecureUserModel secureUserModel=(SecureUserModel)principal;
				liveUserIds.add(secureUserModel.getId());
			}
		}
		return liveUserIds;
	}
	private boolean isLiveByUserId(String userId){
		final List<String> liveUserIds=getLiveUserIds();
		for(String liveUserId:liveUserIds){
			if(liveUserId.equals(userId)){
				return true;
			}
		}
		return false;
	}
	@Override
	public void setLastLoginByUserId(String userId, LocalDateTime now) throws UserNotFoundException{
		try{
			userRepository.updateLastLoginByUserId(userId, now);
		}catch(EntityNotFoundException EntityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
		}
	}
	@Override
	public void setLastLoginByUsername(String username, LocalDateTime now) throws UserNotFoundException{
		
	}
	
	@Override
	public void saveAdminUser(UserModel userModel) throws UserAlreadyExistException {
		try{
			userRepository.selectByUsername(userModel.getUsername());
			throw new UserAlreadyExistException("GE_1004",ChatOnWebConstants.USERNAME,userModel.getUsername());
		}catch(EntityNotFoundException entityNotFoundException){
			final Privilege readPrivilege=new Privilege();
			readPrivilege.setName("READ_PRIVILEGE");
			readPrivilege.setCreateTimestamp(LocalDateTime.now());
			final Privilege writePrivilege=new Privilege();
			writePrivilege.setName("WRITE_PRIVILEGE");
			writePrivilege.setCreateTimestamp(LocalDateTime.now());
			final Role adminRole=new Role();
			adminRole.setName("ROLE_ADMIN");
			adminRole.setCreateTimestamp(LocalDateTime.now());
			adminRole.addPrivilege(readPrivilege);
			adminRole.addPrivilege(writePrivilege);
			final User user=new User();
			user.setName(userModel.getName());
			user.setPassword(userModel.getPassword());
			user.setDateOfBirth(userModel.getDateOfBirth());
			user.setAddress(userModel.getAddress());
			user.setMobileNumber(userModel.getMobileNumber());
			user.setUsername(userModel.getUsername());
			user.setEmailVerified(false);
			user.setCreateTimestamp(LocalDateTime.now());
			user.setLastLoginTimestamp(LocalDateTime.now());
			user.setStatus(UserStatus.ACTIVE);
			user.setPasswordChangeTimestamp(LocalDateTime.now());
			user.addRole(adminRole);
			/*final ProfilePhoto profilePhoto=new ProfilePhoto();
			profilePhoto.setContentType(ImageType.IMAGE_PNG);
			profilePhoto.setCreateTimestamp(LocalDateTime.now());
			profilePhoto.setName("ProfilePhoto.png");
			profilePhoto.setSize(6965);
			profilePhoto.setUser(user);*/
			//user.setProfilePhoto(profilePhoto);
			userRepository.persist(user);
			//userDirectoryService.createUserDirectoryTree(user.getUsername());
		}
	}
	private UserModel getUserModel(User user,boolean status){
		final Set<RoleModel> roleModels=new HashSet<RoleModel>();
		for(Role role:user.getRoles()){
			final Set<PrivilegeModel> privilegeModels=new HashSet<PrivilegeModel>();
			for(Privilege privilege:role.getPrivileges()){
				final PrivilegeModel privilegeModel=new PrivilegeModel(privilege.getId(), privilege.getName(), privilege.getRole().getId(), privilege.getCreateTimestamp());
				privilegeModels.add(privilegeModel);
			}
			final RoleModel roleModel=new RoleModel(role.getId(), role.getName(), role.getUser().getId(), privilegeModels, role.getCreateTimestamp());
			roleModels.add(roleModel);
		}
		final UserModel userModel=new UserModel(user.getId(), user.getUsername(), user.getName(),"",user.getDateOfBirth(), user.getLastLoginTimestamp(), user.getCreateTimestamp(), user.getMobileNumber(), user.getAddress(),status,roleModels);
		return userModel;
	}
	@Override
	public void basicAuthentication(String authCredentials) throws UserNotFoundException{
		if (null == authCredentials)
			throw new UserNotFoundException("GE_1029","username,password","");
		// header value format will be "Basic encodedstring" for Basic
		// authentication. Example "Basic YWRtaW46YWRtaW4="
		final String encodedUserPassword = authCredentials.replaceFirst("Basic"+ " ", "");
		String usernameAndPassword = null;
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(encodedUserPassword);
			usernameAndPassword = new String(decodedBytes, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
		final StringTokenizer tokenizer = new StringTokenizer(
				usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		// we have fixed the userid and password as admin
		// call some UserService/LDAP here
		//boolean authenticationStatus = "admin".equals(username) && "admin".equals(password);
		getByUsernameAndPassword(username, password);
	}
	@Override
	public UserModel getByUsernameAndPassword(String username, String password) throws UserNotFoundException {
		try{
			final User user=userRepository.getUserByUsernameAndPassword(username, password);
			final Set<RoleModel> roleModels=new HashSet<RoleModel>();
			for(final Role role:user.getRoles()){
				final Set<PrivilegeModel> privilegeModels=new HashSet<PrivilegeModel>(role.getPrivileges().size());
				for(final Privilege privilege:role.getPrivileges()){
					final PrivilegeModel privilegeModel=new PrivilegeModel(privilege.getId(), privilege.getName(), role.getId(), role.getCreateTimestamp());
					privilegeModels.add(privilegeModel);
				}
				final RoleModel roleModel=new RoleModel(role.getId(), role.getName(), user.getId(),privilegeModels, role.getCreateTimestamp());
				roleModels.add(roleModel);
			}
			final UserModel userModel=new UserModel(user.getId(), user.getUsername(), user.getName(), "", user.getDateOfBirth(), user.getLastLoginTimestamp(), user.getCreateTimestamp(), user.getMobileNumber(), user.getAddress(), false, roleModels);
			return userModel;
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1029", "username,password", username+","+password);
		}
	}
}