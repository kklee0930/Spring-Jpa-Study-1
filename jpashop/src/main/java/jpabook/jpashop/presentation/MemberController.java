package jpabook.jpashop.presentation;

import jakarta.validation.Valid;
import jpabook.jpashop.application.MemberService;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result) {
        // @Valid: MemberForm에 @NotEmpty가 붙어있으므로, 이를 검증해준다.
        // BindingResult: 검증에 실패하면, 에러가 담겨서 넘어온다.

        if (result.hasErrors()) {
            return "members/createMemberForm"; // 에러가 있으면, 다시 회원가입 페이지로 돌아간다.
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/"; // 첫번 째 페이지로 redirect
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> memberList = memberService.findMembers();
        model.addAttribute("members", memberList);
        return "members/memberList";
    }
}
