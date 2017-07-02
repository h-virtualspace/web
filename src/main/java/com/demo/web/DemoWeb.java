package com.demo.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;

import com.demo.service.DemoBean;
import com.demo.service.DemoService;
import com.demo.service.PersonDemo;
import com.here.framework.bean.InstanceManager;
import com.here.framework.core.cache.HObjectSerializable;
import com.here.framework.core.redis.RedisPool;
import com.here.framework.core.template.freemarker.FreeMarkerConfiguration;
import com.here.framework.service.ServiceException;
import com.here.framework.util.JSONUtil;
import com.here.framework.web.BaseController;
import com.here.virtualspace.file.service.FileService;
import com.mysql.fabric.xmlrpc.base.Data;
import com.test.Test;
import com.virtualspace.database.model.User;
import com.virtualspace.service.common.UserService;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Controller
@RequestMapping("/demo")
public class DemoWeb extends BaseController{
	@Autowired
	private BeanTest beanTest;

	@RequestMapping("/demoWebString")
	@ResponseBody
	public String getDemoWebString(){
		//DemoService domoService=this.getService(DemoService.class);
		
		
		FileService fileService=this.getService(FileService.class);
		String name=null;
		try {
			File file=new File("/disk/picture/temp/upload5.jpg");
			
			name=fileService.upload(new FileInputStream(file), file.length(), "jpg", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "<img src='"+fileService.getURL(name)+"'/>";
	}
	@RequestMapping("/demoTest")
	public void  demoTest(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		HttpSession session=req.getSession();
		session.setAttribute("myname", "test this ");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("name", 123);
		map.put("code", "kkk");
		
		Test test=new Test("hello");
		test.getMap().put("address", "海淀区");
		test.getMap().put("num", 258);
		session.setAttribute("test", test);
		
		UserService userService=this.getService(UserService.class);
		User user=new User();
		user.setNickName("kk");
		try {
			//userService.isUserExistsByEmail("kkk");
			userService.insert(user);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.getWriter().println("demoTest output!");
	}
	
	@RequestMapping("/demo")
	@ResponseBody
	public String  demo(HttpServletRequest req,HttpServletResponse resp) throws IOException, TemplateException{
		DemoService domoService=this.getService(DemoService.class);
		domoService.test(new HashMap<String, String>());
		//beanTest.getConfig().clear();
		Map<String, String> model = new HashMap<String, String>();
		model.put("ipAddress", "121.22.33.54");
		model.put("currentTime", new Date().toLocaleString());
		
		String html = InstanceManager.getInstance(FreeMarkerConfiguration.class).process("userRegister.html",model);
		String result= InstanceManager.getInstance(FreeMarkerConfiguration.class).processText("user:${ipAddress}", model);
		
		HttpSession session=req.getSession();
		Object map=session.getAttribute("test");
		return result+JSONUtil.toJson(map);
	}
	
	@RequestMapping(value="updateDemo",produces="text/html;charset=utf8")
	@ResponseBody()
	public String  updateDemo(HttpServletRequest req,HttpServletResponse resp) throws IOException, ServiceException{
		
		HttpSession session=req.getSession();
		session.invalidate();
		
		DemoService domoService=this.getService(DemoService.class);
//		DemoBean bean = new DemoBean();
//		bean.setName(Math.random()+"");
//		bean.setCode(1);
//		bean.setEmail("www@kkk.com");
//		bean=domoService.updateDemo(bean);
//		UserService userService=this.getService(UserService.class);
//		User user=new User();
//		user.setNickName("kk");
//		userService.insert(user);
//		return bean.toString();
		DemoBean bean = new DemoBean();
		bean.setName(Math.random()+"");
		bean.setCode(1);
		bean.setEmail("www@kkk.com");
		List<PersonDemo> persons = new ArrayList<PersonDemo>();
		PersonDemo person = new PersonDemo();
		person.setName("koujp");
		persons.add(person);
		domoService.addBean(bean, persons);
		return "success";
	}
}
