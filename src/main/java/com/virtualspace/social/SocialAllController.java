package com.virtualspace.social;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.here.framework.service.ServiceException;
import com.here.framework.util.JSONUtil;
import com.here.framework.web.BaseController;
import com.virtualspace.service.social.SocialAllItem;
import com.virtualspace.service.social.SocialService;

@Controller
@RequestMapping("/social")
public class SocialAllController extends BaseController
{
	@ResponseBody
	@RequestMapping(value = "/all/{lon}/{lat}/{sindex}/{eindex}", method = RequestMethod.GET, produces="application/json;charset=utf-8")
	@Transactional(propagation = Propagation.NEVER)
	public String all(@PathVariable("lon") String lon,@PathVariable("lat") String lat,@PathVariable("sindex") int sindex,@PathVariable("eindex") int eindex) throws ServiceException, IOException
	{
//		try {
//			Thread.sleep(50000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		double dlon = Double.parseDouble(lon);
		double dlat = Double.parseDouble(lat);
		int count = 50;
		int distance = 20000;
		String sortType = "asc";
		
		SocialService socialService = this.getService(SocialService.class);
		List<SocialAllItem> allItems = socialService.socialAllNoUser(dlon, dlat, distance, count, sortType);
		
		return JSONUtil.toJson(allItems);
	}
	
	@ResponseBody
	@RequestMapping(value = "/telephone", method = RequestMethod.POST, produces="application/json;charset=utf-8")
	@Transactional(propagation = Propagation.NEVER)
	public String telephone(HttpServletRequest request,HttpServletResponse response) throws ServiceException, IOException
	{
		return null;
	}
	
	@RequestMapping(value = "/email", method = RequestMethod.POST, produces="application/json;charset=utf-8")
	@Transactional(propagation = Propagation.NEVER)
	public void email(HttpServletRequest request,HttpServletResponse response) throws ServiceException, IOException
	{
		
	}
}
