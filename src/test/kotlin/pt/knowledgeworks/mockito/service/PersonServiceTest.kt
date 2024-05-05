package pt.knowledgeworks.mockito.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock

import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import pt.knowledgeworks.exceptions.RequiredObjectIsNullException
import pt.knowledgeworks.mapper.mocks.MockPerson
import pt.knowledgeworks.repository.PersonRepository
import pt.knowledgeworks.service.PersonService
import java.util.*

@ExtendWith(MockitoExtension::class)
class PersonServiceTest {

    private lateinit var inputObject: MockPerson

    @InjectMocks
    private lateinit var service: PersonService

    @Mock
    private lateinit var repository: PersonRepository

    @BeforeEach
    fun setUpMock() {
        inputObject = MockPerson()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findAll() {
        val list = inputObject.mockEntityList()

        `when`(repository.findAll()).thenReturn(list)

        val persons = service.findAll()

        assertNotNull(persons)
        assertEquals(14, persons.size)

        val personOne = persons[1]

        assertNotNull(personOne)
        assertNotNull(personOne.key)
        assertNotNull(personOne.links)

        assertTrue(personOne.links.toString().contains("</api/persons/v1/1>;rel=\"self\""))
        assertEquals("Address Test1", personOne.address)
        assertEquals("First Name Test1", personOne.firstName)
        assertEquals("Last Name Test1", personOne.lastName)
        assertEquals("Female", personOne.gender)

        val personFour = persons[4]

        assertNotNull(personFour)
        assertNotNull(personFour.key)
        assertNotNull(personFour.links)

        assertTrue(personFour.links.toString().contains("</api/persons/v1/4>;rel=\"self\""))
        assertEquals("Address Test4", personFour.address)
        assertEquals("First Name Test4", personFour.firstName)
        assertEquals("Last Name Test4", personFour.lastName)
        assertEquals("Male", personFour.gender)

        val personEight = persons[8]

        assertNotNull(personEight)
        assertNotNull(personEight.key)
        assertNotNull(personEight.links)

        assertTrue(personEight.links.toString().contains("</api/persons/v1/8>;rel=\"self\""))
        assertEquals("Address Test8", personEight.address)
        assertEquals("First Name Test8", personEight.firstName)
        assertEquals("Last Name Test8", personEight.lastName)
        assertEquals("Male", personEight.gender)
    }

    @Test
    fun findById() {
        val person = inputObject.mockEntity(1)
        person.id = 1
        `when`(repository.findById(1)).thenReturn(Optional.of(person))

        val result = service.findById(1)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)

        assertTrue(result.links.toString().contains("</api/persons/v1/1>;rel=\"self\""))
        assertEquals("Address Test1", result.address)
        assertEquals("First Name Test1", result.firstName)
        assertEquals("Last Name Test1", result.lastName)
        assertEquals("Female", result.gender)

    }

    @Test
    fun createPerson() {
        val entity = inputObject.mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        `when`(repository.save(entity)).thenReturn(persisted)

        val dto = inputObject.mockDTO(1)
        val result = service.createPerson(dto)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)

        assertTrue(result.links.toString().contains("</api/persons/v1/1>;rel=\"self\""))
        assertEquals("Address Test1", result.address)
        assertEquals("First Name Test1", result.firstName)
        assertEquals("Last Name Test1", result.lastName)
        assertEquals("Female", result.gender)
    }

    @Test
    fun createWithNullPerson() {
        val exception: Exception = assertThrows(RequiredObjectIsNullException::class.java) {
            service.createPerson(null)
        }
        val expectedMessage = "It's not allowed persist a null object."
        val actualMessage = exception.message

        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun updatePerson() {
        val entity = inputObject.mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        `when`(repository.findById(1)).thenReturn(Optional.of(entity))
        `when`(repository.save(entity)).thenReturn(persisted)

        val dto = inputObject.mockDTO(1)
        val result = service.updatePerson(dto)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)

        assertTrue(result.links.toString().contains("</api/persons/v1/1>;rel=\"self\""))
        assertEquals("Address Test1", result.address)
        assertEquals("First Name Test1", result.firstName)
        assertEquals("Last Name Test1", result.lastName)
        assertEquals("Female", result.gender)
    }

    @Test
    fun updateWithNullPerson() {
        val exception: Exception = assertThrows(RequiredObjectIsNullException::class.java) {
            service.updatePerson(null)
        }
        val expectedMessage = "It's not allowed persist a null object."
        val actualMessage = exception.message

        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun deletePersonById() {
        val entity = inputObject.mockEntity(1)

        `when`(repository.findById(1)).thenReturn(Optional.of(entity))
        service.deletePersonById(1)
    }
}