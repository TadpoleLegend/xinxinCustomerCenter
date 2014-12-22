package com.ls.service.impl;

import java.util.Date;
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

import com.google.common.collect.ImmutableList;
import com.ls.entity.City;
import com.ls.entity.Company;
import com.ls.entity.FeCompanyURL;
import com.ls.entity.GrabCompanyDetailLog;
import com.ls.entity.NegativeCompany;
import com.ls.enums.ResourceTypeEnum;
import com.ls.grab.HtmlParserUtilFor58;
import com.ls.repository.CityRepository;
import com.ls.repository.CityURLRepository;
import com.ls.repository.CompanyRepository;
import com.ls.repository.CompanyResourceRepository;
import com.ls.repository.FeCompanyURLRepository;
import com.ls.repository.GrabCompanyDetailLogRepository;
import com.ls.repository.NegativeCompanyRepository;
import com.ls.service.BasicGrabService;
import com.ls.util.XinXinUtils;
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

	private Company checkIfCompanyAlreadyExists(Company company, String recourceType) {

		Company dataBaseCompany = null;

		if (ResourceTypeEnum.OneThreeEight.getId().equals(recourceType)) {
			dataBaseCompany = this.companyRepository.findCompanyFor138GrabJob(company.getCityId(), company.getoTEresourceId(), company.getName());
		} else if (ResourceTypeEnum.Ganji.getId().equals(recourceType)) {
			dataBaseCompany = this.companyRepository.findCompanyForGanjiGrabJob(company.getCityId(), company.getGanjiresourceId(), company.getName());
		} else if (ResourceTypeEnum.FiveEight.getId().equals(recourceType)) {
			dataBaseCompany = this.companyRepository.findCompanyFor58GrabJob(company.getCityId(), company.getfEresourceId(), company.getName());
		}

		return dataBaseCompany;
	}

	private void handleDescription(Company company) {

		if (StringUtils.isNotEmpty(company.getDescription())) {

			String desc = company.getDescription();
			if (desc.length() > 2000) {
				company.setDescription("");
			}
		}

	}

	private ResponseVo saveStupidCompany(Company company, String recourceType, FeCompanyURL feCompanyURL) {

		feCompanyURL.setComments("sb company");

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
					responseVo = XinXinUtils.makeGeneralErrorResponse(new Exception("retry to save stupid company fail"));
					feCompanyURL.setStatus("SB_SAVE_FAIL");
				}
			}

			feCompanyURL.setStatus("SB_SAVED_SUCCESS");
			if (savedStupidCompany.getCityId() != null && feCompanyURL.getCityId() == null) {
				feCompanyURL.setCityId(savedStupidCompany.getCityId());
			}

			responseVo.setObject(savedStupidCompany);
		}

		feCompanyURLRepository.save(feCompanyURL);

		return responseVo;

	}

	private ResponseVo saveNormalCompany(Company company, String recourceType, FeCompanyURL feCompanyURL) {

		ResponseVo responseVo = ResponseVo.newSuccessMessage(null);
		Company savedCompany = null;

		try {

			savedCompany = this.companyRepository.save(company);
			// Here
		} catch (Throwable e) {
			// can't go in here!

		}

		if (null == savedCompany || savedCompany.getId() == null) {

			savedCompany = tryToSaveCompanyWithSimpleData(company);

			if (null == savedCompany) {
				feCompanyURL.setStatus("NON_SB_RETRY_FAIL");
				responseVo = XinXinUtils.makeGeneralErrorResponse(new Exception("retry to save company fail"));
			} else {
				feCompanyURL.setStatus("NON_SB_RETRY_SUCCESS");
				feCompanyURL.setHasGet(true);
				responseVo = ResponseVo.newSuccessMessage("retry to save company successfully.");
			}
		} else {
			feCompanyURL.setSavedCompany(savedCompany.getId().toString());
			feCompanyURL.setStatus("NON_SB_SUCCESS");
			feCompanyURL.setHasGet(true);
		}

		if (savedCompany.getCityId() != null && feCompanyURL.getCityId() == null) {
			feCompanyURL.setCityId(savedCompany.getCityId());
		}

		feCompanyURLRepository.save(feCompanyURL);
		responseVo.setObject(savedCompany);
		responseVo.setMessage("采集成功，公司编号是 " + savedCompany.getId());

		return responseVo;
	}

	public ResponseVo mergeCompanyData(Company company, String recourceType, FeCompanyURL feCompanyURL) {

		ResponseVo responseVo = ResponseVo.newSuccessMessage("The company inserted successfully.");

		handleDescription(company);

		try {

			Company resultCompany = checkIfCompanyAlreadyExists(company, recourceType);

			if (resultCompany == null) {

				boolean stupid = XinXinUtils.checkIfCompanyIsStupid(company);

				if (stupid) {

					responseVo = saveStupidCompany(company, recourceType, feCompanyURL);

				} else {
					responseVo = saveNormalCompany(company, recourceType, feCompanyURL);
				}

			} else {
				// update company reference if other url with the same company
				// name is saved to ls_company
				if (feCompanyURL.getSavedCompany() == null) {

					feCompanyURL.setSavedCompany(resultCompany.getId().toString());
					feCompanyURL.setComments("duplicate url");
					feCompanyURLRepository.saveAndFlush(feCompanyURL);
				}
				return ResponseVo.newSuccessMessage("这个公司已经采集，编号是 " + resultCompany.getId());
			}

		} catch (Exception e) {

			feCompanyURL.setComments(e.getMessage());
			feCompanyURLRepository.saveAndFlush(feCompanyURL);

			return XinXinUtils.makeGeneralErrorResponse(e);
		}

		return responseVo;
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

		Company company = envelopeCompany(feCompanyURL);

		ResponseVo response = null;

		HtmlParserUtilFor58.getInstance().findCompanyDetails(company);

		handleLocation(company);

		try {

			response = mergeCompanyData(company, ResourceTypeEnum.FiveEight.getId(), feCompanyURL);

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
				company.setCityId(city.getId());

				if (company.getProvinceId() == null) {
					company.setProvinceId(city.getProvince().getId());
				}
			}
		}

	}

	private Company envelopeCompany(FeCompanyURL feCompanyURL) {

		Company company = Company.create();

		company.setName(feCompanyURL.getName());
		company.setArea(feCompanyURL.getArea());

		company.setCityId(feCompanyURL.getCityId());
		company.setfEresourceId(feCompanyURL.getCompanyId());
		company.setfEurl(feCompanyURL.getUrl());

		return company;

	}

	public void feJobDailyWork() {

		GrabCompanyDetailLog grabCompanyDetailLog = new GrabCompanyDetailLog();

		int pageNumber = 0;
		int pageSize = 1000;

		int successCount = 0;
		int failCount = 0;
		try {
			grabCompanyDetailLog.setStartDate(XinXinUtils.getNow());

			while (true) {

				PageRequest pageRequest = new PageRequest(pageNumber, pageSize);

				Page<FeCompanyURL> feCompanyUrlPage = feCompanyURLRepository.findAll(generateSpecificationForDailyJobUrl(), pageRequest);

				for (FeCompanyURL feCompanyURL : feCompanyUrlPage) {

					ResponseVo response = null;
					try {
						response = grabSingleFECompanyByUrl(feCompanyURL);
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
			grabCompanyDetailLog.setEndDate(XinXinUtils.getNow());

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

	private Specification<FeCompanyURL> generateSpecificationForDailyJobUrl() {

		return new Specification<FeCompanyURL>() {

			public Predicate toPredicate(Root<FeCompanyURL> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				Predicate predicate = criteriaBuilder.conjunction();

				predicate.getExpressions().add(criteriaBuilder.isNull(root.get("savedCompany")));
				predicate.getExpressions().add(criteriaBuilder.lessThan(root.<Date> get("createDate"), new Date()));

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
			newUrlToSave.setCreateDate(XinXinUtils.getNow());
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
	public ResponseVo grabCompanyDetailInCityList(List<Integer> userCityIds) {

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

		return ResponseVo.newSuccessMessage(null);
	}

}
