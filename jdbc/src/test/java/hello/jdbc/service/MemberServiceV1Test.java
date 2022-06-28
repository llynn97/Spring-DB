package hello.jdbc.service;

import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 기본동작, 트랜잭션 없어서 문제 발생
 */
class MemberServiceV1Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";
    private MemberRepositoryV1 memberRepository;
    private MemberServiceV1 memberService;

    @BeforeEach
    void beforeEach(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource(ConnectionConst.URL,ConnectionConst.USERNAME,ConnectionConst.PASSWORD);
        memberRepository=new MemberRepositoryV1(dataSource);
        memberService=new MemberServiceV1(memberRepository);
    }

    @AfterEach
    void AfterEach() throws SQLException {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_EX);
    }
    @Test
    @DisplayName("정상 이체")
    void accountTransfer() throws SQLException {
        Member memberA= new Member(MEMBER_A,10000);
        Member memberB=new Member(MEMBER_B,10000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);
        memberService.accountTransfer(memberA.getMemberId(),memberB.getMemberId(),2000);

       Member findMemberA= memberRepository.findById(memberA.getMemberId());
       Member findMemberB=memberRepository.findById(memberB.getMemberId());
        org.assertj.core.api.Assertions.assertThat(findMemberA.getMoney()).isEqualTo(8000);

    }

    @Test
    @DisplayName("이체 중 예외외")
    void accountTransferEx() throws SQLException {
        Member memberA= new Member(MEMBER_A,10000);
        Member memberB=new Member(MEMBER_EX ,10000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        org.assertj.core.api.Assertions.assertThatThrownBy(()->memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(),2000)).isInstanceOf(IllegalStateException.class);
        Member findMemberA= memberRepository.findById(memberA.getMemberId());
        Member findMemberB=memberRepository.findById(memberB.getMemberId());
        org.assertj.core.api.Assertions.assertThat(findMemberA.getMoney()).isEqualTo(8000);

    }


}