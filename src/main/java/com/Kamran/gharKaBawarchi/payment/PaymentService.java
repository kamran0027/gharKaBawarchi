package com.Kamran.gharKaBawarchi.payment;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;

public class PaymentService {


    @Autowired
    private  RazorpayClient razorpayClient;
    @Autowired
    private  RazorPayConfig razorpayConfig;


    public Order creaOrder(long amountInPaisa, String currency,String reciept )throws Exception{
        JSONObject options=new JSONObject();
        options.put("amount", amountInPaisa);
        options.put("currency", currency);
        options.put("receipt", reciept);

        options.put("payment_capture",1);

        return razorpayClient.orders.create(options);
    }

    public String getKeyId(){
        return razorpayConfig.getKeyId();
    }
    public String getKeySecret() {
        return razorpayConfig.getKeySecret();
    }
}
