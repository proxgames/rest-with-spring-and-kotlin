package pt.knowledgeworks.dto.v1

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.github.dozermapper.core.Mapping
import org.springframework.hateoas.RepresentationModel

@JsonPropertyOrder("key", "address", "first_name", "last_name", "gender" )
data class PersonDTO @JvmOverloads constructor (

    @Mapping("id")
    @field:JsonProperty("id")
    var key: Long = 0,

    @field:JsonProperty("first_name")
    var firstName: String = "",

    @field:JsonProperty("last_name")
    var lastName: String = "",

    var address: String = "",

    var gender: String = "",

) : RepresentationModel<PersonDTO>()
