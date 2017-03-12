package com.lijiatun.loadbalance.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 加权轮询（Weight Round Robin）法 
 * @author liguangwen
 * <p>不同的后端服务器可能机器的配置和当前系统的负载并不相同，因此它们的抗压
 * 能力也不相同。给配置高、负载低的机器配置更高的权重，让其处理更多的请；而配置
 * 低、负载高的机器，给其分配较低的权重，降低其系统负载，加权轮询能很好地处理
 * 这一问题，并将请求顺序且按照权重分配到后端。
 * 
 */
public class WeightRoundRobin 
{
	private static Integer pos=0;
    public static String getServer(List<String> serverList,List<String> portList,List<Integer> weightList)  
    {
		List<String> newServerList = new ArrayList<String>(); 
		for(int i=0;i<serverList.size();i++)
		{
			int weight = weightList.get(i);
	        for (int j = 0; j < weight; j++)
	        		newServerList.add(serverList.get(i)+":"+portList.get(i));
		}
  
        String server = null;  
        synchronized (pos)  
        {  
            if (pos > serverList.size()-1)  
                pos = 0;
            server = serverList.get(pos);  
            pos ++;
        }
  
        return server;
    }
}
