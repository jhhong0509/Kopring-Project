package com.example.forsubmit

import com.example.forsubmit.global.jpa.JpaConfig
import com.example.forsubmit.global.jpa.QueryDSLConfig
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import spock.lang.Specification

@Import([JpaConfig, QueryDSLConfig])
@DataJpaTest
class BaseJpaTest extends Specification {
}
