package com.lijiatun.loadbalance.util;

import java.util.List;

/**
 * 
 * 轮询（Round Robin）法
 * @author liguangwen
 */
public class RoundRobin 
{
	private static Integer pos = 0;
	public static String getServer(List<String> serverList,List<String> portList)  
    {
  
        String server = null;  
        synchronized (pos)  
        {  
            if (pos > serverList.size()-1)  
                pos = 0;
            server = serverList.get(pos)+":"+portList.get(pos);
            pos ++;
        }
        return server;  
    } 
}
