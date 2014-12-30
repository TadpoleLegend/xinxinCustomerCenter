package com.ls.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.http.HttpURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.collect.ImmutableList;
import com.ls.entity.BaseCompanyURL;
import com.ls.entity.City;
import com.ls.entity.Company;
import com.ls.entity.FeCompanyURL;
import com.ls.entity.GanjiCompanyURL;
import com.ls.entity.GrabCompanyDetailLog;
import com.ls.entity.NegativeCompany;
import com.ls.entity.OteCompanyURL;
import com.ls.entity.Province;
import com.ls.enums.ResourceTypeEnum;
import com.ls.grab.HtmlParserUtilFor138;
import com.ls.grab.HtmlParserUtilFor58;
import com.ls.grab.HtmlParserUtilForGanJi;
import com.ls.repository.CityRepository;
import com.ls.repository.CityURLRepository;
import com.ls.repository.CompanyRepository;
import com.ls.repository.CompanyResourceRepository;
import com.ls.repository.FeCompanyURLRepository;
import com.ls.repository.GanjiCompanyURLRepository;
import com.ls.repository.GrabCompanyDetailLogRepository;
import com.ls.repository.NegativeCompanyRepository;
import com.ls.repository.OteCompanyURLRepository;
import com.ls.repository.ProvinceRepository;
import com.ls.service.BasicGrabService;

import static com.ls.util.XinXinUtils.*;

import com.ls.vo.ResponseVo;

@Service("grabService")
@Scope("prototype")
public class GrabServiceImpl extends BasicGrabService {

	private Logger logger = LoggerFactory.getLogger(GrabServiceImpl.class);

	@Autowired
	private CityURLRepository cityURLRepository;

	@Autowired
	private CompanyResourceRepository companyResourceRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private NegativeCompanyRepository negativeCompanyRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private FeCompanyURLRepository feCompanyURLRepository;

	@Autowired
	private GrabCompanyDetailLogRepository grabCompanyDetailLogRepository;

	@Autowired
	private GanjiCompanyURLRepository ganjiCompanyURLRepository;

	@Autowired
	private OteCompanyURLRepository oteCompanyURLRepository;
	
	@Autowired
	private ProvinceRepository provinceRepository;
	
	public List<String> findFeCityURLs() {

		List<String> list = ImmutableList.of("http://su.58.com/", "http://nj.58.com/");

		return list;
	}

