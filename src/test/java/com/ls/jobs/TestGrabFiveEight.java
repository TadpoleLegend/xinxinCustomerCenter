package com.ls.jobs;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ls.service.GrabService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestGrabFiveEight {
	
	@Resource(name = "grabService")
	private GrabService grabService;
	
	@Test
	public void testGrabSinglePage() {
		grabService.grabSingleFECompanyByUrlId(3);
	}

}
