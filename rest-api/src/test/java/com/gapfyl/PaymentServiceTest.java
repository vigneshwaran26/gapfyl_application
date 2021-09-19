package com.gapfyl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.gapfyl.services.checkouts.payments.PaymentService;

/**
 * @author vignesh
 * Created on 02/06/21
 **/


@SpringBootTest
@ActiveProfiles("development")
public class PaymentServiceTest {

    @Autowired
    PaymentService paymentService;

    /*
    @Test
    public void testFetchCustomers() throws Exception {
        paymentService.fetchCustomers();
    }

    @Test
    public void b_testCreateCustomer() throws Exception {
        paymentService.createCustomer();
    }

    @Test
    public void c_testFetchCustomerById() throws Exception {
        paymentService.fetchCustomerById("cus_JbATsow9El2c9r");
    }

    @Test
    public void d_testUpdateCustomer() throws Exception {
        paymentService.updateCustomer("cus_JbATsow9El2c9r");
    }

    @Test
    public void e_testDeleteCustomer() throws Exception {
        paymentService.deleteCustomer("cus_JbATsow9El2c9r");
    }

    @Test
    public void f_testCreatePaymentIntent() throws Exception {
        paymentService.createPaymentIntent(null);
    }

    @Test
    public void g_testConfirmPaymentIntent() throws Exception {
        paymentService.confirmPaymentIntent("pi_1IyTKxSFm21l3JmiOpUptqqv");
    }
     */

}
