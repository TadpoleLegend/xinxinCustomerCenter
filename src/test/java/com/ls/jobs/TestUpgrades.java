package com.ls.jobs;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ls.entity.LearningHistory;
import com.ls.entity.PhoneCallHistory;
import com.ls.repository.LearningHistoryRepository;
import com.ls.repository.PhoneCallHistoryRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestUpgrades {

	@Autowired
	private PhoneCallHistoryRepository phoneCallHistoryRepository;
	
	@Autowired
	private LearningHistoryRepository learningHistoryRepository;
	
	@Test
	public void testSavePhoneCallHistory() {
		PhoneCallHistory phoneCallHistory = new PhoneCallHistory();
		//phoneCallHistory.set
	}
	
	@Test
	public void testSaveLearningHistory() {
		LearningHistory learningHistory = new LearningHistory();
		//phoneCallHistory.set
		
		learningHistory.setEndDate(new Date());
		learningHistory.setStartDate(new Date());
		
		learningHistoryRepository.save(learningHistory);
	}
}
