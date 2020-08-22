package com.chris.springcloud.payment.service.impl;

import com.chris.springcloud.payment.dao.PaymentDao;
import com.chris.springcloud.payment.service.PaymentService;
import com.chris.springcloud.entities.Payment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {


    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);

    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
