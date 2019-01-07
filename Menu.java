import java.util.*;
import java.io.*;

class Menu implements Serializable{
	private int id = 0;
	private List<Item> allAvailableItems = new LinkedList<Item>();
	public static final long serialVersionUID = 10000L;
	
	public void addItem(String name, float price){
		Item item = new Item(++id, name, price);
		allAvailableItems.add(item);
	}

	public List<Item> getAllAvailableItems(){
		return allAvailableItems;
	}

	public void showAllItems(){
		System.out.println("-----------------------------------------------------------");
		System.out.println("Menu Items ->");
		System.out.println("Id" + "\t" + "Name" + "\t\t\t" + "Price(Rs)");
		for(Item item : allAvailableItems){
			if(item.getDisable() == false){
				if(item.getName().length() < 8)
					System.out.println(item.getId() + "\t" + item.getName() + "\t\t\t" + item.getPrice());
				else
					System.out.println(item.getId() + "\t" + item.getName() + "\t\t" + item.getPrice());
			}
		}
		System.out.println("-----------------------------------------------------------");
	}

	public Item getItem(int id){
		for(Item item : allAvailableItems){
			if(item.getId() == id && item.getDisable() == false){
				return item;
			}
		}
		return null;
	}

	// public void removeItem(int id){
	// 	for(Item item : allAvailableItems){
	// 		if(item.getId() == id){
	// 			item.disableItem();
	// 		}
	// 	}
	// }

	// public void EnableItem(int id){
	// 	for(Item item : allAvailableItems){
	// 		if(item.getId() == id){
	// 			item.enableItem();
	// 		}
	// 	}
	// }
}