package xohoon.study.JPAs.repository.order.query;

import lombok.Data;

@Data
public class OrderItemQueryDto {

    private Long orderId;
    private String itemName;
    private int orderPrice;
    private int scount;

    public OrderItemQueryDto(Long orderId, String itemName, int orderPrice, int scount) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.scount = scount;
    }
}
