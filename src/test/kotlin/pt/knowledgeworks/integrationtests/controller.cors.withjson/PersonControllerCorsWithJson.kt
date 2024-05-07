package pt.knowledgeworks.integrationtests.controller.cors.withjson

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest
import pt.knowledgeworks.integrationtests.TestConfigs
import pt.knowledgeworks.integrationtests.dto.PersonDTO
import pt.knowledgeworks.integrationtests.testcontainers.AbstractIntegrationTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerCorsWithJson() : AbstractIntegrationTest() {

	private lateinit var specification: RequestSpecification
	private lateinit var objectMapper: ObjectMapper
	private lateinit var person: PersonDTO

	@BeforeAll
	fun setupTests() {
		objectMapper = ObjectMapper()
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
		person = PersonDTO()
	}

	@Test
	@Order(1)
	fun testCreatePerson() {
		mockPerson()

		specification = RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN,
				TestConfigs.ORIGIN_MATHEUS,)
			.setBasePath("/api/persons/v1")
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(RequestLoggingFilter(LogDetail.ALL))
			.addFilter(ResponseLoggingFilter(LogDetail.ALL))
			.build()

		val content = RestAssured.given()
			.spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.body(person)
			.`when`()
			.post()
			.then()
			.statusCode(200)
			.extract()
			.body()
			.asString()

		val createdPerson = objectMapper.readValue(content, PersonDTO::class.java)

		person = createdPerson

		assertNotNull(createdPerson.id)
		assertNotNull(createdPerson.firstName)
		assertNotNull(createdPerson.lastName)
		assertNotNull(createdPerson.address)
		assertNotNull(createdPerson.gender)
		assertTrue(createdPerson.id > 0)
		assertEquals("Matheus", createdPerson.firstName)
		assertEquals("Sales", createdPerson.lastName)
		assertEquals("Porto PT", createdPerson.address)
		assertEquals("Male", createdPerson.gender)
	}

	@Test
	@Order(2)
	fun testCreatePersonWithWrongOrigin() {
		mockPerson()

		specification = RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN,
				TestConfigs.ORIGIN_SALESMTS,)
			.setBasePath("/api/persons/v1")
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(RequestLoggingFilter(LogDetail.ALL))
			.addFilter(ResponseLoggingFilter(LogDetail.ALL))
			.build()

		val content = RestAssured.given()
			.spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.body(person)
			.`when`()
			.post()
			.then()
			.statusCode(403)
			.extract()
			.body()
			.asString()

		assertEquals("Invalid CORS request", content)
	}

	@Test
	@Order(3)
	fun testFindByIdWithWrongOrigin() {
		mockPerson()

		specification = RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN,
						TestConfigs.ORIGIN_LOCALHOST,)
			.setBasePath("/api/persons/v1")
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(RequestLoggingFilter(LogDetail.ALL))
			.addFilter(ResponseLoggingFilter(LogDetail.ALL))
			.build()

		val content = RestAssured.given()
			.spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", person.id)
				.`when`()["{id}"]
			.then()
				.statusCode(200)
			.extract()
			.body()
				.asString()

		val createdPerson = objectMapper.readValue(content, PersonDTO::class.java)

		person = createdPerson

		assertNotNull(createdPerson.id)
		assertNotNull(createdPerson.firstName)
		assertNotNull(createdPerson.lastName)
		assertNotNull(createdPerson.address)
		assertNotNull(createdPerson.gender)
		assertTrue(createdPerson.id > 0)
		assertEquals("Matheus", createdPerson.firstName)
		assertEquals("Sales", createdPerson.lastName)
		assertEquals("Porto PT", createdPerson.address)
		assertEquals("Male", createdPerson.gender)
	}

	@Test
	@Order(4)
	fun testFindById() {
		mockPerson()

		specification = RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN,
						TestConfigs.ORIGIN_SALESMTS,)
			.setBasePath("/api/persons/v1")
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(RequestLoggingFilter(LogDetail.ALL))
			.addFilter(ResponseLoggingFilter(LogDetail.ALL))
			.build()

		val content = RestAssured.given()
			.spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", person.id)
				.`when`()["{id}"]
			.then()
				.statusCode(403)
			.extract()
			.body()
				.asString()

		assertEquals("Invalid CORS request", content)
	}

	private fun mockPerson() {
		person.firstName = "Matheus"
		person.lastName = "Sales"
		person.address = "Porto PT"
		person.gender = "Male"
	}
}