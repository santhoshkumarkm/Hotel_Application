import java.util.*;

class Table{
	private boolean activeStatus;
	private int id;
	private Order order;
	String phoneNumber;

	public Table(int id, String phoneNumber){
		this.id = id;
		this.phoneNumber = phoneNumber;
		activeStatus = true;
		order = new Order(phoneNumber);
	}

	public boolean getActiveStatus(){
		return activeStatus;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public int getId(){
		return id;
	}
	
	private void newOrder(){
		if(!Application.menu.getAllAvailableItems().isEmpty()){
			Order tempOrder = new Order();
			while(true){
				Item item = ApplicationUtilities.inputItem(Application.menu);
				int itemQuantity = ApplicationUtilities.inputInt("quantity", 1, Integer.MAX_VALUE);
				tempOrder.addItemsToOrder(item,itemQuantity);
				tempOrder.generate("Items in order", Application.taxPercent, Application.discountList);
				char[] checkArray = {'P','p','A','a','C','c'};
				char option =ApplicationUtilities.inputChar("P to place order; A to add another item; C to cancel order",checkArray);
				if(option =='A' || option =='a'){
					continue;
				}else if (option=='P' || option=='p'){
					System.out.println("Order placed");
					int length = tempOrder.getItemList().size();
					for(int i = 0 ; i < length ; i++){
						order.addItemsToOrder(tempOrder.getItemList().get(i),tempOrder.getQuantityList().get(i));
					}
					break;
				}else{
					break;
				}
			}
		}else{
				System.out.println("No items available in menu.");
		}
	}

	private void finaliseOrder() throws Exception{
		System.out.println("Order finalised");
		order.generate("Bill", Application.taxPercent, Application.discountList);
		Application.orderList.addOrder(order);
		ApplicationUtilities.writeFile(Application.orderFile, Application.orderList);
		activeStatus = false;
	}

	public void tableMain() throws Exception{
		List<String> tableCommand = new LinkedList<String>();
		tableCommand.add("Add Order");
		tableCommand.add("Finalise Order");
		tableCommand.add("Temporary save");
		while(true){
			int selectedValue = ApplicationUtilities.selectOption(tableCommand);
			if(selectedValue == 1){
				this.newOrder();
			}else if(selectedValue == 2){
				this.finaliseOrder();
				break;
			}else if(selectedValue == 3){
				break;
			}
		}
	}
}