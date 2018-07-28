package com.capgemini.core.pp.pl;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.capgemini.core.pp.beans.Customer;
import com.capgemini.core.pp.exception.InsufficientBalanceException;
import com.capgemini.core.pp.exception.InvalidInputException;
import com.capgemini.core.pp.service.WalletService;
import com.capgemini.core.pp.service.WalletServiceImpl;

public class Client {

	public static void main(String[] args) throws InvalidInputException,InsufficientBalanceException {
		WalletService service = new WalletServiceImpl();
		Customer customer;
		int choice;
		String selection;
		Scanner str = new Scanner(System.in);
		do {
			System.out.println("\nChoose among the following menu:\n ");
			System.out.println("1. Create an Account");
			System.out.println("2. Show the Balance");
			System.out.println("3. Fund Transfer");
			System.out.println("4. Deposit Fund");
			System.out.println("5. Withdraw Fund");
			System.out.println("6. Exit");
			System.out.print("Enter your choice\t:");

			choice = str.nextInt();
			String mobileNo;
			switch (choice) {
			case 1:

				System.out.print("Enter name\t:");
				String name = str.next();
				System.out.print("Enter phone number\t:");
				String mobileno = str.next();
				System.out.print("Enter amount\t:");
				BigDecimal amount=str.nextBigDecimal();
				try {
					customer = service.createAccount(name, mobileno, amount);
					if(customer==null)
						System.out.println("No account created");
					else
						System.out.println(customer);
				} catch (InvalidInputException e) {
					System.err.println(e.getMessage());
				}
				break;

			case 2:

				try {
					System.out.print("Enter mobile number\t:");
					customer = service.showBalance(str.next());
					System.out.println(customer.getWallet());
				} catch (InvalidInputException e) {
					
					System.err.println(e.getMessage());
				}
				break;

			case 3:
				try {
					System.out.print("Enter sender mobile number\t:");
					String sourceMobileNo = str.next();
					System.out.print("Enter reciever mobile number\t:");
					String targetMobileNo = str.next();
					System.out.print("Enter amount\t:");
					amount = str.nextBigDecimal();
					customer = service.fundTransfer(sourceMobileNo, targetMobileNo, amount);
					System.out.println(customer);
				} catch (InvalidInputException e) {
					System.err.println(e.getMessage());
				}catch(InsufficientBalanceException e) {
					System.err.println(e.getMessage());
				}
					
				break;

			case 4:
				try {
				System.out.print("Enter mobile phone\t:");
				mobileNo = str.next();
				System.out.print("Enter the amount to be deposited\t:");
				amount = new BigDecimal(str.next());
				customer = service.depositAmount(mobileNo, amount);
				System.out.println("Updated balance: "+customer.getWallet().getBalance());
				}
				catch(InvalidInputException e) {
					System.err.println(e.getMessage());
				}
				break;

			case 5:

				try {
					System.out.print("Enter mobile phone\t:");
					mobileNo = str.next();
					System.out.print("Enter the amount to be withdrawn\t:");
					amount = new BigDecimal(str.next());
					customer = service.withdrawAmount(mobileNo, amount);
					System.out.print(customer);
				} catch (InvalidInputException e) {
					System.err.println(e.getMessage());
				} catch(InsufficientBalanceException e) {
					System.err.println(e.getMessage());
				}
				break;

			case 6:
				System.out.println("Thank You!");
				System.exit(0);

			default:
				System.out.print("\nEnter correct choice");

			}
			System.out.print("Do you wish to continue(y/n)\t:");
			selection = str.next();
		} while (selection.equalsIgnoreCase("y")||selection.equalsIgnoreCase("yes"));
		str.close();
	}
	

}
