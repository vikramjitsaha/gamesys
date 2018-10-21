1) To start the app >> http://localhost:8888/app 

Spring Security Basic Authentication :-
------------------------------------------
Description :   2 roles are created ADMIN and EMP. 
				2 users are created admin and user.
				admin has ADMIN role and user has EMP role.
				admin can register new user & view and delete registered users 
				user can view registered users				
a) Admin login credentials 		>> user name = admin & password = admin
b) Employee login credentials 	>> user name = user & password = user 


Technologies used : Spring Boot (Rest, JPA, Security) , Java 8, H2 (persistent DB)


Validations done :-
-----------------------------------------
1) mandatory fields : user name , password, dob, ssn are required fields
2) user name : only alphanumeric with out space 
3) ssn : format of ssn is AAA-GG-SSS
4) password : minimum 4 characters , at least 1 uppercase alphabet, at least 1 digit
5) Blacklist : Assumption >> ssn is blacklisted , created a table in H2 database to store mapping of ssn with their blacklist flag
		if user try to register with below ssn then validation will prevent user registration :-
				123-41-6789 , 123-42-6789 , 123-43-6789 , 123-44-6789
6) Existing ssn : is ssn already exists user cannot be registerd
7) Date of birth : the format of date of birth should be : yyyy-MM-dd



Validation Framework :-
------------------------------------------
1) BaseValidator interface with 4 default functions >> isValidUserName(), isValidPassword(), isValidDOB(), isValidSSN()
2) ExclusionService interface (extends BaseValidator) with one default function >> validate() that returns map of errors
3) UserFormValidator class (implments ExclusionService) with one function >> validate() 


Testing :-
------------------------------------------
Only one rest end point in UserServiceController.java is tested using Mockito.
Similarly other functions can be tested. (however not done now, work is in progress)


Note : Due to addition of spring security the H2 db management console is disabled ::: http://localhost:8888/app/db				

