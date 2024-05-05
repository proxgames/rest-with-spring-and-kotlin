package pt.knowledgeworks.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.media.Content
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.knowledgeworks.dto.v1.PersonDTO
import pt.knowledgeworks.model.Person
import pt.knowledgeworks.service.PersonService
import pt.knowledgeworks.utils.MediaType
import pt.knowledgeworks.dto.v2.PersonDTO as PersonDTOv2

@RestController
@RequestMapping("/api/persons/v1")
@Tag(name = "People", description = "Endpoint for managing people")
class PersonController {


    @Autowired
    private lateinit var personService: PersonService

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Get all people", description = "Returns all people", tags = ["people"], responses = [ApiResponse(
        responseCode = "200",
        description = "Success",
        content = [
            Content(array = ArraySchema(schema = Schema(implementation = PersonDTO::class)))
        ]), ApiResponse(responseCode = "204", description = "No Content", content = [
        Content(schema = Schema(implementation = Unit::class))
        ]), ApiResponse(responseCode = "400", description = "Bad Request", content = [
        Content(schema = Schema(implementation = Unit::class))
        ]), ApiResponse(responseCode = "401", description = "Unauthorized", content = [
        Content(schema = Schema(implementation = Unit::class))
        ]), ApiResponse(responseCode = "500", description = "Internal Error", content = [
        Content(schema = Schema(implementation = Unit::class))
        ]), ApiResponse(responseCode = "404", description = "Not Found", content = [
        Content(schema = Schema(implementation = Unit::class))
    ])
    ])
    fun findAll(): List<PersonDTO> {
        return personService.findAll()
    }

    @Operation(summary = "Finds a person", description = "Returns a person", tags = ["people"], responses =
    [ApiResponse(responseCode = "200", description = "Success", content = [
            Content(schema = Schema(implementation = PersonDTO::class))
        ]), ApiResponse(responseCode = "204", description = "No Content", content = [
        Content(schema = Schema(implementation = Unit::class))
    ]), ApiResponse(responseCode = "400", description = "Bad Request", content = [
        Content(schema = Schema(implementation = Unit::class))
    ]), ApiResponse(responseCode = "401", description = "Unauthorized", content = [
        Content(schema = Schema(implementation = Unit::class))
    ]), ApiResponse(responseCode = "500", description = "Internal Error", content = [
        Content(schema = Schema(implementation = Unit::class))
    ]), ApiResponse(responseCode = "404", description = "Not Found", content = [
        Content(schema = Schema(implementation = Unit::class))
    ])
    ])
    @GetMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun findById(@PathVariable(value = "id") id: Long): PersonDTO {
        return personService.findById(id)
    }

    @Operation(summary = "Adds a new person", description = "Adds a new person", tags = ["people"], responses =
    [ApiResponse(responseCode = "200", description = "Success", content = [
        Content(schema = Schema(implementation = PersonDTO::class))
    ]), ApiResponse(responseCode = "400", description = "Bad Request", content = [
        Content(schema = Schema(implementation = Unit::class))
    ]), ApiResponse(responseCode = "401", description = "Unauthorized", content = [
        Content(schema = Schema(implementation = Unit::class))
    ]), ApiResponse(responseCode = "500", description = "Internal Error", content = [
        Content(schema = Schema(implementation = Unit::class))])
    ])
    @PostMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML], consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun create(@RequestBody person: PersonDTO): PersonDTO {
        return personService.createPerson(person)
    }

    @Operation(summary = "Updates a person information", description = "Updates a person information", tags = ["people"], responses =
    [ApiResponse(responseCode = "200", description = "Success", content = [
        Content(schema = Schema(implementation = PersonDTO::class))
    ]), ApiResponse(responseCode = "204", description = "No Content", content = [
        Content(schema = Schema(implementation = Unit::class))
    ]), ApiResponse(responseCode = "400", description = "Bad Request", content = [
        Content(schema = Schema(implementation = Unit::class))
    ]), ApiResponse(responseCode = "401", description = "Unauthorized", content = [
        Content(schema = Schema(implementation = Unit::class))
    ]), ApiResponse(responseCode = "500", description = "Internal Error", content = [
        Content(schema = Schema(implementation = Unit::class))
    ]), ApiResponse(responseCode = "404", description = "Not Found", content = [
        Content(schema = Schema(implementation = Unit::class))
    ])
    ])
    @PutMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML], consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun update(@RequestBody person: PersonDTO): PersonDTO {
        return personService.updatePerson(person)
    }

    @Operation(summary = "Deletes a person information", description = "Deletes a person information", tags = ["people"], responses =
    [ApiResponse(responseCode = "204", description = "No Content", content = [
        Content(schema = Schema(implementation = Unit::class))
    ]), ApiResponse(responseCode = "400", description = "Bad Request", content = [
        Content(schema = Schema(implementation = Unit::class))
    ]), ApiResponse(responseCode = "401", description = "Unauthorized", content = [
        Content(schema = Schema(implementation = Unit::class))
    ]), ApiResponse(responseCode = "500", description = "Internal Error", content = [
        Content(schema = Schema(implementation = Unit::class))
    ]), ApiResponse(responseCode = "404", description = "Not Found", content = [
        Content(schema = Schema(implementation = Unit::class))
    ])
    ])
    @DeleteMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun delete(@PathVariable(value = "id") id: Long) : ResponseEntity<*> {
        personService.deletePersonById(id)
        return ResponseEntity.noContent().build<Any>()
    }
}