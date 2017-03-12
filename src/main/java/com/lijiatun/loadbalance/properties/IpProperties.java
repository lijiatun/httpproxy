package com.lijiatun.loadbalance.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="loadbalance",locations = "classpath:/loadbalance.properties")
public class IpProperties 
{
	private String type;
	private List<String> servers = new ArrayList<String>();
	private List<String> ports   = 	new ArrayList<String>();
	private List<Integer> weights   = 	new ArrayList<Integer>();
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getServers() {
		return servers;
	}
	public void setServers(List<String> servers) {
		this.servers = servers;
	}
	public List<String> getPorts() {
		return ports;
	}
	public void setPorts(List<String> ports) {
		this.ports = ports;
	}
	public List<Integer> getWeights() {
		return weights;
	}
	public void setWeights(List<Integer> weights) {
		this.weights = weights;
	}
	
	
}
