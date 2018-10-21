package com.vikram.demo.controller;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.vikram.demo.constant.CommonConstants;
import com.vikram.demo.entity.UserEntity;
import com.vikram.demo.form.UserForm;
import com.vikram.demo.service.UserService;

@RestController
public class UserServiceController {

	@Autowired
	private UserService service;

	/**
	 * END POINT FOR POSTMAN
	 * 
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	@Secured(CommonConstants.ROLE_ADMIN)
	public String process() {
		return this.service.isUsersLoaded() ? "There are existing records in DB" : service.createData();
	}

	/**
	 * END POINT FOR POSTMAN
	 * 
	 * @return
	 */
	@RequestMapping("/alluser")
	@ResponseBody
	@Secured({CommonConstants.ROLE_EMPLOYEE,CommonConstants.ROLE_ADMIN})
	public List<UserEntity> findAll() {
		return this.service.findAll();
	}
	
	/**
	 * END POINT FOR POSTMAN
	 * 
	 * @return
	 */
	@GetMapping("/allusers")
	@Secured({CommonConstants.ROLE_EMPLOYEE,CommonConstants.ROLE_ADMIN})
	public ResponseEntity<List<UserEntity>> fetchUsers(){
		return new ResponseEntity<List<UserEntity>>(this.service.findAll(), HttpStatus.OK);
	}

	/**
	 * END POINT FOR POSTMAN
	 * 
	 * @return
	 */
	@RequestMapping("/alluserbyname")
	@ResponseBody
	@Secured(CommonConstants.ROLE_ADMIN)
	public ResponseEntity<String> fetchDataByUserName(@RequestParam("username") String username) {
		StringBuffer result = new StringBuffer("");
		List<UserEntity> list = this.service.fetchDataByUserName(username);
		if(!Objects.isNull(list)) {
			list.forEach(user -> result.append(user).append(CommonConstants.HTML_BREAK));
			return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(this.service.getProperty(CommonConstants.APP_PAGE_COMMON_NODATAFOUND_MSG), HttpStatus.NOT_FOUND);
	}

	/**
	 * END POINT FOR POSTMAN
	 * 
	 * @return
	 */
	@RequestMapping(value = "/userbyssn/{ssn}", headers = { "role=admin", "allowed=1" }, produces = {"application/json", "application/xml" })
	@Secured(CommonConstants.ROLE_ADMIN)
	public ResponseEntity<UserEntity> fetchDataByUserId(@PathVariable("ssn") String ssn) {
		final UserEntity entity = this.service.fetchDataBySSN(ssn);
		return new ResponseEntity<UserEntity>(entity, Objects.isNull(entity) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}	
	
	
	/**
	 * END POINT FOR WEB APPLICATION
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/addUser" }, method = RequestMethod.POST)
	@Secured(CommonConstants.ROLE_ADMIN)
    public ModelAndView savePerson(@ModelAttribute(CommonConstants.ATTRIBUTE_USERFORM) UserForm userForm) {
		ModelAndView model = new ModelAndView(CommonConstants.PAGE_REGISTER);
		final String validationMsg = this.service.doUserFormValidation(userForm);
		
		if (StringUtils.isNotEmpty(validationMsg)) model.addObject(CommonConstants.ATTRIBUTE_ERROR, validationMsg);
		else {
			if (this.service.save(new UserEntity(userForm))) {
        		model.addObject(CommonConstants.ATTRIBUTE_SUCCESS, this.service.getProperty(CommonConstants.APP_PAGE_VALIDATION_SUCCESS_MSG));
        		userForm.reset();
			}	
        	else 
        		model.addObject(CommonConstants.ATTRIBUTE_ERROR, this.service.getProperty(CommonConstants.APP_PAGE_SAVE_ERROR_MSG));
		}
		model.addObject(CommonConstants.ATTRIBUTE_USERFORM, userForm);        
        return model;
    }
	
	/**
	 * END POINT FOR WEB APPLICATION
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/deleteUser/{ssn}" })
	@Secured(CommonConstants.ROLE_ADMIN)
	public ModelAndView deleteUser(@PathVariable("ssn") String ssn, Authentication authentication) {
		ModelAndView model = new ModelAndView(CommonConstants.PAGE_USERLIST);
		final String authority = authentication.getAuthorities().stream().map(obj -> obj.getAuthority()).findFirst().get();
		model.addObject(CommonConstants.CURRENT_ROLE, authority);
		
		if (Objects.isNull(this.service.fetchDataBySSN(ssn))) 
			model.addObject(CommonConstants.ATTRIBUTE_ERROR, this.service.getProperty(CommonConstants.APP_PAGE_VALIDATION_USERNOTPRESENT_MSG));
		else {
			if (this.service.deleteUser(ssn)) 
				model.addObject(CommonConstants.ATTRIBUTE_SUCCESS, this.service.getProperty(CommonConstants.APP_PAGE_DELETE_SUCCESS_MSG));
			else
				model.addObject(CommonConstants.ATTRIBUTE_ERROR, this.service.getProperty(CommonConstants.APP_PAGE_DELETE_ERROR_MSG));
		}		
		
		model.addObject(CommonConstants.ATTRIBUTE_USERS, this.service.findAll());
		return model;
	}

}
