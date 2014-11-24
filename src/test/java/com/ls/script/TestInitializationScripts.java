package com.ls.script;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ls.entity.Step;
import com.ls.repository.StepRepository;

/**
 * This class is for basic data preparation for the web app starting up. using this class without retrieving data from the UI. The data must not be
 * changed.
 * 
 * @author jjiang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestInitializationScripts {

	@Autowired
	private StepRepository stepRepository;

	@Test
	public void testInitialCompanySteps() throws Exception {

		Step stepOne = new Step(10, "������ͻ�", 1);
		Step stepTwo = new Step(20, "�����Ϊ����ͻ�", 2);
		Step stepThree = new Step(30, "������", 4);
		Step stepFour = new Step(40, "��ѵ��", 3);
		Step stepFive = new Step(50, "��Ʒ��", 5);
		Step stepSix = new Step(60, "Ժ����", 6);
		Step stepSeven = new Step(70, "������", 7);
		
		stepRepository.save(stepOne);
		stepRepository.save(stepTwo);
		stepRepository.save(stepThree);
		stepRepository.save(stepFour);
		stepRepository.save(stepFive);
		stepRepository.save(stepSix);
		stepRepository.save(stepSeven);
		
	}

}
