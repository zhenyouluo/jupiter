package com.ricky.framework.ioc;

import com.ricky.framework.ioc.anotation.MyBean;
import com.ricky.framework.ioc.anotation.MyResource;
import com.ricky.framework.ioc.dao.UserDAO;

@MyBean(id="userServiceImpl")
public class UserServiceImpl implements UserService {
	
	@MyResource(name="mySQLUserDAOImpl")
    private UserDAO mysqlDAO;
	
    private UserDAO oracleDAO;

    @MyResource(name="oracleUserDAOImpl")
    public void setUserDao(UserDAO oracleDAO) {  
        this.oracleDAO = oracleDAO;  
    }
	
    public void fire(){
        mysqlDAO.test();
        oracleDAO.test();
    }
}
