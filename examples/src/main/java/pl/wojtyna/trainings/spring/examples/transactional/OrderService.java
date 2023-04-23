package pl.wojtyna.trainings.spring.examples.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Transactional
    public void saveDirectlyInTransaction(MyOrder order) {
        orderRepository.persist(order);
        throw new RuntimeException("General error");
    }

    public void saveIndirectlyInTransaction(MyOrder order) {
        saveDirectlyInTransaction(order);
    }

    @Transactional
    public void saveDirectlyInTransactionWithCheckedException(MyOrder order) throws IOException {
        orderRepository.persist(order);
        throw new IOException("Network cable unplugged");
    }

}
