package com.HelloSpring.choices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.HelloSpring.mysql.mysqlconn;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class ChoiceCenter {
	
	
	
	@RequestMapping(value = "/choice0", method = RequestMethod.POST)
	public ModelAndView choice0(String username, String usermail, String code) {
		// 验证传递过来的参数是否正确，否则返回到登陆页面。
		ModelAndView mav = null;
		if (code.equals("0")) {
			// 指定要返回的页面为page0.jsp
			mav = new ModelAndView("page1_1");
			// 将参数返回给页面
			mav.addObject("username", username);
			mav.addObject("usermail", usermail);
			mav.addObject("code", code);
		}
		return mav;
	}
	@RequestMapping(value = "/choice1", method = RequestMethod.POST)
	public ModelAndView choice1(String username, String usermail, String code) {
		// 验证传递过来的参数是否正确，否则返回到登陆页面。
		ModelAndView mav = null;
		if(code.equals("1")){
			// 指定要返回的页面为page0.jsp
			mav = new ModelAndView("page1_2");
			// 将参数返回给页面
			mav.addObject("username", username);
			mav.addObject("usermail", usermail);
			mav.addObject("code", code);
		}
		return mav;
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
	private double[] mesos_info() throws InterruptedException, JsonParseException, JsonMappingException, IOException {
		double[] result = { 1, 1, 1, 1 };
		String json_info = getDataByShellCMD("curl  http://192.168.1.89:5050/master/slaves");
		// json_info="{\"slaves\":[{\"id\":\"6a7936fa-334a-4aa8-a695-2d68af141349-S0\",\"pid\":\"slave(1)@192.168.1.89:5051\",\"hostname\":\"wpdocker\",\"registered_time\":1468488699.76697,\"resources\":{\"cpus\":4.1,\"disk\":460276.0,\"mem\":2823.0,\"ports\":\"[31000-32000]\"},\"used_resources\":{\"cpus\":1.0,\"disk\":0.0,\"mem\":128.0,\"ports\":\"[31002-31004]\"},\"offered_resources\":{\"cpus\":0.0,\"disk\":0.0,\"mem\":0.0},\"reserved_resources\":{},\"unreserved_resources\":{\"cpus\":4.0,\"disk\":460276.0,\"mem\":2823.0,\"ports\":\"[31000-32000]\"},\"attributes\":{},\"active\":true,\"version\":\"0.28.1\",\"reserved_resources_full\":{},\"used_resources_full\":[{\"name\":\"cpus\",\"type\":\"SCALAR\",\"scalar\":{\"value\":0.1},\"role\":\"*\"},{\"name\":\"mem\",\"type\":\"SCALAR\",\"scalar\":{\"value\":128.0},\"role\":\"*\"},{\"name\":\"ports\",\"type\":\"RANGES\",\"ranges\":{\"range\":[{\"begin\":31002,\"end\":31004}]},\"role\":\"*\"}],\"offered_resources_full\":[]},{\"id\":\"6a7936fa-334a-4aa8-a695-2d68af141349-S0\",\"pid\":\"slave(1)@192.168.1.89:5051\",\"hostname\":\"wpdocker\",\"registered_time\":1468488699.76697,\"resources\":{\"cpus\":4.0,\"disk\":460276.0,\"mem\":2823.0,\"ports\":\"[31000-32000]\"},\"used_resources\":{\"cpus\":1.0,\"disk\":0.0,\"mem\":128.0,\"ports\":\"[31002-31004]\"},\"offered_resources\":{\"cpus\":0.0,\"disk\":0.0,\"mem\":0.0},\"reserved_resources\":{},\"unreserved_resources\":{\"cpus\":4.0,\"disk\":460276.0,\"mem\":2823.0,\"ports\":\"[31000-32000]\"},\"attributes\":{},\"active\":true,\"version\":\"0.28.1\",\"reserved_resources_full\":{},\"used_resources_full\":[{\"name\":\"cpus\",\"type\":\"SCALAR\",\"scalar\":{\"value\":0.1},\"role\":\"*\"},{\"name\":\"mem\",\"type\":\"SCALAR\",\"scalar\":{\"value\":128.0},\"role\":\"*\"},{\"name\":\"ports\",\"type\":\"RANGES\",\"ranges\":{\"range\":[{\"begin\":31002,\"end\":31004}]},\"role\":\"*\"}],\"offered_resources_full\":[]}]}";
		JSONObject jo = JSONObject.fromObject(json_info);
		JSONArray array=jo.getJSONArray("slaves");
		double sum_mem =0,used_cpus = 0,used_mem = 0, sum_cpus = 0;
		//String sum_mem =null,used_cpus = null,used_mem = null, sum_cpus = null;
		for(int i=0; i<array.size(); i++){
			
			String resources=array.getJSONObject(i).get("resources").toString();
			JSONObject jo_resources = JSONObject.fromObject(resources);	
			
			String used_resources=array.getJSONObject(i).get("used_resources").toString();
			JSONObject jo_used_resources = JSONObject.fromObject(used_resources);	
			
			 used_cpus+=Double.parseDouble(jo_used_resources.get("cpus").toString());
			 used_mem+=Double.parseDouble(jo_used_resources.get("mem").toString());
			 
			 sum_cpus+=Double.parseDouble(jo_resources.get("cpus").toString());
			 sum_mem+=Double.parseDouble(jo_resources.get("mem").toString());
		}
		result[0]=sum_cpus-used_cpus;
		result[1]=sum_mem-used_mem;
		result[2]=sum_cpus;
		result[3]=sum_mem;
	/////// mysql//update////////////////////////////////////////////////////////////////////
		    String sql="update resourceall set restcpu=?,restmem=?,allcpu=?,allmem=?  where num=1";//注意要有where条件  
		    mysqlconn mysqlconn=new mysqlconn();
			Connection cnn = mysqlconn.connSQL();
		    try{  
		        PreparedStatement preStmt =cnn.prepareStatement(sql);  
		        preStmt.setDouble(1,result[0]);  
		        preStmt.setDouble(2,result[1]);  
		        preStmt.setDouble(3,result[2]);  
		        preStmt.setDouble(4,result[3]);  
		        preStmt.executeUpdate();
		    }  
		    catch (SQLException e)  
		    {  
		        e.printStackTrace();  
		    }  
	/////// mysql///update///////////////////////////////////////////////////////////////////
		return result;
	}
	
	@RequestMapping(value = "/marathon_1", method = RequestMethod.POST)
	public ModelAndView marathon_1(String username,String usermail,String code, String src_github_link,String src_github_branch) throws JsonParseException, JsonMappingException, InterruptedException, IOException {
		String[] a=src_github_link.split("\\.g");
		String[] b= a[0].split(".com/");
		String[] c=b[1].split("/");
		String filename=c[1];
		ModelAndView mav = null;
		
		
			// 指定要返回的页面为page0.jsp
			mav = new ModelAndView("page2");
			double[] mesos_info = mesos_info();
			// 将参数返回给页面
			mav.addObject("rest_cpus", mesos_info[0]);
			mav.addObject("rest_mem", mesos_info[1]);
			mav.addObject("all_cpus", mesos_info[2]);
			mav.addObject("all_mem", mesos_info[3]);
			
			mav.addObject("filename", filename);
			mav.addObject("code", code);
			mav.addObject("username", username);
			mav.addObject("usermail", usermail);
			mav.addObject("src_github_link", src_github_link);
			mav.addObject("src_github_branch", src_github_branch);
			
		return mav;
	}
	
	@RequestMapping(value = "/marathon_0", method = RequestMethod.POST)
	public ModelAndView marathon_0(String username,String usermail,String code, String imagename,String filename,String filename_0) throws JsonParseException, JsonMappingException, InterruptedException, IOException {

		ModelAndView mav = null;
			// 指定要返回的页面为page0.jsp
			mav = new ModelAndView("page2");
			double[] mesos_info = mesos_info();
			// 将参数返回给页面
			mav.addObject("rest_cpus", mesos_info[0]);
			mav.addObject("rest_mem", mesos_info[1]);
			mav.addObject("all_cpus", mesos_info[2]);
			mav.addObject("all_mem", mesos_info[3]);
			
			mav.addObject("filename", filename);
			mav.addObject("code", code);
			mav.addObject("username", username);
			mav.addObject("usermail", usermail);
			mav.addObject("imagename", imagename);
			mav.addObject("filename_0", filename_0);
		return mav;
	}
	
	
	
	

}
