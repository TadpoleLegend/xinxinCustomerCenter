package com.ls.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.ls.constants.XinXinConstants;
import com.ls.entity.City;
import com.ls.entity.CityURL;
import com.ls.entity.Company;
import com.ls.entity.CompanyResource;
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
import com.ls.service.GrabService;
import com.ls.util.DateUtils;
import com.ls.util.XinXinUtils;
import com.ls.vo.GrabStatistic;

@Service("grabService")
@Scope("prototype")
public class GrabServiceImpl implements GrabService {

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

	/**
	 * grab company name and URL to database
	 */
	public void grabCompanyResource(String siteURL) {

	}

	public void grabAllCompanyResource() {

		List<CityURL> allCityURLs = cityURLRepository.findAll();
		Random random = new Random();
		int i = 0;

		for (CityURL cityURL : allCityURLs) {

			try {
				String urlInDb = cityURL.getUrl();

				String pagedCompanyURL = urlInDb.endsWith("/") ? urlInDb + "meirongshi/pn" : urlInDb + "/meirongshi/pn";

				String pagedCompanyURLWithPageNumber = pagedCompanyURL + i;
				String pagedCompanyHTML = null;

				List<Company> companies = null;

				for (Company company : companies) {

					CompanyResource companyResource = new CompanyResource();
					companyResource.setUrl(company.getfEurl());
					companyResource.setName(company.getName());
					companyResource.setType("58");

					companyResourceRepository.save(companyResource);
				}

				i++;

				if (i == 3) {
					break;
				}

				int waitSeconds = random.nextInt(5000);
				Thread.sleep(waitSeconds);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public List<Company> grabCompanyInPage(String indexPageURL) {

		String pagedCompanyHTML = null;

		List<Company> companies = null;

		return companies;
	}

	public Company grabCompanyDetailByUrl(String detailPageUrl) {

		Company company = new Company();

		if (StringUtils.isBlank(detailPageUrl)) {
			throw new IllegalArgumentException("detail page url is empty");
		}
		String detailPageHtml = null;

		return company;
	}

	/**
	 * 只抓10分钟,悠着点
	 */
	public GrabStatistic grabCompanyInformationByUrl(String url, Date publishDateEnd) {

		long start = System.currentTimeMillis();

		GrabStatistic grabStatistic = new GrabStatistic();

		// grab util the publish date
		int pageNumber = 0;

		int proccessCount = 0;
		int saved = 0;
		int error = 0;
		int duplicate = 0;
		while (true) {
			proccessCount++;
			String pageURL = url + "meirongshi/pn" + pageNumber;

			String html = null;

			List<Company> basicCompany = null;
			// List<City> citys = cityRepository.findByUrl(url);
			List<City> citys = null;
			City myCity = citys.get(0);
			for (Company company : basicCompany) {
				if (isDulpicate(company)) {
					duplicate++;
					continue;
				}

				try {

					String companyDetailUrl = company.getfEurl();
					String detailPageHtml = null;

					String contactor = null;
					company.setContactor(contactor);

					String phoneImgSrc = null;
					company.setPhoneSrc(phoneImgSrc);

					String address = null;
					company.setAddress(address);

					if (StringUtils.isNotBlank(phoneImgSrc)) {
						String imgFileNameAfterGrabed = null;
						company.setPhoneSrc(imgFileNameAfterGrabed);
					}

					String emailImgSrc = null;
					company.setEmailSrc(emailImgSrc);

					if (StringUtils.isNotBlank(emailImgSrc)) {
						String emailImgFileNameAfterGrabed = null;
						company.setEmailSrc(emailImgFileNameAfterGrabed);
					}

					company.setCityId(myCity.getId());
					company.setProvinceId(myCity.getProvince().getId());

				} catch (Exception e) {
					error++;
				}

				if (ifCompanyDataValueable(company)) {

					List<Company> companyInDb = companyRepository.findByNameAndFEurl(company.getName(), company.getfEurl());

					if (null == companyInDb || companyInDb.isEmpty()) {

						try {
							companyRepository.save(company);
							saved++;
						} catch (Exception e) {
							//
							error++;
						}

					} else {
						duplicate++;
					}

				} else {
					error++;
				}

				String grabingPublishDate = company.getPublishDate();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-DD");
				try {
					// break condition

					if (StringUtils.isNotBlank(grabingPublishDate)) {

						boolean ifChoosenDateBeforePublishDate = simpleDateFormat.parse("2014-" + grabingPublishDate).before(publishDateEnd);

						if (ifChoosenDateBeforePublishDate) {
							grabStatistic.setSaved(saved);
							grabStatistic.setTotalReaded(proccessCount);
							grabStatistic.setDuplicate(duplicate);

							return grabStatistic;
						}
					}
				} catch (ParseException e) {
					//
				}
			}

			pageNumber++;

			long now = System.currentTimeMillis();

			long timeLasts = now - start;

			if (timeLasts > 1000 * 60 * 10) {

				grabStatistic.setSaved(saved);
				grabStatistic.setTotalReaded(proccessCount);
				grabStatistic.setDuplicate(duplicate);

				return grabStatistic;
			}
		}

	}

	private boolean ifCompanyDataValueable(Company company) {

		if (StringUtils.isBlank(company.getName()) || StringUtils.isBlank(company.getContactor()) || StringUtils.isBlank(company.getfEurl()) || StringUtils.isEmpty(company.getPhoneSrc())) {
			return false;
		}

		return true;
	}

	private boolean isDulpicate(Company company) {

		List<Company> companies = companyRepository.findByNameAndContactorAndArea(company.getName(), company.getContactor(), company.getArea());

		if (companies == null || companies.size() == 0)
			return false;

		return true;
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

	public Company mergeCompanyData(Company company, String recourceType) {

		Company savedCompany = null;

		try {
			if (StringUtils.isNotEmpty(company.getDescription())) {

				String desc = company.getDescription();
				if (desc.length() > 2000) {
					company.setDescription(desc.substring(0, 1990) + ".....");
				}
			}
			Company dataBaseCompany = null;
			if (ResourceTypeEnum.OneThreeEight.getId().equals(recourceType)) {
				dataBaseCompany = this.companyRepository.findCompanyFor138GrabJob(company.getCityId(), company.getoTEresourceId(), company.getName());
			} else if (ResourceTypeEnum.Ganji.getId().equals(recourceType)) {
				dataBaseCompany = this.companyRepository.findCompanyForGanjiGrabJob(company.getCityId(), company.getGanjiresourceId(), company.getName());
			} else if (ResourceTypeEnum.FiveEight.getId().equals(recourceType)) {
				dataBaseCompany = this.companyRepository.findCompanyFor58GrabJob(company.getCityId(), company.getfEresourceId(), company.getName());
			}
			String dateTime = DateUtils.getDateFormate(Calendar.getInstance().getTime(), "yyyy-MM-dd hh:mm:ss");
			if (dataBaseCompany == null) {

				if (XinXinUtils.stringIsEmpty(company.getPhoneSrc()) && XinXinUtils.stringIsEmpty(company.getMobilePhoneSrc())) {

					NegativeCompany nc = envelopNegativeCompany(company, recourceType);
					NegativeCompany dbNC = this.negativeCompanyRepository.findNegativeCompany(company.getCityId(), nc.getResourceId(), recourceType);
					if (dbNC == null) {

						try {
							nc.setGrabDate(dateTime);
							this.negativeCompanyRepository.save(nc);

						} catch (Throwable e) {
							tryToSaveNegativeCompanyWithSimpleData(nc);
						}
					}

				} else {
					company.setGrabDate(dateTime);
					try {

						savedCompany = this.companyRepository.save(company);

						// Here
					} catch (Throwable e) {
						tryToSaveCompanyWithSimpleData(company);

					}
				}
			} else {

				ruleSaveForCompany(dataBaseCompany, company, recourceType);

				try {
					this.companyRepository.save(dataBaseCompany);

				} catch (Exception e) {
					NegativeCompany nc = envelopNegativeCompany(company, recourceType);
					NegativeCompany dbNC = this.negativeCompanyRepository.findNegativeCompany(company.getCityId(), nc.getResourceId(), recourceType);
					if (dbNC == null) {
						nc.setGrabDate(dateTime);
						this.negativeCompanyRepository.save(nc);
					}
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return savedCompany;
	}

	private void tryToSaveNegativeCompanyWithSimpleData(NegativeCompany nc) {

		NegativeCompany negativeCompany = new NegativeCompany();
		negativeCompany.setCityId(nc.getCityId());
		negativeCompany.setResourceId(nc.getResourceId());
		negativeCompany.setResourceType(nc.getResourceType());
		negativeCompany.setSb_count(1);
		negativeCompany.setUrl(nc.getUrl());

		negativeCompanyRepository.save(negativeCompany);
	}

	private void tryToSaveCompanyWithSimpleData(Company oldCompany) {

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
		
		companyRepository.save(company);
	}

	/**
	 * 138 is the basic inforamtion website if 138 not have the company, then set ganji website as the second choice the last choice is 58 website
	 * 
	 * @param dbcompany
	 * @param websiteCompany
	 * @param recourceType
	 */

	private void ruleSaveForCompany(Company dbCompany, Company websiteCompany, String recourceType) {

		if (ResourceTypeEnum.OneThreeEight.getId().equals(recourceType)) {
			generateDBCompany(dbCompany, websiteCompany);
		} else if (ResourceTypeEnum.Ganji.getId().equals(recourceType)) {
			if (dbCompany.getoTEresourceId() != null) {
				dbCompany.setfEresourceId(websiteCompany.getfEresourceId() == null ? dbCompany.getfEresourceId() : websiteCompany.getfEresourceId());
				dbCompany.setfEurl(websiteCompany.getfEurl() == null ? dbCompany.getfEurl() : websiteCompany.getfEurl());
			} else {
				generateDBCompany(dbCompany, websiteCompany);
			}
		} else if (ResourceTypeEnum.FiveEight.getId().equals(recourceType)) {
			if (dbCompany.getoTEresourceId() != null || dbCompany.getGanjiresourceId() != null) {
				dbCompany.setGanjiresourceId(websiteCompany.getGanjiresourceId() == null ? dbCompany.getGanjiresourceId() : websiteCompany.getGanjiresourceId());
				dbCompany.setGanjiUrl(websiteCompany.getGanjiUrl() == null ? dbCompany.getGanjiUrl() : websiteCompany.getGanjiUrl());
			} else {
				generateDBCompany(dbCompany, websiteCompany);
			}
		}
		dbCompany.setGrabDate(DateUtils.getDateFormate(Calendar.getInstance().getTime(), "yyyy-MM-dd hh:mm:ss"));

	}

	private void generateDBCompany(Company dbCompany, Company websiteCompany) {

		dbCompany.setoTEresourceId(websiteCompany.getoTEresourceId() == null ? dbCompany.getoTEresourceId() : websiteCompany.getoTEresourceId());
		dbCompany.setfEresourceId(websiteCompany.getfEresourceId() == null ? dbCompany.getfEresourceId() : websiteCompany.getfEresourceId());
		dbCompany.setGanjiresourceId(websiteCompany.getGanjiresourceId() == null ? dbCompany.getGanjiresourceId() : websiteCompany.getGanjiresourceId());

		dbCompany.setName(websiteCompany.getName() == null ? dbCompany.getName() : websiteCompany.getName());
		dbCompany.setContactor(websiteCompany.getContactor() == null ? dbCompany.getContactor() : websiteCompany.getContactor());
		dbCompany.setEmail(websiteCompany.getEmail() == null ? dbCompany.getEmail() : websiteCompany.getEmail());
		dbCompany.setEmailSrc(websiteCompany.getEmailSrc() == null ? dbCompany.getEmailSrc() : websiteCompany.getEmailSrc());
		dbCompany.setPhone(websiteCompany.getPhone() == null ? dbCompany.getPhone() : websiteCompany.getPhone());
		dbCompany.setPhoneSrc(websiteCompany.getPhoneSrc() == null ? dbCompany.getPhoneSrc() : websiteCompany.getPhoneSrc());
		dbCompany.setMobilePhone(websiteCompany.getMobilePhone() == null ? dbCompany.getMobilePhone() : websiteCompany.getMobilePhone());
		dbCompany.setMobilePhoneSrc(websiteCompany.getMobilePhoneSrc() == null ? dbCompany.getMobilePhoneSrc() : websiteCompany.getMobilePhoneSrc());
		dbCompany.setAddress(websiteCompany.getAddress() == null ? dbCompany.getAddress() : websiteCompany.getAddress());
		dbCompany.setArea(websiteCompany.getArea() == null ? dbCompany.getArea() : websiteCompany.getArea());
		dbCompany.setfEurl(websiteCompany.getfEurl() == null ? dbCompany.getfEurl() : websiteCompany.getfEurl());
		dbCompany.setOteUrl(websiteCompany.getOteUrl() == null ? dbCompany.getOteUrl() : websiteCompany.getOteUrl());
		dbCompany.setGanjiUrl(websiteCompany.getGanjiUrl() == null ? dbCompany.getGanjiUrl() : websiteCompany.getGanjiUrl());
		dbCompany.setEmployeeCount(websiteCompany.getEmployeeCount() == null ? dbCompany.getEmployeeCount() : websiteCompany.getEmployeeCount());
		dbCompany.setDescription(websiteCompany.getDescription() == null ? dbCompany.getDescription() : websiteCompany.getDescription());

	}

	public Company grabSingleFECompanyByUrlId(Integer urlId) {

		return grabSingleFECompanyByUrl(feCompanyURLRepository.findOne(urlId));
	}

	public Company grabSingleFECompanyByUrl(FeCompanyURL feCompanyURL) {

		Company company = envelopeCompany(feCompanyURL);

		HtmlParserUtilFor58.getInstance().findCompanyDetails(company);
		try {
			Company savedCompany = mergeCompanyData(company, ResourceTypeEnum.FiveEight.getId());
			feCompanyURL.setHasGet(true);
			if (savedCompany != null) {
				feCompanyURL.setSavedCompany(savedCompany.getId().toString());
			}
		} catch (org.springframework.orm.jpa.JpaSystemException tmdException) {
			System.out.println(tmdException.getMessage());
		}

		feCompanyURLRepository.save(feCompanyURL);

		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {

		}

		return company;
	}

	private Company envelopeCompany(FeCompanyURL feCompanyURL) {

		Company company = Company.create();

		try {

			company.setName(feCompanyURL.getName());
			company.setArea(feCompanyURL.getArea());

			company.setCityId(feCompanyURL.getCityId());
			company.setfEresourceId(feCompanyURL.getCompanyId());
			company.setfEurl(feCompanyURL.getUrl());

			company.setUpdateDate(XinXinConstants.SIMPLE_DATE_FORMATTER.parse(feCompanyURL.getPublishDate()));
			return company;

		} catch (ParseException e) {
			company.setUpdateDate(null);
		}
		return company;
	}

	public void feJobDailyWork() {

		GrabCompanyDetailLog grabCompanyDetailLog = new GrabCompanyDetailLog();

		int pageNumber = 0;
		int pageSize = 3;

		int successCount = 0;
		int failCount = 0;
		try {
			grabCompanyDetailLog.setStartDate(XinXinUtils.getNow());
			while (true) {
				PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
				Page<FeCompanyURL> feCompanyUrlPage = feCompanyURLRepository.findAll(generateSpecificationForDailyJobUrl(), pageRequest);

				for (FeCompanyURL feCompanyURL : feCompanyUrlPage) {
					try {
						grabSingleFECompanyByUrl(feCompanyURL);
						System.out.println(feCompanyURL.toString());

						successCount++;
					} catch (Exception e) {
						failCount++;
						grabCompanyDetailLog.setStatus("fail");
						logger.error("Grab single company fail : " + e.getMessage() + "      --------------->     " + feCompanyURL.toString());
					}

				}

				if (!feCompanyUrlPage.hasNextPage()) {
					break;
				}
				pageNumber++;
			}

			grabCompanyDetailLog.setStatus("success");
			grabCompanyDetailLog.setEndDate(XinXinUtils.getNow());

		} catch (Exception e) {

			grabCompanyDetailLog.setStatus("fail");
		}

		grabCompanyDetailLog.setFailCount(failCount);
		grabCompanyDetailLog.setSuccessCount(successCount);
		grabCompanyDetailLog.setType("58");

		grabCompanyDetailLogRepository.save(grabCompanyDetailLog);

	}

	private Specification<FeCompanyURL> generateSpecificationForDailyJobUrl() {

		return new Specification<FeCompanyURL>(){

			public Predicate toPredicate(Root<FeCompanyURL> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				Predicate predicate = criteriaBuilder.conjunction();

				predicate.getExpressions().add(criteriaBuilder.isNull(root.get("savedCompany")));
				predicate.getExpressions().add(criteriaBuilder.lessThan(root.<Date> get("createDate"), new Date()));

				return predicate;
			}

		};
	}
	// public Company grabSingleFECompanyByUrlId(Integer urlId) {
	//
	// FeCompanyURL feCompanyURL = feCompanyURLRepository.findOne(urlId);
	// if(feCompanyURL.getHasGet()) {
	// return null;
	// }
	//
	// String resouceId = feCompanyURL.getCompanyId();
	// Integer cityId = feCompanyURL.getCityId();
	//
	// Integer existingCompanyId =
	// companyRepository.findCompanyByFEResourceIdAndCityId(resouceId, cityId);
	// if(existingCompanyId != null) {
	// return null;
	// }
	//
	// Company initialCompany = Company.create();
	// initialCompany.setCityId(cityId);
	// initialCompany.setfEresourceId(resouceId);
	// initialCompany.setArea(feCompanyURL.getArea());
	// initialCompany.setfEurl(feCompanyURL.getUrl());
	//
	// CompanyDetailVo detailVo = null;
	// try {
	// detailVo =
	// FiveEightOneCityDetailPageParser.parseDetailFromUrl(feCompanyURL.getUrl());
	//
	// } catch (FailingHttpStatusCodeException e) {
	// } catch (MalformedURLException e) {
	// } catch (IOException e) {
	// }
	//
	// System.out.println(detailVo.toString());
	//
	// return null;
	// }

}
