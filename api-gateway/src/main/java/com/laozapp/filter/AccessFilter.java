package com.laozapp.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessFilter extends ZuulFilter  {

    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();

        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        Object accessToken = request.getParameter("accessToken");
        /*if(accessToken == null) {
            log.warn("access token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }*/
        log.info("access token ok");
        log.info(request.getContextPath());
		Map<String, String[]> map=request.getParameterMap();
		for(Entry<String, String[]> entry:map.entrySet()){    
		     System.out.println(entry.getKey()+"--->"+entry.getValue());    
		}   
		System.out.println("request.getQueryString()----------"+request.getQueryString());
		System.out.println("this is MyFilter,url :" + request.getRequestURI());
		if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
			// CORS "pre-flight" request
			String Origin = request.getHeader("Origin");
			response.addHeader("Access-Control-Allow-Origin", Origin);
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			response.addHeader("Access-Control-Allow-Headers", "X-Requested-With, Origin, Accept, Content-Type,Token");
			response.addHeader("Access-Control-Max-Age", "2592000");// 30 day
			response.addHeader("Content-Security-Policy", "default-src * data: blob:;script-src 'self' *.baidu.com *.doubleclick.net *.googlesyndication.com *.googletagmanager.com *.google-analytics.com *.googleadservices.com *.google.com res.wx.qq.com 127.0.0.1:* 'unsafe-inline' 'unsafe-eval' blob:;style-src * 'unsafe-inline' data:;img-src * data:");
			response.setStatus(200);
			try {
				PrintWriter out;
				out = response.getWriter();
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			String Origin = request.getHeader("Origin");
			response.addHeader("Access-Control-Allow-Origin", Origin);
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			response.addHeader("Access-Control-Allow-Headers", "X-Requested-With, Origin, Accept, Content-Type,Token");
			response.addHeader("Access-Control-Max-Age", "2592000");// 30 day
			response.addHeader("Content-Security-Policy", "default-src * data: blob:;script-src 'self' *.baidu.com  *.doubleclick.net *.googlesyndication.com *.googletagmanager.com *.google-analytics.com *.googleadservices.com *.google.com res.wx.qq.com 127.0.0.1:* 'unsafe-inline' 'unsafe-eval' blob:;style-src * 'unsafe-inline' data:;img-src * data:");
			//filterChain.doFilter(request, response);
		}			
		
        return null;
    }

}
