package com.tread.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tread.domain.OrderStatus;
import com.tread.domain.OrderType;
import com.tread.model.Asset;
import com.tread.model.Coin;
import com.tread.model.Order;
import com.tread.model.OrderItem;
import com.tread.model.User;
import com.tread.repository.OrderItemRepository;
import com.tread.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceimpl  implements OrderService{

	     @Autowired
	   private OrderRepository orderRepository;
	     
	     @Autowired
	     private WalletService walletService;
	     
	     @Autowired
	     private OrderItemRepository orderItemRepository;
	     @Autowired
	     private AssetService assetService;
	     
	     
	@Override
	public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
          double price=orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();
          
          Order order=new Order();
          order.setUser(user);
          order.setOrderItem(orderItem);
          order.setOrderType(orderType);
          order.setPrice(BigDecimal.valueOf(price));
          order.setTimestamp(LocalDateTime.now());
          order.setStatus(OrderStatus.PENDING);
          
		
		
		
		return orderRepository.save(order);
	}

	@Override
	public Order getOrderById(Long orderId) throws Exception {

		
		
		return orderRepository.findById(orderId).orElseThrow(()->new Exception("order not found"));
	}

	@Override
	public List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) {

		
		return orderRepository.findByUserId(userId);
	}

	private OrderItem createOrderItem(Coin coin,double quantity,double buyprice,double sellprice) {
		
		OrderItem orderItem=new OrderItem();
		  orderItem.setCoin(coin);
		  orderItem.setQuantity(quantity);
		  orderItem.setBuyPrice(buyprice);
		  orderItem.setSellPrice(sellprice);
		  
		  return orderItemRepository.save(orderItem);
	
	}
	
	@Transactional
	public Order buyAsset(Coin coin,double quantity,User user) throws Exception {
		
		 if(quantity<=0) {
			 
			 throw new Exception("quantity should be > 0");
		 }
		 
		 double buyPrice=coin.getCurrentPrice();
		 
		 OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, 0);
		 
		 Order order=createOrder(user, orderItem, OrderType.BUY);
		 orderItem.setOrder(order);
		 
		 walletService.payorderPayment(order, user);
		 
		 order.setStatus(OrderStatus.SUCCESS);
		 order.setOrderType(OrderType.BUY);
		 Order saveOrder=orderRepository.save(order);
		 
		 // create asset
		 Asset oldAsset=assetService.findAssetByUserIdAndCoinId(order.getUser().getId(), order.getOrderItem().getCoin().getId());
		 
		 if(oldAsset==null) {
			 
			 assetService.createAsset(user, orderItem.getCoin(),orderItem.getQuantity());
		 }
		 else{
			 assetService.updateAsset(oldAsset.getId(), quantity);
			 
		 }
		 return saveOrder;
		 
		 
		 
	}
	
	
	@Transactional
	public Order sellAsset(Coin coin,double quantity,User user) throws Exception {
		
		 if(quantity<=0) {
			 
			 throw new Exception("quantity should be > 0");
		 }
		 
		 double sellPrice=coin.getCurrentPrice();
		 
		 Asset assetTosell=assetService.findAssetByUserIdAndCoinId(user.getId(), coin.getId());

		 double buyPrice=assetTosell.getBuyPrice();

		 if(assetTosell!=null) {
			 OrderItem orderItem=createOrderItem(coin, quantity, buyPrice, sellPrice);
		 
		 		 
		 Order order=createOrder(user, orderItem, OrderType.SELL);
		 orderItem.setOrder(order);
		 
		 
		 if(assetTosell.getQuantity()>=quantity) {
			 order.setStatus(OrderStatus.SUCCESS);
			 order.setOrderType(OrderType.SELL);
			 Order saveOrder=orderRepository.save(order);
			 
			 walletService.payorderPayment(order, user);
			 
			 Asset updatedAsset=assetService.updateAsset(assetTosell.getId(),-quantity);
			 
			 if(updatedAsset.getQuantity()*coin.getCurrentPrice()<=1) {
				  assetService.deleteAsset(updatedAsset.getId());
			 }
			 
			 
			 return saveOrder;
			 
		 }
		 
		throw new Exception("Indufficient quantity to sell");
		 
		 }
		 
		 throw new Exception("asset not found");
		 
	}
	
	
	
	@Override
	@Transactional
	public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {
           
		if(orderType.equals(OrderType.BUY)) {
			
			return buyAsset(coin, quantity, user);
		}
		else if(orderType.equals(OrderType.SELL)) {
			
			return sellAsset(coin, quantity, user);
		}
		
		throw new Exception("invalid order type");
	}

}
