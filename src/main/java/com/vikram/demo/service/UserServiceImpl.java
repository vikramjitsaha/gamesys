package com.vikram.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.transaction.Transactional;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.vikram.demo.constant.CommonConstants;
import com.vikram.demo.dao.AuthenticationRepository;
import com.vikram.demo.dao.UserRepository;
import com.vikram.demo.entity.AuthenticationEntity;
import com.vikram.demo.entity.UserEntity;
import com.vikram.demo.form.UserForm;
import com.vikram.demo.validator.UserFormValidator;

@Service
public class UserServiceImpl implements UserService{

	private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private AuthenticationRepository authRepo;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserFormValidator validator;
	
	
	/* (non-Javadoc)
	 * @see com.vikram.demo.service.UserService#getProperty(java.lang.String)
	 */
	@Override
	public String getProperty(final String key) {
		return env.getProperty(key);
	}
	
	
	/* (non-Javadoc)
	 * @see com.vikram.demo.service.UserService#createData()
	 */
	@Override
	@Transactional
	public String createData() {
		this.createAuthenticationData();
		return this.createUserRecords();
	}
	
	
	/**
	 * Creates dummy blacklisted ssn in h2 database
	 */
	private void createAuthenticationData() {
		if ( this.authRepo.count() <= 0) {
			this.authRepo.save(new AuthenticationEntity(Boolean.TRUE, "123-41-6789"));
			this.authRepo.save(new AuthenticationEntity(Boolean.TRUE, "123-42-6789"));
			this.authRepo.save(new AuthenticationEntity(Boolean.TRUE, "123-43-6789"));
			this.authRepo.save(new AuthenticationEntity(Boolean.TRUE, "123-44-6789"));
		}
	}
	
	
	/**
	 * Creates user records 
	 * @return
	 */
	private String createUserRecords () {
		final List<Boolean> flags = new ArrayList<>();
		flags.add(this.save(new UserEntity("Root", "password", "1980-01-01", "123-45-6789")));
		flags.add(this.save(new UserEntity("Anderson", "password", "1980-01-01", "123-46-6789")));
		flags.add(this.save(new UserEntity("Stokes", "password", "1980-01-01", "123-47-6789")));
		flags.add(this.save(new UserEntity("Hales", "password", "1980-01-01", "123-48-6789")));
		flags.add(this.save(new UserEntity("Williams", "password", "1980-01-01", "123-49-6789")));
		final String msg = flags.stream().filter(obj -> obj.equals(Boolean.TRUE)).count() +  " records inserted in DB for users";		
		log.info(msg);
		return msg;
	}
	
	
	/* (non-Javadoc)
	 * @see com.vikram.demo.service.UserService#save(com.vikram.demo.entity.UserEntity)
	 */
	@Override
	public Boolean save(UserEntity user) {
		this.repository.save(user);
		return Objects.isNull(user.getId()) ? Boolean.FALSE : Boolean.TRUE;
	}
	
	/* (non-Javadoc)
	 * @see com.vikram.demo.service.UserService#findAll()
	 */
	@Override
	public List<UserEntity> findAll() {
		List<UserEntity> list = new ArrayList<>();		
		this.repository.findAll().iterator().forEachRemaining(list::add);
		log.info(list.size() + " records present in DB");
		return list;
	}

	/* (non-Javadoc)
	 * @see com.vikram.demo.service.UserService#fetchDataByUserName(java.lang.String)
	 */
	@Override
	public List<UserEntity> fetchDataByUserName(String userName) {
		List<UserEntity> list = new ArrayList<>();		
		this.repository.findByUserName(userName).iterator().forEachRemaining(list::add);
		log.info(list.size() + " records found for search criteria userName = " + userName);
		return list;
	}


	/* (non-Javadoc)
	 * @see com.vikram.demo.service.UserService#fetchDataBySSN(java.lang.String)
	 */
	@Override
	public UserEntity fetchDataBySSN(String ssn) {
		UserEntity entity = this.repository.findBySsn(ssn);
		return entity != null ? entity : null;
	}

	/* (non-Javadoc)
	 * @see com.vikram.demo.service.UserService#isUsersLoaded()
	 */
	@Override
	public Boolean isUsersLoaded() {
		List<UserEntity> list = this.findAll();
		return null == list || list.isEmpty() ? Boolean.FALSE : Boolean.TRUE;
	}
	

	/* (non-Javadoc)
	 * @see com.vikram.demo.service.UserService#deleteUser(java.lang.String)
	 */
	@Override
	public Boolean deleteUser(final String ssn) {
		Boolean flag = Boolean.FALSE;
		try {
			this.repository.deleteUser(ssn);
			flag = Boolean.TRUE;
		}
		catch(final Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return flag;
	}
	
	
	/* (non-Javadoc)
	 * @see com.vikram.demo.service.UserService#doUserFormValidation(com.vikram.demo.form.UserForm)
	 */
	@Override
	public String doUserFormValidation (final UserForm form) {
		StringBuffer msg = new StringBuffer("");
		if (!form.isValid()) msg.append(this.getProperty(CommonConstants.APP_PAGE_VALIDATION_ERROR_MSG)).append(CommonConstants.HTML_BREAK);
		else {
			final Map<String, String> errors = validator.validate(form);
			if (!errors.isEmpty()) errors.values().forEach(obj -> msg.append(obj).append(CommonConstants.HTML_BREAK));
			else if (!Objects.isNull(this.fetchDataBySSN(form.getSsn()))) 
				msg.append(this.getProperty(CommonConstants.APP_PAGE_VALIDATION_USERPRESENT_MSG)).append(CommonConstants.HTML_BREAK);
		}
		return msg.toString();
	}
	
}
