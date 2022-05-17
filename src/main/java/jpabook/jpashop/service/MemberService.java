package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // 파이널 가지고 있는 필드만 생성자로 만들어준다
public class MemberService {

    //필드는 주입하기 어렵고
    //final은 컴파일시점에서 체크를 해줄 수 있기 때문에 final 넣는거 추천
    private final MemberRepository memberRepository;

//가짜 메서드 통해서 주입할 수 있음
    //단점 런타입 도는 시점에서 누군가가 바꿀 수 있는것은 단점이지만 로딩시점에서는 이미 조립이 완성된 후라
/*    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    /**
    회원 가입**/
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다."); //멀티쓰레드 상황 고려하여 디비에 멤버의 이름을 유니크 제약 조건으로 하는 최후의 방어
        }

    }

    //전체 회원조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
