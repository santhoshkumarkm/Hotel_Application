import java.io.*;
import java.util.*;

class Discount implements Serializable{
	public static Scanner scan = new Scanner(System.in).useDelimiter("\n");
	public static final long serialVersionUID = 10004L;
	private int type;
	private Item item;
	private int quantity;
	private float discountPercent;
	private float total;

	public Item getItem(){
		return item;
	}

	public int getQuantity(){
		return quantity;
	}

	public int getType(){
		return type;
	}

	public float getDiscountPercent(){
		return discountPercent;
	}

	public float getTotal(){
		return total;
	}

	public Discount(Item item, float discountPercent){
		type=1;
		this.item = item;
		this.discountPercent = discountPercent;
	}

	public Discount(Item item, int quantity, float discountPercent){
		type=2;
		this.item = item;
		this.quantity = quantity;
		this.discountPercent = discountPercent;
	}

	public Discount(float total, float discountPercent){
		type=3;
		this.total = total;
		this.discountPercent = discountPercent;
	}
}