package pt.knowledgeworks.dto.v1

data class PersonDTO @JvmOverloads constructor (


    var id: Long = 0,

    var firstName: String = "",

    var lastName: String = "",

    var address: String = "",

    var gender: String = "",

)
