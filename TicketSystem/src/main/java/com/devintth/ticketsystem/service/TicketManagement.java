package com.devintth.ticketsystem.service;


import com.devintth.ticketsystem.task.CustomerTask;
import com.devintth.ticketsystem.task.VendorTask;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service//service layer
public class TicketManagement {
    private final TicketPoolService ticketService;
    private final ConcurrentLinkedQueue<Thread> vendorThreads=new ConcurrentLinkedQueue<>();//thread list to store vendor threads
    private final ConcurrentLinkedQueue<Thread> customerThreads=new ConcurrentLinkedQueue<>();//thread list to store customer threads
    private final ConcurrentLinkedQueue<VendorTask> vendorTasks=new ConcurrentLinkedQueue<>();//object list to store vendor object
    private final ConcurrentLinkedQueue<CustomerTask> customerTasks=new ConcurrentLinkedQueue<>();//object list to store customer object
    private int numVendor;
    private int numCustomer;
    private int releaseRate;
    private int retrievalRate;
    private int vendorAmount;
    private int customerAmount;


    public TicketManagement(TicketPoolService ticketService) {//constructor
        this.ticketService = ticketService;
    }

    public void configureSystem(int numTickets, int maxTicket){
        ticketService.Systemconfig(numTickets,maxTicket);//send data to create the ticket pool
        System.out.println("System configuration successfully with max capacity: " + maxTicket +", total tickets: "+numTickets);
    }
    public void createVendor(int numVendor,int releaseRate){
        this.numVendor=numVendor;
        this.releaseRate=releaseRate;
        for(int i=0;i< numVendor;i++){//creates threads of the amount of numVendor
            VendorTask vendorTask=new VendorTask(ticketService,releaseRate);//initialises the specific vendor thread
            Thread vendorThread=new Thread(vendorTask,"Vendor "+i);//create vendor thread
            vendorTasks.add(vendorTask);//store the task in a list
            vendorThreads.add(vendorThread);//store thread in a list to improve the synchronization and future uses
        }
        System.out.println("Vendor task created");
    }
    public void createCustomer(int numCustomer,int retrievalRate){
        this.numCustomer=numCustomer;
        this.retrievalRate=retrievalRate;
        for(int i=0;i< numCustomer;i++){//creates threads of the amount of numCustomer
            CustomerTask customerTask=new CustomerTask(ticketService,retrievalRate);//initialises the specific customer thread
            Thread customerThread=new Thread(customerTask,"Customer "+i);//create customer thread
            customerTasks.add(customerTask);//store the task in a list
            customerThreads.add(customerThread);//store thread in a list to improve the synchronisation and future uses
        }
        System.out.println("Customer task created");
    }

    public void setVendorTicketAmount(int ticketAmount){// set ticket amount for vendor
        vendorAmount=ticketAmount;
        System.out.println("Vendor amount set to "+ticketAmount);
        vendorTasks.forEach(task -> task.setTicketAmount(ticketAmount));//set the value for each vendor tasks in ConcurrentLinkedQueue even when thread is running
        System.out.println("Vendor added"+ ticketAmount +" ticket successfully");
    }

    public void setCustomerTicketAmount(int ticketAmount){// set ticket amount for customer
        customerAmount=ticketAmount;
        System.out.println("Customer amount set to "+ticketAmount);
        customerTasks.forEach(task -> task.setTicketAmount(ticketAmount));//set the value for each customer tasks in ConcurrentLinkedQueue  even when thread is running
        System.out.println("Customer purchased"+ ticketAmount +" ticket successfully");
    }
    public void start(){//start all threads created
        vendorThreads.forEach(Thread::start);
        customerThreads.forEach(Thread::start);
        System.out.println("System has started successfully");
    }
    public void stop(){//stop all thread created
        vendorThreads.forEach(Thread::interrupt);
        customerThreads.forEach(Thread::interrupt);
        //clear all list
        vendorThreads.clear();
        customerThreads.clear();
        vendorTasks.clear();
        customerTasks.clear();
        System.out.println("System has stopped successfully");
    }public String useState(){//the string of the data used to being showed in use state
        return "\n"+"Number of Vendor Thread: "+numVendor+"\n"+"Release Rate: "+releaseRate+"\n"+"Number of Customer Thread: "+numCustomer+"\n"+"Retrieval Rate: "+retrievalRate+"\n"+"Number of Vendor Ticket Amount: "+vendorAmount+"\n"+"Number of Customer Ticket Amount: "+customerAmount;
    }
}
