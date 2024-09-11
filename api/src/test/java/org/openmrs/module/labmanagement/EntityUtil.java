package org.openmrs.module.labmanagement;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.openmrs.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.Privileges;
import org.openmrs.module.labmanagement.api.dao.LabManagementDao;
import org.openmrs.module.labmanagement.api.dto.*;
import org.openmrs.module.labmanagement.api.model.*;
import org.openmrs.module.labmanagement.api.utils.GlobalProperties;

@SuppressWarnings("deprecation")
public class EntityUtil {

	public static final String BASE_DATASET_DIR = "org/openmrs/module/labmanagement/api/";

	public static final String TEST_DATA = BASE_DATASET_DIR + "TestData.xml";
	public static final String TEST_CONFIGURATIONS_IMPORT_CSV = BASE_DATASET_DIR + "TestConfigsImport.csv";
	public static final String TEST_CONFIGURATIONS_IMPORT_CSV2 = BASE_DATASET_DIR + "TestConfigsImport2.csv";
	public static final String TEST_CONFIGURATIONS_BAD_IMPORT_CSV = BASE_DATASET_DIR + "TestConfigsImportBad1.csv";

	private static final Random random = new Random();

	private final Drug drug;

	private final User user;

	private final Location location;

	private final Role role;

	private final Concept concept;

	private final Patient patient;

	public EntityUtil(Drug drug, User user, Location location, Role role, Concept concept, Patient patient) {
		this.drug = drug;
		this.user = user;
		this.location = location;

		this.role = role;
		this.concept = concept;
		this.patient = patient;
	}
	public Provider getProvider(){
		return Context.getProviderService().getProvider(1);
	}

	public Visit getVisit(){
		int[] visits = { 1,2,3,4,5,6};
		int index = random.nextInt(visits.length - 1);
		return Context.getVisitService().getVisit(visits[index]);
	}

	public CareSetting getCareSetting(){
		int[] careSettings = { 1,2,3};
		int index = random.nextInt(careSettings.length - 1);
		return Context.getOrderService().getCareSetting(careSettings[index]);
	}

	public Obs getObs(){
		int[] observations = { 9, 10, 11, 12, 13, 14, 16};
		int index = random.nextInt(observations.length - 1);
		return Context.getObsService().getObs(observations[index]);
	}

	public Order getOrder() {
		int[] orders = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int index = random.nextInt(orders.length - 1);
		return Context.getOrderService().getOrder(orders[index]);
	}

	public Encounter getEncounter() {
		int[] encounters = { 3, 4, 5, 6 };
		int index = random.nextInt(encounters.length - 1);
		return Context.getEncounterService().getEncounter(encounters[index]);
	}

	public boolean getRandomBool() {
		return random.nextBoolean();
	}

	public Date getRandomDate() {
		int day = random.nextInt(365);
		day = getRandomBool() ? day : day * -1;
		Date date = new Date();
		return DateUtils.addDays(date, day);
	}

	public Order.Urgency getRandomUrgency(){
		int day = random.nextInt(10);
		return day % 2 == 0 ? Order.Urgency.ROUTINE : Order.Urgency.STAT;
	}

	public Double getRandomDouble() {
		return random.nextDouble();
	}

	public float getRandomFloat() {
		return random.nextFloat();
	}

	public long getRandomLong() {
		return random.nextLong();
	}

	public int getRandomInt() {
		return random.nextInt();
	}

	public BigDecimal getRandomBigDecimal() {
		return BigDecimal.valueOf(Math.random());
	}

	public short getRandomShort() {
		return (short) random.nextInt(Short.MAX_VALUE);
	}

	public byte getRandomByte() {
		byte[] bytes = new byte[1];
		random.nextBytes(bytes);
		return bytes[0];
	}

	public String getLocationTag() {
		return "Store";
	}

	public String getRandomString(int length) {
		return RandomStringUtils.randomAlphabetic(length);
	}


