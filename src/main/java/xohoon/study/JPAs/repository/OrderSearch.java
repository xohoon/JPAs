package xohoon.study.JPAs.repository;

import lombok.Getter;
import lombok.Setter;
import xohoon.study.JPAs.domain.OrderStatus;

@Getter @Setter
public class OrderSearch {

    private String memberName; // 회원 이름
    private OrderStatus orderStatus; // 주문 상태(ORDER, CANCEL)
}
