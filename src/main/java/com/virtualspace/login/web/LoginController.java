package com.virtualspace.login.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.here.framework.service.ServiceException;
import com.here.framework.util.JSONUtil;
import com.here.framework.web.BaseController;
import com.virtualspace.database.model.User;
import com.virtualspace.service.common.UserService;
import com.virtualspace.service.login.LoginService;

@Controller
@RequestMapping("/login")
public class LoginController  extends BaseController
{
	@ResponseBody
	@RequestMapping(value = "/telephone", method = RequestMethod.POST, produces="application/json;charset=utf-8")
	@Transactional(propagation = Propagation.NEVER)
	public String loginByTelephone(HttpServletRequest request,HttpServletResponse response) throws ServiceException, IOException
	{
		String telephone = request.getParameter("telephone");
		String md5Password = request.getParameter("password");
		if (telephone != null && md5Password != null) 
		{
			UserService userService = this.getService(UserService.class);
			if (!userService.isUserExistsByTelephone(telephone)) {
				return "userNoRegister";
			}
			LoginService loginService = this.getService(LoginService.class);
			User user = loginService.loginByTelephone(telephone, md5Password);
			if (user != null) 
			{
				request.getSession().setAttribute(LoginContext.IS_USER_LOGIN, true);
				return JSONUtil.toJson(user);
			}
			else
			{
				return "loginFailed";
			}
		}
		
		return null;
	}
	@RequestMapping(value = "/email", method = RequestMethod.POST, produces="application/json;charset=utf-8")
	@Transactional(propagation = Propagation.NEVER)
	public void loginByEmail(HttpServletRequest request,HttpServletResponse response) throws ServiceException, IOException
	{
		String email = request.getParameter("email");
		String md5Password = request.getParameter("password");
		if (email != null && md5Password != null) 
		{
			LoginService loginService = this.getService(LoginService.class);
			User user = loginService.loginByEmail(email, md5Password);
			if (user != null) 
			{
				request.getSession().setAttribute(LoginContext.IS_USER_LOGIN, true);
				response.getWriter().write("loginSuccess");
			}
			else
			{
				response.getWriter().write("loginFailed");
			}
		}
	}
	@RequestMapping(value = "/userlogout", method = RequestMethod.POST, produces="application/json;charset=utf-8")
	@Transactional(propagation = Propagation.NEVER)
	public void logout(HttpServletRequest request,HttpServletResponse response) throws ServiceException
	{
		request.getSession().invalidate();
	}
}
