package com.AriesT.run;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.AriesT.service.MyfirstService;

import test.UnitTestBase;


@RunWith(BlockJUnit4ClassRunner.class)
public class Run extends UnitTestBase {

	public Run( ) {
		super("classpath:spring/*.xml");
	}
	
	@Test
	public void testsetter() {
		
			
	}
	
	@Test
	public void getjava() {
		
	}
	
}

