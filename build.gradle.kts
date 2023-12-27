plugins {
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    id("java")
}

group = "infra-workshop"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

val jgraphtVersion = "1.5.2"
val jjwtVersion = "0.11.5"
val restAssuredVersion = "5.3.0"

dependencies {
    // spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // thymeleaf
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // jgraph
    implementation("org.jgrapht:jgrapht-core:$jgraphtVersion")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")

    runtimeOnly("com.h2database:h2")

    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}
