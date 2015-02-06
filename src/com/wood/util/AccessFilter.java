package com.wood.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wood.model.TbUser;

public class AccessFilter implements Filter {
	/**
	 * 是否启用过滤器标识
	 * true:启用
	 * false:禁用（开发模式可以设置该属性）
	 */
	private boolean useFilter;
	
	/**
	 * 不需要拦截的路径
	 */
	private List<String> notCheckURLList = new ArrayList<String>();
	private List<String> urlList = new ArrayList<String>();
	
	private static final String LoginPath = "/views/login.jsp";
	private static final String PATHFILTER = "resources";
	private static final String PATHFILTERVIEW = "views/js";
	private static final String SERVICES = "services";
	
	@Override
	public void destroy() {
		try{
			notCheckURLList.clear();
			urlList.clear();
			}catch(Exception ex){}
		
	}
	
	private boolean checkRequestURIIntNotFilterList(HttpServletRequest request)    {    
	    String uri = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo()); 
	    if(uri.contains(PATHFILTER)){
	    	return true;
	    }
	    if(uri.contains(PATHFILTERVIEW)){
	    	return true;
	    }
	    if(uri.contains(SERVICES)){
	    	return true;
	    }
	    for(String nochkurl : notCheckURLList){
	    	if(uri.contains(nochkurl)){
	    		return true;
	    	}
	    }
	    return false;    
	} 
	
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession(true);
		if(checkRequestURIIntNotFilterList(req)){
			chain.doFilter(request, response);
			return;
		}
		
		//判断用户是否登录
		TbUser user = (TbUser)session.getAttribute(SystemConstant.CURRENT_USER);
		if (useFilter) {
			if (user == null) {
				resp.sendRedirect(req.getContextPath() + LoginPath);
				return;
			}
			
			chain.doFilter(request, response);
			return;
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		String control = fConfig.getInitParameter("useFilter");
		if ("false".equals(control)) {
			useFilter = false;
		} else {
			useFilter = true;
		}
		
		String notCheckURLListStr = fConfig.getInitParameter("notCheckURLList");
		if (notCheckURLListStr != null) {
			StringTokenizer st = new StringTokenizer(notCheckURLListStr, ",");
			notCheckURLList.clear();
			while (st.hasMoreTokens()) {
				notCheckURLList.add(st.nextToken());
			}
		}
		
		String urlListStr = fConfig.getInitParameter("urlList");
		if(urlListStr != null)
		{
			String[] urls = urlListStr.split(",");
			urlList = Arrays.asList(urls);
		}
	}
}
