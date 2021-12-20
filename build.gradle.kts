import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    kotlin("plugin.jpa") version "1.6.0"
    kotlin("kapt") version "1.4.10"
    jacoco
    groovy
    id("org.sonarqube") version "3.3"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11


configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["snippetsDir"] = file("$buildDir/generated-snippets")

dependencies {

    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // socketio
    implementation("com.corundumstudio.socketio:netty-socketio:1.7.19")

    // mysql
    runtimeOnly("mysql:mysql-connector-java")

    // configuration-processor
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")


    // restDocs
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

    // test
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // jwt
    implementation("io.jsonwebtoken:jjwt:0.9.0")

    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // spock
    testImplementation("org.spockframework:spock-core:2.0-groovy-3.0")
    testImplementation("org.spockframework:spock-spring:2.0-groovy-3.0")

    // groovy plugin
    testRuntimeOnly("org.codehaus.groovy:groovy:3.0.8")

    implementation("it.ozimov:embedded-redis:0.7.2")

    testImplementation("com.h2database:h2")

    // queryDsl
    kapt(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")
    implementation("com.querydsl:querydsl-jpa")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// jpa Setting
noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks.test {
    val property = project.property("snippetsDir")!!
    outputs.dir(property)
}


// asciiDoc Setting
tasks.asciidoctor {
    dependsOn(tasks.jacocoTestCoverageVerification)
}

// jacoco Setting
jacoco {
    toolVersion = "0.8.7"
}

tasks.jacocoTestReport {
    reports {
        html.required.set(true)
        xml.required.set(true)
        csv.required.set(false)
    }
    dependsOn(tasks.test)
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                counter = "INSTRUCTION"
                value = "COVEREDRATIO"
                minimum = "0.000".toBigDecimal()
            }

            excludes = listOf(
                "com.example.forsubmit.ForSubmitApplication.kt",
                "*.html",
                "*.adoc",
            )
        }
    }
    dependsOn(tasks.jacocoTestReport)
}

// sonarqube Setting
sonarqube {
    properties {
        property("sonar.organization", "jhhong0509")
        property("sonar.projectKey", "jhhong0509_Kopring-Best-Practice")
        property("sonar.sources", ".")
        property("sonar.coverage.jacoco.xmlReportPaths", "$buildDir/reports/jacoco/test/jacocoTestReport.xml")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.exclusions", "**/*Test*.*, **/Q*.java, **/*.html, **/*.adoc")
        property("sonar.test.inclusions", "**/*.groovy, **/test/**/*.kt")
    }
}

kotlin.sourceSets.main {
    kotlin.srcDir("$buildDir/generated/source/kapt/main")
}