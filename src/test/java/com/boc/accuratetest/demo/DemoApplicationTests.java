package com.boc.accuratetest.demo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * spring boot测试类不支持事物？需要在@Test下面加上@Transactonal?
 * @author tom
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	@Test
	public void a() {
		System.out.println("springboot单元测试test方法执行。。。");
	}
	public static void main(String[] args) {
		System.out.println("df");
	}
}
