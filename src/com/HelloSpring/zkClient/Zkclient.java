package com.HelloSpring.zkClient;

import com.github.zkclient.ZkClient;

import net.sf.json.JSONObject;

public class Zkclient {

	public Zkclient() {
	}

	public String getip() {

		String serverList = "10.97.144.84:2181,10.97.144.82:2181,10.97.144.83:2181";
		ZkClient zkClient = new ZkClient(serverList, 10000, 10000);
		boolean e = zkClient.exists("/mesos");
		zkClient.getChildren("/mesos");
		String aa;
		String[] bb;
		int min = 65535;
		int index = 0;
		for (int i = 0; i < zkClient.getChildren("/mesos").size(); i++) {
			aa = zkClient.getChildren("/mesos").get(i).toString();
			if ((aa.equals("log_replicas"))) {
			} else {
				// writer.println(aa);
				bb = aa.split("_");
				if (Integer.parseInt(bb[1]) < min) {
					min = Integer.parseInt(bb[1]);
					index = i;
				}
			}
		}
		// writer.println(min);
		// writer.println(index);
		String res = new String(zkClient.readData("/mesos/" + zkClient.getChildren("/mesos").get(index)));
		JSONObject jsonobject = JSONObject.fromObject(res);
		Object ob = jsonobject.get("address");
		JSONObject jo = JSONObject.fromObject(ob);
		String ip = (String) jo.get("ip");
	//	System.out.println(ip);
		return ip;
	}

}
