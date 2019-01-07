import java.util.*;
import java.io.*;

class OrderList implements Serializable{
	private List<Order> orderList = new LinkedList<Order>();
	public static final long serialVersionUID = 10001L;

	public void addOrder(Order order){
		orderList.add(order);
	}

	public List<Order> getOrderList(){
		return orderList;
	}
}