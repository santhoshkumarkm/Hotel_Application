import java.util.*;
import java.io.*;

class Order implements Serializable{
	private List<Item> itemList = new LinkedList<Item>();
	private List<Integer> quantityList = new LinkedList<Integer>();
	private Date date = new Date();
	public static final long serialVersionUID = 10002L;
	private float tax,total,discountAmount,finalAmount;
	private String phoneNumber;

	Order(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	Order(){
	}

	public Date getDate(){
		return date;
	}
	public float getTotal(){
		return total;
	}
	public List<Item> getItemList(){
		return itemList;
	}
	public List<Integer> getQuantityList(){
		return quantityList;
	}

	public int addItemsToOrder(Item item, int quantity){
		for(int i = 0; i < itemList.size(); i++){
			if(itemList.get(i).equals(item)){
				int temp = quantityList.get(i) + quantity;
				quantityList.set(i,temp);
				return 0;
			}
		}
		itemList.add(item);
		quantityList.add(quantity);
		return 0;
	}

	private float calulateDiscount(float sum, Discount discount){
		if(discount.getType() == 3){
			if (total >= discount.getTotal()){
				return total*(discount.getDiscountPercent()/100.0f);
			}
		}
		if(discount.getType() == 1){
			for(int i = 0; i < itemList.size(); i++){
				if(itemList.get(i).getId()==discount.getItem().getId()){
					return itemList.get(i).getPrice()*quantityList.get(i)*(discount.getDiscountPercent()/100.0f);
				}
			}
		}
		if(discount.getType() == 2){
			for(int i = 0; i < itemList.size(); i++){
				if(itemList.get(i).getId() == discount.getItem().getId() && quantityList.get(i) >= discount.getQuantity()){
					return itemList.get(i).getPrice()*quantityList.get(i)*(discount.getDiscountPercent()/100.0f);
				}
			}
		}
		return 0;
	}

	private float traverseItemsinOrder(){
		System.out.println("Item" + "\t\t\t" + "Quantity" + "\t" + "Price(Rs)" + "\t" + "Sub-Total(Rs)");
		float sum = 0.0f;
		for(int i = 0; i < itemList.size(); i++){
			sum = sum + (quantityList.get(i)*itemList.get(i).getPrice());
			if(itemList.get(i).getName().length() < 8)
				System.out.println(itemList.get(i).getName() + "\t\t\t" + quantityList.get(i) + "\t\t" + itemList.get(i).getPrice() + "\t\t" + (itemList.get(i).getPrice()*quantityList.get(i)));
			else
				System.out.println(itemList.get(i).getName() + "\t\t" + quantityList.get(i) + "\t\t" + itemList.get(i).getPrice() + "\t\t" + (itemList.get(i).getPrice()*quantityList.get(i)));
		}
		return sum;
	}

	public int generate(String name, float taxPercent, List<Discount> discountList){
		System.out.println("--------------------------" + name + "---------------------------");
		if(!name.equals("Items in order")){
			System.out.println("Date: " + date + "\tPh: " + phoneNumber);
		}
		float sum = traverseItemsinOrder();
		System.out.println();
		tax = (taxPercent/100.0f)*sum;
		System.out.println("Tax --> Rs." + tax + " (" + taxPercent + " %)");
		total = sum + tax;
		System.out.println("Total --> Rs." + total);
		System.out.println("-----------------------------------------------------------");
		if(!name.equals("Bill History")){
			for(Discount d : discountList)
				discountAmount += calulateDiscount(sum, d);
		}
		if(discountAmount != 0){
			System.out.println("Discount Amount --> Rs." + discountAmount);
			System.out.println("Discounted Total --> Rs." + (total-discountAmount));
			System.out.println("-----------------------------------------------------------");
			if(name.equals("Items in order"))
				return 0;
			finalAmount=total-discountAmount;
		}
		return 0;
	}
}