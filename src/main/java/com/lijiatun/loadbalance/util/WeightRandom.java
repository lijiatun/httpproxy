package com.lijiatun.loadbalance.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 加权随机（Weight Random）法 
 * @author liguangwen
 * <p>与加权轮询法一样，加权随机法也根据后端机器的配置，系统的负载分配不同的权重。
 * 不同的是，它是按照权重随机请求后端服务器，而非顺序。
 */
public class WeightRandom 
{
	public static String getServer(List<String> serverList,List<String> portList,List<Integer> weightList)  
    {
		List<String> newServerList = new ArrayList<String>(); 
		for(int i=0;i<serverList.size();i++)
		{
			int weight = weightList.get(i);
	        for (int j = 0; j < weight; j++)
	        		newServerList.add(serverList.get(i)+":"+portList.get(i));
		}
        java.util.Random random = new java.util.Random();  
        int randomPos = random.nextInt(serverList.size());  
  
        return serverList.get(randomPos);  
    }  
}
