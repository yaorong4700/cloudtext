package com.HelloSpring.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class mesos_slaves {
	private List<Slaves> slaves;

	public void setSlaves(List<Slaves> slaves) {
		this.slaves = slaves;
	}

	public List<Slaves> getSlaves() {
		return slaves;
	}

	public static class Slaves {

		private String id;
		private String pid;
		private String hostname;

		private double registered_time;
		private Resources resources;
		private UsedResources used_resources;
		private OfferedResources offered_resources;
		private ReservedResources reserved_resources;
		private UnreservedResources unreserved_resources;
		private Attributes attributes;
		private boolean active;
		private String version;
		private ReservedResourcesFull reserved_resources_full;
		private List<UsedResourcesFull> used_resources_full;
		private List<String> offered_resources_full;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getPid() {
			return pid;
		}

		public void setPid(String pid) {
			this.pid = pid;
		}

		public String getHostname() {
			return hostname;
		}

		public void setHostname(String hostname) {
			this.hostname = hostname;
		}

		public double getRegistered_time() {
			return registered_time;
		}

		public void setRegistered_time(double registered_time) {
			this.registered_time = registered_time;
		}

		public Resources getResources() {
			return resources;
		}

		public void setResources(Resources resources) {
			this.resources = resources;
		}

		public UsedResources getUsed_resources() {
			return used_resources;
		}

		public void setUsed_resources(UsedResources used_resources) {
			this.used_resources = used_resources;
		}

		public OfferedResources getOffered_resources() {
			return offered_resources;
		}

		public void setOffered_resources(OfferedResources offered_resources) {
			this.offered_resources = offered_resources;
		}

		public ReservedResources getReserved_resources() {
			return reserved_resources;
		}

		public void setReserved_resources(ReservedResources reserved_resources) {
			this.reserved_resources = reserved_resources;
		}

		public UnreservedResources getUnreserved_resources() {
			return unreserved_resources;
		}

		public void setUnreserved_resources(UnreservedResources unreserved_resources) {
			this.unreserved_resources = unreserved_resources;
		}

		public Attributes getAttributes() {
			return attributes;
		}

		public void setAttributes(Attributes attributes) {
			this.attributes = attributes;
		}

		public boolean isActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public ReservedResourcesFull getReserved_resources_full() {
			return reserved_resources_full;
		}

		public void setReserved_resources_full(ReservedResourcesFull reserved_resources_full) {
			this.reserved_resources_full = reserved_resources_full;
		}

		public List<UsedResourcesFull> getUsed_resources_full() {
			return used_resources_full;
		}

		public void setUsed_resources_full(List<UsedResourcesFull> used_resources_full) {
			this.used_resources_full = used_resources_full;
		}

		public List<String> getOffered_resources_full() {
			return offered_resources_full;
		}

		public void setOffered_resources_full(List<String> offered_resources_full) {
			this.offered_resources_full = offered_resources_full;
		}

		public static class Resources {

			private double cpus;
			private double disk;
			private double mem;
			private String ports;

			public void setCpus(double cpus) {
				this.cpus = cpus;
			}

			public double getCpus() {
				return cpus;
			}

			public void setDisk(double disk) {
				this.disk = disk;
			}

			public double getDisk() {
				return disk;
			}

			public void setMem(double mem) {
				this.mem = mem;
			}

			public double getMem() {
				return mem;
			}

			public void setPorts(String ports) {
				this.ports = ports;
			}

			public String getPorts() {
				return ports;
			}

		}

		public static class UsedResources {

			private double cpus;
			private double disk;
			private double mem;
			private String ports;

			public void setCpus(double cpus) {
				this.cpus = cpus;
			}

			public double getCpus() {
				return cpus;
			}

			public void setDisk(double disk) {
				this.disk = disk;
			}

			public double getDisk() {
				return disk;
			}

			public void setMem(double mem) {
				this.mem = mem;
			}

			public double getMem() {
				return mem;
			}

			public void setPorts(String ports) {
				this.ports = ports;
			}

			public String getPorts() {
				return ports;
			}

		}

		public static class OfferedResources {

			private double cpus;
			private double disk;
			private double mem;

			public void setCpus(double cpus) {
				this.cpus = cpus;
			}

			public double getCpus() {
				return cpus;
			}

			public void setDisk(double disk) {
				this.disk = disk;
			}

			public double getDisk() {
				return disk;
			}

			public void setMem(double mem) {
				this.mem = mem;
			}

			public double getMem() {
				return mem;
			}

		}

		public static class ReservedResources {
			private double cpus;
			private double disk;
			private double mem;
			private String ports;

			public void setCpus(double cpus) {
				this.cpus = cpus;
			}

			public double getCpus() {
				return cpus;
			}

			public void setDisk(double disk) {
				this.disk = disk;
			}

			public double getDisk() {
				return disk;
			}

			public void setMem(double mem) {
				this.mem = mem;
			}

			public double getMem() {
				return mem;
			}

			public void setPorts(String ports) {
				this.ports = ports;
			}

			public String getPorts() {
				return ports;
			}

		}

		public static class UnreservedResources {

			private double cpus;
			private double disk;
			private double mem;
			private String ports;

			public void setCpus(double cpus) {
				this.cpus = cpus;
			}

			public double getCpus() {
				return cpus;
			}

			public void setDisk(double disk) {
				this.disk = disk;
			}

			public double getDisk() {
				return disk;
			}

			public void setMem(double mem) {
				this.mem = mem;
			}

			public double getMem() {
				return mem;
			}

			public void setPorts(String ports) {
				this.ports = ports;
			}

			public String getPorts() {
				return ports;
			}

		}

		public static class Attributes {

		}

		public static class ReservedResourcesFull {

		}

		public static class Scalar {

			private double value;

			public void setValue(double value) {
				this.value = value;
			}

			public double getValue() {
				return value;
			}

		}

		public static class UsedResourcesFull {

			private String name;
			private String type;
			private Scalar scalar;
			private String role;
			

			public void setName(String name) {
				this.name = name;
			}

			public String getName() {
				return name;
			}

			public void setType(String type) {
				this.type = type;
			}

			public String getType() {
				return type;
			}

			public void setScalar(Scalar scalar) {
				this.scalar = scalar;
			}

			public Scalar getScalar() {
				return scalar;
			}

			public void setRole(String role) {
				this.role = role;
			}

			public String getRole() {
				return role;
			}

		}
	}

}
