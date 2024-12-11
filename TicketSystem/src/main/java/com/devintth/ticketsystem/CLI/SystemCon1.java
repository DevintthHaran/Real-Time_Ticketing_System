package com.devintth.ticketsystem.CLI;

import java.util.InputMismatchException;
import java.util.Scanner;
public class SystemCon1 {
    boolean firstrun = false;
    public int totalTickets;
    public int ticketReleaseRate;
    public int customerRetrievalRate;
    public int maxTicketCapacity;
    public int numVendor;
    public int numCustomer;
    public SystemCon1(){//default constructor used to create object

    }
    //constructor to get in details/data
    public SystemCon1(boolean firstrun, int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity, int numVendor, int numCustomer) {
        this.firstrun=firstrun;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
        this.numVendor = numVendor;
        this.numCustomer = numCustomer;
    }

    public void config(){
        Scanner input = new Scanner(System.in);
        int choice;
        if (!firstrun){//checks if the system has not configured even once
            System.out.println("Please input the system configuration to proceed");
            totalTickets=validInput("Enter the Total number of ticket: ");
            ticketReleaseRate=validInput("Enter the Ticket Release Rate(s): ");
            customerRetrievalRate=validInput("Enter the Customer Retrieval Rate(s): ");
            maxTicketCapacity=validInput("Enter the Max Ticket Capacity: ",totalTickets);//checks if max capacity is above the current ticket in pool
            numVendor=validInput("Enter the Number of Vendors allowed at a time: ");
            numCustomer=validInput("Enter the number of Customers allowed at a time: ");
            firstrun=true;//changes the first run to true
        }else{
            while (true){
                System.out.println("----Update System Configuration----");
                System.out.println("1.Total Tickets");
                System.out.println("2.Ticket Release Rate");
                System.out.println("3.Customer Retrieval Rate");
                System.out.println("4.Max Ticket Capacity");
                System.out.println("5.Number of Vendors");
                System.out.println("6.Number of Customers");
                System.out.println("7.Exit the menu and Run the System");
                while (true) {
                    try {//validation of choice being an integer
                        System.out.print("Enter choice:");
                        choice = input.nextInt();
                        while (choice < 1 || choice > 7) {//checks if choice in range
                            System.out.print("Invalid choice,Please input between (1-7):");
                            choice = input.nextInt();
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid choice,Please input an integer value.");
                        input.nextLine();
                    }
                }
                switch (choice) {
                    case 1:
                        totalTickets=validInput("Enter the Total number of ticket: ");;
                        break;
                    case 2:
                        ticketReleaseRate=validInput("Enter the Ticket Release Rate: ");
                        break;
                    case 3:
                        customerRetrievalRate=validInput("Enter the Customer Retrieval Rate: ");
                        break;
                    case 4:
                        maxTicketCapacity=validInput("Enter the Max Ticket Capacity: ",totalTickets);//checks if max capacity is above the current ticket in pool
                        break;
                    case 5:
                        numVendor=validInput("Enter the Number of Vendors allowed at a time: ");
                        break;
                    case 6:
                        numCustomer=validInput("Enter the number of Customers allowed at a time: ");
                }if (choice == 7) {
                    break;
                }

            }

        }
    }
    public static int validInput(String prompt){//validation methods
        Scanner input = new Scanner(System.in);
        int value;
        while (true) {
            try {//validation to see if value is integer
                System.out.print(prompt);//displays the prompt message
                value = input.nextInt();
                while (value < 1 ) {//checks if value above 0
                    System.out.print("Invalid input,Please input positive integer (greater than 0): ");
                    value = input.nextInt();
                }
                break;//breaks the loop if a valid input given
            } catch (InputMismatchException e) {
                System.out.println("Invalid input,Please input an integer value.");
                input.nextLine();//clears the buffer
            }
        }
        return value;
    }
    public static int validInput(String prompt,int min){//validation methods for max capacity
        Scanner input = new Scanner(System.in);
        int value;
        while (true) {
            try {//validation to see if value is integer
                System.out.print(prompt);//displays the prompt message
                value = input.nextInt();
                while (value < min) {//validation to make sure value above min
                    System.out.print("Invalid input,Please input number greater than or equal value total ticket available "+min+ ": " );
                    value = input.nextInt();
                }
                break;//breaks the loop if a valid input given
            } catch (InputMismatchException e) {
                System.out.println("Invalid input,Please input an integer value.");
                input.nextLine();//clears the buffer
            }
        }
        return value;


    }
}


