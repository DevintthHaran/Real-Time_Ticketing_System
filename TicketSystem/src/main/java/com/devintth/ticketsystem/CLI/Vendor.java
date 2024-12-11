package com.devintth.ticketsystem.CLI;


public class Vendor implements Runnable {// uses runnable method of the thread class
    private final TicketPool ticketPool;
    private final int ticketReleaseRate;

    public Vendor(TicketPool ticketPool, int ticketReleaseRate){//constructor
        this.ticketPool=ticketPool;
        this.ticketReleaseRate=ticketReleaseRate;
    }
    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                Thread.sleep(ticketReleaseRate*1000);//delay time when each thread is executed in seconds
                ticketPool.addTicket(1);//adds the ticket from the pool
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}


