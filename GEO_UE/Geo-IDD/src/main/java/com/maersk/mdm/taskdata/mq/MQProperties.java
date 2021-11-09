package com.maersk.mdm.taskdata.mq;


public class MQProperties {

private String hostNameMQ;
	
	private int mqPort;
	
	private String queueManager;
	
	private String queueChannel;
	
	private String queueName;

	public MQProperties() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getHostNameMQ() {
		return hostNameMQ;
	}

	public void setHostNameMQ(String hostNameMQ) {
		this.hostNameMQ = hostNameMQ;
	}

	public int getMqPort() {
		return mqPort;
	}

	public void setMqPort(int mqPort) {
		this.mqPort = mqPort;
	}

	public String getQueueManager() {
		return queueManager;
	}

	public void setQueueManager(String queueManager) {
		this.queueManager = queueManager;
	}

	public String getQueueChannel() {
		return queueChannel;
	}

	public void setQueueChannel(String queueChannel) {
		this.queueChannel = queueChannel;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
}
