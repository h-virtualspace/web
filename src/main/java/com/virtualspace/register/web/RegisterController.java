package com.virtualspace.register.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.here.framework.service.ServiceException;
import com.here.framework.util.JSONUtil;
import com.here.framework.web.BaseController;
import com.virtualspace.database.model.User;
import com.virtualspace.redis.service.RedisKVCacheService;
import com.virtualspace.service.checkcode.CheckCodeService;
import com.virtualspace.service.common.UserService;
import com.virtualspace.service.register.RegisterService;
import com.virtualspace.service.register.imp.RegisterRedisKey;
import com.virtualspace.util.Md5Util;

@Controller
@RequestMapping("/register")
public class RegisterController  extends BaseController
{
	@ResponseBody
	@RequestMapping(value = "/gtcheckcode", method = RequestMethod.POST, produces="application/json;charset=utf-8")
	@Transactional(propagation = Propagation.NEVER)
	public String gtCheckCode(HttpServletRequest request,HttpServletResponse response) throws ServiceException, IOException
	{
		String userTelephone = request.getParameter("telephone");
		if (StringUtils.isEmpty(userTelephone)) {
			return "非法参数";
		}
		UserService userService = this.getService(UserService.class);
		if (userService.isUserExistsByTelephone(userTelephone)) 
		{
			response.getWriter().write("telephoneExists");
		}
		else
		{
			RedisKVCacheService cacheService = this.getService(RedisKVCacheService.class);
			CheckCodeService checkCodeService = this.getService(CheckCodeService.class);
			
			int result = (int)(Math.random()*9000+1000);
			if(checkCodeService.sendCheckCodeByTelephone(userTelephone, result) != result)
			{
				throw new ServiceException("发送校验码失败");
			}
			String key = RegisterRedisKey.REGISTER_CODE+":"+userTelephone;
			cacheService.setKeyValue(key, String.valueOf(result));
			cacheService.expire(key,RegisterRedisKey.REGISTER_CODE_EXPIRE);
			return String.valueOf(result);
		}
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/telephone", method = RequestMethod.POST, produces="application/json;charset=utf-8")
	@Transactional(propagation = Propagation.NEVER)
	public String telephone(HttpServletRequest request,HttpServletResponse response) throws ServiceException, IOException
	{
		String telephone = request.getParameter("telephone");
		String md5Password = request.getParameter("password");
		String checkCode = request.getParameter("checkcode");
		String token = request.getParameter("token");
		String serverToken = Md5Util.getMD5Str(Md5Util.getMD5Str(telephone)+Md5Util.getMD5Str(md5Password)+Md5Util.getMD5Str(checkCode));
		if(serverToken.equals(token))
		{
			RegisterService registerService = this.getService(RegisterService.class);
			RedisKVCacheService cacheService = this.getService(RedisKVCacheService.class);
			String key = RegisterRedisKey.REGISTER_CODE+":"+telephone;
			String cacheCheckCode = cacheService.getKey(key);
			if (cacheCheckCode == null || StringUtils.isEmpty(cacheCheckCode)) {
				return "checkCodeExpire";
			}
			else if (checkCode.equals(cacheCheckCode)) 
			{
				User resultUser = registerService.registerUserByTelephone(telephone, md5Password, checkCode);
				if (resultUser != null) 
				{
					return JSONUtil.toJson(resultUser);
				}
				else
				{
					return "registerError";
				}
			}
			else
			{
				return "checkCodeError";
			}
		}
		else{
			return "tokenError";
		}
	}
	
	@RequestMapping(value = "/email", method = RequestMethod.POST, produces="application/json;charset=utf-8")
	@Transactional(propagation = Propagation.NEVER)
	public void email(HttpServletRequest request,HttpServletResponse response) throws ServiceException, IOException
	{
		String email = request.getParameter("email");
		String md5Password = request.getParameter("password");
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(md5Password)) 
		{
			response.getWriter().write("registerError");
		}
		else
		{
			RegisterService registerService = this.getService(RegisterService.class);
			User resultUser = registerService.registerUserByEmail(email, md5Password);
			if (resultUser != null) 
			{
				response.getWriter().write(JSONUtil.toJson(resultUser));
			}
			else
			{
				response.getWriter().write("registerError");
			}
		}
	}
}
