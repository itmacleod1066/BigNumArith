import java.io.*;
//Basic Stack implementation 
//This will hold each operand, pushing and popping as required
class Stack{  
	//Array implementation 
	private String[] array;
	//represents the location of top item
	private int top;
	
	/**
	 * Stack Constructor
	 * @param size size to set the array
	 */
	public Stack(int size) {
		array = new String[size]; 
		top = -1;   //Sets top to -1 upon initialization
	}
	
	/**
	 * Method that pushes a new item to the stack
	 * @param e item that will be pushed onto the stack
	 */
	public void push(String e) {
		array[top + 1] = e;
		top++;
	}
	
	/**
	 * Method that returns the top most item on the stack
	 * @return top most item on the stack
	 */
	public String pop() {
		String e = array[top];
		top--; 
		return e;    
	} 
	
	/**
	 * Simple check to see if the stack is empty or not
	 * @return whether or not the stack is empty
	 */
	public boolean isEmpty() {
		return top == -1;
	} 
	
} 
/**
 * Big Num Arithmetic
 * CPSC 340 Project 1
 * @author Ian MacLeod
 * @Date 9/26/21
 * @version 5.0   
 */
public class BigNumArithmetic {

	public static void main(String[] args) {
		
		//Reads in the file
		File file = new File(args[0]); 
		
		try { 
			//Use BufferedReader to read in the file
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String line = "";
			StringBuilder sb = new StringBuilder();
			while((line = bf.readLine()) != null) { //While there are still lines to be read in
				line = line.trim(); //trims any whitespace
				
				if(line.length() != 0) { //if there is actually something to read in
					String[] lineAsArr = line.split(" "); //split the line to create an array where each element is an operand or operator
					String[] lineAsOperation = format(lineAsArr); //call the utility function format() to properly format the line
					
					for(int i = 0; i < lineAsOperation.length; i++) {
						System.out.print(lineAsOperation[i] + " ");
					}
					
					
					System.out.print("= ");
					
					//Calls the evalRPN function to do Reverse Polish Notation on the newly formatted line
					System.out.println(evalRPN(lineAsOperation));
					
					
				}
			
			}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Method that correctly formats the expression by getting rid of whitespaces
	 * and adding appropriate spacing between each operand
	 * @param lineAsArr String array that represents the unformatted expression
	 * @return formatted expression as an array
	 */
	public static String[] format(String[] lineAsArr) {
		StringBuilder sb = new StringBuilder(); //Will hold the formatted expression as one long string
		
		for(int i =0; i < lineAsArr.length; i++) {
			//if there are actual numbers in the file line, add the formatted number to sb
			//if i == lineAsArr.length - 1, then we don't want the extra " " at the end
			if(i != lineAsArr.length - 1 && lineAsArr[i].length() != 0) {
				sb.append(removeZeros(lineAsArr[i].trim()) + " "); //calls the utility function removeZeros()
			}else {
				sb.append(lineAsArr[i].trim());
			}
		}
		
		String opStr = sb.toString();
		return opStr.split(" ");
	}
	
	/**
	 * Method that takes in a num as a String and removes any leading zeros
	 * @param num number to removes leading zeros from
	 * @return number formatted correctly 
	 */
	public static String removeZeros(String num) {
		if(num.equals("0")) { //If the number itself is just 0, we want to keep that
			return num;
		}
		
		int countZeros = 0; //keeps track of the number of leading zeros
		
		//Loop through the String, incrementing the zero counter each time a zero 
		//is encountered. Once the first char is not a zero, we know the actual number 
		//has started and all other zeros are fair game, so we break from the loop
		//and cease counting
		for(int i =0 ; i < num.length(); i++) {
			if(num.charAt(i) == '0') {
				countZeros++;
			}else {
				break;
			}
		}
		
		if(countZeros == num.length()) {
			return "0";
		}
		num = num.substring(countZeros); //returns the string without all the leading zeros in front
		return num;
	}
	
	/**
	 * Method that reverses a String. This will be used during the multiplication step, as multiplication results will
	 * be incrementally added to a StringBuilder object in reverse via the append method. This method will reverse that StringBuilder
	 * object, and use it as one of the parameters in the addition method
	 * @param str String to be reversed
	 * @return The reversed String
	 */
	public static String reverse(String str) {
		int l = 0;
		int r = str.length() - 1;
		char[] strAsArr = new char[str.length()];
		for(int i = 0; i < str.length(); i++) {
			strAsArr[i] = str.charAt(i);
		}
		while(l <= r) {
			char temp = strAsArr[l];
			strAsArr[l] = strAsArr[r];
			strAsArr[r] = temp;
			l++;
			r--;
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < str.length(); i++) {
			sb.append(strAsArr[i]);
		}
		
		return sb.toString();
	}
	
	/**
	 * Method that evaluates an expression via Reverse Polish Notation
	 * @param tokens Operators and Operands as Strings
	 * @return result The final result of the expression
	 */
	public static String evalRPN(String[] tokens) {
		try {
			//Create the stack
			Stack stack = new Stack(tokens.length); 
			
			for(int i = 0; i < tokens.length; i++) { 
				//If the token doesn't equal an operator, push it onto the stack as a String
				if(!tokens[i].equals("+") && !tokens[i].equals("-") && !tokens[i].equals("*") && !tokens[i].equals("^")) {
					stack.push(tokens[i]);
				}else if(tokens[i].equals("+")) { //if the token is a "+", then pop the two numbers off the stack and perform addition
					
					String num1 = stack.pop();
					String num2 = stack.pop();
					
					stack.push(addition(num1, num2)); //pushes the result of addition() back to the stack
					
				}else if(tokens[i].equals("*")) {
					String num1 = stack.pop();
					String num2 = stack.pop();
					
					stack.push(multiply(num1, num2));
				}
				
				else if(tokens[i].equals("^")) {
					String num1 = stack.pop();
					String num2 = stack.pop();
					
					stack.push(expo(num2, num1));
				}
				
				
				
			}
			
			String result = stack.pop();
			if(!stack.isEmpty()) {
				return "";
			}
			
			return result;
		}catch(ArrayIndexOutOfBoundsException e) {
			return "";
		}
        
    }
	
	/**
	 * Method that adds two linkedlists together 
	 * @param num1 First operand as a string
	 * @param num2 Second operand as a string
	 * @return the new number as a string to be pushed back to the stack
	 */
	public static String addition(String num1, String num2) {
		
		//Converts num1 to a linkedlist
		LinkedList<Integer> ls = new LinkedList<>();
		for(int j = num1.length() - 1; j >= 0; j--) {
			ls.append(Integer.parseInt(String.valueOf(num1.charAt(j))));
		}
		
		//Converts num2 to a linkedlist
		LinkedList<Integer> ls2 = new LinkedList<>();
		for(int x = num2.length() - 1; x >= 0; x--) {
			ls2.append(Integer.parseInt(String.valueOf(num2.charAt(x))));
		}
		
		//Initialize a new LinkedList to hold the sum
		LinkedList<Integer> sum = new LinkedList<>();
		
		//moves both curr pointers to first element
		ls.moveToStart();
		ls2.moveToStart();
		
		int carry = 0; //represents the number that will need to be carried over during addition
		
		//While neither linkedlist pointer is at the end...
		while(ls.isAtEnd() != true && ls2.isAtEnd() != true) {
			
			//Add the two numbers (plus the carry value if applicable)
			int sumOfNums = ls.getCurr().getElement() + ls2.getCurr().getElement() + carry;
			
			//If the sum is less than 10, keep carry at 0
			if(sumOfNums < 10) {
				carry = 0;
			}
			
			//If the sum of the two numbers are greater than or equal to 10
			if(sumOfNums >= 10) {
				carry = 1; //change carry to a 1
	
				sumOfNums = sumOfNums - 10; //subtract from the sum 10; carry will hold the 10's place and 
				//sumOfNums will hold the one's place
				
				sum.insert(sumOfNums); //insert sumOfNums
				ls.next(); //move pointer to the next digit of ls
				ls2.next(); //move pointer to the next digit of ls2
				sumOfNums = 0; //reinitialize sumOfNums back to 0
			}else {
				//If the sum wasn't >= 10
				sum.insert(sumOfNums); //simply insert the sum into the sum linkedlist
				ls.next(); //increment
				ls2.next(); //increment
				sumOfNums = 0; //reinitialize sumOfNums back to 0
			}
			
		}
		
		//If the first number has reached its end
		if(ls.isAtEnd()) {
			while(!ls2.isAtEnd()) { //while the second number hasn't reached its end
				if(ls2.getCurr().getElement() + carry >= 10) {
					sum.insert(ls2.getCurr().getElement() + carry - 10);
					carry = 1;
				}else {
					sum.insert(ls2.getCurr().getElement() + carry);
					carry = 0;
				}
				
				ls2.next();
			}
		}else if(ls2.isAtEnd()) {
			while(!ls.isAtEnd()) {
				if(ls.getCurr().getElement() + carry >= 10) {
					sum.insert(ls.getCurr().getElement() + carry - 10);
					carry = 1;
				}else {
					sum.insert(ls.getCurr().getElement() + carry);
					carry = 0;
				}
				
				ls.next();
				
			}
		}
		
		//If carry still has a value in it other than 0 after everything, then simply add carry to sum as a new node
		if(carry != 0) {
			sum.insert(carry);
		}
		
		
		//Converts the sum linkedlist to a String to be returned 
		
		StringBuilder sumAsStr = new StringBuilder();
		
		sum.moveToStart();
		
		while(!sum.isAtEnd()) {
			sumAsStr.append(sum.getCurr().getElement());
			sum.next();
		}
		
		
		return sumAsStr.toString();
	}
	
	/**
	 * Method that multiplies two Strings by converting to LinkedLists and multiplying each node together
	 * @param num1 The first number to be multiplied
	 * @param num2 The second number to be multiplied
	 * @return The product as a String to be put back onto the stack
	 */
	public static String multiply(String num1, String num2) {
		
		//we want the smaller of the two numbers to be at the bottom of the multiplication
		if(num2.length() > num1.length()) {
			String temp = num1;
			num1 = num2;
			num2 = temp;
		}
		
		//Convert one of the numbers to a linked list
		LinkedList<Integer> ls1 = new LinkedList<>();
		for(int j = num1.length() - 1; j >= 0; j--) {
			ls1.append(Integer.parseInt(String.valueOf(num1.charAt(j))));
		}
		
		//Convert the other number to a linked list as well
		LinkedList<Integer> ls2 = new LinkedList<>();
		for(int j = num2.length() - 1; j >= 0; j--) {
			ls2.append(Integer.parseInt(String.valueOf(num2.charAt(j))));
		}
		
		//This checks that if we multiply by 0, then 0 is returned
		//For example, two linkedlists of null -> 7 -> 6 -> 5 -> * null -> 0 -> null will return 0
		if(ls2.length() == 1 && ls2.getCurr().getElement() == 0) {
			return "0";
		}
		
		//This variable will continuously update the summing of two numbers during the multiplication step
		String result = "0";
		
		int carry = 0;
		int placeCounter = 0; //will keep track of how many zeros needed to be tacked on during each new step
		StringBuilder sb = new StringBuilder();
		while(!ls2.isAtEnd()) {
			while(!ls1.isAtEnd()) {
				//multiply the two elements, adding any carry value if needed
				int product = ls2.getCurr().getElement() * ls1.getCurr().getElement() + carry;
				//if the product is less than 10, then carry is still 0 
				//simply append the product to sb
				if(product < 10) {
					sb.append(ls2.getCurr().getElement() * ls1.getCurr().getElement() + carry);
					carry = 0;
				//otherwise, append the second character of the String product
				//and put the first char as an int into carry
				}else {
					sb.append(String.valueOf(product).charAt(String.valueOf(product).length() - 1));
					
					carry = Integer.parseInt(String.valueOf(product).charAt(0) + "");
					
				}
				
				//move pointer and repeat until ls1 is at its end
				ls1.next();
				
			}
			//Move ls2 to its next node
			ls2.next();
			if(ls2.isAtEnd()) {
				if(carry != 0) {
					sb.append(carry); //add whatever is in carry to sb
					carry = 0;
					result = addition(result, reverse(sb.toString()));
				}else {
					result = addition(result, reverse(sb.toString()));
				}
			}
			
			if(!ls2.isAtEnd()) {
				if(carry != 0) {
					sb.append(carry);
					result = addition(result, reverse(sb.toString()));
					carry = 0;
				}else {
					result = addition(result, reverse(sb.toString()));
				}
				
				placeCounter++;
				ls1.moveToStart();
				
				sb = new StringBuilder();
				
				//Adds the correct amount of trailing zeros
				for(int i = 0; i < placeCounter; i++) {
					sb.append(0);
				}
				
			}  
			
		}
		
		if(carry != 0) {
			sb.append(carry);
		}
		
		return result;
	}
	
	/**
	 * Method that calculates the exponent result using the exponentiation by squaring recursive algorithm
	 * @param x Number to be exponentially increased
	 * @param n exponent
	 * @return result as a String
	 */
	public static String expo(String x, String n) {
		//Base cases
		if(n.equals("1")) return x;
		if(n.equals("0")) return "1";
		
		//Recursive Case
		if(Integer.parseInt(n) % 2 == 0) {
			return expo(multiply(x, x), Integer.parseInt(n) / 2 + "");
		}else {
			return multiply(x, expo(multiply(x, x), ((Integer.parseInt(n) - 1) / 2) + ""));
		}
		
		
	}
}
