package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * JDBC - Datasource , JdbcUtils 사용
 */
@Slf4j
class MemberRepositoryV1Test {
    MemberRepositoryV1 memberRepositoryV1;

    @BeforeEach
    void beforeEach(){
       // DriverManagerDataSource dataSource=new DriverManagerDataSource(ConnectionConst.URL,ConnectionConst.USERNAME,ConnectionConst.PASSWORD);

        //커넥션 풀링
        HikariDataSource dataSource=new HikariDataSource();
        dataSource.setJdbcUrl(ConnectionConst.URL);
        dataSource.setUsername(ConnectionConst.USERNAME);
        dataSource.setPassword(ConnectionConst.PASSWORD);
        memberRepositoryV1=new MemberRepositoryV1(dataSource);
    }

    @Test
    void crud() throws SQLException, InterruptedException {

        //save
        Member member=new Member("memberV8",10000);
        memberRepositoryV1.save(member);

        //findById
        Member findMember=memberRepositoryV1.findById(member.getMemberId());
        log.info("findMember={}",findMember);
        Assertions.assertThat(findMember).isEqualTo(member);

        //update
        memberRepositoryV1.update(member.getMemberId(),20000);
        Member UpdatedMember=memberRepositoryV1.findById(member.getMemberId());
        Assertions.assertThat(UpdatedMember.getMoney()).isEqualTo(20000);
       //delete
        memberRepositoryV1.delete(member.getMemberId());
        Assertions.assertThatThrownBy(()->memberRepositoryV1.findById(member.getMemberId())).isInstanceOf(NoSuchElementException.class);
       Thread.sleep(1000);
    }
}