package com.AriesT.dao;

import org.springframework.stereotype.Repository;

@Repository
public class Testdao implements TestInterface {
	
	public Testdao() {
	}

	public void save(String arg) {
		System.out.println("保存数据" + arg);
	}

}
