package com.boc.accuratetest.demo;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

public class User {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("hello world");
		Robot robot;
			while(true) {
				try {
					int r = (int)(Math.random()*6)+1; // [1,7)
					int width = 100*r;
					int heigh = 100*r;
					robot = new Robot();
					robot.mouseMove(width,heigh);
					Thread.sleep(5000);
				} catch (AWTException e) {
					e.printStackTrace();
				}
			}
	}
	// 鼠标移动
	public static void change(int type, int x, int y){
		Point p = MouseInfo.getPointerInfo().getLocation();
		int width = (int) p.getX() + x;
		int heigh = (int) p.getY() + y;
		if(type == 0) {
			width = x;
			heigh = y;
		}
		Robot robot;
		try {
			robot = new Robot();
			robot.mouseMove(width,heigh);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}
