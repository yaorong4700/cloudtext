package com.HelloSpring.fileupload;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.HelloSpring.mysql.mysqlconn;
import com.HelloSpring.zkClient.Zkclient;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class SourceController implements ServletConfigAware, ServletContextAware {
	private ServletContext servletContext;

	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;
	}

	private ServletConfig servletConfig;

	@Override
	public void setServletConfig(ServletConfig arg0) {
		this.servletConfig = arg0;
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

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView upload(HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=GB2312");
		response.setCharacterEncoding("gb2312");
		System.out.println("<HTML>");
		System.out.println("<BODY BGCOLOR='white'>");
		System.out.println("<H1>你的上传</H1>");
		System.out.println("<HR>");
		String FilePath;
		response.setContentType("text/html;charset=GB2312");
		response.setCharacterEncoding("gb2312");
		// 文件上传个数
		int count = 0;
		// 初始化对象
		SmartUpload su = new SmartUpload();
		su.initialize(servletConfig, request, response);
		// 设置文件最大容量
		su.setMaxFileSize(10 * 1024 * 1024 * 1024 * 1024);
		// 设置所有文件最大容量
		// su.setTotalMaxFileSize(100*1024*1024);
		// 设置上传文件类型
		su.setAllowedFilesList("rar,zip,war,jar,odt");
		// 设置禁止上传的文件类型
		try {
			su.setDeniedFilesList("jsp,js,html,css");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 上传文件
		try {
			su.upload();
		} catch (SmartUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String username = su.getRequest().getParameter("username");
		// String file_name = su.getRequest().getParameter("file_name");
		String usermail = su.getRequest().getParameter("usermail");
		String code = su.getRequest().getParameter("code");
		String jdk = su.getRequest().getParameter("jdk");

		String baseimage = "192.168.1.89:5000/tomcat7_jre7";
	if(jdk.equals("jdk-7")){
		 baseimage = "192.168.1.89:5000/tomcat7_jre7";
	}
	else if (jdk.equals("jdk-8"))
	{
		 baseimage = "192.168.1.89:5000/tomcat7_jre8";
	}
		// 文件上传地址
		String filePath = request.getSession().getServletContext().getRealPath("/") + "WEB-INF/User/" + username;
		// 如果文件夹不存在 则创建这个文件夹
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}
		try {
			count = su.save(filePath);
		} catch (SmartUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		com.jspsmart.upload.File tempFile = su.getFiles().getFile(0);
		String filename = null;
		String fileexe = null;
		filename = tempFile.getFileName();
		fileexe = tempFile.getFileExt();
		System.out.println("-------------------------------------------------");
		System.out.println("<BR>");
		System.out.println("表单项名称:" + tempFile.getFieldName());
		System.out.println("<BR>");
		System.out.println("文件名：" + filename);
		System.out.println("<BR>");
		System.out.println("文件长度：" + tempFile.getSize());
		System.out.println("<BR>");
		System.out.println("文件扩展名:" + fileexe);
		System.out.println("<BR>");
		System.out.println("文件全名：" + tempFile.getFilePathName());
		System.out.println("<BR>");
		System.out.println("-------------------------------------------------");
		System.out.println("<BR>");
		System.out.println("上传成功！共" + count + "个文件！");
		System.out.println("<BR>");
		FilePath = filePath + "/" + filename;
		System.out.println(FilePath);
		System.out.println("</BODY>");
		System.out.println("</HTML>");

		System.out.println(filename);
		String filename_0[] = filename.split("\\.");
		System.out.println(filename_0[0]);

		try {
			getDataByShellCMD("chmod +x " + request.getSession().getServletContext().getRealPath("/")
					+ "WEB-INF/sh/create_dockerfile.sh");
			getDataByShellCMD(
					request.getSession().getServletContext().getRealPath("/") + "WEB-INF/sh/create_dockerfile.sh "
							+ username + " " + filename_0[0] + " " + usermail + " " + baseimage);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String imagename = "192.168.1.89:5000/" + username + "/" + filename_0[0];
	//	double[] mesos_info = mesos_info();

		/////// mysql//////////////////////////////////////////////////////////////////////
		String sql = "insert into imagelist(user,status,programname,imagename,resource,service) values(?,?,?,?,?,?)";
		mysqlconn mysqlconn=new mysqlconn();
		Connection cnn = mysqlconn.connSQL();
        
		try {
			PreparedStatement preStmt = cnn.prepareStatement(sql);
			preStmt.setString(1, username);
			preStmt.setString(2,  "not yet");
			preStmt.setString(3, filename_0[0]);
			preStmt.setString(4, imagename);
			preStmt.setString(5, "SET 1");
			preStmt.setString(6, "mysql");
			 preStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/////// mysql//////////////////////////////////////////////////////////////////////

		// 指定要返回的页面为page1.jsp
		ModelAndView mav = new ModelAndView("page1_1_1");
		// 将参数返回给页面

//		mav.addObject("rest_cpus", mesos_info[0]);
//		mav.addObject("rest_mem", mesos_info[1]);
//		mav.addObject("all_cpus", mesos_info[2]);
//		mav.addObject("all_mem", mesos_info[3]);
		mav.addObject("imagename", imagename);
		mav.addObject("filename", filename);
		mav.addObject("filename_0", filename_0[0]);
		mav.addObject("username", username);
		mav.addObject("usermail", usermail);
		mav.addObject("code", code);
		return mav;
	}

}
