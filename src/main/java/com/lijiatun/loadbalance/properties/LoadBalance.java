package com.lijiatun.loadbalance.properties;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lijiatun.loadbalance.properties.IpProperties;
import com.lijiatun.loadbalance.util.Hash;
import com.lijiatun.loadbalance.util.Random;
import com.lijiatun.loadbalance.util.RoundRobin;
import com.lijiatun.loadbalance.util.WeightHash;
import com.lijiatun.loadbalance.util.WeightRandom;
import com.lijiatun.loadbalance.util.WeightRoundRobin;
@Component
public class LoadBalance 
{
	/*
     * 注入配置文件类
     */
    @Autowired
    private IpProperties ipProperties;
    
    public String getServer(int hashCode)
    {
    		String type = ipProperties.getType();
    		List<String> serverList = ipProperties.getServers();
    		List<String> portList = ipProperties.getPorts();
    		List<Integer> weightList = ipProperties.getWeights();
    		String server = null;
    		if("hash".equals(type))
    			server = Hash.getServer(serverList,portList,hashCode);
    		else if("random".equals(type))
    			server = Random.getServer(serverList,portList);
    		else if("roundRobin".equals(type))
    			server = RoundRobin.getServer(serverList,portList);
    		else if("weightHash".equals(type))
    			server = WeightHash.getServer(serverList,portList,weightList,hashCode);
    		else if("weightRandom".equals(type))
    			server = WeightRandom.getServer(serverList,portList,weightList);
    		else if("weightRoundRobin".equals(type))
    			server = WeightRoundRobin.getServer(serverList,portList,weightList);
    		else
    			server = Random.getServer(serverList,portList);
    		return server;
    }
}
