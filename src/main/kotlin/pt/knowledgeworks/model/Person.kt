package pt.knowledgeworks.model

import jakarta.persistence.*

@Entity
@Table(name = "person")
data class Person (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "first_name", nullable = false, length = 50)
    var firstName: String = "",

    @Column(name = "last_name", nullable = false, length = 50)
    var lastName: String = "",

    @Column(nullable = false, length = 100)
    var address: String = "",

    @Column(nullable = false, length = 10)
    var gender: String = "",

)
