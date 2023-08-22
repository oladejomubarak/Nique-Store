package oladejo.mubarak.niquestore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentService {
private final String pay_stack_key = System.getenv("PAY_STACK_SECRET_KEY");
}
