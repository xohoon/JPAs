package xohoon.study.JPAs.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xohoon.study.JPAs.domain.Member;
import xohoon.study.JPAs.service.MemberService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController // Controller + ResponseBody
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /*
     * 회원등록 API
     * */
    // entity를 변경하면 API스펙이 완변 바뀌어버린다
    // entity 활용한것 -> 실무에서는 entity를 노출하지말고 파라미터로 받지마라(DTO)
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    // entity 스펙을 변경하면 컴파일 오류, API 스펙에는 영향을 주지 않는다.
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    @Data // DTO 만들면 어떤 값이 오는지 API스펙에 맞춰져서 유지보수에 좋다
    static class CreateMemberRequest {
        private String name;
    }

    /*
     * 회원 수정 API
     * */
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    /*
     * 회원 조회 API
     * */
    // entity 안에 order 정보도 포함되어 있다(@JsonIgnore 로 제외시킬 순 있다 하지만 API마다 요청하는 데이터가 다르기 때문에 NO)
    // array를 바로 반환하면 다양한 api 스펙을 만들 수 없다(json 문법에 어긋남)
    @GetMapping("/api/v1/members")
    public List<Member> memberV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

//        List<MemberDto> collect = memberService.findMembers().stream()
//                .map(m -> new MemberDto(m.getName()))
//                .collect(Collectors.toList());
//        통합해도된다

        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }
}
