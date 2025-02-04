package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.ddl.exception.AnnotationMissingException;
import persistence.sql.ddl.exception.IdAnnotationMissingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateQueryBuilderTest {
    @Test
    @DisplayName("요구사항1: 기본 테이블 생성 쿼리 생성 테스트")
    void testGenerateCreateQuery() {
        CreateQueryBuilder sut = new CreateQueryBuilder(new H2Dialect(), Requirement1.class);
        String expect = "CREATE TABLE requirement1 (id BIGINT, name VARCHAR, age INTEGER, PRIMARY KEY (id))";

        String query = sut.toQuery();

        assertThat(query.toLowerCase()).isEqualTo(expect.toLowerCase());
    }

    @Test
    @DisplayName("요구사항2: 추가된 정보 테이블 생성 쿼리 생성 테스트")
    void testGenerateCreateQuery2() {
        CreateQueryBuilder sut = new CreateQueryBuilder(new H2Dialect(), Requirement2.class);
        String expect = "CREATE TABLE requirement2 (id BIGINT generated by default as identity, nick_name VARCHAR, old INTEGER, email varchar not null, PRIMARY KEY (id))";

        String query = sut.toQuery();

        assertThat(query.toLowerCase()).isEqualTo(expect.toLowerCase());
    }

    @Test
    @DisplayName("요구사항3: 추가된 정보 테이블 생성 쿼리 생성 테스트2")
    void testGenerateCreateQuery3() {
        CreateQueryBuilder sut = new CreateQueryBuilder(new H2Dialect(), Requirement3.class);
        String expect = "CREATE TABLE users (id BIGINT generated by default as identity, nick_name VARCHAR, old INTEGER, email varchar not null, PRIMARY KEY (id))";

        String query = sut.toQuery();

        assertThat(query.toLowerCase()).isEqualTo(expect.toLowerCase());
    }
}
