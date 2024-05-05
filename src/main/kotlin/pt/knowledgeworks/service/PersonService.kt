package pt.knowledgeworks.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pt.knowledgeworks.dto.v1.PersonDTO
import pt.knowledgeworks.dto.v2.PersonDTO as PersonDTOv2
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
        return ModelMapper.parseListObjects(persons, PersonDTO::class.java)
    }

    fun findById(id: Long): PersonDTO {
        logger.info("Finding Person with ID: $id")

        val person = personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found with ID: $id") }
        return ModelMapper.parseObject(person, PersonDTO::class.java)
    }

    fun createPerson(person: PersonDTO): PersonDTO {
        logger.info("Creating Person with ID: ${person.id}")
        val entity: Person = ModelMapper.parseObject(person, Person::class.java)
        return ModelMapper.parseObject(personRepository.save(entity), PersonDTO::class.java)
    }

    fun createPersonV2(person: PersonDTOv2): PersonDTOv2 {
        logger.info("Creating Person with ID: ${person.id}")
        val entity: Person = personMapper.mapDtoToEntity(person)
        return personMapper.mapEntityToDto(personRepository.save(entity))
    }

    fun updatePerson(person: PersonDTO): PersonDTO {
        logger.info("Updating Person with ID ${person.id}")
        val entity = personRepository.findById(person.id)
            .orElseThrow { ResourceNotFoundException("No records found with ID: $person.id") }

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender
        return ModelMapper.parseObject(personRepository.save(entity), PersonDTO::class.java)
    }

    fun deletePersonById(id: Long) {
        logger.info("Deleting Person with ID: $id")

        val entity = personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found with ID: $id") }
        personRepository.delete(entity)

    }

}