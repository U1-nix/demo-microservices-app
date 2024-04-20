package lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.repository;

import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.model.CustomerOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<CustomerOrder, Long> {}
