package com.boc.accuratetest.demo;

public class User_beifen {
	public volatile int counts = 0;
	public synchronized void inc() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		this.counts++;
		synchronized(this) {
			this.counts++;
		}
	}
	public static void main(String[] args) throws InterruptedException {
		User_beifen ub = new User_beifen();
		for(int i=0;i<1000;i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					ub.inc();
				}
			}).start();
		}
		Thread.sleep(1000); // main线程休眠1秒
		System.out.println(ub.counts);
	}
}
