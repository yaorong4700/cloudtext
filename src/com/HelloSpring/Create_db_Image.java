package com.HelloSpring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Create_db_Image {
	
	public Create_db_Image() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 此函数实现 后台执行脚本命令
	 * 
	 * @param command
	 *            终端脚本命令
	 * @throws InterruptedException
	 */
	private String getDataByShellCMD(String command) throws InterruptedException {
		Process process = null;
		List<String> processList = new ArrayList<String>();
		String result = "";
		try {
			process = Runtime.getRuntime().exec(command);
			int exitValue = process.waitFor();
			System.out.println(exitValue);
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";

			while ((line = input.readLine()) != null) {
				processList.add(line);
				result += line + "<BR>";
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// String idnum=processList.get((processList.size()-1));
		// System.out.println(result);
		for (String line : processList) {
			System.out.println(line);
		}
		return result;
	}
	
	public void run(HttpServletRequest request, HttpServletResponse response, String username,String imagename,String usermail,String baseimage,String db_user,String db_passd) {
		// TODO Auto-generated method stub
		try {
			getDataByShellCMD("chmod +x " + request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/sh/db_imagebuild.sh");
			getDataByShellCMD(
					request.getSession().getServletContext().getRealPath("/") + "WEB-INF/sh/db_imagebuild.sh "
							+ username + " " + imagename + " " + usermail + " " + baseimage+" "+db_user+" "+db_passd);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String i_name = "192.168.1.89:5000/" + username + "/" +imagename;

	}
	
	

}