	private NegativeCompany envelopNegativeCompany(Company company, String recourceType) {

		NegativeCompany nc = new NegativeCompany();
		try {
			nc.setCityId(company.getCityId());
			nc.setResourceType(recourceType);
			nc.setName(company.getName());
			nc.setDescription(company.getDescription());
			if (ResourceTypeEnum.OneThreeEight.getId().equals(recourceType)) {
				nc.setResourceId(company.getoTEresourceId());
				nc.setUrl(company.getOteUrl());
			} else if (ResourceTypeEnum.Ganji.getId().equals(recourceType)) {
				nc.setResourceId(company.getGanjiresourceId());
				nc.setUrl(company.getGanjiUrl());
			} else if (ResourceTypeEnum.FiveEight.getId().equals(recourceType)) {
				nc.setResourceId(company.getfEresourceId());
				nc.setUrl(company.getfEurl());
			}
			// nc.setDescription(company.getDescription());
			nc.setEmployeeCount(company.getEmployeeCount());
			nc.setName(company.getName());
			nc.setAddress(company.getAddress());
			nc.setArea(company.getArea());
			nc.setContactor(company.getContactor());
			nc.setEmailSrc(company.getEmailSrc());
			nc.setSb_count(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nc;

	}

	private Company findSameCompanyInTheCity(Company company) {

		return companyRepository.findByCityIdAndName(company.getCityId(), company.getName());
	}

	private void handleDescription(Company company) {

		if (StringUtils.isNotEmpty(company.getDescription())) {

			String desc = company.getDescription();
			if (desc.length() > 2000) {
				company.setDescription("");
			}
		}

	}

	private ResponseVo saveStupidCompany(Company company, String recourceType, BaseCompanyURL baseCompanyURL) {

		baseCompanyURL.setComments("sb company");

		ResponseVo responseVo = ResponseVo.newSuccessMessage("这个公司暂时没有提供有效的数据，如 电话，联系人姓名等重要信息");

		NegativeCompany negativeCompany = envelopNegativeCompany(company, recourceType);

		NegativeCompany resultNegativeCompany = negativeCompanyRepository.findNegativeCompany(company.getCityId(), negativeCompany.getResourceId(), recourceType);

		if (resultNegativeCompany == null) {

			NegativeCompany savedStupidCompany = null;
			try {

				savedStupidCompany = negativeCompanyRepository.saveAndFlush(negativeCompany);

			} catch (Throwable e) {
				// will try to save again
			}

			if (null == savedStupidCompany) {

				savedStupidCompany = tryToSaveNegativeCompanyWithSimpleData(negativeCompany);

				if (null == savedStupidCompany) {
					responseVo = makeGeneralErrorResponse(new Exception("retry to save stupid company fail"));
					baseCompanyURL.setStatus("SB_SAVE_FAIL");
				}
			}

			baseCompanyURL.setStatus("SB_SAVED_SUCCESS");
			if (savedStupidCompany.getCityId() != null && baseCompanyURL.getCityId() == null) {
				baseCompanyURL.setCityId(savedStupidCompany.getCityId());
			}

			responseVo.setObject(savedStupidCompany);
		}

		commonSaveUrl(baseCompanyURL);

		return responseVo;

	}

	private ResponseVo saveNormalCompany(Company company, String recourceType, BaseCompanyURL baseCompanyURL) {

		ResponseVo responseVo = ResponseVo.newSuccessMessage(null);

		Company savedCompany = null;
		// fist try
		try {
			savedCompany = this.companyRepository.save(company);
		} catch (Throwable e) {

		}
		// second try
		if (saveCompanyFails(savedCompany)) {

			savedCompany = tryToSaveCompanyWithSimpleData(company);

			if (saveCompanyFails(savedCompany)) {

				baseCompanyURL.setStatus("NON_SB_RETRY_FAIL");
				commonSaveUrl(baseCompanyURL);
				baseCompanyURL.setHasGet(false);
				responseVo = makeGeneralErrorResponse(new Exception("第二次尝试保存公司信息失败!"));
				return responseVo;

			} else {
				baseCompanyURL.setHasGet(true);
				baseCompanyURL.setStatus("NON_SB_RETRY_SUCCESS");
				baseCompanyURL.setSavedCompany("" + savedCompany.getId());
				
			}

		} else {
			baseCompanyURL.setHasGet(true);
			baseCompanyURL.setStatus("NON_SB_SUCCESS");
			baseCompanyURL.setSavedCompany("" + savedCompany.getId());
		}

		if (savedCompany.getCityId() != null && baseCompanyURL.getCityId() == null) {
			baseCompanyURL.setCityId(savedCompany.getCityId());
		}
		
		commonSaveUrl(baseCompanyURL);

		responseVo.setObject(savedCompany);
		responseVo.setMessage("采集成功，公司编号是 " + savedCompany.getId());

		return responseVo;
	}

	private boolean saveCompanyFails(Company savedCompany) {

		return (null == savedCompany || savedCompany.getId() == null);
	}

	private void updateResourceUrlForCompany(BaseCompanyURL baseCompanyURL, Company existedCompany) {

		if (baseCompanyURL instanceof FeCompanyURL && StringUtils.isEmpty(existedCompany.getfEresourceId())) {

			saveCompanyUrls(baseCompanyURL, existedCompany);

			baseCompanyURL.setSavedCompany("" + existedCompany.getId());
			baseCompanyURL.setComments("duplicate url with other website.");
			feCompanyURLRepository.saveAndFlush((FeCompanyURL) baseCompanyURL);

		} else if (baseCompanyURL instanceof GanjiCompanyURL && StringUtils.isEmpty(existedCompany.getGanjiresourceId())) {

			saveCompanyUrls(baseCompanyURL, existedCompany);

			baseCompanyURL.setSavedCompany("" + existedCompany.getId());
			baseCompanyURL.setComments("duplicate url with other website.");
			companyRepository.saveAndFlush(existedCompany);

		} else if (baseCompanyURL instanceof OteCompanyURL && StringUtils.isEmpty(existedCompany.getoTEresourceId())) {

			saveCompanyUrls(baseCompanyURL, existedCompany);

			baseCompanyURL.setSavedCompany("" + existedCompany.getId());
			baseCompanyURL.setComments("duplicate url with other website.");

			oteCompanyURLRepository.saveAndFlush((OteCompanyURL)baseCompanyURL);

		}

	}

	private void saveCompanyUrls(BaseCompanyURL baseCompanyURL, Company existedCompany) {

		existedCompany.setfEresourceId(baseCompanyURL.getCompanyId());
		existedCompany.setfEurl(baseCompanyURL.getUrl());
		companyRepository.saveAndFlush(existedCompany);
	}

	public ResponseVo saveCompanyToDb(Company company, String recourceType, BaseCompanyURL baseCompanyURL) {

		ResponseVo responseVo = ResponseVo.newSuccessMessage("The company inserted successfully.");

		handleDescription(company);

		try {

			Company existedCompany = findSameCompanyInTheCity(company);

			if (existedCompany == null) {

				boolean stupid = checkIfCompanyIsStupid(company);

				if (stupid) {

					responseVo = saveStupidCompany(company, recourceType, baseCompanyURL);

				} else {
					responseVo = saveNormalCompany(company, recourceType, baseCompanyURL);
				}

			} else {

				updateResourceUrlForCompany(baseCompanyURL, existedCompany);

				return ResponseVo.newSuccessMessage("这个公司已经采集，编号是 " + existedCompany.getId());
			}

		} catch (Exception e) {

			baseCompanyURL.setComments(e.getMessage());
			commonSaveUrl(baseCompanyURL);

			return makeGeneralErrorResponse(e);
		}

		return responseVo;
	}

	private BaseCompanyURL commonSaveUrl(BaseCompanyURL baseCompanyURL) {

		if (baseCompanyURL instanceof FeCompanyURL) {

			FeCompanyURL feCompanyURL = (FeCompanyURL)baseCompanyURL;
			return feCompanyURLRepository.saveAndFlush(feCompanyURL);

		} else if (baseCompanyURL instanceof GanjiCompanyURL) {
			GanjiCompanyURL ganjiCompanyURL = (GanjiCompanyURL)baseCompanyURL;

			return ganjiCompanyURLRepository.save(ganjiCompanyURL);
		} else if (baseCompanyURL instanceof OteCompanyURL) {

			OteCompanyURL oteCompanyURL = (OteCompanyURL)baseCompanyURL;

			return oteCompanyURLRepository.save(oteCompanyURL);
		}

		return null;

	}

	private NegativeCompany tryToSaveNegativeCompanyWithSimpleData(NegativeCompany nc) {

		NegativeCompany negativeCompany = new NegativeCompany();
		negativeCompany.setCityId(nc.getCityId());
		negativeCompany.setResourceId(nc.getResourceId());
		negativeCompany.setResourceType(nc.getResourceType());
		negativeCompany.setSb_count(1);
		negativeCompany.setUrl(nc.getUrl());

		return negativeCompanyRepository.saveAndFlush(negativeCompany);
	}

	private Company tryToSaveCompanyWithSimpleData(Company oldCompany) {

		Company company = Company.create();

		company.setCityId(oldCompany.getCityId());
		company.setContactor(oldCompany.getContactor());
		company.setMobilePhoneSrc(oldCompany.getMobilePhoneSrc());
		company.setPhoneSrc(oldCompany.getPhoneSrc());
		company.setfEresourceId(oldCompany.getfEresourceId());
		company.setfEurl(oldCompany.getfEurl());
		company.setoTEresourceId(oldCompany.getoTEresourceId());
		company.setOteUrl(oldCompany.getOteUrl());
		company.setGanjiresourceId(oldCompany.getGanjiresourceId());
		company.setGanjiUrl(oldCompany.getGanjiUrl());

		return companyRepository.saveAndFlush(company);
	}

	public ResponseVo grabSingleFECompanyByUrlId(Integer urlId) {

		return grabSingleFECompanyByUrl(feCompanyURLRepository.findOne(urlId));
	}

	public ResponseVo grabSingleFECompanyByUrl(FeCompanyURL feCompanyURL) {

		Company company = compositeBasicCompanyInfo(feCompanyURL);

		ResponseVo response = null;

		HtmlParserUtilFor58.getInstance().findCompanyDetails(company);

		handleLocation(company);

		try {

			response = this.saveCompanyToDb(company, ResourceTypeEnum.FiveEight.getId(), feCompanyURL);

		} catch (org.springframework.orm.jpa.JpaSystemException tmdException) {
			System.out.println(tmdException.getMessage());
		}

		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {

		}

		return response;
	}

	private void handleLocation(Company company) {

		if (StringUtils.isNotBlank(company.getCityName())) {

			City city = cityRepository.findByName(company.getCityName());

			if (city != null) {
				
				if (company.getCityId() == null) {
					company.setCityId(city.getId());
				}

				if (company.getProvinceId() == null) {
					company.setProvinceId(city.getProvince().getId());
				}
			}
		}

	}

	private Company compositeBasicCompanyInfo(BaseCompanyURL basicCompanyURL) {

		Company company = Company.create();

		company.setName(basicCompanyURL.getName());
		company.setArea(basicCompanyURL.getArea());
		company.setCityId(basicCompanyURL.getCityId());
		
		company.setCityId(basicCompanyURL.getCityId());

		if (basicCompanyURL instanceof FeCompanyURL) {

			company.setfEresourceId(basicCompanyURL.getCompanyId());
			company.setfEurl(basicCompanyURL.getUrl());
			

		} else if (basicCompanyURL instanceof GanjiCompanyURL) {

			company.setGanjiresourceId(basicCompanyURL.getCompanyId());
			company.setGanjiUrl(basicCompanyURL.getUrl());

		} else if (basicCompanyURL instanceof OteCompanyURL) {

			company.setoTEresourceId(basicCompanyURL.getCompanyId());
			company.setOteUrl(basicCompanyURL.getUrl());
		}

		return company;

	}

	public void feJobDailyWork() {

		GrabCompanyDetailLog grabCompanyDetailLog = new GrabCompanyDetailLog();

		int pageNumber = 0;
		int pageSize = 1000;

		int successCount = 0;
		int failCount = 0;
		try {
			grabCompanyDetailLog.setStartDate(getNow());

			while (true) {

				PageRequest pageRequest = new PageRequest(pageNumber, pageSize);

				Page<FeCompanyURL> feCompanyUrlPage = feCompanyURLRepository.findAll(generateSpecificationForDailyJobUrl(), pageRequest);

				for (FeCompanyURL feCompanyURL : feCompanyUrlPage) {

					ResponseVo response = null;
					try {
						response = this.grabSingleFECompanyByUrl(feCompanyURL);
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (null == response || response.getType().equals("FAIL")) {
						failCount++;

						System.out.println("fail--->" + feCompanyURL.toString());
					} else {
						successCount++;

						System.out.println("success--->" + feCompanyURL.toString());
					}

				}

				if (feCompanyUrlPage.isLastPage()) {
					break;
				}
				pageNumber++;
			}

			grabCompanyDetailLog.setStatus("success");
			grabCompanyDetailLog.setEndDate(getNow());

		} catch (Exception e) {
			e.printStackTrace();
			grabCompanyDetailLog.setMessage(e.getMessage());

			grabCompanyDetailLog.setStatus("fail");
		}

		grabCompanyDetailLog.setFailCount(failCount);
		grabCompanyDetailLog.setSuccessCount(successCount);
		grabCompanyDetailLog.setType("58");

		grabCompanyDetailLogRepository.save(grabCompanyDetailLog);

	}

	@Override
	public void gjJobDailyWork() {

		GrabCompanyDetailLog grabCompanyDetailLog = new GrabCompanyDetailLog();

		int pageNumber = 0;
		int pageSize = 1000;

		int successCount = 0;
		int failCount = 0;
		try {
			grabCompanyDetailLog.setStartDate(getNow());

			while (true) {

				PageRequest pageRequest = new PageRequest(pageNumber, pageSize);

				Page<GanjiCompanyURL> ganjiPage = ganjiCompanyURLRepository.findAll(generateSpecificationForGanjiDailyJobUrl(), pageRequest);

				for (GanjiCompanyURL ganjiCompanyURL : ganjiPage) {

					ResponseVo response = null;
					try {
						response = this.grabSingleGJCompanyByUrl(ganjiCompanyURL);
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (null == response || response.getType().equals("FAIL")) {
						failCount++;

						System.out.println("fail--->" + ganjiCompanyURL.toString());
					} else {
						successCount++;

						System.out.println("success--->" + ganjiCompanyURL.toString());
					}

				}

				if (ganjiPage.isLastPage()) {
					break;
				}
				pageNumber++;
			}

			grabCompanyDetailLog.setStatus("success");
			grabCompanyDetailLog.setEndDate(getNow());

		} catch (Exception e) {
			e.printStackTrace();
			grabCompanyDetailLog.setMessage(e.getMessage());

			grabCompanyDetailLog.setStatus("fail");
		}

		grabCompanyDetailLog.setFailCount(failCount);
		grabCompanyDetailLog.setSuccessCount(successCount);
		grabCompanyDetailLog.setType("Ganji");

		grabCompanyDetailLogRepository.save(grabCompanyDetailLog);

	}

	@Override
	public void oteJobDailyWork() {
		
		GrabCompanyDetailLog grabCompanyDetailLog = new GrabCompanyDetailLog();

		int pageNumber = 0;
		int pageSize = 1000;

		int successCount = 0;
		int failCount = 0;
		try {
			grabCompanyDetailLog.setStartDate(getNow());

			while (true) {

				PageRequest pageRequest = new PageRequest(pageNumber, pageSize);

				Page<OteCompanyURL> otePage = oteCompanyURLRepository.findAll(generateSpecificationForOneThreeEightDailyJobUrl(), pageRequest);

				for (OteCompanyURL oteCompanyURL : otePage) {

					ResponseVo response = null;
					try {
						response = this.grabSingleOTECompanyByUrl(oteCompanyURL);
						
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (null == response || response.getType().equals("FAIL")) {
						failCount++;

						System.out.println("fail--->" + oteCompanyURL.toString());
					} else {
						successCount++;

						System.out.println("success--->" + oteCompanyURL.toString());
					}

				}

				if (otePage.isLastPage()) {
					break;
				}
				pageNumber++;
			}

			grabCompanyDetailLog.setStatus("success");
			grabCompanyDetailLog.setEndDate(getNow());

		} catch (Exception e) {
			e.printStackTrace();
			grabCompanyDetailLog.setMessage(e.getMessage());

			grabCompanyDetailLog.setStatus("fail");
		}

		grabCompanyDetailLog.setFailCount(failCount);
		grabCompanyDetailLog.setSuccessCount(successCount);
		grabCompanyDetailLog.setType("138");

		grabCompanyDetailLogRepository.save(grabCompanyDetailLog);

	
	}

	private Specification<FeCompanyURL> generateSpecificationForDailyJobUrl() {

		return new Specification<FeCompanyURL>(){

			public Predicate toPredicate(Root<FeCompanyURL> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				Predicate predicate = criteriaBuilder.conjunction();

				predicate.getExpressions().add(criteriaBuilder.isNull(root.get("savedCompany")));

				return predicate;
			}

		};
	}

	private Specification<GanjiCompanyURL> generateSpecificationForGanjiDailyJobUrl() {

		return new Specification<GanjiCompanyURL>(){

			public Predicate toPredicate(Root<GanjiCompanyURL> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				Predicate predicate = criteriaBuilder.conjunction();
				
				//URL表中没有已保存的公司ID表示： 1，新的数据。 2，抓取失败的数据。 3，重要数据缺失的公司
				predicate.getExpressions().add(criteriaBuilder.isNull(root.get("savedCompany")));
				return predicate;
			}

		};
	}

	private Specification<OteCompanyURL> generateSpecificationForOneThreeEightDailyJobUrl() {

		return new Specification<OteCompanyURL>(){

			public Predicate toPredicate(Root<OteCompanyURL> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				Predicate predicate = criteriaBuilder.conjunction();
				
				//URL表中没有已保存的公司ID表示： 1，新的数据。 2，抓取失败的数据。 3，重要数据缺失的公司
				predicate.getExpressions().add(criteriaBuilder.isNull(root.get("savedCompany")));
				return predicate;
			}

		};
	}
	
	@Override
	public ResponseVo grabSingleFECompanyByUrl(String url) {

		// remove parameters
		HttpURI fehHttpURI = new HttpURI(url);

		String resourceId = fehHttpURI.getPath().replace("/", "");

		if (StringUtils.isBlank(resourceId)) {
			ResponseVo responseVo = ResponseVo.newFailMessage("请求数据错误");
			return responseVo;
		}

		Company existedCompanyInDb = companyRepository.findByFEresourceId(resourceId);
		if (existedCompanyInDb != null) {
			ResponseVo responseVo = ResponseVo.newFailMessage("这个公司已经采集，编号是：" + existedCompanyInDb.getId());
			return responseVo;
		}
		FeCompanyURL dbUrl = feCompanyURLRepository.findByCompanyId(resourceId);

		// save new url to db
		if (null == dbUrl) {
			FeCompanyURL newUrlToSave = new FeCompanyURL();

			newUrlToSave.setCompanyId(resourceId);
			newUrlToSave.setUrl(url);
			newUrlToSave.setCreateDate(getNow());
			newUrlToSave.setComments("MANUALLY_SAVED");

			dbUrl = feCompanyURLRepository.saveAndFlush(newUrlToSave);

		}

		// grab it
		ResponseVo response = grabSingleFECompanyByUrl(dbUrl);

		if (null != response && null != response.getObject() && response.getObject() instanceof Company) {
			return response;
		} else {

			// clear data
			ResponseVo responseVo = ResponseVo.newFailMessage(response.getMessage());

			return responseVo;
		}
	}

	@Override
	public ResponseVo grabCompanyDetailInCityList(List<Integer> userCityIds, String datasourceType) {

		if (StringUtils.isEmpty(datasourceType)) {

			return ResponseVo.newFailMessage("Unknown Data Source Type.");
		}

		// 58
		if (datasourceType.equals("58")) {

			for (Integer cityId : userCityIds) {

				List<FeCompanyURL> feCompaniesInCity = feCompanyURLRepository.findByCityId(cityId);

				for (FeCompanyURL feCompanyURL : feCompaniesInCity) {
					String savedCompanyId = feCompanyURL.getSavedCompany();
					if (StringUtils.isEmpty(savedCompanyId) && !feCompanyURL.getHasGet()) {

						grabSingleFECompanyByUrl(feCompanyURL);

						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
						}

					}
				}
			}

			// ganji
		} else if (datasourceType.equals("gj")) {

			for (Integer cityId : userCityIds) {

				List<GanjiCompanyURL> ganjiCompaniesInCity = ganjiCompanyURLRepository.findByCityId(cityId);

				for (GanjiCompanyURL ganjiCompanyURL : ganjiCompaniesInCity) {

					String savedCompanyId = ganjiCompanyURL.getSavedCompany();
					if (StringUtils.isEmpty(savedCompanyId) && !ganjiCompanyURL.getHasGet()) {

						grabSingleGJCompanyByUrl(ganjiCompanyURL);
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
						}

					}
				}
			}

		}

		return ResponseVo.newSuccessMessage(null);
	}

	@Override
	public ResponseVo grabSingleGJCompanyByUrlId(Integer urlId) {

		return super.grabSingleGJCompanyByUrlId(urlId);
	}

	@Override
	public ResponseVo grabSingleGJCompanyByUrl(GanjiCompanyURL ganjiCompanyURL) {

		Company company = compositeBasicCompanyInfo(ganjiCompanyURL);

		ResponseVo response = null;

		HtmlParserUtilForGanJi.getInstance().findCompanyDetails(company);

		handleLocation(company);

		try {

			response = saveCompanyToDb(company, ResourceTypeEnum.Ganji.getId(), ganjiCompanyURL);

		} catch (org.springframework.orm.jpa.JpaSystemException tmdException) {
			System.out.println(tmdException.getMessage());
		}

		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {

		}

		return response;

	}

	@Override
	public ResponseVo grabSingleGJCompanyByUrl(String url) {

		// remove parameters
		HttpURI gjhHttpURI = new HttpURI(url);

		String resourceId = gjhHttpURI.getPath().replace("/", "").replace("gongsi", "");

		if (StringUtils.isBlank(resourceId)) {
			ResponseVo responseVo = ResponseVo.newFailMessage("请求数据错误");
			return responseVo;
		}

		Company existedCompanyInDb = companyRepository.findByGanjiresourceId(resourceId);

		if (existedCompanyInDb != null) {
			ResponseVo responseVo = ResponseVo.newFailMessage("这个公司已经采集，编号是：" + existedCompanyInDb.getId());
			return responseVo;
		}
		GanjiCompanyURL dbUrl = ganjiCompanyURLRepository.findByCompanyId(resourceId);

		// save new url to db
		if (null == dbUrl) {
			GanjiCompanyURL newUrlToSave = GanjiCompanyURL.create();

			newUrlToSave.setCompanyId(resourceId);
			newUrlToSave.setUrl(url);
			newUrlToSave.setCreateDate(getNow());
			newUrlToSave.setComments("MANUALLY_SAVED");

			dbUrl = ganjiCompanyURLRepository.saveAndFlush(newUrlToSave);
		}

		// grab it
		ResponseVo response = grabSingleGJCompanyByUrl(dbUrl);

		if (null != response && null != response.getObject() && response.getObject() instanceof Company) {
			return response;
		} else {

			// clear data
			ResponseVo responseVo = ResponseVo.newFailMessage(response.getMessage());

			return responseVo;
		}

	}

	@Override
	public ResponseVo grabSingleOTECompanyByUrl(String url) {

		String resourceId = getOteResourceIdFromUrl(url);

		Company existedCompanyInDb = companyRepository.findByOTEresourceId(resourceId);

		if (existedCompanyInDb != null) {
			ResponseVo responseVo = ResponseVo.newFailMessage("这个公司已经采集，编号是：" + existedCompanyInDb.getId());
			return responseVo;
		}

		OteCompanyURL dbUrl = oteCompanyURLRepository.findByCompanyId(resourceId);

		// save new url to db
		if (null == dbUrl) {
			OteCompanyURL newUrlToSave = OteCompanyURL.create();

			newUrlToSave.setCompanyId(resourceId);
			newUrlToSave.setUrl(url);
			newUrlToSave.setCreateDate(getNow());
			newUrlToSave.setComments("MANUALLY_SAVED");

			dbUrl = oteCompanyURLRepository.saveAndFlush(newUrlToSave);
		}

		// grab it
		ResponseVo response = grabSingleOTECompanyByUrl(dbUrl);

		if (null != response && null != response.getObject() && response.getObject() instanceof Company) {
			return response;
		} else {

			// clear data
			ResponseVo responseVo = ResponseVo.newFailMessage(response.getMessage());

			return responseVo;
		}

	}

	public ResponseVo grabSingleOTECompanyByUrl(OteCompanyURL oteCompanyURL) {

		Company company = compositeBasicCompanyInfo(oteCompanyURL);
		ResponseVo response = null;
		try {

			getOTECompanyDetails(company, company.getOteUrl());
			
			response = saveCompanyToDb(company, ResourceTypeEnum.OneThreeEight.getId(), oteCompanyURL);

		} catch (org.springframework.orm.jpa.JpaSystemException tmdException) {
			System.out.println(tmdException.getMessage());
		} catch (Exception e) {
			System.out.println("fails -->" + e.getMessage() + " FOR --> " + oteCompanyURL.toString());
		}

		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {

		}

		return response;
	}

	public void getOTECompanyDetails(Company company, String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		
		HtmlParserUtilFor138.getInstance().findCompanyDetails(company);
		
		// city id, province id completion, in manually grab, the city id is retrieved from detail page
		if (company.getCityId() == null && StringUtils.isNotBlank(company.getArea())) {
			
			String area = company.getArea();
			Province companyProvince = null;
			
			List<Province> allProvinces = provinceRepository.findAll();
			for (Province singleProvince : allProvinces) {
				
				if (area.startsWith(singleProvince.getName())) {
					companyProvince = singleProvince;
					break;
				}
			}
			
			if (companyProvince != null) {
				
				company.setProvinceId(companyProvince.getId());
				
				City municipalityCity = checkMunicipality(companyProvince.getName());
				
				if (municipalityCity == null) {
					
					String cityName = area.substring(companyProvince.getName().length());
					City city = cityRepository.findByName(cityName);
					
					if (city != null) {
						company.setCityId(city.getId());
					} else {
						throw new RuntimeException("No matched city");
					}
					
				} else {
					
					company.setCityId(municipalityCity.getId());
				}
				
			} else {
				throw new RuntimeException("No matched city");
			}
		}
	}
	
	private City checkMunicipality(String provinceName) {
		
		return cityRepository.findByName(provinceName);
	}

}
