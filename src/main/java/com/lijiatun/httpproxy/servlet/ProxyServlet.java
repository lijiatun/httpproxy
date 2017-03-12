package com.lijiatun.httpproxy.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lijiatun.loadbalance.properties.LoadBalance;
@WebServlet(urlPatterns="/*", description="servlet proxy")
public class ProxyServlet extends HttpServlet 
{
	private static Logger log = Logger.getLogger(ProxyServlet.class);
	private static final long serialVersionUID = 1L;
	@Autowired
	private LoadBalance loadBalance;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	{
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try 
		{
			int hashCode = request.getRemoteAddr().hashCode();
			String basePath = request.getScheme() + "://" + loadBalance.getServer(hashCode);
			//String basePath = request.getScheme() + "://" + "127.0.0.1:8080";
			String urlString = basePath + request.getRequestURI();
			String queryString = request.getQueryString();
			log.info("queryString:" + queryString);
			urlString += queryString == null ? "" : "?" + queryString;
			URL url = new URL(urlString);

			log.info("Fetching >" + url.toString());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			String methodName = request.getMethod();
			con.setRequestMethod(methodName);
			con.setDoOutput(true);
			con.setDoInput(true);
			HttpURLConnection.setFollowRedirects(false);
			con.setUseCaches(true);

			for (Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements();) {
				String headerName = e.nextElement().toString();
				log.info(headerName+":"+request.getHeader(headerName));
				con.setRequestProperty(headerName, request.getHeader(headerName));
				
			}
			con.connect();
			log.info("method:" + methodName);
			if ("POST".equalsIgnoreCase(methodName)) 
			{
				
				String contentType = request.getContentType();
				if(contentType.toLowerCase().indexOf("application/x-www-form")!=-1)
				{
					Map<String, String[]> params = request.getParameterMap();
					queryString = "";
					for (String key : params.keySet()) 
					{
						String[] values = params.get(key);
						for (int i = 0; i < values.length; i++) 
						{
							String value = values[i];
							queryString += key + "=" + value + "&";
						}
					}
					queryString = queryString.substring(0, queryString.length() - 1);
					log.info("queryString:" + queryString);
					BufferedOutputStream proxyToWebBuf = new BufferedOutputStream(con.getOutputStream());
					proxyToWebBuf.write(queryString.getBytes());
					proxyToWebBuf.flush();
					proxyToWebBuf.close();
				}
				else
				{
					BufferedInputStream clientToProxyBuf = new BufferedInputStream(request.getInputStream());
					BufferedOutputStream proxyToWebBuf = new BufferedOutputStream(con.getOutputStream());
					int oneByte;
	                while ((oneByte = clientToProxyBuf.read()) != -1)
	                {
	                    proxyToWebBuf.write(oneByte);
	                }
	                proxyToWebBuf.flush();
	                proxyToWebBuf.close();
	                clientToProxyBuf.close();
				}
			}
			int statusCode = con.getResponseCode();
			response.setStatus(statusCode);
			for (Iterator<Map.Entry<String, List<String>>> i = con.getHeaderFields().entrySet().iterator(); i
					.hasNext();) 
			{
				Map.Entry<String, List<String>> mapEntry = i.next();
				if (mapEntry.getKey() != null)
					response.setHeader(mapEntry.getKey().toString(), (mapEntry.getValue()).get(0).toString());
			}
			BufferedInputStream webToProxyBuf = new BufferedInputStream(con.getInputStream());
			BufferedOutputStream proxyToClientBuf = new BufferedOutputStream(response.getOutputStream());
			StringBuffer sb = new StringBuffer();
			int oneByte;
			while ((oneByte = webToProxyBuf.read()) != -1) 
			{
				proxyToClientBuf.write(oneByte);
				sb.append(oneByte);
			}
			proxyToClientBuf.flush();
			proxyToClientBuf.close();
			webToProxyBuf.close();
			con.disconnect();
		} 
		catch (Exception e) 
		{
			log.error("ProxyServlet:", e);
		}
	}
}
