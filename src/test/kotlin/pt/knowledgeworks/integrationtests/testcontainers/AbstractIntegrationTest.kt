package pt.knowledgeworks.integrationtests.testcontainers

import java.util.stream.Stream
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.Environment
import org.springframework.core.env.MapPropertySource
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.lifecycle.Startables

@ContextConfiguration(initializers = [AbstractIntegrationTest.Initializer::class])
open class AbstractIntegrationTest {

    @Autowired
    private lateinit var environment: Environment

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            startContainers()

            val environment = applicationContext.environment
            val testContainers = MapPropertySource(
                "testContainers", createConnectionConfiguration()
            )
            environment.propertySources.addFirst(testContainers)
        }

        companion object {
            private var mysql: MySQLContainer<*> = MySQLContainer("mysql:8.0.28")

            private fun startContainers() {
                Startables.deepStart(Stream.of(mysql)).join()
            }

            private fun createConnectionConfiguration(): MutableMap<String, Any> {
                return java.util.Map.of(
                    "spring.datasource.url=", mysql.jdbcUrl,
                    "spring.datasource.username=", mysql.username,
                    "spring.datasource.password=", mysql.password
                )
            }
        }
    }
}