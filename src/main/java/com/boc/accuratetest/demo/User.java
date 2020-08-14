package com.boc.accuratetest.demo;
import java.io.IOException;
/**
java.lang.Thread
getStackTrace
1559
com.boc.accuratetest.demo.User
t
20
com.boc.accuratetest.demo.User
b
32
com.boc.accuratetest.demo.User
a
29
com.boc.accuratetest.demo.User
main
13

 */
public class User {
	public static void main(String[] args) throws IOException {
		//a();
		
	}
	public static void e() {
		System.out.println("e方法开始");
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        // 0是线程信息，1是当前方法自身信息，2是直接上级信息，3是上级的上级            以此类推
        for (int i=0;i<stackTrace.length;i++) {
			System.out.println("堆栈信息，类："+stackTrace[i].getClassName());
			System.out.println("方法名："+stackTrace[i].getMethodName());
			System.out.println("代码行："+stackTrace[i].getLineNumber());
			System.out.println("文件名："+stackTrace[i].getFileName());
		}
        System.out.println("e方法结束");
	}
	public static void a() {
		System.out.println("a方法开始");
		b();
		System.out.println("a方法结束");
	}
	public static void b() {
		System.out.println("b方法开始");
		new Thread(new Runnable() {
			@Override
			public void run() {
				//Thread.sleep(1000);
				Thread.currentThread();
				e();
			}
		}).start();
		
		System.out.println("b方法结束");
	}
}
