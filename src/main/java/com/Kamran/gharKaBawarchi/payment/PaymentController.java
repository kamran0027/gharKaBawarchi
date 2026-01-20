package com.Kamran.gharKaBawarchi.payment;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@RestController
@RequestMapping("/home/payment")
public class PaymentController {

    private final RazorpayClient razorpayClient;
    private final RazorPayConfig razorPayConfig;

    public PaymentController(RazorpayClient razorpayClient,
                             RazorPayConfig razorPayConfig) {
        this.razorpayClient = razorpayClient;
        this.razorPayConfig = razorPayConfig;
    }

    @PostMapping("/create-order")
    public ResponseEntity<Map<String, Object>> createOrder(
            @RequestParam double amount
    ) {
        try {
            long amountInPaisa = (long) (amount * 100);

            JSONObject options = new JSONObject();
            options.put("amount", amountInPaisa);
            options.put("currency", "INR");
            options.put("receipt", "booking_receipt");

            Order order = razorpayClient.orders.create(options);

            Map<String, Object> response = new HashMap<>();
            response.put("orderId", order.get("id"));
            response.put("amount", order.get("amount"));
            response.put("key", razorPayConfig.getKeyId());

            return ResponseEntity.ok(response); // ✅ ALWAYS JSON
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
}
