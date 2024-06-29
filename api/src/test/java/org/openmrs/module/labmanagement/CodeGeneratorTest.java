/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.labmanagement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.openmrs.*;
import org.openmrs.module.labmanagement.api.dto.TestConfigDTO;
import org.openmrs.module.labmanagement.api.model.Sample;
import org.openmrs.util.OpenmrsClassLoader;
import org.openmrs.util.OpenmrsUtil;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.persistence.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This is a unit test, which verifies logic in LabManagementService. It doesn't extend
 * BaseModuleContextSensitiveTest, thus it is run without the in-memory DB and Spring context.
 */
public class CodeGeneratorTest {

	String lineSeparator = System.getProperty("line.separator");

	@Before
	public void setupMocks() {
		MockitoAnnotations.initMocks(this);
	}

	private Set<Class> getModelClasses() {
		Set<Class> classes = findAllClassesUsingReflectionsLibrary("org.openmrs.module.labmanagement.api.model");
		return classes;
	}

	private Set<Class> getDtoClasses() {
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
				.setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
				.filterInputsBy(new FilterBuilder().include(
						FilterBuilder.prefix("org.openmrs.module.labmanagement.api.dto"))));

		return new HashSet<>(reflections.getSubTypesOf(Object.class));
	}

	private Path getTargetDir() {
		String relPath = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
		File targetDir = new File(relPath + "..");
		if (!targetDir.exists()) {
			targetDir.mkdir();
		}
		return targetDir.toPath();
	}

	@Test
	public void update_permission_constants() {
		if (!"true".equalsIgnoreCase(System.getenv("update_permission_constants"))) {
			return;
		}

		Paths.get(getTargetDir().toString(), "../../omod/src/main/resources/config.xml");
	}

	@Test
	public void generate_dao_entries() throws IOException {
		//		if (!"true".equalsIgnoreCase(System.getenv("labmgmt_generate_dao"))) {
		//			return;
		//		}

		Sample labOperation = new Sample();

		Set<Class> classes = getModelClasses();
		if (classes.isEmpty())
			return;

		String lineSeparator = System.getProperty("line.separator");
		StringBuilder s = new StringBuilder();

		for (Class modelClass : classes) {
			s.append(String.format("public %s get%1$sById(Integer id) {", modelClass.getSimpleName()));
			s.append(lineSeparator);
			s.append(String
					.format(
							"return (%1$s) getSession().createCriteria(%1$s.class).add(Restrictions.eq(\"id\", id)).uniqueResult();",
							modelClass.getSimpleName()));
			s.append(lineSeparator);
			s.append("}");
			s.append(lineSeparator);
			s.append(lineSeparator);
			s.append(String.format("public %s get%1$sByUuid(String uuid) {", modelClass.getSimpleName()));
			s.append(lineSeparator);
			s.append(String
			        .format(
			            "return (%1$s) getSession().createCriteria(%1$s.class).add(Restrictions.eq(\"uuid\", uuid)).uniqueResult();",
			            modelClass.getSimpleName()));
			s.append(lineSeparator);
			s.append("}");
			s.append(lineSeparator);
			s.append(lineSeparator);
			s.append(String.format("public %1$s save%1$s(%1$s %2$s%3$s) {", modelClass.getSimpleName(), modelClass
			        .getSimpleName().toLowerCase().charAt(0), modelClass.getSimpleName().substring(1)));
			s.append(lineSeparator);
			s.append(String.format("getSession().saveOrUpdate(%1$s%2$s);", modelClass.getSimpleName().toLowerCase()
			        .charAt(0), modelClass.getSimpleName().substring(1)));
			s.append(lineSeparator);
			s.append(String.format("return %1$s%2$s;", modelClass.getSimpleName().toLowerCase().charAt(0), modelClass
			        .getSimpleName().substring(1)));
			s.append(lineSeparator);
			s.append("}");
			s.append(lineSeparator);
			s.append(lineSeparator);
		}
		Path targetFile = Paths.get(getTargetDir().toString(), "generated_dao_entries.txt");
		//throw new RuntimeException(targetFile.toAbsolutePath().toString());
		Files.write(targetFile, s.toString().getBytes());
	}

	@Test
	public void generate_util_create_entries() throws IOException {
		//		if (!"true".equalsIgnoreCase(System.getenv("generate_util_create_entries"))) {
		//			return;
		//		}

		Set<Class> classes = getModelClasses();
		if (classes.isEmpty())
			return;

		StringBuilder s = new StringBuilder();

		for (Class modelClass : classes) {
			s.append(String.format("public %1$s new%1$s(LabManagementDao dao){", modelClass.getSimpleName()));
			s.append(lineSeparator);
			String variableName = modelClass.getSimpleName().toLowerCase().charAt(0)
			        + modelClass.getSimpleName().substring(1);
			s.append(String.format("%1$s %2$s=new %1$s();", modelClass.getSimpleName(), variableName));
			s.append(lineSeparator);
			setFields(modelClass, s, variableName);
			s.append(String.format("return  %1$s;", variableName));
			s.append(lineSeparator);
			s.append("}");
			s.append(lineSeparator);
			s.append(lineSeparator);
		}
		Path targetFile = Paths.get(getTargetDir().toString(), "generate_util_create_entries.txt");
		//throw new RuntimeException(targetFile.toAbsolutePath().toString());
		Files.write(targetFile, s.toString().getBytes());
	}

	@Test
	public void generate_dao_tests() throws IOException {
		//		if (!"true".equalsIgnoreCase(System.getenv("generate_dao_tests"))) {
		//			return;
		//		}

		Set<Class> classes = getModelClasses();
		if (classes.isEmpty())
			return;

		StringBuilder s = new StringBuilder();

		for (Class modelClass : classes) {
			s.append("@Test");
			s.append(lineSeparator);
			s.append(String.format("public void save%1$s_shouldSaveAllProperties(){", modelClass.getSimpleName()));
			s.append(lineSeparator);
			s.append("//Given");
			s.append(lineSeparator);
			String variableName = modelClass.getSimpleName().toLowerCase().charAt(0)
			        + modelClass.getSimpleName().substring(1);
			s.append(String.format("%1$s %2$s=eu().new%1$s(dao());", modelClass.getSimpleName(), variableName));
			s.append(lineSeparator);
			s.append(lineSeparator);
			s.append("//When");
			s.append(lineSeparator);
			s.append(String.format("dao().save%1$s(%2$s);", modelClass.getSimpleName(), variableName));
			s.append(lineSeparator);
			s.append(lineSeparator);
			s.append(String.format("//Let's clean up the cache to be sure get%sByUuid fetches from DB and not from cache",
			    modelClass.getSimpleName()));
			s.append(lineSeparator);
			s.append("Context.flushSession();");
			s.append(lineSeparator);
			s.append("Context.flushSession();");
			s.append(lineSeparator);
			s.append(lineSeparator);
			s.append("//Then");
			s.append(lineSeparator);
			s.append(String.format("%1$s saved%1$s = dao().get%1$sByUuid(%2$s.getUuid());", modelClass.getSimpleName(),
			    variableName));
			s.append(lineSeparator);
			assertFields(modelClass, s, variableName, modelClass.getSimpleName());
			s.append(lineSeparator);
			s.append(String.format("saved%1$s = dao().get%1$sById(%2$s.getId());", modelClass.getSimpleName(),
					variableName));
			s.append(lineSeparator);
			assertFields(modelClass, s, variableName, modelClass.getSimpleName());
			s.append("}");
			s.append(lineSeparator);
			s.append(lineSeparator);
		}
		Path targetFile = Paths.get(getTargetDir().toString(), "generate_dao_tests.txt");
		//throw new RuntimeException(targetFile.toAbsolutePath().toString());
		Files.write(targetFile, s.toString().getBytes());
	}

	public static String capatalizeFieldName(String fieldName) {
		final String result;
		if (fieldName != null && !fieldName.isEmpty() && Character.isLowerCase(fieldName.charAt(0))
		        && (fieldName.length() == 1 || Character.isLowerCase(fieldName.charAt(1)))) {
			result = StringUtils.capitalize(fieldName);
		} else {
			result = fieldName;
		}
		return result;
	}

	private void setFields(Class modelClass, StringBuilder s, String variableName) {
		List<Field> fields = getAllFields(modelClass, false);
		for (Field field : fields) {
			if (field.getName().equals("id")) {
				/*if (modelClass == LabOperationType.class) {
					s.append(String.format("%1$s.set%2$s(1);", variableName, capatalizeFieldName(field.getName())));
				}*/
				continue;
			}

			if (field.getName().equals("uuid") || Collection.class.isAssignableFrom(field.getType())) {
				continue;
			}

			boolean fieldSet = false;
			Class target = field.getType();
			if (target instanceof Class && ((Class<?>) target).isEnum()) {
				s.append(String.format("setProperty(%1$s,\"%2$s\",getRandomEnum(%3$s.class));", variableName,
				    field.getName(), target.getSimpleName()));
				fieldSet = true;
			}

			if (target == String.class) {
				Column annotation = field.getAnnotation(Column.class);
				s.append(String.format("%1$s.set%2$s(getRandomString(%3$s));", variableName,
				    capatalizeFieldName(field.getName()), annotation.length()));
				fieldSet = true;
			}
			if (target == Character.class || target == char.class) {
				s.append(String.format("%1$s.set%2$s(getRandomString(1));", variableName,
				    capatalizeFieldName(field.getName())));
				fieldSet = true;
			}
			if (target == Byte.class || target == byte.class) {
				s.append(String.format("%1$s.set%2$s(getRandomByte());", variableName, capatalizeFieldName(field.getName())));
				fieldSet = true;
			}
			if (target == Short.class || target == short.class) {
				s.append(String.format("%1$s.set%2$s(getRandomShort());", variableName, capatalizeFieldName(field.getName())));
				fieldSet = true;
			}
			if (target == Integer.class || target == int.class) {
				if (field.getName().equals("activityProcessJobId") || field.getName().equals("processJobId")
				        || field.getName().equals("parentWorkflowProcessJobId")
				        || field.getName().equals("parentWorkflowId") || field.getName().equals("parentWorkflowProcessId"))
					continue;
				if (field.getName().equals("parentLocationId") || field.getName().equals("childLocationId")) {
					s.append(String.format("%1$s.set%2$s(1);", variableName, capatalizeFieldName(field.getName())));
				} else
					s.append(String.format("%1$s.set%2$s(getRandomInt());", variableName,
					    capatalizeFieldName(field.getName())));
				fieldSet = true;
			}
			if (target == Long.class || target == long.class) {
				s.append(String.format("%1$s.set%2$s(getRandomLong());", variableName, capatalizeFieldName(field.getName())));
				fieldSet = true;
			}
			if (target == Float.class || target == float.class) {
				s.append(String.format("%1$s.set%2$s(getRandomFloat());", variableName, capatalizeFieldName(field.getName())));
				fieldSet = true;
			}
			if (target == Double.class || target == double.class) {
				s.append(String.format("%1$s.set%2$s(getRandomDouble());", variableName,
				    capatalizeFieldName(field.getName())));
				fieldSet = true;
			}
			if (target == Boolean.class || target == boolean.class) {
				s.append(String.format("%1$s.set%2$s(getRandomBool());", variableName, capatalizeFieldName(field.getName())));
				fieldSet = true;
			}
			if (target == Date.class || target == Date.class) {
				s.append(String.format("%1$s.set%2$s(getRandomDate());", variableName, capatalizeFieldName(field.getName())));
				fieldSet = true;
			}

			if (target == BigDecimal.class || target == BigDecimal.class) {
				s.append(String.format("%1$s.set%2$s(getRandomBigDecimal());", variableName,
				    capatalizeFieldName(field.getName())));
				fieldSet = true;
			}

			if (field.isAnnotationPresent(JoinColumn.class) || field.isAnnotationPresent(OneToOne.class)
			        || field.isAnnotationPresent(ManyToOne.class)) {
				if (field.getType() == Drug.class) {
					s.append(String.format("%1$s.set%2$s(getDrug());", variableName, capatalizeFieldName(field.getName())));
					fieldSet = true;
				} else if (field.getType() == Concept.class) {
					s.append(String.format("%1$s.set%2$s(getConcept());", variableName, capatalizeFieldName(field.getName())));
					fieldSet = true;
				} else if (field.getType() == User.class) {
					s.append(String.format("%1$s.set%2$s(getUser());", variableName, capatalizeFieldName(field.getName())));
					fieldSet = true;
				}  else if (field.getType() == Obs.class) {
					s.append(String.format("%1$s.set%2$s(getObs());", variableName,
							capatalizeFieldName(field.getName())));
					fieldSet = true;
				}else if (field.getType() == Encounter.class) {
					s.append(String.format("%1$s.set%2$s(getEncounter());", variableName,
							capatalizeFieldName(field.getName())));
					fieldSet = true;
				} else if (field.getType() == Order.class) {
					s.append(String.format("%1$s.set%2$s(getOrder());", variableName,
							capatalizeFieldName(field.getName())));
					fieldSet = true;
				} else if (field.getType() == Location.class) {
					s.append(String.format("%1$s.set%2$s(getLocation());", variableName,
					    capatalizeFieldName(field.getName())));
					fieldSet = true;
				} else if (field.getType() == Role.class) {
					s.append(String.format("%1$s.set%2$s(getRole());", variableName, capatalizeFieldName(field.getName())));
					fieldSet = true;
				} else if (field.getType() == Patient.class) {
					s.append(String.format("%1$s.set%2$s(getPatient());", variableName, capatalizeFieldName(field.getName())));
					fieldSet = true;
				} else {
					String fieldName = field.getName();
					s.append(String.format("%1$s %2$s=new%1$s(dao);", field.getType().getSimpleName(), fieldName));
					s.append(lineSeparator);
					s.append(String.format("dao.save%1$s(%2$s);", field.getType().getSimpleName(), fieldName));
					s.append(lineSeparator);
					s.append(String.format("%1$s.set%2$s(%3$s);", variableName, capatalizeFieldName(field.getName()),
					/*field.getType().getSimpleName()*/fieldName));
					fieldSet = true;
				}
			}

			if (fieldSet)
				s.append(lineSeparator);

		}
	}

	private void assertFields(Class modelClass, StringBuilder s, String variableName, String simpleName) {
		List<Field> fields = getAllFields(modelClass, false);
		for (Field field : fields) {
			if (field.getName().equals("id") || Collection.class.isAssignableFrom(field.getType())) {
				continue;
			}

			boolean fieldSet = false;
			if (field.getName().equals("uuid"))
				fieldSet = true;

			Class target = field.getType();
			if (target instanceof Class && ((Class<?>) target).isEnum()) {
				fieldSet = true;
			}

			if (target == String.class) {
				fieldSet = true;
			}
			if (target == Character.class || target == char.class) {
				fieldSet = true;
			}
			if (target == Byte.class || target == byte.class) {
				fieldSet = true;
			}
			if (target == Short.class || target == short.class) {
				fieldSet = true;
			}
			if (target == Integer.class || target == int.class) {
				if (field.getName().equals("activityProcessJobId") || field.getName().equals("processJobId")
				        || field.getName().equals("parentWorkflowProcessJobId")
				        || field.getName().equals("parentWorkflowId") || field.getName().equals("parentWorkflowProcessId"))
					continue;
				fieldSet = true;
			}
			if (target == Long.class || target == long.class) {
				fieldSet = true;
			}
			if (target == Float.class || target == float.class) {
				fieldSet = true;
			}
			if (target == Double.class || target == double.class) {
				fieldSet = true;
			}
			if (target == Boolean.class || target == boolean.class) {
				fieldSet = true;
			}
			if (target == Date.class || target == Date.class) {
				fieldSet = true;
			}

			if (target == BigDecimal.class || target == BigDecimal.class) {
				fieldSet = true;
			}

			if (field.isAnnotationPresent(JoinColumn.class) || field.isAnnotationPresent(OneToOne.class)
			        || field.isAnnotationPresent(ManyToOne.class)) {
				if (field.getType() == Drug.class) {
					fieldSet = true;
				} else if (field.getType() == Concept.class) {
					fieldSet = true;
				} else if (field.getType() == User.class) {
					fieldSet = true;
				} else if (field.getType() == Location.class) {
					fieldSet = true;
				} else if (field.getType() == Role.class) {
					fieldSet = true;
				} else if (field.getType() == Patient.class) {
					fieldSet = true;
				} else {
					fieldSet = true;
				}
			}

			if (fieldSet) {
				s.append(String.format("assertThat(saved%1$s, hasProperty(\"%2$s\", is(%3$s.get%4$s())));", simpleName,
				    field.getName(), variableName, capatalizeFieldName(field.getName())));
				s.append(lineSeparator);
			}
		}
	}

	public void saveItem_shouldSetOwnerIfNotSet() {
		//		if (!"true".equalsIgnoreCase(System.getenv("labmgmt_generate_entity"))) {
		//			return;
		//		}

		Set<Class> classes = getModelClasses();
		if (classes.isEmpty())
			return;

		String lineSeparator = System.getProperty("line.separator");
		StringBuilder s = new StringBuilder();

		for (Class modelClass : classes) {
			s.append("@Test");
			s.append(lineSeparator);
			s.append(String.format("\tpublic void save%1$s_shouldSaveAllColumns() {", modelClass.getSimpleName()));
			s.append(lineSeparator);
			s.append(String.format("%1$s entity = new %1$s();", modelClass.getSimpleName()));
			List<Field> fields = getAllFields(modelClass, false);

			s.append(String.format(""));

			s.append(String.format(""));
			s.append(String.format(""));
			s.append(String.format(""));
			s.append(String.format(""));

		}

	}

	List<Field> getAllFields(Class clazz,boolean allFields) {
        if (clazz == null) {
            return Collections.emptyList();
        }

        List<Field> result = new ArrayList<>(getAllFields(clazz.getSuperclass(), allFields));
        List<Field> filteredFields = Arrays.stream(clazz.getDeclaredFields())
                .filter(f ->allFields || f.isAnnotationPresent(JoinColumn.class) || f.isAnnotationPresent(Column.class) || f.isAnnotationPresent(OneToOne.class) || f.isAnnotationPresent(ManyToMany.class) || f.isAnnotationPresent(OneToMany.class))
                .collect(Collectors.toList());
        result.addAll(filteredFields);
        return result;
    }

	public Set<Class> findAllClassesUsingReflectionsLibrary(String packageName) {
		Reflections reflections = new Reflections(packageName);
		return reflections.getTypesAnnotatedWith(Entity.class).stream().collect(Collectors.toSet());
	}

	@Test
	public void generate_resources() throws IOException {
		//		if (!"true".equalsIgnoreCase(System.getenv("generate_resources"))) {
		//			return;
		//		}

		Set<Class> classes = getModelClasses();
		if (classes.isEmpty())
			return;

		String targetDir = getTargetDir().toString();
		StringBuilder stringBuilder = new StringBuilder();
		Path resourceTemplateFile = Paths.get(targetDir,
		    "..\\src\\test\\resources\\org\\openmrs\\module\\labmanagement\\api\\ResourceTemplate.txt");
		String resourceString = new String(Files.readAllBytes(resourceTemplateFile));
		Path outputDir = Paths.get(targetDir, "rescs");
		if (!Files.exists(outputDir)) {
			Files.createDirectory(outputDir);
		}
		for (Class modelClass : classes) {

			StringBuilder s = new StringBuilder(resourceString);
			String output = setRepresentation(modelClass, s, false);
			Path targetFile = Paths.get(outputDir.toString(), modelClass.getSimpleName() + "Resource.java");
			Files.write(targetFile, output.getBytes());
		}
	}

	@Test
	public void generate_resources_base_on_dto() throws IOException {
		//		if (!"true".equalsIgnoreCase(System.getenv("generate_resources"))) {
		//			return;
		//		}

		Set<Class> classes = getDtoClasses();
		if (classes.isEmpty())
			return;

		String targetDir = getTargetDir().toString();
		StringBuilder stringBuilder = new StringBuilder();
		Path resourceTemplateFile = Paths.get(targetDir,
				"..\\src\\test\\resources\\org\\openmrs\\module\\labmanagement\\api\\ResourceTemplate.txt");
		String resourceString = new String(Files.readAllBytes(resourceTemplateFile));
		Path outputDir = Paths.get(targetDir, "rescsdto");
		if (!Files.exists(outputDir)) {
			Files.createDirectory(outputDir);
		}
		for (Class modelClass : classes) {

			StringBuilder s = new StringBuilder(resourceString);
			String output = setRepresentation(modelClass, s, true);
			Path targetFile = Paths.get(outputDir.toString(), modelClass.getSimpleName().replaceAll("DTO","") + "Resource.java");
			Files.write(targetFile, output.getBytes());
		}
	}

	@Test
	public void generate_dto_resources() throws IOException {
//		if (!"true".equalsIgnoreCase(System.getenv("generate_dto_resources"))) {
//			return;
//		}

		Set<Class> classes = new HashSet<>();
		classes.add(TestConfigDTO.class);
		if (classes.isEmpty())
			return;

		String targetDir = getTargetDir().toString();
		StringBuilder stringBuilder = new StringBuilder();
		Path resourceTemplateFile = Paths.get(targetDir,
				"..\\src\\test\\resources\\org\\openmrs\\module\\labmanagement\\api\\ResourceTemplate.txt");
		String resourceString = new String(Files.readAllBytes(resourceTemplateFile));
		Path outputDir = Paths.get(targetDir, "rescs");
		if (!Files.exists(outputDir)) {
			Files.createDirectory(outputDir);
		}
		for (Class modelClass : classes) {

			StringBuilder s = new StringBuilder(resourceString);
			String output = setRepresentation(modelClass, s, true);
			Path targetFile = Paths.get(outputDir.toString(), modelClass.getSimpleName() + "Resource.java");
			Files.write(targetFile, output.getBytes());
		}
	}

	private void addToBuilders(String string, StringBuilder... builders) {
		for (StringBuilder builder : builders) {
			builder.append(string);
			builder.append(lineSeparator);
		}
	}

	private String setRepresentation(Class modelClass, StringBuilder s, boolean allFieds) {
		List<Field> fields = getAllFields(modelClass, allFieds);
		StringBuilder defaultFullRepresentation = new StringBuilder();
		StringBuilder fullRepresentation = new StringBuilder();
		StringBuilder defaultRepresentation = new StringBuilder();
		StringBuilder refRepresentation = new StringBuilder();

		StringBuilder defaultFullModel = new StringBuilder();
		StringBuilder fullModel = new StringBuilder();
		StringBuilder defaultModel = new StringBuilder();
		StringBuilder refModel = new StringBuilder();

		for (Field field : fields) {
			if (field.getName().equals("id") || field.getName().equals("voided") || field.getName().equals("dateVoided")
			        || field.getName().equals("voidReason") || field.getName().equals("voidedBy")) {
				continue;
			}

			if (field.getName().equals("uuid")) {
				addToBuilders("description.addProperty(\"uuid\");", defaultFullRepresentation, refRepresentation);
				addToBuilders(" modelImpl.property(\"uuid\", new StringProperty());", defaultFullModel, refModel);
				continue;
			}

			Class target = field.getType();
			if (target instanceof Class && ((Class<?>) target).isEnum() || target == String.class
			        || target == Character.class || target == char.class) {
				addToBuilders(String.format("description.addProperty(\"%1$s\");", field.getName()),
				    defaultFullRepresentation);
				addToBuilders(String.format("modelImpl.property(\"%1$s\", new StringProperty());", field.getName()), defaultFullModel);
				continue;
			}
			if (target == Byte.class || target == byte.class) {
				addToBuilders(String.format("description.addProperty(\"%1$s\");", field.getName()),
				    defaultFullRepresentation);
				addToBuilders(String.format("modelImpl.property(\"%1$s\", new BinaryProperty(1));", field.getName()), defaultFullModel);
				continue;
			}
			if (target == Short.class || target == short.class || target == Integer.class || target == int.class) {
				addToBuilders(String.format("description.addProperty(\"%1$s\");", field.getName()),
				    defaultFullRepresentation);
				addToBuilders(String.format("modelImpl.property(\"%1$s\", new IntegerProperty());", field.getName()), defaultFullModel);
				continue;
			}

			if (target == Long.class || target == long.class) {
				addToBuilders(String.format("description.addProperty(\"%1$s\");", field.getName()),
				    defaultFullRepresentation);
				addToBuilders(String.format("modelImpl.property(\"%1$s\", new LongProperty());", field.getName()), defaultFullModel);
				continue;
			}
			if (target == Float.class || target == float.class) {
				addToBuilders(String.format("description.addProperty(\"%1$s\");", field.getName()),
				    defaultFullRepresentation);
				addToBuilders(String.format("modelImpl.property(\"%1$s\", new FloatProperty());", field.getName()), defaultFullModel);
				continue;
			}
			if (target == Double.class || target == double.class) {
				addToBuilders(String.format("description.addProperty(\"%1$s\");", field.getName()),
				    defaultFullRepresentation);
				addToBuilders(String.format("modelImpl.property(\"%1$s\", new DoubleProperty());", field.getName()), defaultFullModel);
				continue;
			}
			if (target == Boolean.class || target == boolean.class) {
				addToBuilders(String.format("description.addProperty(\"%1$s\");", field.getName()),
				    defaultFullRepresentation);
				addToBuilders(String.format("modelImpl.property(\"%1$s\", new BooleanProperty());", field.getName()), defaultFullModel);
				continue;
			}
			if (target == Date.class || target == Date.class) {
				addToBuilders(String.format("description.addProperty(\"%1$s\");", field.getName()),
				    defaultFullRepresentation);
				addToBuilders(String.format("modelImpl.property(\"%1$s\", new DateTimeProperty());", field.getName()),
				    defaultFullModel);
				continue;
			}

			if (target == BigDecimal.class || target == BigDecimal.class) {
				addToBuilders(String.format("description.addProperty(\"%1$s\");", field.getName()),
				    defaultFullRepresentation);
				addToBuilders(String.format("modelImpl.property(\"%1$s\", new DecimalProperty());", field.getName()), defaultFullModel);
				continue;
			}

			if (field.isAnnotationPresent(JoinColumn.class) || field.isAnnotationPresent(OneToOne.class)
			        || field.isAnnotationPresent(ManyToOne.class)) {
				if (field.getType() == Drug.class || field.getType() == Concept.class || field.getType() == User.class
				        || field.getType() == Location.class || field.getType() == Role.class
				        || field.getType() == Patient.class) {
					addToBuilders(String.format("description.addProperty(\"%1$s\", Representation.REF);", field.getName()),
					    defaultRepresentation);
					addToBuilders(String.format("description.addProperty(\"%1$s\", Representation.REF);", field.getName()),
					    fullRepresentation);
					addToBuilders(String.format("modelImpl.property(\"%1$s\", new RefProperty(\"#/definitions/%2$sGetRef\"));",
					    field.getName(), field.getType().getSimpleName()), defaultModel);
					addToBuilders(String.format("modelImpl.property(\"%1$s\", new RefProperty(\"#/definitions/%2$sGetRef\"));",
					    field.getName(), field.getType().getSimpleName()), fullModel);
					continue;
				} else {
					addToBuilders(String.format("description.addProperty(\"%1$s\", Representation.REF);", field.getName()),
					    defaultRepresentation);
					addToBuilders(String.format("description.addProperty(\"%1$s\", Representation.REF);", field.getName()),
					    fullRepresentation);
					addToBuilders(String.format("modelImpl.property(\"%1$s\", new RefProperty(\"#/definitions/%2$sGetRef\"));",
					    field.getName(), field.getType().getSimpleName()), defaultModel);
					addToBuilders(String.format("modelImpl.property(\"%1$s\", new RefProperty(\"#/definitions/%2$sGetRef\"));",
					    field.getName(), field.getType().getSimpleName()), fullModel);
					continue;
				}
			}
		}

		return s.toString().replaceAll("%resourcenamelower%", modelClass.getSimpleName().toLowerCase())
		        .replaceAll("%resourceclassname%", modelClass.getSimpleName())
		        .replaceAll("%defaultFullRepresentation%", defaultFullRepresentation.toString())
		        .replaceAll("%defaultRepresentation%", defaultRepresentation.toString())
		        .replaceAll("%fullRepresentation%", fullRepresentation.toString())
		        .replaceAll("%refRepresentation%", refRepresentation.toString())
		        .replaceAll("%defaultFullModel%", defaultFullModel.toString())
		        .replaceAll("%defaultModel%", defaultModel.toString()).replaceAll("%fullModel%", fullModel.toString())
		        .replaceAll("%refModel%", refModel.toString());

	}

	@Test
	public void SyncPermissions() {
		//		if (!"true".equalsIgnoreCase(System.getenv("labmgmt_generate_entity"))) {
		//			return;
		//		}
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			String targetDir = getTargetDir().toString();
			Path privilegesJavaTemplateFile = Paths.get(targetDir,
			    "..\\src\\test\\resources\\org\\openmrs\\module\\labmanagement\\api\\PriviledgesTemplateJava.txt");
			Path privilegesTypescriptTemplateFile = Paths.get(targetDir,
			    "..\\src\\test\\resources\\org\\openmrs\\module\\labmanagement\\api\\PriviledgesTemplateTs.txt");
			//Path configFile = Paths.get(targetDir, "..\\..\\omod\\src\\main\\resources\\config.xml");
			Path configFile = Paths.get(targetDir, "..\\src\\main\\resources\\labmgmt\\metadata\\Role_Privilege.xml");
			Document doc = db.parse(new File(configFile.toString()));
			doc.getDocumentElement().normalize();

			NodeList list = doc.getElementsByTagName("privilege");
			if (list.getLength() == 0)
				throw new Exception("No priviledges found in " + configFile.toString());

			StringBuilder javaStrings = new StringBuilder();
			StringBuilder tsStrings = new StringBuilder();
			StringBuilder arrayList = new StringBuilder();
			for (int nodeIndex = 0; nodeIndex < list.getLength(); nodeIndex++) {
				Node node = list.item(nodeIndex);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node; // get text
					/*String name = element.getElementsByTagName("name").item(0).getTextContent();
					String normalizedName = normalizePermissionName(name);
					String description = element.getElementsByTagName("description").item(0).getTextContent();*/

					String name = element.getAttribute("privilege");
					String normalizedName = normalizePermissionName(name);
					String description = element.getAttribute("description");

					if (arrayList.length() > 0) {
						arrayList.append(",");
						arrayList.append(lineSeparator);
					}
					arrayList.append("\t");
					arrayList.append(normalizedName);

					javaStrings.append("\t/**");
					javaStrings.append(lineSeparator);
					javaStrings.append(String.format("\t * %1$s: %2$s", name, description));
					javaStrings.append(lineSeparator);
					javaStrings.append("\t */");
					javaStrings.append(lineSeparator);
					javaStrings.append(String.format("\tpublic static final String %1$s = \"%2$s\";", normalizedName, name));
					javaStrings.append(lineSeparator);
					javaStrings.append(lineSeparator);

					tsStrings.append(String.format("/** @type {string}: %1$s, %2$s*/", name, description));
					tsStrings.append(lineSeparator);
					tsStrings.append(String.format("export const %1$s = \"%2$s\";", normalizedName, name));
					tsStrings.append(lineSeparator);
					tsStrings.append(lineSeparator);
				}
			}

			Path targetFile = Paths.get(targetDir.toString(),
			    "..\\src\\main\\java\\org\\openmrs\\module\\labmanagement\\api\\Privileges.java");
			String resourceString = new String(Files.readAllBytes(privilegesJavaTemplateFile));
			Files.write(targetFile,
			    resourceString.replaceAll("%replace%", javaStrings.toString()).replaceAll("%ALL%", arrayList.toString())
			            .getBytes());

			targetFile = Paths.get(targetDir.toString(), "priviledges.ts");
			resourceString = new String(Files.readAllBytes(privilegesTypescriptTemplateFile));
			Files.write(targetFile, resourceString.replaceAll("%replace%", tsStrings.toString()).getBytes());

		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	protected String normalizePermissionName(String permissionName) {
		return permissionName.replaceAll("[^a-zA-Z0-9\\s]", " ").replaceAll("\\s+", " ").replaceAll(" ", "_").toUpperCase();
	}

	@Test
	public void TestResourceLoader(){
		InputStream stream = getClass().getResourceAsStream("/messages.properties");
		Properties props = new Properties();
		if (stream != null) {

			OpenmrsUtil.loadProperties(props, stream);
		}
		String result = props.getProperty("labmanagement.testresulteditnotallowed");
		Assert.assertNotNull(result);
	}

}
