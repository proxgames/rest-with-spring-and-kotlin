package pt.knowledgeworks.mappers.custom

import org.springframework.stereotype.Service
import pt.knowledgeworks.dto.v2.PersonDTO
import pt.knowledgeworks.model.Person
import java.util.*

@Service
class PersonMapper {

    fun mapEntityToDto(person: Person): PersonDTO {
        val dto = PersonDTO()

        dto.id = person.id
        dto.firstName = person.firstName
        dto.lastName = person.lastName
        dto.address = person.address
        dto.gender = person.gender
        dto.birthDay = Date()

        return dto
    }

    fun mapDtoToEntity(person: PersonDTO): Person {
        val entity = Person()

        entity.id = person.id
        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender
//        entity.birthDay = person.birthDay
        
        return entity
    }

}