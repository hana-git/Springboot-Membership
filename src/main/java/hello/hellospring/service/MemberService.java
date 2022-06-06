package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
//command+shift+T : test 생성
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원가입
    public Long join(Member member){
        //중복 이름 존재하지않음
        /**
         * case 1 - 변수 선언 후 처리
         Optional<Member> result =  memberRepository.findByName(member.getName());  //단축키 c+o+N
         result.ifPresent(m -> {
         throw new IllegalStateException("이미 존재하는 회원입니다.");
         });
        **/
        //case 2 - 바로 반환 / 메서드로 뽑는게 낫다.
        validateDuplicateMember(member); //중복회원검증

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });

        memberRepository.findByName(member.getName()).orElseThrow(()->{
            return new IllegalArgumentException("회원찾기 실패");
        });
    }

    //전체회원조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
