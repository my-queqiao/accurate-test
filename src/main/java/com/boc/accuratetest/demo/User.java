package com.boc.accuratetest.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.util.StringBuilders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMember;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.analysis.ControlFlow.Block;
import javassist.bytecode.annotation.Annotation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class User {
	public static void main(String[] args) throws CannotCompileException, NotFoundException, IOException {
		//字节码操作创建一个类
		ClassPool cp = ClassPool.getDefault();
		// 扫描不到怎么办？
		CtClass cc = cp.makeClass("tcpServer.Student");
		// add a annotation on class
		ClassFile classFile = cc.getClassFile();
	    ConstPool constpool = classFile.getConstPool();
	    AnnotationsAttribute annotationsAttribute = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
	    Annotation annotation = new Annotation("org.springframework.stereotype.Component", constpool);
	    annotationsAttribute.setAnnotation(annotation);
	    classFile.addAttribute(annotationsAttribute);
		
		//添加自定义方法
//		CtMethod cm01 = CtMethod.make("static{//静态代码块}", cc);
//		cc.addMethod(cm01);
		
		
		String gen = System.getProperty("user.dir");
		cc.writeFile(gen+"/target/classes");
		System.out.println("创建成功");
	}
}
