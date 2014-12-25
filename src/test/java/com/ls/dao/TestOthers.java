package com.ls.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.ImmutableList;
import com.ls.entity.GanjiCompanyURL;
import com.ls.repository.CityRepository;
import com.ls.repository.CompanyRepository;
import com.ls.repository.DropDownRepository;
import com.ls.repository.GanjiCompanyURLRepository;
import com.ls.repository.JobScheduleConfigurationRepository;
import com.ls.repository.MenuRepository;
import com.ls.repository.PhaseRepository;
import com.ls.repository.ProblemCategoryRepository;
import com.ls.repository.ProblemRepository;
import com.ls.repository.ProvinceRepository;
import com.ls.repository.RoleRepository;
import com.ls.repository.StepRepository;
import com.ls.repository.UserRepository;

/**
 * This class is for basic data preparation for the web app starting up. using this class without retrieving data from the UI. The data must not be
 * changed.
 * 
 * @author jjiang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestOthers {

	@Autowired
	private StepRepository stepRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private PhaseRepository phaseRepository;
	
	@Autowired
	private DropDownRepository dropDownRepository;
	
	@Autowired
	private ProblemRepository problemRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private ProblemCategoryRepository problemCategoryRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProvinceRepository provinceRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private JobScheduleConfigurationRepository jobScheduleConfigurationRepository;
	
	@Autowired
	private GanjiCompanyURLRepository ganjiCompanyURLRepository;
	
	@Test
	public void testGanjiUrlRepository() {
		
		List<String> queryUrlDatasourceExcludeList = ImmutableList.of("SB_SAVED_SUCCESS", "NON_SB_RETRY_SUCCESS");
		
		List<Integer> userCityIds = ImmutableList.of(1);
		
		List<GanjiCompanyURL> ganjiCompanyURLs = ganjiCompanyURLRepository.findByCityIdInAndSavedCompanyIsNullAndStatusIsNullOrderByIdDesc(userCityIds);
		
		System.out.println(ganjiCompanyURLs);
	}
	
	
}
