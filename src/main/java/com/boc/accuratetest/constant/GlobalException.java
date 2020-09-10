package com.boc.accuratetest.constant;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice //全局复用的异常处理，跳转处理
public class GlobalException {
	
	// 空指针
	@ExceptionHandler(value= {NullPointerException.class})
	public ModelAndView nullException(Exception e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorInfo", e.toString());
		mv.setViewName("error");//页面
		return mv;
	}
	// 未登录
	@ExceptionHandler(value= {NotLoginInException.class})
	public ModelAndView NotLoginInException(Exception e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorInfo", e.toString());
		mv.setViewName("error");//页面
		return mv;
	}
	// 权限不足
	@ExceptionHandler(value= {PermissionUndifinedException.class})
	public ModelAndView permissionUndefined(Exception e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorInfo", e.toString());
		mv.setViewName("error");//页面
		return mv;
	}
	// 用户未选择生产任务编号
	@ExceptionHandler(value= {NotSelectProductionTaskException.class})
	public ModelAndView notSelectProductionTaskException(Exception e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorInfo", e.toString());
		mv.setViewName("error");//页面
		return mv;
	}
}
