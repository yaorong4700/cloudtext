package com.HelloSpring.jenkins;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.HelloSpring.mysql.mysqlconn;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller

public class JenkinsController {
	/**
	 * 此函数实现 后台执行脚本命令
	 * 
	 */
	private String getDataByShellCMD(String command)
			throws InterruptedException, IOException {
		
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
		System.out.println(result);
		for (String line : processList) {
			System.out.println(line);
		}
		return result;
	}

	@RequestMapping(value = "/jenkins", method = RequestMethod.POST)
	public void jenkins(HttpServletRequest request, HttpServletResponse response) throws Exception {
System.out.println("进入 jenkins");
	    PrintWriter writer=response.getWriter();
		String username = request.getParameter("username");
		String code = request.getParameter("code");
		String usermail = request.getParameter("usermail");
		String filename = request.getParameter("filename");
		String imagename = request.getParameter("imagename");
		String pregcpu = request.getParameter("pregcpu");
		String status = request.getParameter("status");
		//https://github.com/yaorong4700/paasweb.git
		String pregmem = request.getParameter("pregmem");
		String preginstances = request.getParameter("preginstances");
		
		
		//writer.println("sadfasdf");
		//System.out.println("fasong");
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		String task=getDataByShellCMD("curl http://10.97.144.83:8080/v2/apps/"+username+"/"+filename+"/tasks");
		while(task.length()<20)
		{
			task = getDataByShellCMD("curl http://10.97.144.83:8080/v2/apps/"+username+"/"+filename+"/tasks");
		}
		JSONObject jsonobject = JSONObject.fromObject(task);
		JSONArray array = jsonobject.getJSONArray("tasks");
		System.out.println(array);
		JSONObject object = (JSONObject) array.get(0);
		String appport = object.getJSONArray("ports").get(0).toString();
		String apphost = object.getString("host");
		System.out.println(apphost+":"+appport);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(
					request.getSession().getServletContext().getRealPath("/") + "WEB-INF/User/"+username+"/"+filename+".xml"); // 使用dom解析xml文件
		
	
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
	
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(doc),
					new StreamResult(new File(request.getSession().getServletContext().getRealPath("/")
							+ "WEB-INF/User/" + username + "/" + filename + ".xml")));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////// mysql//update////////////////////////////////////////////////////////////////////
	    String sql="update applist set appport=?  where programname='"+filename+"'";//注意要有where条件  
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
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println(username);
		System.out.println(code);
		// 将参数返回给页面
		if (code.equals("1")) {
			String jenkinsbuildresult = "[WARN] Failed to authenticate with your SSH keys. Proceeding as anonymousFinished: SUSS";
			getDataByShellCMD("chmod +x " + request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/sh/set_jenkinsbuildagain.sh");
			
					jenkinsbuildresult = getDataByShellCMD(request.getSession().getServletContext().getRealPath("/")
							+ "WEB-INF/sh/set_jenkinsbuildagain.sh  " + username + " " + filename);
					if(jenkinsbuildresult.contains("SUCCESS"))
					{					writer.println("ok");
					}
					else writer.println("false");
		}
	
		
	}
}
