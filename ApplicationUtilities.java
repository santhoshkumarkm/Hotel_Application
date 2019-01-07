import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

class ApplicationUtilities{
	public static Scanner scan = new Scanner(System.in).useDelimiter("\n");
	private static FileInputStream fin;
	private static ObjectInputStream oin;
	private static FileOutputStream fout;
	private static ObjectOutputStream oout;

	public static Object readFile(File file) throws Exception{
		fin = new FileInputStream(file);
		oin = new ObjectInputStream(fin);
		Object object = oin.readObject();
		if(oin != null)
			oin.close();
		if(fin != null)
			fin.close();
		return object;
	}

	public static void writeFile(File file, Object object) throws Exception{
		fout = new FileOutputStream(file);
		oout = new ObjectOutputStream(fout);
		oout.writeObject(object);
		if(fout != null)
			fout.close();
		if(oout != null)
			oout.close();
	}

	private static boolean isDateValid(String date){
		if( date.matches("(0?[1-9]|1[0-9]|2[0-9]|30)/(0?[469]|11)/(\\d){4}")
			|| date.matches("(0?[1-9]|1[0-9]|2[0-9]|3[01])/(0?[13578]|1[02])/(\\d){4}")
			|| date.matches("(0?[1-9]|1[0-9]|2[0-8])/0?2/(\\d){4}") )
			return true;
		if(date.matches("29/0?2/(\\d){4}")){
			int year = Integer.valueOf(date.substring(date.length()-4));
			if(year%4==0 && year%100!=0)
				return true;
			if(year%100==0 && year%400==0)
				return true;
		}
		return false;
	}

	public static Date inputDate(String name) throws Exception{
		String dateText;
		Date date;
		while(true){
			System.out.println("Enter " + name + " in \"dd/MM/yyyy\" format");
			if(scan.hasNext()){
				dateText = scan.next();
				if(isDateValid(dateText)){
					date = new SimpleDateFormat("dd/MM/yyyy").parse(dateText);
					return date;
				}
			}
			System.out.println("Wrong format.");
		}
	}

	public static String inputString(String name, String match, int minLength, int maxLength){
		String string;
		while(true){
			System.out.println("Enter " + name);
			if(scan.hasNext()){
				string=scan.next();
				if(string.length() >= minLength && string.length() <= maxLength && string.matches(match)){
					break;
				}
			}
			System.out.println("Invalid input");
		}
		return string;
	}

	public static int inputInt(String name, int min, int max){
		int number;
		while(true){
			System.out.println("Enter " + name);
			if(scan.hasNextInt()){
				number = scan.nextInt();
				if(number >= min && number <= max)
					break;
				else
					System.out.println(name + " should be between " + min + " and " + max + ".");
			}else{
				scan.next();
				System.out.println("Invalid input.");
			}
		}
		return number;
	}

	public static float inputFloat(String name, float min, float max){
		float number;
		while(true){
			System.out.println("Enter " + name);
			if(scan.hasNextFloat()){
				number = scan.nextFloat();
				if(number >= min && number <= max)
					break;
				else
					System.out.println(name + " should be between " + min + " and " + max + ".");
			}else{
				scan.next();
				System.out.println("Invalid input.");
			}
		}
		return number;
	}

	public static Item inputItem(Menu menu){
		menu.showAllItems();
		int searchId;
		Item item;
		while(true){
			System.out.println("Enter item id");
			if(scan.hasNextInt()){
				searchId = scan.nextInt();
				item = menu.getItem(searchId);
				if(item == null){
					System.out.println("Please choose correctly.");
					continue;
				}else{
					break;
				}
			}else{
				scan.next();
				System.out.println("Invalid input.");
			}
		}
		System.out.println(item.getName() + " selected.");
		return item;
	}

	public static char inputChar(String name, char[] check){
		while(true){
			System.out.println("Enter " + name);
			if(scan.hasNext()){
				String temp = scan.next();
				if(temp.length() == 1)
					for(char c : check)
						if(c == temp.charAt(0))
							return c;
			System.out.println("Wrong input. Enter value correctly.");
			}
		}
	}

	public static int selectOption(List<String> list){
		int selectedValue = 0;
		while(true){
			System.out.println("-----------------------------------------------------------");
			System.out.println("Press any of the below number to begin");
			int index = 0;
			for(String a : list){
				System.out.println((++index)+". "+a);
			}
			System.out.println("-----------------------------------------------------------");
			if(scan.hasNextInt()){
				selectedValue = scan.nextInt();
				if(selectedValue <= 0 || selectedValue > index){
					System.out.println("Wrong input. Enter value correctly.");
					continue;
				}
				else{
					System.out.println("\""+list.get(selectedValue-1)+"\" selected.");
					System.out.println("-----------------------------------------------------------");
					break;
				}
			}
			else{
				scan.next();
				System.out.println("Wrong input. Enter value correctly.");
				continue;
			}
		}
		return selectedValue;
	}
	
}