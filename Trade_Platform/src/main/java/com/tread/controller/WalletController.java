package com.tread.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tread.model.Order;
import com.tread.model.PaymentOrder;
import com.tread.model.User;
import com.tread.model.Wallet;
import com.tread.model.Wallettransaction;
import com.tread.response.PaymentResponse;
import com.tread.service.OrderService;
import com.tread.service.PaymentService;
import com.tread.service.UserService;
import com.tread.service.WalletService;

@RestController
public class WalletController {
         @Autowired
	private WalletService walletService;
         
         @Autowired
       private UserService userService;
         
         @Autowired
         private  OrderService orderService;
          @Autowired
         private PaymentService paymentService;
       
       @GetMapping("/api/wallet")
       public ResponseEntity<Wallet>getUserWallet(@RequestHeader("Authorization")String jwt) throws Exception{
    	   
    	   User user=userService.findUserProfileByJwt(jwt);
    	   
    	   Wallet wallet=walletService.getUserWallet(user);
    	   
    	   
    	   return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    	   
       }
	
       @PutMapping("/api/wallet/{walletId}/transfer")
       public ResponseEntity<Wallet>walletToWalletTransfer(@RequestHeader("Authorization") String jwt,@PathVariable Long walletId,@RequestBody Wallettransaction req) throws Exception{
    	   User senderUser=userService.findUserProfileByJwt(jwt);
    	   Wallet receiverWallet=walletService.findWalletById(walletId);
    	   Wallet wallet=walletService.walletToWallettransfer(senderUser,receiverWallet,req.getAmount());
    	   
    	   
         return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    	   
       }
	
       @PutMapping("/api/wallet/order/{orderId}/pay")
       public ResponseEntity<Wallet>payOrderPayment(@RequestHeader("Authorization") String jwt,@PathVariable Long orderId) throws Exception{
    	   User user=userService.findUserProfileByJwt(jwt);
    	   
    	   Order order=orderService.getOrderById(orderId);
    	   
    	   Wallet wallet = walletService.payorderPayment(order, user);
    	   
    	   
         return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
         
    	   
       }
       
       @PutMapping("/api/wallet/order/deposite")
       public ResponseEntity<Wallet>addBalanceToWallet(@RequestHeader("Authorization") String jwt,@RequestParam(name="order_id") Long orderId,@RequestParam(name = "payment_id") String paymentId) throws Exception{
    	   User user=userService.findUserProfileByJwt(jwt);
    	   
    	   
    	   Wallet wallet = walletService.getUserWallet(user);
    	   
    	   PaymentOrder order=paymentService.getPaymentOrderById(orderId);
    	   
    	   Boolean status = paymentService.ProceedPaymentOrder(order, paymentId);
    	   
    	   if(status) {
    		   wallet=walletService.addBalance(wallet, order.getAmount());
    	   }
    	 
    	   
         return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
         
    	   
       }
	
	
}
