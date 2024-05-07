package pt.knowledgeworks.integrationtests.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PersonDTO (

    @JsonProperty("id")
    var id: Long = 0,

    @JsonProperty("address")
    var address: String = "",

    @JsonProperty("first_name")
    var firstName: String = "",

    @JsonProperty("last_name")
    var lastName: String = "",

    @JsonProperty("gender")
    var gender: String = ""

)
