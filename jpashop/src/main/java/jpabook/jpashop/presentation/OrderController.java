package jpabook.jpashop.presentation;

import jpabook.jpashop.application.ItemService;
import jpabook.jpashop.application.MemberService;
import jpabook.jpashop.application.OrderService;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.infrastructure.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // 주문 내역 조회하기
    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {

        // 단순한 조회같은 경우에는 바로 Controller에서 repository 호출하는 것도 괜찮은 방법일 수 있다.
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    // 주문 취소
    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {

        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
