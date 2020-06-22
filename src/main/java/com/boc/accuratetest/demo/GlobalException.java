package com.boc.accuratetest.demo;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice //全局复用的异常处理，跳转处理
public class GlobalException {
	@ExceptionHandler(value= {NullPointerException.class})
	public ModelAndView nullException(Exception e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("error", e.toString());
		mv.setViewName("error");//页面
		return mv;
	}
}
