package com.ls.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.COMM_FAILURE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.ls.constants.XinXinConstants;
import com.ls.entity.ApplyingWillingCustomer;
import com.ls.entity.City;
import com.ls.entity.Company;
import com.ls.entity.CompanyAdditional;
import com.ls.entity.LearningHistory;
import com.ls.entity.Phase;
import com.ls.entity.PhoneCallHistory;
import com.ls.entity.Problem;
import com.ls.entity.ProblemCategory;
import com.ls.entity.Province;
import com.ls.entity.User;
import com.ls.enums.ApplyingCustomerStatus;
import com.ls.enums.CustomerStatusEnum;
import com.ls.repository.ApplyingWillingCustomerRepository;
import com.ls.repository.CompanyAdditionalRepository;
import com.ls.repository.CompanyRepository;
import com.ls.repository.PhoneCallHistoryRepository;
import com.ls.repository.ProblemRepository;
import com.ls.repository.ProvinceRepository;
import com.ls.repository.UserRepository;
import com.ls.service.CommonService;
import com.ls.service.CompanyService;
import com.ls.util.XinXinUtils;
import com.ls.vo.AdvanceSearch;
import com.ls.vo.CompanySearchVo;
import com.ls.vo.ResponseVo;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ProblemRepository problemRepository;

	@Autowired
	private ProvinceRepository provinceRepository;

	@Autowired
	private ApplyingWillingCustomerRepository applyingWillingCustomerRepository;

	@Autowired
	private CompanyAdditionalRepository companyAdditionalRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PhoneCallHistoryRepository phoneCallHistoryRepository;

	@Resource(name = "commonService")
	private CommonService commonService;

	public List<Company> findCompany(String name) {

		// TODO Auto-generated method stub
		return null;
	}

	public List<Company> findAllCompanies() {

		return companyRepository.findAll();
	}

	public Page<Company> getCompanyInPage(Integer index) {

		return companyRepository.findAll(new PageRequest(index, 10));
	}

	public Problem saveProblem(Problem problem) {

		return problemRepository.save(problem);
	}

	public Page<Company> getCompanyInPage(final CompanySearchVo companySearchVo) {

		User currentUser = commonService.getCurrentLoggedInUser();
		boolean isNotAdmin = commonService.checkUserNotHasRole(currentUser.getUsername(), "ROLE_ADMIN");

		if (isNotAdmin && (currentUser.getCities() == null || currentUser.getCities().isEmpty())) {
			// throw new RuntimeException("未分配城市到当前用户。");
		}

		Page<Company> companyPage = companyRepository.findAll(generateSpecification(companySearchVo), new PageRequest(Integer.valueOf(companySearchVo.getPageNumber()) - 1, 10));

		// fuck lazy loading
		List<Company> companies = companyPage.getContent();

		for (Company company : companies) {
			company.setProblems(new ArrayList<Problem>());
			company.setAddtion(null);
			company.setPhoneCallHistories(null);
			company.setLearningHistories(null);
		}

		return companyPage;
	}

	private Specification<Company> generateSpecification(final CompanySearchVo companySearchVo) {

		return new Specification<Company>(){

			public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				query.distinct(true);

				Predicate predicate = criteriaBuilder.conjunction();
				// Predicate otherPredicate = criteriaBuilder.conjunction();

				// Predicate totalPredicate = criteriaBuilder.or(predicate, otherPredicate);

				User currentUser = commonService.getCurrentLoggedInUser();
				boolean isNotAdmin = commonService.checkUserNotHasRole(currentUser.getUsername(), "ROLE_ADMIN");

				Predicate privateCustomerPredicate = criteriaBuilder.equal(root.get("ownerUserId"), currentUser.getId());
				
				String selectedDatasourceType = companySearchVo.getSelectedDataSourceType();

				if (isNotAdmin && (currentUser.getCities() == null || currentUser.getCities().isEmpty())) {
					return privateCustomerPredicate;
				}

				String searchId = companySearchVo.getSearchId();
				if (StringUtils.isNotBlank(searchId)) {

					Iterable<String> ids = null;

					if (searchId.trim().contains(" ")) {
						ids = Splitter.on(" ").omitEmptyStrings().trimResults().split(searchId);
					} else if (searchId.trim().contains(",")) {
						ids = Splitter.on(",").omitEmptyStrings().trimResults().split(searchId);
					} else {
						predicate.getExpressions().add(criteriaBuilder.equal(root.get("id"), Integer.valueOf(companySearchVo.getSearchId())));
					}

					if (ids != null) {

						List<Integer> idsIntegers = new ArrayList<Integer>();
						for (String id : ids) {
							idsIntegers.add(Integer.valueOf(id));
						}
						predicate.getExpressions().add(root.get("id").in(idsIntegers));
					}
					return predicate;
				}

				if (StringUtils.isNotBlank(companySearchVo.getSelectedProblemCategory())) {

					Join<Company, Problem> problemJoin = root.joinList("problems", JoinType.LEFT);

					predicate.getExpressions().add(criteriaBuilder.equal(problemJoin.<Integer> get("category"), companySearchVo.getSelectedProblemCategory()));
				}

				// status
				if (StringUtils.isNotBlank(companySearchVo.getCustomerStatus())) {
					Integer status = Integer.valueOf(companySearchVo.getCustomerStatus());
					predicate.getExpressions().add(criteriaBuilder.equal(root.<Integer> get("status"), status));
				}

				// predicate.getExpressions().add(criteriaBuilder.or(criteriaBuilder.equal(root.get("ownerUserId"), currentUser.getId()),
				// criteriaBuilder.isNull(root. <Integer> get("ownerUserId")))); //nobody owns it

				if (StringUtils.isNotBlank(companySearchVo.getCompanyNameParam())) {
					predicate.getExpressions().add(criteriaBuilder.like(root.<String> get("name"), "%" + companySearchVo.getCompanyNameParam().trim() + "%"));
				}

				if (StringUtils.isNotBlank(companySearchVo.getContactorParam())) {
					predicate.getExpressions().add(criteriaBuilder.equal(root.<String> get("contactor"), companySearchVo.getContactorParam().trim()));
				}

				String starLevelComparator = companySearchVo.getStarLevelOperator();
				if (StringUtils.isNotBlank(starLevelComparator) && !starLevelComparator.equals("ALL")) {

					Integer star = Integer.valueOf(companySearchVo.getStarParam());
					if (starLevelComparator.equals(">")) {
						predicate.getExpressions().add(criteriaBuilder.greaterThan(root.<Integer> get("star"), star));
					} else if (starLevelComparator.equals(">=")) {
						predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.<Integer> get("star"), star));
					} else if (starLevelComparator.equals("=")) {
						predicate.getExpressions().add(criteriaBuilder.equal(root.<Integer> get("star"), star));
					} else if (starLevelComparator.equals("<")) {
						predicate.getExpressions().add(criteriaBuilder.lessThan(root.<Integer> get("star"), star));
					} else if (starLevelComparator.equals("<=")) {
						predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.<Integer> get("star"), star));
					} else if (starLevelComparator.equals("<>")) {
						predicate.getExpressions().add(criteriaBuilder.notEqual(root.<Integer> get("star"), star));
					}
				}

				AdvanceSearch advanceSearch = companySearchVo.getAdvanceSearch();

				if (advanceSearch != null && !advanceSearch.isEverythingBlank()) {

					// String birthdayType = advanceSearch.getBirthdayType();
					// String birthdayValue = advanceSearch.getBirthDayValue();
					//
					// if (StringUtils.isNotBlank(birthdayType) && StringUtils.isNotBlank(birthdayValue)) {
					//
					// Join<Company, CompanyAdditional> companyAddtionalJoin = root.join("addtion", JoinType.LEFT);
					// predicate.getExpressions().add(criteriaBuilder.equal(companyAddtionalJoin.<String> get(birthdayType), birthdayValue));
					// }

					String contactStartDate = advanceSearch.getAppointStartDate();
					String contactEndDate = advanceSearch.getAppointEndDate();

					if (StringUtils.isNotBlank(contactStartDate) || StringUtils.isNotBlank(contactEndDate)) {

						Join<Company, PhoneCallHistory> phoneCallHistoryJoin = root.joinList("phoneCallHistories", JoinType.LEFT);
						Join<PhoneCallHistory, User> historyUserJoin = phoneCallHistoryJoin.join("user");

						if (StringUtils.isNotBlank(contactStartDate)) {
							try {
								Date startDate = XinXinConstants.SIMPLE_DATE_FORMATTER.parse(contactStartDate);
								predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(phoneCallHistoryJoin.<Date> get("nextDate"), startDate));

							} catch (ParseException e) {
								throw new RuntimeException("日期不符合格式");
							}
						}

						if (StringUtils.isNotBlank(contactEndDate)) {
							try {
								Date endDate = XinXinConstants.SIMPLE_DATE_FORMATTER.parse(contactEndDate);
								predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(phoneCallHistoryJoin.<Date> get("nextDate"), endDate));
							} catch (ParseException e) {
								throw new RuntimeException("日期不符合格式");
							}
						}
						if (isNotAdmin) {
							predicate.getExpressions().add(criteriaBuilder.equal(historyUserJoin.get("id"), currentUser.getId()));
						}

					}

					String phase = advanceSearch.getPhase();

					if (StringUtils.isNotBlank(phase)) {

						Subquery<Company> companyIdInLearningHistorySubquery = query.subquery(Company.class);

						Root<Company> companySubqueryRoot = companyIdInLearningHistorySubquery.from(Company.class);

						Join<Company, LearningHistory> learningHistoriesJoin = companySubqueryRoot.joinList("learningHistories", JoinType.LEFT);

						Join<LearningHistory, Phase> phaseJoin = learningHistoriesJoin.join("phase", JoinType.LEFT);

						Predicate phaseIdPredicate = criteriaBuilder.equal(phaseJoin.get("id"), Integer.valueOf(phase));

						Predicate companyIdPredicate = criteriaBuilder.equal(learningHistoriesJoin.get("company"), root);

						companyIdInLearningHistorySubquery.where(criteriaBuilder.and(phaseIdPredicate, companyIdPredicate));

						predicate.getExpressions().add(criteriaBuilder.not(criteriaBuilder.exists(companyIdInLearningHistorySubquery)));

						companyIdInLearningHistorySubquery.select(companySubqueryRoot);

						predicate.getExpressions().add(criteriaBuilder.greaterThan(root.<Integer> get("status"), 40));
					}

					String movingMonth = advanceSearch.getSelectedMovingMonth();

					if (StringUtils.isNotBlank(movingMonth)) {
						if (movingMonth.length() == 1) {

							movingMonth = "0" + movingMonth;
						}
						Join<Company, CompanyAdditional> companyAddtionalJoin = root.join("addtion", JoinType.LEFT);

						Predicate movingPredicate =
							criteriaBuilder.or(criteriaBuilder.like(companyAddtionalJoin.<String> get("firstKidBirthday"), movingMonth + "-%"), criteriaBuilder.like(companyAddtionalJoin.<String> get("bossBirthday"), movingMonth + "-%"),
								criteriaBuilder.like(companyAddtionalJoin.<String> get("companyAnniversary"), movingMonth + "-%"), criteriaBuilder.like(companyAddtionalJoin.<String> get("merryAnniversary"), movingMonth + "-%"), criteriaBuilder.like(companyAddtionalJoin.<String> get("loverBirthday"), movingMonth + "-%"));

						predicate.getExpressions().add(movingPredicate);
					}

				}

				if (StringUtils.isNotBlank(selectedDatasourceType)) {
					
					if (selectedDatasourceType.equals("M")) {
						predicate.getExpressions().add(privateCustomerPredicate);
						
						return predicate;
						
					} else if(selectedDatasourceType.equals("A")) {
						predicate.getExpressions().add(criteriaBuilder.isNull(root.get("ownerUserId")));
					} else {
						predicate.getExpressions().add(criteriaBuilder.or(privateCustomerPredicate, criteriaBuilder.isNull(root.get("ownerUserId"))));
						
						return predicate;
						
					}
				}
				
				if (StringUtils.isNotBlank(companySearchVo.getCityId())) {
					
					Predicate cityIdPredicate = criteriaBuilder.equal(root.<String> get("cityId"), companySearchVo.getCityId().trim());

					//predicate.getExpressions().add(criteriaBuilder.or(cityIdPredicate, privateCustomerPredicate));
					
					predicate.getExpressions().add(cityIdPredicate);

				} else if (StringUtils.isNotBlank(companySearchVo.getProvinceId())) {

					if (isNotAdmin) {

						List<City> assignedCities = currentUser.getCities();

						List<Integer> cityIds = new ArrayList<Integer>();
						for (City city : assignedCities) {
							
							if (city.getProvince().getId().toString().equals(companySearchVo.getProvinceId())) {
								cityIds.add(city.getId());
							}
						}
						
						if (cityIds.isEmpty()) {
							
							throw new RuntimeException("这个省市下未分配任何城市。");
							
						} else {
							
							predicate.getExpressions().add(root.get("cityId").in(cityIds));
							
						}
						
					} else {

						Province province = provinceRepository.findOne(Integer.valueOf(companySearchVo.getProvinceId()));

						List<City> cities = province.getCitys();

						List<Integer> cityIds = new ArrayList<Integer>();
						for (City city : cities) {
							cityIds.add(city.getId());
						}

						if (!cityIds.isEmpty()) {
							predicate.getExpressions().add(root.get("cityId").in(cityIds));
						}
					}

				} else {

					if (isNotAdmin) {
						// need refactor
						List<City> assignedCities = currentUser.getCities();

						List<Integer> cityIds = new ArrayList<Integer>();
						for (City city : assignedCities) {
							cityIds.add(city.getId());
						}
						if (!cityIds.isEmpty()) {
							predicate.getExpressions().add(root.get("cityId").in(cityIds));
						}
					}

				}

				// user private customer shown on the most top.
				Order ownerUserIdOrder = criteriaBuilder.desc(root.get("ownerUserId"));
				Order starOrder = criteriaBuilder.desc(root.get("star"));

				List<Order> orders = ImmutableList.of(ownerUserIdOrder, starOrder);

				query.orderBy(orders);

				return predicate;
			}
		};

	}

	public CompanyAdditional saveAdditionalCompanyInformation(CompanyAdditional addtion) {

		return companyAdditionalRepository.saveAndFlush(addtion);
	}

	public CompanyAdditional findCompanyAddtionalInformationByCompanyId(Integer companyId) {

		Company company = companyRepository.findOne(Integer.valueOf(companyId));

		CompanyAdditional companyAdditional = companyAdditionalRepository.findByCompany(company);
		// fuck lazy
		if (companyAdditional != null) {
			companyAdditional.setCompany(null);
		}

		return companyAdditional;
	}

	@Transactional
	public void checkOrUncheckProblem(String companyJson, String problemJson, String checkFlag) {

		Company company = XinXinUtils.getJavaObjectFromJsonString(companyJson, Company.class);
		Problem problem = XinXinUtils.getJavaObjectFromJsonString(problemJson, Problem.class);

		Company freshCompanyFromDb = companyRepository.findOne(company.getId());
		Problem freshProblemFromDb = problemRepository.findOne(problem.getId());

		List<Problem> companyProblemList = freshCompanyFromDb.getProblems();
		List<Company> problemCompanyList = freshProblemFromDb.getCompanies();

		if (companyProblemList == null) {
			companyProblemList = new ArrayList<Problem>();
		}

		if (problemCompanyList == null) {
			problemCompanyList = new ArrayList<Company>();
		}
		Boolean checked = Boolean.valueOf(checkFlag);
		// add a problem
		if (checked) {

			if (findProblemId(companyProblemList, freshProblemFromDb) == null) {
				companyProblemList.add(freshProblemFromDb);
			}

			companyRepository.saveAndFlush(freshCompanyFromDb);

			// remove a problem
		} else {

			// problem.setCompanies(new ArrayList<Company>());

			Integer problemIdToRemove = findProblemId(companyProblemList, freshProblemFromDb);
			if (problemIdToRemove != null) {

				companyProblemList.remove(freshProblemFromDb);
			}

			companyRepository.saveAndFlush(freshCompanyFromDb);
		}

	}

	private Integer findProblemId(List<Problem> problems, Problem problem) {

		if (null == problems || problems.size() == 0) {
			return null;
		}

		for (Problem element : problems) {

			if (element.getId() == problem.getId()) {
				return problem.getId();
			}
		}
		return null;
	}

	@Secured({"ROLE_SALES_MANAGER", "ROLE_ADMIN"})
	public ResponseVo changeCompanyStatus(String companyId, String statusId) {

		ResponseVo response = XinXinUtils.makeGeneralSuccessResponse();

		String currentUsername = XinXinUtils.getCurrentUserName();
		User currentUser = userRepository.findByUsername(currentUsername);

		ApplyingWillingCustomer applyingWillingCustomer = applyingWillingCustomerRepository.findByCompanyIdAndUser(Integer.valueOf(companyId), currentUser);

		Company company = companyRepository.findOne(Integer.valueOf(companyId));
		Integer targetStatus = Integer.valueOf(statusId);

		if (company.getStatus() == CustomerStatusEnum.APPLYING_WILLING_CUSTOMER.getId()) {

			if (targetStatus > CustomerStatusEnum.APPLYING_WILLING_CUSTOMER.getId()) {

				response = ResponseVo.newFailMessage("该顾客正在意向客户申请中，不能改变状态！");
				return response;
			} else if (targetStatus > CustomerStatusEnum.APPLYING_WILLING_CUSTOMER.getId()) {

				response = ResponseVo.newFailMessage("该顾客已经在意向客户申请中！");
				return response;
			} else {

				if (applyingWillingCustomer != null) {
					applyingWillingCustomerRepository.delete(applyingWillingCustomer);
				}
				response = ResponseVo.newSuccessMessage("该客户的意向申请已经取消！");
			}

		}

		if (targetStatus == CustomerStatusEnum.APPLYING_WILLING_CUSTOMER.getId()) {

			if (applyingWillingCustomer == null) {
				CompanyAdditional companyAdditional = companyAdditionalRepository.findByCompany(company);

				if (null == companyAdditional) {
					throw new RuntimeException("顾客信息不充分，如缺少：院长姓名，联系方式等关键信息。");
				}

				ApplyingWillingCustomer applyingWillingCustomerToSave = new ApplyingWillingCustomer();

				applyingWillingCustomerToSave.setUser(currentUser);
				applyingWillingCustomerToSave.setBossName(companyAdditional.getBossName());
				applyingWillingCustomerToSave.setCompanyId(Integer.valueOf(companyId));
				applyingWillingCustomerToSave.setApplyingDate(XinXinUtils.getNow());
				applyingWillingCustomerToSave.setUpdateDate(XinXinUtils.getNow());
				applyingWillingCustomerToSave.setStatus(ApplyingCustomerStatus.APPLYING.getId());
				applyingWillingCustomerToSave.setCompanyName(company.getName());
				applyingWillingCustomerToSave.setApplyerName(currentUser.getName());
				applyingWillingCustomerToSave.setCompanyAdditionalId(companyAdditional.getId());
				applyingWillingCustomerRepository.save(applyingWillingCustomerToSave);

				response = ResponseVo.newSuccessMessage("已成功提交意向客户申请！");

			} else {

				response = ResponseVo.newFailMessage("该申请已经存在！");
				return response;
			}
		}

		company.setStatus(targetStatus);
		companyRepository.save(company);

		return response;
	}

	public PhoneCallHistory savePhoneCallHistory(String jsonPhonecall, String companyId) throws ParseException {

		JSONObject jsonObject = JSONObject.fromObject(jsonPhonecall);
		String nextDate = jsonObject.getString("nextDate");

		PhoneCallHistory phoneCallHistory = (PhoneCallHistory)JSONObject.toBean(jsonObject, PhoneCallHistory.class);
		phoneCallHistory.setNextDate(XinXinConstants.SIMPLE_DATE_FORMATTER.parse(nextDate));

		Company company = companyRepository.findOne(Integer.valueOf(companyId));

		phoneCallHistory.setCreateDate(XinXinUtils.getNow());

		phoneCallHistory.setCompany(company);
		phoneCallHistory.setUser(commonService.getCurrentLoggedInUser());

		return phoneCallHistoryRepository.saveAndFlush(phoneCallHistory);
	}

}
