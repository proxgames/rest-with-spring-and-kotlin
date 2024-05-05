package pt.knowledgeworks.repository

import org.springframework.data.jpa.repository.JpaRepository
import pt.knowledgeworks.model.Person

interface PersonRepository : JpaRepository<Person, Long?> {
}