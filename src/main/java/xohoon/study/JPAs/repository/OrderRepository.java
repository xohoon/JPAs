package xohoon.study.JPAs.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xohoon.study.JPAs.domain.Order;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

}