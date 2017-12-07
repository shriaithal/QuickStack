package edu.sjsu.openstack.response;

public class OpenStackMetrics extends GenericResponse{

		private static final long serialVersionUID = 1L;
		private String cpuNumb;
		private String memoryMB;
		private String diskGB;
		
		private String totCpuNumb;
		private String totMemoryMB;
		private String totDiskGB;
		

		public OpenStackMetrics() {
			
		}

		
		public String getTotCpuNumb() {
			return totCpuNumb;
		}


		public void setTotCpuNumb(String totCpuNumb) {
			this.totCpuNumb = totCpuNumb;
		}


		public String getTotMemoryMB() {
			return totMemoryMB;
		}


		public void setTotMemoryMB(String totMemoryMB) {
			this.totMemoryMB = totMemoryMB;
		}


		public String getTotDiskGB() {
			return totDiskGB;
		}


		public void setTotDiskGB(String totDiskGB) {
			this.totDiskGB = totDiskGB;
		}


		public OpenStackMetrics(String cpuNumb, String memoryMB, String diskGB) {
			super();
			this.cpuNumb = cpuNumb;
			this.memoryMB = memoryMB;
			this.diskGB = diskGB;
		}

		
		public String getCpuNumb() {
			return cpuNumb;
		}


		public void setCpuNumb(String cpuNumb) {
			this.cpuNumb = cpuNumb;
		}


		public String getMemoryMB() {
			return memoryMB;
		}


		public void setMemoryMB(String memoryMB) {
			this.memoryMB = memoryMB;
		}


		public String getDiskGB() {
			return diskGB;
		}


		public void setDiskGB(String diskGB) {
			this.diskGB = diskGB;
		}


		@Override
		public String toString() {
			return "openstackMetrics [cpuNumb=" + cpuNumb + ", memoryMB=" + memoryMB + ", diskGB=" + diskGB + "]";
		}


	}