	public Object getRandomEnum(Class classType) {
		try {
			Object[] list = classType.getEnumConstants();;
			return list[random.nextInt(list.length - 1)];
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setProperty(Object object, String field, Object value) {
		try {
			Field f = object.getClass().getDeclaredField(field);
			f.setAccessible(true);
			f.set(object, value);
		}
		catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public Drug getDrug() {
		return drug;
	}

	public User getUser() {
		return user;
	}

	public Location getLocation() {
		return location;
	}

	public Role getRole() {
		return role;
	}

	public Patient getPatient() {
		return patient;
	}

	public Concept getConcept() {
		return concept;
	}

	public Worksheet newWorksheet(LabManagementDao dao){
		Worksheet worksheet=new Worksheet();
		worksheet.setCreator(getUser());
		worksheet.setDateCreated(new Date());
		worksheet.setChangedBy(getUser());
		worksheet.setDateChanged(getRandomDate());
		worksheet.setVoided(false);
		worksheet.setDateVoided(getRandomDate());
		worksheet.setVoidedBy(getUser());
		worksheet.setVoidReason(getRandomString(255));
		worksheet.setAtLocation(getLocation());
		worksheet.setWorksheetDate(getRandomDate());
		worksheet.setTest(getConcept());
		worksheet.setDiagnosisType(getConcept());
		worksheet.setStatus(WorksheetStatus.PENDING);
		worksheet.setWorksheetNo(getRandomString(8));
		worksheet.setRemarks(getRandomString(100));
		worksheet.setResponsiblePerson(getUser());
		worksheet.setResponsiblePersonOther(null);
		return  worksheet;
	}

	public TestRequestItem newTestRequestItem(LabManagementDao dao){
		TestRequestItem testRequestItem=new TestRequestItem();
		testRequestItem.setCreator(getUser());
		testRequestItem.setDateCreated(getRandomDate());
		testRequestItem.setChangedBy(getUser());
		testRequestItem.setDateChanged(getRandomDate());
		testRequestItem.setVoided(false);
		testRequestItem.setDateVoided(getRandomDate());
		testRequestItem.setVoidedBy(getUser());
		testRequestItem.setVoidReason(getRandomString(255));
		testRequestItem.setOrder(getOrder());
		testRequestItem.setReturnCount(getRandomInt());
		testRequestItem.setAtLocation(getLocation());
		testRequestItem.setToLocation(getLocation());
		testRequestItem.setReferredOut(getRandomBool());
		testRequestItem.setReferralOutOrigin((ReferralOutOrigin) getRandomEnum(ReferralOutOrigin.class));
		testRequestItem.setReferralOutBy(getUser());
		testRequestItem.setReferralOutDate(getRandomDate());
		ReferralLocation referralLocation=newReferralLocation(dao);
		dao.saveReferralLocation(referralLocation);
		testRequestItem.setReferralToFacility(referralLocation);
		testRequestItem.setReferralToFacilityName(getRandomString(255));
		testRequestItem.setRequireRequestApproval(getRandomBool());
		testRequestItem.setRequestApprovalResult((ApprovalResult) getRandomEnum(ApprovalResult.class));
		testRequestItem.setRequestApprovalBy(getUser());
		testRequestItem.setRequestApprovalDate(getRandomDate());
		testRequestItem.setRequestApprovalRemarks(getRandomString(500));
		testRequestItem.setInitialSample(null);
		testRequestItem.setFinalResult(null);
		testRequestItem.setStatus((TestRequestItemStatus) getRandomEnum(TestRequestItemStatus.class));
		testRequestItem.setEncounter(getEncounter());
		TestRequest testRequest=newTestRequest(dao);
		dao.saveTestRequest(testRequest);
		testRequestItem.setTestRequest(testRequest);
		return  testRequestItem;
	}

	public TestResult newTestResult(LabManagementDao dao){
		TestResult testResult=new TestResult();		testResult.setCreator(getUser());
		testResult.setDateCreated(getRandomDate());
		testResult.setChangedBy(getUser());
		testResult.setDateChanged(getRandomDate());
		testResult.setVoided(false);
		testResult.setDateVoided(getRandomDate());
		testResult.setVoidedBy(getUser());
		testResult.setVoidReason(getRandomString(255));
		WorksheetItem worksheetItem=newWorksheetItem(dao);
		dao.saveWorksheetItem(worksheetItem);
		testResult.setWorksheetItem(worksheetItem);
		TestRequestItemSample sample=newTestRequestItemSample(dao);
		dao.saveTestRequestItemSample(sample);
		testResult.setTestRequestItemSample(sample);
		testResult.setOrder(getOrder());
		testResult.setObs(getObs());
		testResult.setResultBy(getUser());
		testResult.setStatus(getRandomString(50));
		testResult.setResultDate(getRandomDate());
		testResult.setRequireApproval(getRandomBool());
		testResult.setCompletedResult(false);
		testResult.setCompleted(false);
		testResult.setRemarks(getRandomString(100));
		testResult.setAdditionalTestsRequired(getRandomBool());
		testResult.setArchiveSample(getRandomBool());
		SampleActivity sampleActivity=newSampleActivity(dao);
		dao.saveSampleActivity(sampleActivity);
		testResult.setSampleActivity(sampleActivity);
		testResult.setRemarks(getRandomString(1000));
		return  testResult;
	}

	public BatchJobOwner newBatchJobOwner(LabManagementDao dao){
		BatchJobOwner batchJobOwner=new BatchJobOwner();
		BatchJob batchJob=newBatchJob(dao);
		dao.saveBatchJob(batchJob);
		batchJobOwner.setBatchJob(batchJob);
		batchJobOwner.setOwner(getUser());
		batchJobOwner.setDateCreated(getRandomDate());
		return  batchJobOwner;
	}

	public TestRequestItemSample newTestRequestItemSample(LabManagementDao dao){
		TestRequestItemSample testRequestItemSample=new TestRequestItemSample();
		testRequestItemSample.setCreator(getUser());
		testRequestItemSample.setDateCreated(getRandomDate());
		testRequestItemSample.setChangedBy(getUser());
		testRequestItemSample.setDateChanged(getRandomDate());
		testRequestItemSample.setVoided(false);
		testRequestItemSample.setDateVoided(getRandomDate());
		testRequestItemSample.setVoidedBy(getUser());
		testRequestItemSample.setVoidReason(getRandomString(255));
		TestRequestItem testRequestItem=newTestRequestItem(dao);
		dao.saveTestRequestItem(testRequestItem);
		testRequestItemSample.setTestRequestItem(testRequestItem);
		Sample sample=newSample(dao);
		dao.saveSample(sample);
		testRequestItemSample.setSample(sample);
		return  testRequestItemSample;
	}

	public BatchJob newBatchJob(LabManagementDao dao){
		BatchJob batchJob=new BatchJob();
		batchJob.setCreator(getUser());
		batchJob.setDateCreated(getRandomDate());
		batchJob.setChangedBy(getUser());
		batchJob.setDateChanged(getRandomDate());
		batchJob.setVoided(false);
		batchJob.setDateVoided(getRandomDate());
		batchJob.setVoidedBy(getUser());
		batchJob.setVoidReason(getRandomString(255));
		setProperty(batchJob,"batchJobType",getRandomEnum(BatchJobType.class));
		setProperty(batchJob,"status",getRandomEnum(BatchJobStatus.class));
		batchJob.setDescription(getRandomString(255));
		batchJob.setStartTime(getRandomDate());
		batchJob.setEndTime(getRandomDate());
		batchJob.setExpiration(getRandomDate());
		batchJob.setParameters(getRandomString(5000));
		batchJob.setPrivilegeScope(getRandomString(255));
		batchJob.setLocationScope(getLocation());
		batchJob.setExecutionState(getRandomString(5000));
		batchJob.setCancelReason(getRandomString(500));
		batchJob.setCancelledBy(getUser());
		batchJob.setCancelledDate(getRandomDate());
		batchJob.setExitMessage(getRandomString(2500));
		batchJob.setCompletedDate(getRandomDate());
		batchJob.setOutputArtifactSize(getRandomLong());
		batchJob.setOutputArtifactFileExt(getRandomString(10));
		batchJob.setOutputArtifactViewable(getRandomBool());
		return  batchJob;
	}

	public Sample newSample(LabManagementDao dao){
		Sample sample=new Sample();
		sample.setCreator(getUser());
		sample.setDateCreated(getRandomDate());
		sample.setChangedBy(getUser());
		sample.setDateChanged(getRandomDate());
		sample.setVoided(false);
		sample.setDateVoided(getRandomDate());
		sample.setVoidedBy(getUser());
		sample.setVoidReason(getRandomString(255));
		sample.setSampleType(getConcept());
		sample.setAtLocation(getLocation());
		sample.setContainerType(getConcept());
		sample.setCollectedBy(getUser());
		sample.setCollectionDate(getRandomDate());
		sample.setContainerCount(getRandomInt());
		sample.setAccessionNumber(getRandomString(255));
		sample.setProvidedRef(getRandomString(255));
		sample.setExternalRef(getRandomString(100));
		sample.setReferredOut(getRandomBool());
		sample.setVolume(getRandomBigDecimal());
		sample.setVolumeUnit(getConcept());
		//sample.setCurrentSampleActivity(ge);
		sample.setStatus(SampleStatus.COLLECTION);
		sample.setEncounter(getEncounter());
		TestRequest testRequest=newTestRequest(dao);
		dao.saveTestRequest(testRequest);
		sample.setTestRequest(testRequest);
		return  sample;
	}

	public ApprovalFlow newApprovalFlow(LabManagementDao dao){
		ApprovalFlow approvalFlow=new ApprovalFlow();
		approvalFlow.setCreator(getUser());
		approvalFlow.setDateCreated(getRandomDate());
		approvalFlow.setChangedBy(getUser());
		approvalFlow.setDateChanged(getRandomDate());
		approvalFlow.setVoided(false);
		approvalFlow.setDateVoided(getRandomDate());
		approvalFlow.setVoidedBy(getUser());
		approvalFlow.setVoidReason(getRandomString(255));
		approvalFlow.setName(getRandomString(100));
		ApprovalConfig levelOne=newApprovalConfig(dao);
		dao.saveApprovalConfig(levelOne);
		approvalFlow.setLevelOne(levelOne);
		ApprovalConfig levelTwo=newApprovalConfig(dao);
		dao.saveApprovalConfig(levelTwo);
		approvalFlow.setLevelTwo(levelTwo);
		ApprovalConfig levelThree=newApprovalConfig(dao);
		dao.saveApprovalConfig(levelThree);
		approvalFlow.setLevelThree(levelThree);
		ApprovalConfig levelFour=newApprovalConfig(dao);
		dao.saveApprovalConfig(levelFour);
		approvalFlow.setLevelFour(levelFour);
		approvalFlow.setSystemName(getRandomString(50));
		approvalFlow.setLevelOneAllowOwner(getRandomBool());
		approvalFlow.setLevelTwoAllowOwner(getRandomBool());
		approvalFlow.setLevelThreeAllowOwner(getRandomBool());
		approvalFlow.setLevelFourAllowOwner(getRandomBool());
		approvalFlow.setLevelTwoAllowPrevious(getRandomBool());
		approvalFlow.setLevelThreeAllowPrevious(getRandomBool());
		approvalFlow.setLevelFourAllowPrevious(getRandomBool());
		return  approvalFlow;
	}

	public TestResultDocument newTestResultDocument(LabManagementDao dao){
		TestResultDocument testResultDocument=new TestResultDocument();
		testResultDocument.setCreator(getUser());
		testResultDocument.setDateCreated(getRandomDate());
		testResultDocument.setChangedBy(getUser());
		testResultDocument.setDateChanged(getRandomDate());
		testResultDocument.setVoided(false);
		testResultDocument.setDateVoided(getRandomDate());
		testResultDocument.setVoidedBy(getUser());
		testResultDocument.setVoidReason(getRandomString(255));
		TestResult testResult=newTestResult(dao);
		dao.saveTestResult(testResult);
		testResultDocument.setTestResult(testResult);
		testResultDocument.setDocumentType(getRandomString(50));
		testResultDocument.setDocumentName(getRandomString(256));
		testResultDocument.setDocumentProvider(getRandomByte());
		testResultDocument.setDocumentProviderRef(getRandomString(1024));
		testResultDocument.setRemarks(getRandomString(500));
		return  testResultDocument;
	}

	public ApprovalConfig newApprovalConfig(LabManagementDao dao){
		ApprovalConfig approvalConfig=new ApprovalConfig();
		approvalConfig.setCreator(getUser());
		approvalConfig.setDateCreated(getRandomDate());
		approvalConfig.setChangedBy(getUser());
		approvalConfig.setDateChanged(getRandomDate());
		approvalConfig.setVoided(false);
		approvalConfig.setDateVoided(getRandomDate());
		approvalConfig.setVoidedBy(getUser());
		approvalConfig.setVoidReason(getRandomString(30));
		approvalConfig.setApprovalTitle(getRandomString(100));
		approvalConfig.setPrivilege(Privileges.TASK_LABMANAGEMENT_TESTRESULTS_APPROVE);
		approvalConfig.setPendingStatus(getRandomString(30));
		approvalConfig.setReturnedStatus(getRandomString(30));
		approvalConfig.setRejectedStatus(getRandomString(30));
		approvalConfig.setApprovedStatus(getRandomString(30));
		return  approvalConfig;
	}

	public SampleActivity newSampleActivity(LabManagementDao dao){
		SampleActivity sampleActivity=new SampleActivity();
		sampleActivity.setCreator(getUser());
		sampleActivity.setDateCreated(getRandomDate());
		sampleActivity.setChangedBy(getUser());
		sampleActivity.setDateChanged(getRandomDate());
		sampleActivity.setVoided(false);
		sampleActivity.setDateVoided(getRandomDate());
		sampleActivity.setVoidedBy(getUser());
		sampleActivity.setVoidReason(getRandomString(255));
		Sample sample=newSample(dao);
		dao.saveSample(sample);
		sampleActivity.setSample(sample);
		sampleActivity.setActivityType(getRandomString(50));
		sampleActivity.setSource(getLocation());
		sampleActivity.setDestination(getLocation());
		sampleActivity.setSourceState(getRandomString(50));
		sampleActivity.setDestinationState(getRandomString(50));
		sampleActivity.setActivityBy(getUser());
		sampleActivity.setRemarks(getRandomString(500));
		sampleActivity.setStatus(getRandomString(50));
		Sample toSample=newSample(dao);
		dao.saveSample(toSample);
		sampleActivity.setToSample(toSample);
		sampleActivity.setVolume(getRandomBigDecimal());
		sampleActivity.setThawCycles(getRandomInt());
		return  sampleActivity;
	}

	public TestConfig newTestConfig(LabManagementDao dao){
		TestConfig testConfig=new TestConfig();
		testConfig.setCreator(getUser());
		testConfig.setDateCreated(getRandomDate());
		testConfig.setChangedBy(getUser());
		testConfig.setDateChanged(getRandomDate());
		testConfig.setVoided(false);
		testConfig.setDateVoided(getRandomDate());
		testConfig.setVoidedBy(getUser());
		testConfig.setVoidReason(getRandomString(255));
		testConfig.setTest(getConcept());
		testConfig.setRequireApproval(getRandomBool());
		ApprovalFlow approvalFlow=newApprovalFlow(dao);
		dao.saveApprovalFlow(approvalFlow);
		testConfig.setApprovalFlow(approvalFlow);
		testConfig.setTestGroup(getConcept());
		testConfig.setEnabled(true);
		return  testConfig;
	}

	public TestApproval newTestApproval(LabManagementDao dao){
		TestApproval testApproval=new TestApproval();
		testApproval.setCreator(getUser());
		testApproval.setDateCreated(getRandomDate());
		testApproval.setChangedBy(getUser());
		testApproval.setDateChanged(getRandomDate());
		testApproval.setVoided(false);
		testApproval.setDateVoided(getRandomDate());
		testApproval.setVoidedBy(getUser());
		testApproval.setVoidReason(getRandomString(255));
		ApprovalFlow approvalFlow=newApprovalFlow(dao);
		dao.saveApprovalFlow(approvalFlow);
		testApproval.setApprovalFlow(approvalFlow);
		ApprovalConfig approvalConfig=newApprovalConfig(dao);
		dao.saveApprovalConfig(approvalConfig);
		testApproval.setApprovalConfig(approvalConfig);
		testApproval.setApprovalResult(null);
		testApproval.setRemarks(getRandomString(500));
		testApproval.setActivatedDate(getRandomDate());
		testApproval.setApprovalDate(getRandomDate());
		testApproval.setApprovedBy(getUser());

		testApproval.setCurrentApprovalLevel(getRandomInt());
		TestResult testResult=newTestResult(dao);
		dao.saveTestResult(testResult);
		testApproval.setTestResult(testResult);
		return  testApproval;
	}

	public WorksheetItem newWorksheetItem(LabManagementDao dao){
		WorksheetItem worksheetItem=new WorksheetItem();
		worksheetItem.setCreator(getUser());
		worksheetItem.setDateCreated(getRandomDate());
		worksheetItem.setChangedBy(getUser());
		worksheetItem.setDateChanged(getRandomDate());
		worksheetItem.setVoided(false);
		worksheetItem.setDateVoided(getRandomDate());
		worksheetItem.setVoidedBy(getUser());
		worksheetItem.setVoidReason(getRandomString(255));
		Worksheet worksheet=newWorksheet(dao);
		dao.saveWorksheet(worksheet);
		worksheetItem.setWorksheet(worksheet);
		TestRequestItemSample sample=newTestRequestItemSample(dao);
		dao.saveTestRequestItemSample(sample);
		worksheetItem.setTestRequestItemSample(sample);
		worksheetItem.setStatus(WorksheetItemStatus.PENDING);
		worksheetItem.setCompletedDate(getRandomDate());
		worksheetItem.setCancelledDate(getRandomDate());
		worksheetItem.setCancellationRemarks(getRandomString(500));
		return  worksheetItem;
	}

	public TestRequest newTestRequest(LabManagementDao dao){
		TestRequest testRequest=new TestRequest();
		testRequest.setCreator(getUser());
		testRequest.setDateCreated(getRandomDate());
		testRequest.setChangedBy(getUser());
		testRequest.setDateChanged(getRandomDate());
		testRequest.setVoided(false);
		testRequest.setDateVoided(getRandomDate());
		testRequest.setVoidedBy(getUser());
		testRequest.setVoidReason(getRandomString(255));
		testRequest.setPatient(getPatient());
		testRequest.setVisit(getVisit());
		testRequest.setEncounter(getEncounter());
		testRequest.setProvider(getProvider());
		testRequest.setRequestNo(getRandomString(100));
		testRequest.setUrgency(getRandomUrgency());
		testRequest.setCreator(getUser());
		testRequest.setCareSetting(getCareSetting());
		testRequest.setStatus((TestRequestStatus) getRandomEnum(TestRequestStatus.class));
		testRequest.setClinicalNote(getRandomString(500));
		testRequest.setAtLocation(getLocation());
		testRequest.setReferredIn(getRandomBool());
		ReferralLocation referralLocation=newReferralLocation(dao);
		dao.saveReferralLocation(referralLocation);
		testRequest.setReferralFromFacility(referralLocation);
		testRequest.setReferralFromFacilityName(getRandomString(255));
		testRequest.setReferralInExternalRef(getRandomString(50));
		testRequest.setRequestDate(new Date());
		return  testRequest;
	}

	public ReferralLocation newReferralLocation(LabManagementDao dao){
		ReferralLocation referralLocation=new ReferralLocation();
		referralLocation.setCreator(getUser());
		referralLocation.setDateCreated(getRandomDate());
		referralLocation.setChangedBy(getUser());
		referralLocation.setDateChanged(getRandomDate());
		referralLocation.setVoided(false);
		referralLocation.setDateVoided(getRandomDate());
		referralLocation.setVoidedBy(getUser());
		referralLocation.setVoidReason(getRandomString(255));
		referralLocation.setConcept(getConcept());
		referralLocation.setPatient(getPatient());
		referralLocation.setReferrerIn(getRandomBool());
		referralLocation.setReferrerOut(getRandomBool());
		referralLocation.setEnabled(getRandomBool());
		referralLocation.setSystem(false);
		referralLocation.setName(getRandomString(250));
		referralLocation.setAcronym(getRandomString(6));
		return  referralLocation;
	}


	public List<TestConfigDTO> requireTestConfigurations(LabManagementDao dao){

		List<TestConfigDTO> testConfigs=dao.findTestConfigurations(new TestConfigSearchFilter()).getData();
		if(!testConfigs.isEmpty()){
			return testConfigs;
		}

		ApprovalFlow approvalFlow=newApprovalFlow(dao);
		approvalFlow = dao.saveApprovalFlow(approvalFlow);

		List<Concept> concepts = Context.getConceptService().getAllConcepts();
		int max = 1;
		List<Concept> usedConcepts = new ArrayList<>();
		while(max < 11 && max < concepts.size() ){
			Concept concept1 = null;
			if(max == 1){
				concept1  = concepts.stream().filter(p-> p.getDatatype() != null && p.getDatatype().isCoded()  && p.getAnswers() != null && !p.getAnswers().isEmpty()).findFirst().orElse(null);
			}else if(max == 2){
				concept1  = concepts.stream().filter(p-> p.getSetMembers() != null && !p.getSetMembers().isEmpty()).findFirst().orElse(null);
			}else if(max == 3){
				concept1  = concepts.stream().filter(p-> p.getDatatype() != null && p.getDatatype().isText()/*&& p.getDatatype().isNumeric()*/).findFirst().orElse(null);
			}else if(max == 4){
				concept1  = concepts.stream().filter(p-> p.getDatatype() != null && p.getDatatype().isText()).findFirst().orElse(null);
			}

			Concept finalConcept = concept1;
			if(concept1 == null || usedConcepts.stream().anyMatch(p->p.getId().equals(finalConcept.getId()))){
				concept1 = concepts.stream().filter(p->
					(p.getDatatype() != null &&
						(p.getDatatype().isText() /*|| p.getDatatype().isNumeric()*/ || p.getDatatype().isCoded() || (p.getSetMembers() != null && !p.getSetMembers().isEmpty()) )) &&
						usedConcepts.stream().noneMatch(x->x.getId().equals(p.getId()))).findFirst().orElse(null);
			}

			if(concept1 == null){
				concept1 = concepts.stream().filter(p->usedConcepts.stream().noneMatch(x->x.getId().equals(p.getId()))).findFirst().orElse(null);
			}

			TestConfig testConfig=new TestConfig();
			testConfig.setCreator(getUser());
			testConfig.setDateCreated(getRandomDate());
			testConfig.setChangedBy(getUser());
			testConfig.setDateChanged(getRandomDate());
			testConfig.setVoided(false);
			testConfig.setVoidReason(getRandomString(255));
			testConfig.setTest(concept1);
			testConfig.setRequireApproval(true);
			testConfig.setApprovalFlow(approvalFlow);
			testConfig.setTestGroup(concepts.get(0));
			testConfig.setEnabled(true);
			dao.saveTestConfig(testConfig);

			usedConcepts.add(concept1);

			max++;
		}

		return dao.findTestConfigurations(new TestConfigSearchFilter()).getData();
	}

	public void setRequiredTestRequestEnvironment(){
		String encounterType = getEncounter().getEncounterType().getUuid();
		EncounterType  types = Context.getEncounterService().getAllEncounterTypes().stream().filter(p->"Laboratory".equalsIgnoreCase(p.getName())).findFirst().orElse(null);
		Context.getAdministrationService().setGlobalProperty(GlobalProperties.LABORATORY_ENCOUNTER_TYPE, types == null ? encounterType : types.getUuid());
	}

	public TestRequestDTO newLabRequestReferral(LabManagementDao dao){
		setRequiredTestRequestEnvironment();
		TestRequestDTO testRequestDTO=new TestRequestDTO();
		testRequestDTO.setAtLocationUuid(getLocation().getUuid());
		testRequestDTO.setRequestDate(new Date());
		testRequestDTO.setClinicalNote(getRandomString(500));
		testRequestDTO.setProviderUuid(getProvider().getUuid());
		testRequestDTO.setUrgency(getRandomUrgency());
		testRequestDTO.setReferredIn(true);

		ReferralLocation referralLocation = newReferralLocation(dao);
		referralLocation = dao.saveReferralLocation(referralLocation);

		testRequestDTO.setReferralFromFacilityUuid(referralLocation.getUuid());
		testRequestDTO.setReferralInExternalRef(getRandomString(50));


		List<TestConfigDTO> testConfigs = requireTestConfigurations(dao);
		testRequestDTO.setSamples(new ArrayList<>());

		List<Concept> concepts = Context.getConceptService().getAllConcepts();

		Random random = new Random();

		// Sample 1
		TestRequestSampleDTO testRequestSampleDTO = new TestRequestSampleDTO();
		testRequestSampleDTO.setSampleTypeUuid(concepts.get(random.nextInt(concepts.size() - 1)).getUuid());
		testRequestSampleDTO.setAccessionNumber(getRandomString(6));
		testRequestSampleDTO.setExternalRef(getRandomString(8));
		testRequestSampleDTO.setTests(new ArrayList<>());
		TestRequestItemDTO testRequestTestDTO=new TestRequestItemDTO();
		testRequestTestDTO.setLocationUuid(getLocation().getUuid());
		testRequestTestDTO.setToLocationUuid(getLocation().getUuid());
		testRequestTestDTO.setTestUuid(testConfigs.get(0).getTestUuid());
		testRequestSampleDTO.getTests().add(testRequestTestDTO);
		testRequestTestDTO=new TestRequestItemDTO();
		testRequestTestDTO.setLocationUuid(getLocation().getUuid());
		testRequestTestDTO.setToLocationUuid(getLocation().getUuid());
		testRequestTestDTO.setTestUuid(testConfigs.get(1).getTestUuid());
		testRequestSampleDTO.getTests().add(testRequestTestDTO);
		testRequestDTO.getSamples().add(testRequestSampleDTO);

		// Sample 2
		testRequestSampleDTO = new TestRequestSampleDTO();
		testRequestSampleDTO.setSampleTypeUuid(concepts.get(random.nextInt(concepts.size() - 1)).getUuid());
		testRequestSampleDTO.setAccessionNumber(getRandomString(6));
		testRequestSampleDTO.setExternalRef(getRandomString(8));
		testRequestSampleDTO.setTests(new ArrayList<>());
		testRequestTestDTO=new TestRequestItemDTO();
		testRequestTestDTO.setLocationUuid(getLocation().getUuid());
		testRequestTestDTO.setTestUuid(testConfigs.get(2).getTestUuid());
		testRequestSampleDTO.getTests().add(testRequestTestDTO);
		testRequestTestDTO=new TestRequestItemDTO();
		testRequestTestDTO.setLocationUuid(getLocation().getUuid());
		testRequestTestDTO.setTestUuid(testConfigs.get(3).getTestUuid());
		testRequestSampleDTO.getTests().add(testRequestTestDTO);
		testRequestDTO.getSamples().add(testRequestSampleDTO);

		// Sample 3
		testRequestSampleDTO = new TestRequestSampleDTO();
		testRequestSampleDTO.setSampleTypeUuid(concepts.get(random.nextInt(concepts.size() - 1)).getUuid());
		testRequestSampleDTO.setAccessionNumber(getRandomString(6));
		testRequestSampleDTO.setExternalRef(getRandomString(8));
		testRequestSampleDTO.setTests(new ArrayList<>());
		testRequestTestDTO=new TestRequestItemDTO();
		testRequestTestDTO.setLocationUuid(getLocation().getUuid());
		testRequestTestDTO.setTestUuid(testConfigs.get(4).getTestUuid());
		testRequestSampleDTO.getTests().add(testRequestTestDTO);
		testRequestTestDTO=new TestRequestItemDTO();
		testRequestTestDTO.setLocationUuid(getLocation().getUuid());
		testRequestTestDTO.setTestUuid(testConfigs.get(5).getTestUuid());
		testRequestSampleDTO.getTests().add(testRequestTestDTO);
		testRequestDTO.getSamples().add(testRequestSampleDTO);

		// Sample 3
		testRequestSampleDTO = new TestRequestSampleDTO();
		testRequestSampleDTO.setSampleTypeUuid(concepts.get(random.nextInt(concepts.size() - 1)).getUuid());
		testRequestSampleDTO.setAccessionNumber(getRandomString(6));
		testRequestSampleDTO.setExternalRef(getRandomString(8));
		testRequestSampleDTO.setTests(new ArrayList<>());
		testRequestTestDTO=new TestRequestItemDTO();
		testRequestTestDTO.setLocationUuid(getLocation().getUuid());
		testRequestTestDTO.setTestUuid(testConfigs.get(1).getTestUuid());
		testRequestSampleDTO.getTests().add(testRequestTestDTO);
		testRequestTestDTO=new TestRequestItemDTO();
		testRequestTestDTO.setLocationUuid(getLocation().getUuid());
		testRequestTestDTO.setTestUuid(testConfigs.get(6).getTestUuid());
		testRequestSampleDTO.getTests().add(testRequestTestDTO);
		testRequestDTO.getSamples().add(testRequestSampleDTO);

		// Sample 3
		testRequestSampleDTO = new TestRequestSampleDTO();
		testRequestSampleDTO.setSampleTypeUuid(concepts.get(random.nextInt(concepts.size() - 1)).getUuid());
		testRequestSampleDTO.setAccessionNumber(getRandomString(6));
		testRequestSampleDTO.setExternalRef(getRandomString(8));
		testRequestSampleDTO.setTests(new ArrayList<>());
		testRequestTestDTO=new TestRequestItemDTO();
		testRequestTestDTO.setLocationUuid(getLocation().getUuid());
		testRequestTestDTO.setTestUuid(testConfigs.get(8).getTestUuid());
		testRequestSampleDTO.getTests().add(testRequestTestDTO);
		testRequestTestDTO=new TestRequestItemDTO();
		testRequestTestDTO.setLocationUuid(getLocation().getUuid());
		testRequestTestDTO.setTestUuid(testConfigs.get(3).getTestUuid());
		testRequestSampleDTO.getTests().add(testRequestTestDTO);
		testRequestDTO.getSamples().add(testRequestSampleDTO);

		return testRequestDTO;
	}

	public TestRequestDTO newLabRequestPatient(LabManagementDao dao){
		setRequiredTestRequestEnvironment();
		TestRequestDTO testRequestDTO=new TestRequestDTO();
		testRequestDTO.setAtLocationUuid(getLocation().getUuid());
		testRequestDTO.setProviderUuid(getProvider().getUuid());
		testRequestDTO.setRequestDate(new Date());
		testRequestDTO.setClinicalNote(getRandomString(500));
		testRequestDTO.setPatientUuid(getPatient().getUuid());
		testRequestDTO.setReferredIn(false);
		testRequestDTO.setUrgency(getRandomUrgency());
		testRequestDTO.setCareSettingUuid(getCareSetting().getUuid());

		List<TestConfigDTO> testConfigs = requireTestConfigurations(dao);
		testRequestDTO.setTests(new ArrayList<>());

		TestRequestItemDTO testRequestTestDTO=new TestRequestItemDTO();
		testRequestTestDTO.setLocationUuid(getLocation().getUuid());
		testRequestTestDTO.setTestUuid(testConfigs.get(0).getTestUuid());
		testRequestDTO.getTests().add(testRequestTestDTO);
		testRequestTestDTO=new TestRequestItemDTO();
		testRequestTestDTO.setLocationUuid(getLocation().getUuid());
		testRequestTestDTO.setTestUuid(testConfigs.get(1).getTestUuid());
		testRequestDTO.getTests().add(testRequestTestDTO);

		testRequestTestDTO=new TestRequestItemDTO();
		testRequestTestDTO.setLocationUuid(getLocation().getUuid());
		testRequestTestDTO.setTestUuid(testConfigs.get(2).getTestUuid());
		testRequestDTO.getTests().add(testRequestTestDTO);
		testRequestTestDTO=new TestRequestItemDTO();
		testRequestTestDTO.setLocationUuid(getLocation().getUuid());
		testRequestTestDTO.setTestUuid(testConfigs.get(3).getTestUuid());
		testRequestDTO.getTests().add(testRequestTestDTO);

		return testRequestDTO;
	}

	public SampleDTO newLabSample(LabManagementDao dao, String testRequestUuid , List<String> testRequestItemUuids){
		SampleDTO sampleDTO = new SampleDTO();
		sampleDTO.setContainerCount(2);
		sampleDTO.setContainerTypeUuid(getConcept().getUuid());
		sampleDTO.setSampleTypeUuid(getConcept().getUuid());
		sampleDTO.setAccessionNumber(getRandomString(8));
		sampleDTO.setExternalRef(getRandomString(6));
		sampleDTO.setProvidedRef(getRandomString(6));
		sampleDTO.setAtLocationUuid(getLocation().getUuid());
		sampleDTO.setVolume(BigDecimal.valueOf(20));
		sampleDTO.setVolumeUnitUuid(getConcept().getUuid());
		sampleDTO.setReferredOut(false);
		sampleDTO.setTestRequestUuid(testRequestUuid);
		sampleDTO.setSampleTestItemUuids(new HashSet<>(testRequestItemUuids));
		return sampleDTO;
	}

	public SampleDTO newLabSampleWithReferral(LabManagementDao dao, String testRequestUuid , List<String> testRequestItemUuids){
		SampleDTO sampleDTO = new SampleDTO();
		sampleDTO.setContainerCount(2);
		sampleDTO.setContainerTypeUuid(getConcept().getUuid());
		sampleDTO.setSampleTypeUuid(getConcept().getUuid());
		sampleDTO.setAccessionNumber(getRandomString(8));
		sampleDTO.setExternalRef(getRandomString(6));
		sampleDTO.setProvidedRef(getRandomString(6));
		sampleDTO.setAtLocationUuid(getLocation().getUuid());
		sampleDTO.setVolume(BigDecimal.valueOf(20));
		sampleDTO.setVolumeUnitUuid(getConcept().getUuid());
		sampleDTO.setReferredOut(true);
		ReferralLocation referralLocation = newReferralLocation(dao);
		referralLocation = dao.saveReferralLocation(referralLocation);
		sampleDTO.setReferralToFacilityUuid(referralLocation.getUuid());
		sampleDTO.setTestRequestUuid(testRequestUuid);
		sampleDTO.setSampleTestItemUuids(new HashSet<>(testRequestItemUuids));
		return sampleDTO;
	}


	public TestResultImportConfig newTestResultImportConfig(LabManagementDao dao){
		TestResultImportConfig testResultImportConfig=new TestResultImportConfig();
		testResultImportConfig.setCreator(getUser());
		testResultImportConfig.setDateCreated(getRandomDate());
		testResultImportConfig.setTest(getConcept());
		testResultImportConfig.setHeaderHash(getRandomString(500));
		testResultImportConfig.setFieldMapping(getRandomString(2000));
		return  testResultImportConfig;
	}


	public Storage newStorage(LabManagementDao dao){
		Storage storage=new Storage();
		storage.setCreator(getUser());
		storage.setDateCreated(getRandomDate());
		storage.setChangedBy(getUser());
		storage.setDateChanged(getRandomDate());
		storage.setVoided(getRandomBool());
		storage.setDateVoided(getRandomDate());
		storage.setVoidedBy(getUser());
		storage.setVoidReason(getRandomString(255));
		storage.setAtLocation(getLocation());
		storage.setName(getRandomString(255));
		storage.setActive(getRandomBool());
		storage.setCapacity(getRandomInt());
		storage.setDescription(getRandomString(500));
		return  storage;
	}


	public StorageUnit newStorageUnit(LabManagementDao dao){
		StorageUnit storageUnit=new StorageUnit();
		storageUnit.setCreator(getUser());
		storageUnit.setDateCreated(getRandomDate());
		storageUnit.setChangedBy(getUser());
		storageUnit.setDateChanged(getRandomDate());
		storageUnit.setVoided(getRandomBool());
		storageUnit.setDateVoided(getRandomDate());
		storageUnit.setVoidedBy(getUser());
		storageUnit.setVoidReason(getRandomString(255));
		Storage storage=newStorage(dao);
		dao.saveStorage(storage);
		storageUnit.setStorage(storage);
		storageUnit.setUnitName(getRandomString(255));
		storageUnit.setDescription(getRandomString(500));
		storageUnit.setActive(getRandomBool());
		return  storageUnit;
	}

}
