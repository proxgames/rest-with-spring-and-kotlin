package pt.knowledgeworks.dto.v1

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("id", "address", "first_name", "last_name", "gender" )
data class PersonDTO @JvmOverloads constructor (


    var id: Long = 0,

    @field:JsonProperty("first_name")
    var firstName: String = "",

    @field:JsonProperty("last_name")
    var lastName: String = "",

    var address: String = "",

    @field:JsonIgnore
    var gender: String = "",

    )
