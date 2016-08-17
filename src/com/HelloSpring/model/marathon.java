package com.HelloSpring.model;

import java.util.List;


public class marathon {
	private String id;
	private String cmd;
	private double cpus;
	private int mem;
	private int disk;
	private int instances;
	private Docker_Container container;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public double getCpus() {
		return cpus;
	}

	public void setCpus(double cpus) {
		this.cpus = cpus;
	}

	public int getMem() {
		return mem;
	}

	public void setMem(int mem) {
		this.mem = mem;
	}

	public int getInstances() {
		return instances;
	}

	public void setInstances(int instances) {
		this.instances = instances;
	}

	public Docker_Container getContainer() {
		return container;
	}

	public void setContainer(Docker_Container container) {
		this.container = container;
	}

	

	public int getDisk() {
		return disk;
	}

	public void setDisk(int disk) {
		this.disk = disk;
	}



	public static class Docker_Container {
		private Docker docker;
		private String type;

		public Docker getDocker() {
			return docker;
		}

		public void setDocker(Docker docker) {
			this.docker = docker;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public static class Docker {
			private String network;
			private String image;
			//private PortMap[] portMappings;
			private List<PortMap> portMappings;

			public String getNetwork() {
				return network;
			}

			public void setNetwork(String network) {
				this.network = network;
			}

			public String getImage() {
				return image;
			}

			public void setImage(String image) {
				this.image = image;
			}

			public  List<PortMap> getPortMappings() {
				return portMappings;
			}

			public void setPortMappings( List<PortMap> portMappings) {
				this.portMappings = portMappings;
			}

			public static class PortMap {
				private int containerPort;
				private String protocol;
				private int hostPort;

				public int getContainerPort() {
					return containerPort;
				}

				public void setContainerPort(int containerPort) {
					this.containerPort = containerPort;
				}

				public String getProtocol() {
					return protocol;
				}

				public void setProtocol(String protocol) {
					this.protocol = protocol;
				}

				public int getHostPort() {
					return hostPort;
				}

				public void setHostPort(int hostPort) {
					this.hostPort = hostPort;
				}
			}
		}
	}
}
