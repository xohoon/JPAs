package xohoon.study.JPAs.repository.order.simpleQuery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    // v3보다 성능이 조금 더 낫다(데이터 최적화)
    // 재사용율이 낮다 / DTO 조회라서 변경 불가능 / 코드 너무 길어짐
    // repository에서 entity가 아닌 dto를 조회하므로 논리게층에 어긋남
    // API 스펙 자체를 repository에 넣었기 때문에 스펙이 바뀌면 수정해야함
    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                        "select new xohoon.study.JPAs.repository.order.simpleQuery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o" +
                                " join o.member m" +
                                " join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
    }
}
