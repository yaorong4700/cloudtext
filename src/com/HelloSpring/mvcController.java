package com.HelloSpring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Statement;

@Controller
public class mvcController {

	@RequestMapping(value = "/twochoices", method = RequestMethod.GET)
	public ModelAndView twochioces(String username, String usermail, String password) {
		// 验证传递过来的参数是否正确，否则返回到登陆页面。
		ModelAndView mav = null;
	
			// 指定要返回的页面为page0.jsp
			mav = new ModelAndView("page0");
			// 将参数返回给页面
			mav.addObject("username", username);
			mav.addObject("usermail", usermail);
			return mav;
	
	}

	@RequestMapping(value = "/choice", method = RequestMethod.GET)
	public ModelAndView choice(String username, String usermail) {
		// 验证传递过来的参数是否正确，否则返回到登陆页面。
		ModelAndView mav = null;
		// 指定要返回的页面为page0.jsp
		mav = new ModelAndView("page3");
		// 将参数返回给页面
		mav.addObject("username", username);
		mav.addObject("usermail", usermail);
		return mav;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView upload1(String username, String usermail) {
		// 验证传递过来的参数是否正确，否则返回到登陆页面。
		ModelAndView mav = null;

		// 指定要返回的页面为page0.jsp
		mav = new ModelAndView("page1_1_1");
		// 将参数返回给页面
		mav.addObject("username", username);
		mav.addObject("usermail", usermail);

		return mav;
	}

	@RequestMapping(value = "/resource", method = RequestMethod.GET)
	public ModelAndView resource(String username, String usermail) {
		// 验证传递过来的参数是否正确，否则返回到登陆页面。
		ModelAndView mav = null;

		// 指定要返回的页面为page0.jsp
		mav = new ModelAndView("page2");
		// 将参数返回给页面
		mav.addObject("username", username);
		mav.addObject("usermail", usermail);

		return mav;
	}

	@RequestMapping(value = "/applist", method = RequestMethod.GET)
	public ModelAndView applist(String username, String usermail) {
		// 验证传递过来的参数是否正确，否则返回到登陆页面。
		ModelAndView mav = null;

		// 指定要返回的页面为page0.jsp
		mav = new ModelAndView("page5");
		// 将参数返回给页面
		mav.addObject("username", username);
		mav.addObject("usermail", usermail);

		return mav;
	}

	@RequestMapping(value = "/servicelist", method = RequestMethod.GET)
	public ModelAndView servicelist(String username, String usermail) {
		// 验证传递过来的参数是否正确，否则返回到登陆页面。
		ModelAndView mav = null;

		// 指定要返回的页面为page0.jsp
		mav = new ModelAndView("page4");
		// 将参数返回给页面
		mav.addObject("username", username);
		mav.addObject("usermail", usermail);

		return mav;
	}

}
