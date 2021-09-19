package com.gapfyl.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author vignesh
 * Created on 13/06/21
 **/

@Configuration
public class RazorpayConfig {

    @Autowired
    Environment environment;

    @Bean
    public RazorpayClient razorpayClient() throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(environment.getProperty("razorpay.key_id"),
                environment.getProperty("razorpay.key_secret"));
        return razorpayClient;
    }
}
