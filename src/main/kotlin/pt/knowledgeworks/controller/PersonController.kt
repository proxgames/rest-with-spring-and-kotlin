package pt.knowledgeworks.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.knowledgeworks.dto.v1.PersonDTO
import pt.knowledgeworks.service.PersonService
import pt.knowledgeworks.utils.MediaType
import pt.knowledgeworks.dto.v2.PersonDTO as PersonDTOv2

@RestController
@RequestMapping("/api/persons/v1")
class PersonController {


    @Autowired
    private lateinit var personService: PersonService

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun findAll(): List<PersonDTO> {
        return personService.findAll()
    }

    @GetMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun findById(@PathVariable(value = "id") id: Long): PersonDTO {
        return personService.findById(id)
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML], consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun create(@RequestBody person: PersonDTO): PersonDTO {
        return personService.createPerson(person)
    }

    @PostMapping(value = ["/v2"], produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML], consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun createV2(@RequestBody person: PersonDTOv2): PersonDTOv2 {
        return personService.createPersonV2(person)
    }

    @PutMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML], consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun update(@RequestBody person: PersonDTO): PersonDTO {
        return personService.updatePerson(person)
    }

    @DeleteMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun delete(@PathVariable(value = "id") id: Long) : ResponseEntity<*> {
        personService.deletePersonById(id)
        return ResponseEntity.noContent().build<Any>()
    }
}