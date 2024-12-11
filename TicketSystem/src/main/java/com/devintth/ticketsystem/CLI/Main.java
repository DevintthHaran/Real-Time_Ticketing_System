package com.devintth.ticketsystem.CLI;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
public class Main {
    private static List<Thread> vendors=new ArrayList<>();
    private static List<Thread> customers=new ArrayList<>();
    private static TicketPool ticketPool = new TicketPool();
    private static Scanner input=new Scanner(System.in);
    public  static boolean running=false;// used to know if system is running
    public static void main (String[] args){
        SystemCon1 config=new SystemCon1();//create an object for system configuration
        ticketPool = new TicketPool();//create an object for ticketpool
        int choice;
        while(true){
            System.out.println("-----System in progress-----");
            System.out.println("1. Load Data");
            System.out.println("2. Configure System");
            System.out.println("3. Start");
            System.out.println("4. Stop");
            System.out.println("5. Store Data");
            System.out.println("6. Exit");
            while (true) {
                try {//validation for choice input to be integer
                    System.out.print("Enter choice:");
                    choice = input.nextInt();
                    while (choice < 1 || choice > 6) {//validation ot see if choice in range
                        System.out.print("Invalid choice,Please input between (1-6): ");
                        choice = input.nextInt();
                    }
                    break;
                } catch (InputMismatchException e) {// error message if choice not integer
                    System.out.println("Invalid choice,Please input an integer value.");
                    input.nextLine();
                }
            }
            switch(choice){
                case 1:
                    config=load(config);//call the load function and initialization the load file data to system configuration
                    ticketPool=new TicketPool(config.totalTickets,config.maxTicketCapacity);//initialization ticketpool using config data
                    break;

                case 2:
                    config.config();//call config data to initialize/update the system
                    ticketPool = new TicketPool(config.totalTickets,config.maxTicketCapacity);//initialization ticketpool using config data
                    System.out.println("System configured successfully.");
                    break;

                case 3:
                    if(config.firstrun){//check if system has been configured before starting the system
                        if (!running){//check if system has not running or stopped before starting the system
                            ticketPool=new TicketPool(config.totalTickets,config.maxTicketCapacity);
                            start(config.numVendor, config.numCustomer,config);//starts the thread of customer and vendor
                            System.out.println("System Started Successfully");
                        }else{
                            System.out.println("The System has Already Started");
                        }
                    }else{
                        System.out.println("The System has not been Configured.Please Configure the System to Start");
                    }
                    break;

                case 4:
                    if(running){//checks if the system is running/started before stopping the system
                        stop();//calls the stop function to interrupt the threads
                        System.out.println("System Stopped Successfully");
                    }else{
                        System.out.println("The system has already stopped");
                    }break;

                case 5:
                    store(config);//call the store function to store the configure data
                    break;
                case 6:
                    if (running){//checks if system running to display error message saying to stop the system before exiting
                        System.out.println("System is Running Stop the System to Proceed Further");
                        continue;//ignores the below function in the loop therefore the loop would not break
                    }
            }if (choice==6){break;}// breaks the loop of menu display and exits
        }
    }
    private static void start(int numVendor, int numCustomer, SystemCon1 config){
        for(int i=0;i<numVendor;i++){//creates threads for the amount of numVendor declared in configurations
            Thread vendorThread=new Thread(new Vendor(ticketPool,config.ticketReleaseRate));//creates the thread
            vendors.add(vendorThread);//adds the threads to a list for future uses
            vendorThread.start();//start the threads
        }
        for(int i=0;i<numCustomer;i++){//creates threads for the amount of numCustomer declared in configurations
            Thread customerThread=new Thread(new Customer(ticketPool,config.customerRetrievalRate));//creates the thread
            customers.add(customerThread);//adds the threads to a list for future uses
            customerThread.start();//start the threads
        }
        running=true;//change the running state to true
    }

    private static void stop(){
        for (Thread vendor : vendors) {//interrupts and stops all the vendor threads
            vendor.interrupt();
        }
        for (Thread customer : customers) {//interrupts and stops all the customer threads
            customer.interrupt();
        }
        vendors.clear();//clear the thread list of vendors
        customers.clear();//clear the thread list of customer
        running=false;//change the running state to false
    }
    private static SystemCon1 load(SystemCon1 config){
        Gson gson=new Gson();
        System.out.print("Please enter the file name to load : ");
        String filename=input.next();// filename to load  the system
        input.nextLine();
        try {//validation to check if file not found or any other error
            FileReader reader=new FileReader(filename);
            config=gson.fromJson(reader,SystemCon1.class);//load the file data to the Systemcon1 object
            System.out.println("File successfully loaded");
        } catch (IOException e) {//displays the error message
            System.out.println(e.getMessage());;
        }return config;
    }
    public static void store(SystemCon1 config){
        Gson gson=new Gson();
        System.out.print("Please enter the file name to store : ");
        String filename =input.next();
        input.nextLine();
        try {//validation to see if error storing the data
            FileWriter writer=new FileWriter(filename);
            gson.toJson(config,writer);//store the object Systemcon1 to a string format
            writer.close();//close the writer so file would be store once this method is executed
            System.out.println("System successfully stored");
        } catch (IOException e) {//displays the error message
            System.out.println("Error storing the data"+e.getMessage());;
            throw new RuntimeException(e);
        }
    }
}


