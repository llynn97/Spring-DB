package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
class MemberRepositoryV0Test {
    MemberRepositoryV0 memberRepositoryV0=new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {

        //save
        Member member=new Member("memberV8",10000);
        memberRepositoryV0.save(member);

        //findById
        Member findMember=memberRepositoryV0.findById(member.getMemberId());
        log.info("findMember={}",findMember);
        Assertions.assertThat(findMember).isEqualTo(member);

        //update
        memberRepositoryV0.update(member.getMemberId(),20000);
        Member UpdatedMember=memberRepositoryV0.findById(member.getMemberId());
        Assertions.assertThat(UpdatedMember.getMoney()).isEqualTo(20000);
       //delete
        memberRepositoryV0.delete(member.getMemberId());
        Assertions.assertThatThrownBy(()->memberRepositoryV0.findById(member.getMemberId())).isInstanceOf(NoSuchElementException.class);

    }
}