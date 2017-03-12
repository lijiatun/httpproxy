package com.lijiatun.loadbalance.util;

import java.util.List;

/**
 * 随机（Random）法 
 */
public class Random {
	public static String getServer(List<String> serverList,List<String> portList)
	{
        java.util.Random random = new java.util.Random();  
        int randomPos = random.nextInt(serverList.size());
        return serverList.get(randomPos)+":"+portList.get(randomPos);
    }  
}
