package xohoon.study.JPAs;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xohoon.study.JPAs.domain.*;
import xohoon.study.JPAs.domain.item.Book;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        public void dbInit1() {
            Member member = createMember("userA", "서울", "1", "111");
            em.persist(member);

            Book book1 = createBook("JPA1", 10000, 999);
            em.persist(book1);

            Book book2 = createBook("JPA2", 20000, 999);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 2);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 3);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("userB", "부산", "12", "11222");
            em.persist(member);

            Book book1 = createBook("SPRING1", 15000, 999);
            em.persist(book1);

            Book book2 = createBook("SPRING2", 25000, 999);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 15000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 25000, 5);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

    }
}


