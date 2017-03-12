package com.lijiatun.loadbalance.util;

import java.util.List;

/**
 * 源地址哈希（Hash）法 
 * @author liguangwen
 */
public class Hash 
{
	public static String getServer(List<String> serverList,List<String> portList,int hashCode)  
    {
        int serverListSize = serverList.size();
        int serverPos =   Math.abs(hashCode) % serverListSize;
        return serverList.get(serverPos)+":"+portList.get(serverPos);
    }
}
