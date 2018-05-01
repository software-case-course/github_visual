package com.AriesT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AriesT.dao.Testdao;;

@Service
public class MyfirstService implements ServiceInterface {
	
	@Autowired
	private Testdao testdao;
	
	public void save(String arg) {
		System.out.println("Service接受参数"+arg);
		arg = arg + ":" + this.hashCode();
		testdao.save(arg);
	}
}
