import java.io.*;

class Item implements Serializable{
	private int id;
	private String name;
	private float price;
	public static final long serialVersionUID = 10003L;

	public Item(int id, String name, float price){
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public int getId(){
		return id;
	}
	public String getName(){
		return name;
	}
	public float getPrice(){
		return price;
	}

	public boolean equals(Item item){
		if(item.getId() == this.getId())
			return true;
		return false;
	}
}