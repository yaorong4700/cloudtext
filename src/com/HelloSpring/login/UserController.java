package com.HelloSpring.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

	/**
	 * 此函数实现 后台执行脚本命令
	 * 
	 * @param command
	 *            终端脚本命令
	 * @throws InterruptedException
	 */
	private void getDataByShellCMD(String command) throws InterruptedException {
		Process process = null;
		List<String> processList = new ArrayList<String>();
		try {
			process = Runtime.getRuntime().exec(command);
			int exitValue = process.waitFor();
			System.out.println(exitValue);
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			while ((line = input.readLine()) != null) {
				processList.add(line);
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// String idnum = processList.get((processList.size() - 1));
		// System.out.println(idnum);
		for (String line : processList) {
			System.out.println(line);
		}
	}

	/***
	 * 用户登陆
	 * <p>
	 * 注解配置，只允许POST提交到该方法
	 * 
	 * @param username
	 * @param usermail
	 * @return
	 */
	@RequestMapping(value = "/twochoices", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, String username, String usermail,String password) {
		// 验证传递过来的参数是否正确，否则返回到登陆页面。
		if (this.checkParams(new String[] { username, password,usermail })) {

			try {

				getDataByShellCMD("chmod +x " + request.getSession().getServletContext().getRealPath("/")
						+ "WEB-INF/sh/create_user.sh");
				getDataByShellCMD(request.getSession().getServletContext().getRealPath("/")
						+ "WEB-INF/sh/create_user.sh " + username);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 指定要返回的页面为page0.jsp
			ModelAndView mav = new ModelAndView("page0");
			// 将参数返回给页面
			mav.addObject("username", username);
			mav.addObject("usermail", usermail);
			return mav;
		}
		return new ModelAndView("index");
	}

	/***
	 * 验证参数是否为空
	 * 
	 * @param params
	 * @return
	 */
	private boolean checkParams(String[] params) {
		for (String param : params) {
			if (param == "" || param == null || param.isEmpty()) {
				return false;
			}
		}
		if (params[0].equals("hscn") && params[1].equals("hscn")) {
			return true;
		}
		return false;
	}
}
