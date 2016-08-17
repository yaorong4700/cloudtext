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
public class dbtask_MarathonController {
	
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

	@RequestMapping(value = "/dbtask_save_deploy", method = RequestMethod.POST)
	public ModelAndView dbtask_save_deploy(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, InterruptedException, IOException {

		String username = request.getParameter("username");
		String usermail = request.getParameter("usermail");
		String db_user = request.getParameter("db_user");
		String db_passd = request.getParameter("db_passd");
		String mem_db = request.getParameter("mem_db");
		String disk_db = request.getParameter("disk_db");
		String from = request.getParameter("from");
		
		String servicename = request.getParameter("servicename");
		String servicetype = request.getParameter("servicetype");
		String Preg_ID = username + "/" + servicename;
		String Preg_Instances = request.getParameter("preg_instances");

		//double d_Preg_CPU = Double.parseDouble(Preg_CPU);
	//	int i_Preg_Mem = Integer.parseInt(Preg_Mem);
//		int i_Preg_Instances = Integer.parseInt(Preg_Instances);
	//	int tomcat_port = 8080;
		
		Create_db_Image createImage= new Create_db_Image();
		createImage.run(request,response,username,servicename,usermail,"10.97.144.83:5000/mysql",db_user,db_passd);
	
		
		
	////////////////////////////////////////////////////////////////
//		String sql1 = "select * from resourcelist where setname='"+resource+"'";  
//		mysqlconn mysqlconn1=new mysqlconn();
//		Connection cnn1 = mysqlconn1.connSQL();
//	    try  
//	    {  
//	        Statement stmt1 = cnn1.createStatement();  
//	        ResultSet rs1 = stmt1.executeQuery(sql1);  
//	        if(rs1.next())  
//	        {  
//	        	Preg_CPU = rs1.getString(3);//或者为rs.getString(1)，根据数据库中列的值类型确定，参数为第一列  
//	        	Preg_Mem= rs1.getString(4);
//	        	Preg_Instances= rs1.getString(5);
//	        }  
//	    }  
//	    catch (SQLException e)  
//	    {  
//	        e.printStackTrace();  
//	    }  
/////////////////////////////////////////////////////////////////
	    double d_Preg_CPU = 0.1;
		int i_Preg_Mem = Integer.parseInt(mem_db);
		int i_Preg_Disk=Integer.parseInt(disk_db);
		int i_Preg_Instances = 1;
		int mysql_port = 3306;
		
		ObjectMapper mapper = new ObjectMapper();
		marathon marathon_task = mapper.readValue(
				new File(request.getSession().getServletContext().getRealPath("/") + "WEB-INF/json/marathon_m.json"),
				marathon.class);
		Docker_Container container = new Docker_Container();
		Docker docker = new Docker();

		List<PortMap> portMappings = new ArrayList<PortMap>();
		PortMap Portmap0 = new PortMap();
		Portmap0.setContainerPort(mysql_port);
		Portmap0.setHostPort(0);
		Portmap0.setProtocol("tcp");

		PortMap Portmap1 = new PortMap();
		Portmap1.setContainerPort(22);
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
		marathon_task.setCmd(null);
		marathon_task.setCpus(d_Preg_CPU);
		marathon_task.setDisk(i_Preg_Disk);
		marathon_task.setInstances(i_Preg_Instances);
		marathon_task.setMem(i_Preg_Mem);
		
		docker.setImage("10.97.144.83:5000/"+username+"/"+servicename);
		
		docker.setNetwork("BRIDGE");
		docker.setPortMappings(portMappings);
		container.setDocker(docker);
		container.setType("DOCKER");
		marathon_task.setContainer(container);
		FileOutputStream fos = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")
				+ "WEB-INF/User/" + username + "/marathon_m.json");
		mapper.writeValue(fos, marathon_task);
		String run_result = "";
		String appport = "";
		String apphost = "";
		
	
		try {
			getDataByShellCMD("chmod +x " + request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/sh/run_image.sh");
			run_result = getDataByShellCMD(request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/sh/run_image.sh  " + username + " " + servicename);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] task = run_result.split("the return:");
		String task_result=null;
		while(task[1].length()<20)
		{
			task[1] = getDataByShellCMD("curl http://10.97.144.83:8080/v2/apps/"+username+"/"+servicename+"/tasks");
			System.out.println(task_result);
		}
	

		JSONObject jsonobject = JSONObject.fromObject(task[1]);
		JSONArray array = jsonobject.getJSONArray("tasks");
		System.out.println(array);
		JSONObject object = (JSONObject) array.get(0);
		appport = object.getJSONArray("ports").get(0).toString();
		apphost = object.getString("host");
		
		System.out.println(apphost+":"+appport);
		
		ModelAndView mav = null;
		
	if(from.equals("fresh")){
		
	/////// mysql//update////////////////////////////////////////////////////////////////////
			    String sql="update servicelist set ipport=?  where servicename='"+servicename+"'";//注意要有where条件  
			    mysqlconn mysqlconn=new mysqlconn();
				Connection cnn = mysqlconn.connSQL();
			    try{  
			        PreparedStatement preStmt =cnn.prepareStatement(sql);  
			        preStmt.setString(1,apphost+":"+appport);  
			        preStmt.executeUpdate();
			    }  
			    catch (SQLException e)  
			    {  
			        e.printStackTrace();  
			    }  
						/////// mysql///update///////////////////////////////////////////////////////////////////
	}
	else if (from.equals("create")){
		
		/////// mysql//insert////////////////////////////////////////////////////////////////////
				String sql = "insert into servicelist(servicename,servicetype,dbuser,dbpasswd,ipport,dbmem,dbdisk) values(?,?,?,?,?,?,?)";
				mysqlconn mysqlconn=new mysqlconn();
				Connection cnn = mysqlconn.connSQL();
				try {
					PreparedStatement preStmt = cnn.prepareStatement(sql);
					
					preStmt.setString(1, servicename);
					preStmt.setString(2, servicetype);
					preStmt.setString(3, db_user);
					preStmt.setString(4, db_passd);
					preStmt.setString(5, apphost+":"+appport);
					preStmt.setInt(6, i_Preg_Mem);
					preStmt.setInt(7, i_Preg_Disk);
					preStmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				/////// mysql///insert///////////////////////////////////////////////////////////////////
	}
			// 指定要返回的页面为page2.jsp
			mav = new ModelAndView("page4");
			// 将参数返回给页面
			mav.addObject("username", username);
			mav.addObject("usermail", usermail);
		    return mav;
	
		
	}
	
	@RequestMapping(value = "/db_delete", method = RequestMethod.POST)
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
		String sql = "delete from servicelist where servicename='" + filename + "'";  
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
		mav = new ModelAndView("page4");
		mav.addObject("username", username);
		mav.addObject("usermail", usermail);
		return mav;

	}
	

}
