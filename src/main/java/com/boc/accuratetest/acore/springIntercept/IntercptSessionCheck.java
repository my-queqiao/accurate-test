package com.boc.accuratetest.acore.springIntercept;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.boc.accuratetest.annotation.SecurityIgnoreHandler;
import com.boc.accuratetest.annotation.SecurityManagement;
import com.boc.accuratetest.constant.NotLoginInException;
import com.boc.accuratetest.constant.PermissionUndifinedException;
import com.boc.accuratetest.constant.ProductionTaskSession;
import com.boc.accuratetest.pojo.Permission;
import com.boc.accuratetest.pojo.User;

public class IntercptSessionCheck extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			SecurityIgnoreHandler securityIgnoreHandler = handlerMethod.getMethodAnnotation(SecurityIgnoreHandler.class);
			if(null != securityIgnoreHandler) { // 放行
				return true;
			}
			/**
			 *	
			 *	1、用户登陆成功之后，在session中存储用户名、用户id等信息，并且存储其拥有的方法注解信息。 
			 *	2、然后，查看其是否拥有当前方法的注解信息，没有拥有就拦截。
			 */
			Object login = request.getSession().getAttribute(ProductionTaskSession.loginUser);
			if(null == login) {
				request.getRequestDispatcher("/").forward(request, response); // 登陆页面
				//throw new NotLoginInException("您尚未登陆");
				return false;
			}
			SecurityManagement securityManagement = handlerMethod.getMethodAnnotation(SecurityManagement.class);
			if(null == securityManagement) {
				return false; // 应该设为false。一段代码出错，spring通过自身一个controller（它没有这个注解）报出错误原因。
			}
			Class<?> value = securityManagement.value();
			String rankName = value.getName(); // com.boc.accuratetest.acl.LiuyanRank  查看这个用户是否拥有这个rank。
			Object object2 = request.getSession().getAttribute(ProductionTaskSession.permissionsOfLoginUser);
			@SuppressWarnings("unchecked")
			List<Permission> ps = (List<Permission>)object2;
			for (Permission p : ps) {
				if(p.getRankName().equals(rankName)) {
					return true; // 放行
				}
			}
			throw new PermissionUndifinedException("权限不足，请联系管理员");
		}
		//请求转发（重定向是redirect）
		//request.getRequestDispatcher("/_permission_undefined").forward(request, response);
		return true; // 不属于方法的放行
	}

//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		// TODO Auto-generated method stub
//		super.postHandle(request, response, handler, modelAndView);
//	}
//
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		// TODO Auto-generated method stub
//		super.afterCompletion(request, response, handler, ex);
//	}
//
//	@Override
//	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		// TODO Auto-generated method stub
//		super.afterConcurrentHandlingStarted(request, response, handler);
//	}
	
}
