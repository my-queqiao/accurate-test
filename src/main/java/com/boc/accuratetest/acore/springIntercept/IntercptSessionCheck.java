package com.boc.accuratetest.acore.springIntercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.boc.accuratetest.annotation.SecurityIgnoreHandler;

public class IntercptSessionCheck extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			
			// 
			String name = handlerMethod.getMethod().getName();
//			System.out.println("name:"+name);
			
			SecurityIgnoreHandler securityIgnoreHandler = handlerMethod.getMethodAnnotation(SecurityIgnoreHandler.class);
			if(null != securityIgnoreHandler) {
				return true;
			}
			else {
				System.out.println("已被拦截");
				return false;
			}
		}
		//请求转发（重定向是redirect）
		//request.getRequestDispatcher("/_permission_undefined").forward(request, response);
		//不是HandlerMethod实例的不拦截
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterConcurrentHandlingStarted(request, response, handler);
	}
	
}
