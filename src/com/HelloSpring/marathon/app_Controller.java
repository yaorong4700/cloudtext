package com.HelloSpring.marathon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.HelloSpring.Create_db_Image;
import com.HelloSpring.model.marathon;
import com.HelloSpring.model.marathon.Docker_Container;
import com.HelloSpring.model.marathon.Docker_Container.Docker;
import com.HelloSpring.model.marathon.Docker_Container.Docker.PortMap;
import com.HelloSpring.mysql.mysqlconn;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class app_Controller {

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
				result += line;
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

	@RequestMapping(value = "/app_stop", method = RequestMethod.POST)
	public ModelAndView app_stop(HttpServletRequest request, HttpServletResponse response)
			throws JsonParseException, JsonMappingException, InterruptedException, IOException {

		String code = request.getParameter("code");
		String from = request.getParameter("from");
		String filename = request.getParameter("filename");
		String filename_0 = request.getParameter("filename_0");

		String username = request.getParameter("username");
		String usermail = request.getParameter("usermail");
		String resource = request.getParameter("resource");
		System.out.print(resource);
		String imagename = request.getParameter("imagename");
		String Preg_ID = username + "/" + filename;
		String Preg_CPU = request.getParameter("preg_cpu");
		String Preg_Mem = request.getParameter("preg_mem");
		String Preg_Instances = request.getParameter("preg_instances");
		String Preg_CMD = null;

		double d_Preg_CPU = Double.parseDouble(Preg_CPU);
		int i_Preg_Mem = Integer.parseInt(Preg_Mem);
		int i_Preg_Instances = Integer.parseInt(Preg_Instances);
		int tomcat_port = 8080;
		i_Preg_Instances = 0;
		ObjectMapper mapper = new ObjectMapper();
		marathon marathon_task = mapper.readValue(
				new File(request.getSession().getServletContext().getRealPath("/") + "WEB-INF/json/marathon_m.json"),
				marathon.class);
		Docker_Container container = new Docker_Container();
		Docker docker = new Docker();

		List<PortMap> portMappings = new ArrayList<PortMap>();
		PortMap Portmap0 = new PortMap();
		Portmap0.setContainerPort(tomcat_port);
		Portmap0.setHostPort(0);
		Portmap0.setProtocol("tcp");

		PortMap Portmap1 = new PortMap();
		Portmap1.setContainerPort(10000);
		Portmap1.setHostPort(0);
		Portmap1.setProtocol("tcp");

		PortMap Portmap2 = new PortMap();
		Portmap2.setContainerPort(10001);
		Portmap2.setHostPort(0);
		Portmap2.setProtocol("tcp");
		portMappings.add(0, Portmap0);
		portMappings.add(1, Portmap1);
		portMappings.add(2, Portmap2);
		marathon_task.setId(Preg_ID);
		marathon_task.setCmd(Preg_CMD);
		marathon_task.setCpus(d_Preg_CPU);
		marathon_task.setInstances(i_Preg_Instances);
		marathon_task.setMem(i_Preg_Mem);
		docker.setImage(imagename);
		docker.setNetwork("BRIDGE");
		docker.setPortMappings(portMappings);
		container.setDocker(docker);
		container.setType("DOCKER");
		marathon_task.setContainer(container);
		FileOutputStream fos = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")
				+ "WEB-INF/User/" + username + "/marathon_m.json");
		mapper.writeValue(fos, marathon_task);
		String run_result = "";

		try {
			getDataByShellCMD(
					"chmod +x " + request.getSession().getServletContext().getRealPath("/") + "WEB-INF/sh/stop_app.sh");
			run_result = getDataByShellCMD(request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/sh/stop_app.sh  " + username + " " + filename);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/////// mysql//update////////////////////////////////////////////////////////////////////
		String sql = "update applist set lisence=?  where programname='" + filename + "'";// 注意要有where条件
		mysqlconn mysqlconn = new mysqlconn();
		Connection cnn = mysqlconn.connSQL();
		try {
			PreparedStatement preStmt = cnn.prepareStatement(sql);
			preStmt.setString(1, "0");
			preStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/////// mysql///update///////////////////////////////////////////////////////////////////
		ModelAndView mav = null;
		// 指定要返回的页面为page2.jsp
		mav = new ModelAndView("page5");
		mav.addObject("username", username);
		mav.addObject("usermail", usermail);
		return mav;

	}

	@RequestMapping(value = "/app_start", method = RequestMethod.POST)
	public ModelAndView app_start(HttpServletRequest request, HttpServletResponse response)
			throws JsonParseException, JsonMappingException, InterruptedException, IOException {

		String code = request.getParameter("code");
		String from = request.getParameter("from");
		String filename = request.getParameter("filename");
		String filename_0 = request.getParameter("filename_0");

		String username = request.getParameter("username");
		String usermail = request.getParameter("usermail");
		String resource = request.getParameter("resource");
		System.out.print(resource);
		String imagename = request.getParameter("imagename");
		String Preg_ID = username + "/" + filename;
		String Preg_CPU = request.getParameter("preg_cpu");
		String Preg_Mem = request.getParameter("preg_mem");
		String Preg_Instances = request.getParameter("preg_instances");
		String Preg_CMD = null;

		double d_Preg_CPU = Double.parseDouble(Preg_CPU);
		int i_Preg_Mem = Integer.parseInt(Preg_Mem);
		int i_Preg_Instances = Integer.parseInt(Preg_Instances);
		int tomcat_port = 8080;
		i_Preg_Instances = 1;
		ObjectMapper mapper = new ObjectMapper();
		marathon marathon_task = mapper.readValue(
				new File(request.getSession().getServletContext().getRealPath("/") + "WEB-INF/json/marathon_m.json"),
				marathon.class);
		Docker_Container container = new Docker_Container();
		Docker docker = new Docker();

		List<PortMap> portMappings = new ArrayList<PortMap>();
		PortMap Portmap0 = new PortMap();
		Portmap0.setContainerPort(tomcat_port);
		Portmap0.setHostPort(0);
		Portmap0.setProtocol("tcp");

		PortMap Portmap1 = new PortMap();
		Portmap1.setContainerPort(10000);
		Portmap1.setHostPort(0);
		Portmap1.setProtocol("tcp");

		PortMap Portmap2 = new PortMap();
		Portmap2.setContainerPort(10001);
		Portmap2.setHostPort(0);
		Portmap2.setProtocol("tcp");
		portMappings.add(0, Portmap0);
		portMappings.add(1, Portmap1);
		portMappings.add(2, Portmap2);
		marathon_task.setId(Preg_ID);
		marathon_task.setCmd(Preg_CMD);
		marathon_task.setCpus(d_Preg_CPU);
		marathon_task.setInstances(i_Preg_Instances);
		marathon_task.setMem(i_Preg_Mem);
		docker.setImage(imagename);
		docker.setNetwork("BRIDGE");
		docker.setPortMappings(portMappings);
		container.setDocker(docker);
		container.setType("DOCKER");
		marathon_task.setContainer(container);
		FileOutputStream fos = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")
				+ "WEB-INF/User/" + username + "/marathon_m.json");
		mapper.writeValue(fos, marathon_task);
		String run_result = "";

		try {
			getDataByShellCMD(
					"chmod +x " + request.getSession().getServletContext().getRealPath("/") + "WEB-INF/sh/stop_app.sh");
			run_result = getDataByShellCMD(request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/sh/stop_app.sh  " + username + " " + filename);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String task = getDataByShellCMD(
				"curl http://10.97.144.83:8080/v2/apps/" + username + "/" + filename + "/tasks");
		while (task.length() < 20) {
			task = getDataByShellCMD("curl http://10.97.144.83:8080/v2/apps/" + username + "/" + filename + "/tasks");
		}
		JSONObject jsonobject = JSONObject.fromObject(task);
		JSONArray array = jsonobject.getJSONArray("tasks");
		System.out.println(array);
		JSONObject object = (JSONObject) array.get(0);
		String appport = object.getJSONArray("ports").get(0).toString();
		String apphost = object.getString("host");
		System.out.println(apphost + ":" + appport);

		/////// mysql//update////////////////////////////////////////////////////////////////////
		String sql = "update applist set lisence=?,appport=?  where programname='" + filename + "'";// 注意要有where条件
		mysqlconn mysqlconn = new mysqlconn();
		Connection cnn = mysqlconn.connSQL();
		try {
			PreparedStatement preStmt = cnn.prepareStatement(sql);
			preStmt.setString(1, "1");
			preStmt.setString(2, apphost + ":" + appport);
			preStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/////// mysql///update///////////////////////////////////////////////////////////////////
		ModelAndView mav = null;
		// 指定要返回的页面为page2.jsp
		mav = new ModelAndView("page5");
		mav.addObject("username", username);
		mav.addObject("usermail", usermail);
		return mav;

	}

	@RequestMapping(value = "/app_delete", method = RequestMethod.POST)
	public ModelAndView app_delate(HttpServletRequest request, HttpServletResponse response)
			throws JsonParseException, JsonMappingException, InterruptedException, IOException {

	
		String filename = request.getParameter("filename");
		String username = request.getParameter("username");
		String usermail = request.getParameter("usermail");

			String run_result = "";

		try {
			getDataByShellCMD(
					"chmod +x " + request.getSession().getServletContext().getRealPath("/") + "WEB-INF/sh/delete_app.sh");
			run_result = getDataByShellCMD(request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/sh/delete_app.sh  " + username + " " + filename);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/////// mysql//delete////////////////////////////////////////////////////////////////////
		String sql = "delete from applist where programname='" + filename + "'";  
		mysqlconn mysqlconn = new mysqlconn();
		Connection cnn = mysqlconn.connSQL();
	    try  
	    {  
	    	PreparedStatement preStmt = cnn.prepareStatement(sql);
			preStmt.executeUpdate();
	    }  
	    catch (SQLException e)  
	    {  
	        e.printStackTrace();  
	    }  
		/////// mysql///delete///////////////////////////////////////////////////////////////////
		ModelAndView mav = null;
		// 指定要返回的页面为page2.jsp
		mav = new ModelAndView("page5");
		mav.addObject("username", username);
		mav.addObject("usermail", usermail);
		return mav;

	}
	
	@RequestMapping(value = "/image_delete", method = RequestMethod.POST)
	public ModelAndView image_delete(HttpServletRequest request, HttpServletResponse response)
			throws JsonParseException, JsonMappingException, InterruptedException, IOException {

		
		String imagename = request.getParameter("imagename");
		String filename = request.getParameter("filename");
		String username = request.getParameter("username");
		String usermail = request.getParameter("usermail");

		/////// mysql//delete////////////////////////////////////////////////////////////////////
		String sql = "delete from imagelist where imagename='" + imagename + "'";  
		mysqlconn mysqlconn = new mysqlconn();
		Connection cnn = mysqlconn.connSQL();
	    try  
	    {  
	    	PreparedStatement preStmt = cnn.prepareStatement(sql);
			preStmt.executeUpdate();
	    }  
	    catch (SQLException e)  
	    {  
	        e.printStackTrace();  
	    }  
		/////// mysql///delete///////////////////////////////////////////////////////////////////
		ModelAndView mav = null;
		// 指定要返回的页面为page2.jsp
		mav = new ModelAndView("page1_1_1");
		mav.addObject("username", username);
		mav.addObject("usermail", usermail);
		return mav;
	}
	@RequestMapping(value = "/resource_delete", method = RequestMethod.POST)
	public ModelAndView resource_delete(HttpServletRequest request, HttpServletResponse response)
			throws JsonParseException, JsonMappingException, InterruptedException, IOException {
		
		String resourcename = request.getParameter("resourcename");
		String username = request.getParameter("username");
		String usermail = request.getParameter("usermail");

		/////// mysql//delete////////////////////////////////////////////////////////////////////
		String sql = "delete from resourcelist where setname='" + resourcename + "'";  
		mysqlconn mysqlconn = new mysqlconn();
		Connection cnn = mysqlconn.connSQL();
	    try  
	    {  
	    	PreparedStatement preStmt = cnn.prepareStatement(sql);
			preStmt.executeUpdate();
	    }  
	    catch (SQLException e)  
	    {  
	        e.printStackTrace();  
	    }  
		/////// mysql///delete///////////////////////////////////////////////////////////////////
		ModelAndView mav = null;
		// 指定要返回的页面为page2.jsp
		mav = new ModelAndView("page2");
		mav.addObject("username", username);
		mav.addObject("usermail", usermail);
		return mav;
	}
	
}
