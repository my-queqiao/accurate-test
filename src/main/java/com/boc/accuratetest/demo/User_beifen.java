package com.boc.accuratetest.demo;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

public class User_beifen {
	public static void main(String[] args) {
		try {
			String gen = System.getProperty("user.dir");
			System.out.println("gen目录:"+gen);
			ClassPool classPool = ClassPool.getDefault();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			classPool.appendClassPath(new LoaderClassPath(loader));
//			classPool.appendClassPath(gen+"/target/classses/"); // notFoundException
			CtClass ctclass = classPool.get("com.example.controller.SaoLeiController");
			// modify class,like aop
			for (CtMethod ctMethod : ctclass.getDeclaredMethods()) {
				String methodName = ctMethod.getName();
				String classname2 = ctclass.getName();
				if (!ctMethod.isEmpty()) { // have method body
					StringBuilder before = new StringBuilder();
					before.append("System.err.println(\"=============="	
							+ classname2 + "." + methodName	+ " ==============\");\n");
					ctMethod.insertBefore(before.toString());
				}
			}
			
			//这里会将这个创建的类对象编译为.class文件
			ctclass.writeFile(gen+"/target/classes");
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (CannotCompileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
