package pt.knowledgeworks.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import pt.knowledgeworks.controller.PersonController
import pt.knowledgeworks.dto.v1.PersonDTO
import pt.knowledgeworks.exceptions.RequiredObjectIsNullException
import pt.knowledgeworks.exceptions.ResourceNotFoundException
import pt.knowledgeworks.mappers.ModelMapper
import pt.knowledgeworks.mappers.custom.PersonMapper
import pt.knowledgeworks.model.Person
import pt.knowledgeworks.repository.PersonRepository
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var personMapper: PersonMapper

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<PersonDTO> {
        logger.info("Finding all people")

        val persons = personRepository.findAll()
        val dtos = ModelMapper.parseListObjects(persons, PersonDTO::class.java)
        for (person in dtos) {
            val withSelfRel = linkTo(PersonController::class.java).slash(person.key).withSelfRel()
            person.add(withSelfRel)
        }
        return dtos
    }

    fun findById(id: Long): PersonDTO {
        logger.info("Finding Person with ID: $id")

        val person = personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found with ID: $id") }
        val personDTO: PersonDTO = ModelMapper.parseObject(person, PersonDTO::class.java)

        val withSelfRel = linkTo(PersonController::class.java).slash(personDTO.key).withSelfRel()
        personDTO.add(withSelfRel)

        return personDTO
    }

    fun createPerson(person: PersonDTO?): PersonDTO {
        if(person == null) throw RequiredObjectIsNullException()
        logger.info("Creating Person with ID: ${person.key}")
        val entity: Person = ModelMapper.parseObject(person, Person::class.java)
        val personDTO: PersonDTO = ModelMapper.parseObject(personRepository.save(entity), PersonDTO::class.java)

        val withSelfRel = linkTo(PersonController::class.java).slash(personDTO.key).withSelfRel()
        personDTO.add(withSelfRel)

        return personDTO
    }

    fun updatePerson(person: PersonDTO?): PersonDTO {
        if(person == null) throw RequiredObjectIsNullException()
        logger.info("Updating Person with ID ${person.key}")
        val entity = personRepository.findById(person.key)
            .orElseThrow { ResourceNotFoundException("No records found with ID: $person.id") }

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender
        val personDTO: PersonDTO = ModelMapper.parseObject(personRepository.save(entity), PersonDTO::class.java)

        val withSelfRel = linkTo(PersonController::class.java).slash(personDTO.key).withSelfRel()
        personDTO.add(withSelfRel)

        return personDTO
    }

    fun deletePersonById(id: Long) {
        logger.info("Deleting Person with ID: $id")

        val entity = personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found with ID: $id") }
        personRepository.delete(entity)

    }

}