package com.boc.accuratetest.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
	public static void main(String[] args) {
		Set<Integer> as = new HashSet<>();
		Set<Integer> re = re(as);
		System.out.println(re);
		// 排序
		List<Integer> list = new ArrayList<>(re);
		Collections.sort(list,new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1-o2;
			}
		});
		System.out.println(list);
		System.out.println(33*32*31*30*29 *20*20L);
	}
	private static Set<Integer> re(Set<Integer> as) {
		int a = (int)(Math.random()*32)+1; // [1,33)
		as.add(a);
		if(as.size()>=5) {
			return as;
		}
		return re(as);
	}
}
