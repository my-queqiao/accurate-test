package com.boc.accuratetest.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class User {
	public static void main(String[] args) throws IOException {
		InputStream is = new FileInputStream(
				new File("C:\\Users\\tom\\Desktop\\jacoco-client.exec"));
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String info = null;
        while ((info = br.readLine()) != null) { // 
            System.out.println("服务器发来消息说：" + info);
        }
	}
}
