import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.asciidoctor.convert") version "1.5.8"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    kotlin("plugin.jpa") version "1.6.0"
    jacoco
    groovy
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

extra["snippetsDir"] = file("build/generated-snippets")

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

tasks.test {
    project.property("snippetsDir")?.let { outputs.dir(it) }
    finalizedBy("jacocoTestReport")
}

tasks.asciidoctor {
    project.property("snippetsDir")?.let { inputs.dir(it) }
    dependsOn(tasks.test)
}

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

jacoco {
    toolVersion = "0.8.7"
//    reportsDirectory.set(file("$buildDir/customJacocoReportDir"))
}

tasks.jacocoTestReport {
    reports {
        html.required.set(true)
        xml.required.set(false)
        csv.required.set(false)
    }
    finalizedBy("jacocoTestCoverageVerification")
}
tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            // 'element'가 없으면 프로젝트의 전체 파일을 합친 값을 기준으로 한다.
            limit {
                // 'counter'를 지정하지 않으면 default는 'INSTRUCTION'
                // 'value'를 지정하지 않으면 default는 'COVEREDRATIO'
                minimum = "0.30".toBigDecimal()
            }
        }

        rule {
            // 룰을 간단히 켜고 끌 수 있다.
            enabled = true

            // 룰을 체크할 단위는 클래스 단위
            element = "CLASS"

            // 브랜치 커버리지를 최소한 90% 만족시켜야 한다.
            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
//                minimum = "0.90".toBigDecimal()       임시로 사용 중지
            }

//            // 라인 커버리지를 최소한 80% 만족시켜야 한다.
//            limit {
//                counter = "LINE"
//                value = "COVEREDRATIO"
//                minimum = "0.80".toBigDecimal()
//            }

//             빈 줄을 제외한 코드의 라인수를 최대 200라인으로 제한한다.
//            limit {
//                counter = "LINE"
//                value = "TOTALCOUNT"
//                maximum = "200".toBigDecimal()
//            }

            // 커버리지 체크를 제외할 클래스들
            excludes = listOf(
                    "com.example.forsubmit.ForSubmitApplication.kt",
            )
        }
    }
}