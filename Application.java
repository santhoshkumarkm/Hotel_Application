import java.util.*;
import java.io.*;

class Application{
	private static List<String> commandList = new LinkedList<String>();
	private static FileOutputStream fout;
	private static ObjectOutputStream oout;
	private static int MAX_TABLE = 100;
	private static Table[] tableList = new Table[100];
	private static Table activeTable;
	public static File menuFile = new File("/Users/santhosh-pt2425/Documents/Hotel_Application/Menu.txt"), orderFile = new File("/Users/santhosh-pt2425/Documents/Hotel_Application/Order.txt");
	public static Menu menu;
	public static OrderList orderList;
	public static List<Discount> discountList = new LinkedList<Discount>();
	public static float taxPercent = 10;
	

	private static void createFiles() throws Exception{
		if(menuFile.exists() && menuFile.length()>0){
			menu = (Menu)ApplicationUtilities.readFile(menuFile);
		}else{
			menu = new Menu();
			System.out.println("(Caution: No items in menu. Add Items to menu first.)");	
		}
		if(orderFile.exists() && orderFile.length()>0){
			orderList = (OrderList)ApplicationUtilities.readFile(orderFile);
		}
		else{
			orderList = new OrderList();	
		}
	}

	private static boolean calcActiveTables(){
		System.out.println("-----------------------------------------------------------");
		System.out.println("Active tables: ");
		boolean activePresent = false;
		for(Table t : tableList){
			if(t != null && t.getActiveStatus() == true){
				System.out.println("Table " + t.getId() + " (Ph.no -> " + t.getPhoneNumber() + ")");
				activePresent = true;
			}
		}
		if(!activePresent){
			System.out.println("None!");
			return false;
		}else
			return true;
	}

	private static void addNewTable() throws Exception{
		int tableNumber = 0;
		for(int i = 0; i < 100; i++){
			if(tableList[i] == null || tableList[i].getActiveStatus() == false){
				tableNumber = i+1;
				break;
			}
		}
		String phoneNumber = ApplicationUtilities.inputString("phoneNumber", "[789](\\d){9}", 10, 10);
		Table table = new Table(tableNumber, phoneNumber);
		tableList[tableNumber-1] =table;
	}

	private static void selectTable(boolean isActivePresent) throws Exception{
		if(isActivePresent){
			int activeTableNumber = ApplicationUtilities.inputInt("table number", 1, 100);
			boolean flag = true;
			for(Table t : tableList){
				if(t != null && t.getId() == activeTableNumber && t.getActiveStatus() == true){
					activeTable = t;
					flag=false;
					break;
				}
			}
			if(flag != true)
				activeTable.tableMain();
			else
				System.out.println("Table not active.");
		}else
			System.out.println("No tables active.");
	}

	private static void addNewItem() throws Exception{
		String newItemName = ApplicationUtilities.inputString("name of the item (Max char=15)", "(.)*?(\\D)(.)*?", 1 , 15);
		float newItemPrice = ApplicationUtilities.inputFloat("price of the item", 1f, Float.MAX_VALUE);
		menu.addItem(newItemName, newItemPrice);
		System.out.println("New Item added.");
		ApplicationUtilities.writeFile(menuFile,menu);
	}

	private static void showOrderHistory() throws Exception{
		if(orderFile.exists() && orderFile.length()>0){
			orderList = (OrderList)ApplicationUtilities.readFile(orderFile);

			List<String> orderHistoryList = new LinkedList<String>();
			orderHistoryList.add("List orders in a given date range");
			orderHistoryList.add("List all orders");
			orderHistoryList.add("List orders based on item");
			int selectedValue = ApplicationUtilities.selectOption(orderHistoryList);

			boolean objectListFlag = false;
			if(selectedValue == 1){
				Date startDate = ApplicationUtilities.inputDate("Starting date");
				Date endDate = ApplicationUtilities.inputDate("Ending date");
				System.out.println("-----------------------------------------------------------");
				for(Order o : orderList.getOrderList()){
					if(o.getDate().after(startDate) && o.getDate().before(endDate)){
						objectListFlag = true;
						o.generate("Bill History", taxPercent, discountList);
						System.out.println();
					}
				}
			}else if(selectedValue == 2){
				for(Order o : orderList.getOrderList()){
					objectListFlag = true;
					o.generate("Bill History", taxPercent, discountList);
					System.out.println();
				}
			}else if(selectedValue == 3){
				int searchId;
				Item item = ApplicationUtilities.inputItem(menu);
				for(Order o : orderList.getOrderList()){
					List<Item> tempItemList = o.getItemList();
					for(Item i : tempItemList){
						if(i.getName().equals(item.getName())){
							objectListFlag = true;
							o.generate("Bill History", taxPercent, discountList);
							System.out.println();
						}
					}
				}
			}
			if(!objectListFlag)
				System.out.println("No record to display.");
		}else{
			System.out.println("No record to display.");
		}
	}

	private static void addDiscount(){
		Discount discount;
		List<String> tempCommandList = new LinkedList<String>();
		tempCommandList.add("Discount if a particular item is bought, no matter the quantity");
		tempCommandList.add("Discount if a particular item above a given quantity is bought");
		tempCommandList.add("Discount if the total cost crosses a given threshold");
		int selectedValue=ApplicationUtilities.selectOption(tempCommandList);
		switch(selectedValue){
			case 1:{
				Item item = ApplicationUtilities.inputItem(menu);
				float discountPercent = ApplicationUtilities.inputFloat("discount percentage", 0.001f, 99.99f);
				discount = new Discount(item, discountPercent);
				discountList.add(discount);
				break;
			}
			case 2:{
				Item item = ApplicationUtilities.inputItem(menu);
				int quantity = ApplicationUtilities.inputInt("quantity", 1, Integer.MAX_VALUE);
				float discountPercent = ApplicationUtilities.inputFloat("discount percentage", 0.001f, 99.99f);
				discount = new Discount(item, quantity, discountPercent);
				discountList.add(discount);
				break;
			}
			case 3:{
				float total = ApplicationUtilities.inputFloat("total", 0.001f, Float.MAX_VALUE);
				float discountPercent = ApplicationUtilities.inputFloat("discount percentage", 0.001f, 99.99f);
				discount = new Discount(total, discountPercent);
				discountList.add(discount);
			}
		}
	}

	public static void main(String[] args) throws Exception{
		commandList.add("Add new table");
		commandList.add("Select table");
		commandList.add("Add items to menu");
		commandList.add("List order history");
		commandList.add("Add discount");

		createFiles();

		System.out.println("-----------------------------------------------------------");
		System.out.println("Welcome to ZOHO Hotel!");
		System.out.println("-----------------------------------------------------------");

		while(true){
			boolean isActivePresent = calcActiveTables();
			int selectedValue = ApplicationUtilities.selectOption(commandList);
			switch (selectedValue){
				case 1:{
					addNewTable();
					break;
				}
				case 2:{
					selectTable(isActivePresent);
					break;
				}
				case 3:{
					addNewItem();
					break;
				}
				case 4:{
					showOrderHistory();
					break;
				}
				case 5:{
					addDiscount();
				}
			}
		}
	}
}