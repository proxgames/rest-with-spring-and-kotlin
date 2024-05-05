package pt.knowledgeworks.dto.v2

import java.util.*

data class PersonDTO @JvmOverloads constructor(


    var id: Long = 0,

    var firstName: String = "",

    var lastName: String = "",

    var address: String = "",

    var gender: String = "",

    var birthDay: Date? = null

    )
