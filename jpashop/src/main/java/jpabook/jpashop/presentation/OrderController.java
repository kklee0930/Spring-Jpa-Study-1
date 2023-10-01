package jpabook.jpashop.presentation;

import jpabook.jpashop.application.ItemService;
import jpabook.jpashop.application.MemberService;
import jpabook.jpashop.application.OrderService;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        // Controller에서는 식별자만 넘기고, 서비스 계층에서 실제 엔티티를 조회한다.
        orderService.order(memberId, itemId, count);
        return "redirect:/orders"; // 주문 목록으로 리다이렉트.
    }
}
