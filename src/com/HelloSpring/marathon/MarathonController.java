package com.HelloSpring.marathon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.HelloSpring.model.marathon;
import com.HelloSpring.model.marathon.Docker_Container;
import com.HelloSpring.model.marathon.Docker_Container.Docker;
import com.HelloSpring.model.marathon.Docker_Container.Docker.PortMap;
import com.HelloSpring.mysql.mysqlconn;
import com.HelloSpring.zkClient.Zkclient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class MarathonController {
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

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, InterruptedException, IOException {

		String username = request.getParameter("username");
		String usermail = request.getParameter("usermail");
		String filename = request.getParameter("filename");
		String filename_0 = request.getParameter("filename_0");
		String setname = request.getParameter("setname");
		String code = request.getParameter("code");
		if (code.equals("1")) {
			filename_0 = filename;
		}
		String Preg_ID = username + "/" + filename;
		String Preg_CPU = request.getParameter("preg_cpu");
		String Preg_Mem = request.getParameter("preg_mem");
		String Preg_Instances = request.getParameter("preg_instances");
		String Preg_CMD = null;
		double d_Preg_CPU = Double.parseDouble(Preg_CPU);
		int i_Preg_Mem = Integer.parseInt(Preg_Mem);
		int i_Preg_Instances = Integer.parseInt(Preg_Instances);
		int tomcat_port = 8080;
		String imagename = "hscnimages/" + username + "/" + filename;
		ModelAndView mav = null;
	
	/////// mysql//insert////////////////////////////////////////////////////////////////////
			String sql = "insert into resourcelist(user,setname,cpu,mem,instance) values(?,?,?,?,?)";
			mysqlconn mysqlconn=new mysqlconn();
			Connection cnn = mysqlconn.connSQL();
			try {
				PreparedStatement preStmt = cnn.prepareStatement(sql);
				
				preStmt.setString(1, username);
				preStmt.setString(2, setname);
				preStmt.setString(3, Preg_CPU);
				preStmt.setString(4, Preg_Mem);
				preStmt.setString(5, Preg_Instances);
				
				preStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			/////// mysql///insert///////////////////////////////////////////////////////////////////
		
		// 指定要返回的页面为page2.jsp
		mav = new ModelAndView("page2");
	
		// 将参数返回给页面
		
		mav.addObject("filename", filename);
		mav.addObject("code", code);
		mav.addObject("username", username);
		mav.addObject("usermail", usermail);
		mav.addObject("imagename", imagename);
		mav.addObject("filename_0", filename_0);
	    return mav;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/marathon", method = RequestMethod.POST)
	public ModelAndView marathon(HttpServletRequest request, HttpServletResponse response) throws Exception {

String src_github_link = request.getParameter("src_github_link");
String src_github_branch = request.getParameter("src_github_branch");
String code = request.getParameter("code");
String from = request.getParameter("from");
String filename = request.getParameter("filename");
String filename_0 = request.getParameter("filename_0");
if (code.equals("1")) {
	String[] a=src_github_link.split("\\.g");
	String[] b= a[0].split(".com/");
	String[] c=b[1].split("/");
	 filename_0=c[1];
	filename = filename_0;
	System.out.print(filename_0);
}
System.out.println(from);
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
		if(resource.equals("00"))
		{}
		else{
	////////////////////////////////////////////////////////////////
		String sql1 = "select * from resourcelist where setname='"+resource+"'";  
		mysqlconn mysqlconn1=new mysqlconn();
		Connection cnn1 = mysqlconn1.connSQL();
	    try  
	    {  
	        Statement stmt1 = cnn1.createStatement();  
	        ResultSet rs1 = stmt1.executeQuery(sql1);  
	        if(rs1.next())  
	        {  
	        	Preg_CPU = rs1.getString(3);//或者为rs.getString(1)，根据数据库中列的值类型确定，参数为第一列  
	        	Preg_Mem= rs1.getString(4);
	        	Preg_Instances= rs1.getString(5);
	        }  
	    }  
	    catch (SQLException e)  
	    {  
	        e.printStackTrace();  
	    }  
/////////////////////////////////////////////////////////////////
		}
	    double d_Preg_CPU = Double.parseDouble(Preg_CPU);
		int i_Preg_Mem = Integer.parseInt(Preg_Mem);
		int i_Preg_Instances = Integer.parseInt(Preg_Instances);
		int tomcat_port = 8080;
		
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
		if (code.equals("0")) {
			docker.setImage(imagename);
		} else if (code.equals("1")) {
			docker.setImage("10.97.144.83:5000/tomcat7_jre7");
		}
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
					+ "WEB-INF/sh/run_image.sh  " + username + " " + filename);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] task = run_result.split("the return:");
		String task_result=null;
		while(task[1].length()<20)
		{
			task[1] = getDataByShellCMD("curl http://10.97.144.83:8080/v2/apps/"+username+"/"+filename+"/tasks");
			System.out.println(task_result);
		}
	
		JSONObject jsonobject = JSONObject.fromObject(task[1]);
		JSONArray array = jsonobject.getJSONArray("tasks");
		System.out.println(array);
		JSONObject object = (JSONObject) array.get(0);
		appport = object.getJSONArray("ports").get(0).toString();
		apphost = object.getString("host");
		
		System.out.println(apphost+":"+appport);
	

		////// 持续集成实现代码/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (code.equals("1")) {
		
			ModelAndView mav = null;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setIgnoringElementContentWhitespace(true);
			try {
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(
						request.getSession().getServletContext().getRealPath("/") + "WEB-INF/resource/mode_tomcat.xml"); // 使用dom解析xml文件
				/** 修改xml: github link */
				NodeList sonlist = doc.getElementsByTagName("hudson.plugins.git.UserRemoteConfig");
				for (int i = 0; i < sonlist.getLength(); i++) // 循环处理对象
				{
					Element son = (Element) sonlist.item(i);
					for (Node node = son.getFirstChild(); node != null; node = node.getNextSibling()) {
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							node.getFirstChild().setNodeValue(src_github_link);
							// System.out.println(node.getFirstChild().getNodeValue());
						}
					}
				}

				/** 修改xml: github branch */
				NodeList sonlist1 = doc.getElementsByTagName("hudson.plugins.git.BranchSpec");
				for (int i = 0; i < sonlist1.getLength(); i++) // 循环处理对象
				{
					Element son = (Element) sonlist1.item(i);
					for (Node node = son.getFirstChild(); node != null; node = node.getNextSibling()) {
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							node.getFirstChild().setNodeValue("*/" + src_github_branch);
						}
					}
				}
				/** 修改xml: tomcat 的端口 */
				NodeList sonlist2 = doc.getElementsByTagName("hudson.plugins.deploy.tomcat.Tomcat7xAdapter");
				for (int i = 0; i < sonlist2.getLength(); i++) // 循环处理对象
				{
					Element son = (Element) sonlist2.item(i);
					for (Node node = son.getFirstChild(); node != null; node = node.getNextSibling()) {
						if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("url")) {
							node.getFirstChild().setNodeValue("http://"+apphost+":" + appport);
						}
					}
				}

				/** 修改xml: sourcename */
				NodeList sonlist3 = doc.getElementsByTagName("hudson.plugins.deploy.DeployPublisher");
				for (int i = 0; i < sonlist3.getLength(); i++) // 循环处理对象
				{
					Element son = (Element) sonlist3.item(i);
					for (Node node = son.getFirstChild(); node != null; node = node.getNextSibling()) {
						if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("contextPath")) {
							node.getFirstChild().setNodeValue(filename_0);
						}
						if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("war")) {
							node.getFirstChild().setNodeValue("dist/" + filename_0 + ".war");
						}
					}
				}
				// System.out.println(username);
				// System.out.println(filename_0);
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer former = factory.newTransformer();
				former.transform(new DOMSource(doc),
						new StreamResult(new File(request.getSession().getServletContext().getRealPath("/")
								+ "WEB-INF/User/" + username + "/" + filename_0 + ".xml")));
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			String jenkins_shResult = "";
			String jenkins_BuiltResult = "";
			try {
				getDataByShellCMD("chmod +x " + request.getSession().getServletContext().getRealPath("/")
						+ "WEB-INF/sh/using_jenkins.sh");
				jenkins_shResult = getDataByShellCMD(request.getSession().getServletContext().getRealPath("/")
						+ "WEB-INF/sh/using_jenkins.sh  " + username + " " + filename_0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		String status = "ok";
		// 将参数返回给页面
		if (code.equals("0")) {
			status = "ok";
		} else if (code.equals("1")) {
			String jenkinsbuildresult = "[WARN] Failed to authenticate with your SSH keys. Proceeding as anonymousFinished: SUSS";
			status = "wait";
			int count=0;
			while (!(jenkinsbuildresult.contains("Finished: SUCCESS"))) {
				count+=1;
				try {
					getDataByShellCMD("chmod +x " + request.getSession().getServletContext().getRealPath("/")
							+ "WEB-INF/sh/get_jenkinsbuildresult.sh");
					jenkinsbuildresult = getDataByShellCMD(request.getSession().getServletContext().getRealPath("/")
							+ "WEB-INF/sh/get_jenkinsbuildresult.sh  " + username + " " + filename_0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(count>10){ status = "fail";break; }
					status = "ok";
			}
		}
	//	String imagename = "hscnimages/" + username + "/" + filename;
	

				
			/////// mysql//////////////////////////////////////////////////////////////////////
				String sql = "insert into applist(user,status,programname,imagename,cpu,mem,lisence,CD,appport) values(?,?,?,?,?,?,?,?,?)";
				mysqlconn mysqlconn=new mysqlconn();
				Connection cnn = mysqlconn.connSQL();
				try {
					PreparedStatement preStmt = cnn.prepareStatement(sql);
					preStmt.setString(1, username);
					preStmt.setString(2, status);
					preStmt.setString(3, filename);
					preStmt.setString(4, imagename);
					preStmt.setString(5, Preg_CPU);
					preStmt.setString(6, Preg_Mem);
					preStmt.setString(7, Preg_Instances);
					preStmt.setString(8, code);
					preStmt.setString(9, apphost+":"+appport);
					 preStmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				/////// mysql//////////////////////////////////////////////////////////////////////
			
		
		// 指定要返回的页面为page1.jsp
		ModelAndView mav = new ModelAndView("page5");
		mav.addObject("username", username);
		mav.addObject("usermail", usermail);
		return mav;
	}

}