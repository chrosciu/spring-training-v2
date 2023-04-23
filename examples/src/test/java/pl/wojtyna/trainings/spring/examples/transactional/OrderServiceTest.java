package pl.wojtyna.trainings.spring.examples.transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = TransactionalExampleConfiguration.class)
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    // @formatter:off
    @DisplayName(
            """
             If method marked with @Transactional is called from another method
             of the same bean, transaction is not being applied
             and order is saved even if an exception is thrown
            """
    )
    // @formatter:on
    @Test
    void saveIndirectlyInTransaction() {
        MyOrder order = new MyOrder();
        order.setId(UUID.randomUUID().toString());
        try {
            orderService.saveIndirectlyInTransaction(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(orderRepository.findById(order.getId())).isNotEmpty();
    }

    // @formatter:off
    @DisplayName(
            """
             If method marked with @Transactional is called directly
             transaction is being applied but order is saved
             in case when an exception is thrown
            """
    )
    // @formatter:on
    @Test
    void saveDirectlyInTransaction() {
        MyOrder order = new MyOrder();
        order.setId(UUID.randomUUID().toString());
        try {
            orderService.saveDirectlyInTransaction(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(orderRepository.findById(order.getId())).isEmpty();
    }

    // @formatter:off
    @DisplayName(
            """
             If method marked with @Transactional is called directly
             transaction is being applied and order is not saved
             in case when a checked exception is thrown
            """
    )
    // @formatter:on
    @Test
    void saveDirectlyInTransactionWithCheckedException() {
        MyOrder order = new MyOrder();
        order.setId(UUID.randomUUID().toString());
        try {
            orderService.saveDirectlyInTransactionWithCheckedException(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(orderRepository.findById(order.getId())).isNotEmpty();
    }
}