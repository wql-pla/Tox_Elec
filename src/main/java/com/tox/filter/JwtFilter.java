package com.tox.filter;

import com.tox.utils.Constant;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//@Component
public class JwtFilter extends GenericFilterBean

{

  //鉴权信息
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
    throws IOException, ServletException
  {
    HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
    HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;
    String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
    String urlall = httpRequest.getRequestURI();
    StringBuffer requestURL = httpRequest.getRequestURL();
    String remoteAddr = httpRequest.getRemoteAddr();
//    Map<String, Object> params = new HashMap<>();
//    params.put("ip",remoteAddr);
//    Map<String,Object> map = new HashMap<String,Object>();

    if ((url.startsWith("/")) && (url.length() > 1)) {
      url = url.substring(1);
    }
    if ((url.contains("admin/login")) || (url.contains("admin/logout"))|| (url.contains("app/sendCode"))|| (url.contains("app/login"))
            || (url.contains("station/selectStationsAndPilesNum"))
            || (url.contains("station/elecPriceMap"))) {
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }
    String header = httpRequest.getHeader("auth");
    logger.info("header==="+header);
    if (Constant.auth.equals(header)) {
    	filterChain.doFilter(servletRequest, servletResponse);
      return;
    }


    logger.info("请重新登录");
//    map.put("result", "-1");
//    map.put("msg", "请重新登录");
//    Object json = JSON.toJSON(map);
//    httpResponse.sendRedirect("http://elec.toxchina.com/tox-admin-pile/html/login.html");
//    httpResponse.getWriter().write(json.toString());
    httpResponse.setStatus(3001);
  }
}