package com.devintth.ticketsystem.CLI;



public class Customer implements Runnable {// uses runnable method of the thread class
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;

    public Customer(TicketPool ticketPool, int customerRetrievalRate) {//constructor
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try{
                Thread.sleep(customerRetrievalRate*1000);//delay time when each thread is executed in seconds
                ticketPool.removeTicket(1);//removes the ticket from the pool
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}


