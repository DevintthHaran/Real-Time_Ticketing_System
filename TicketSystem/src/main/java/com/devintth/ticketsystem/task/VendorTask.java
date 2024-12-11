package com.devintth.ticketsystem.task;

import com.devintth.ticketsystem.service.TicketPoolService;

import java.util.concurrent.locks.ReentrantLock;

public class VendorTask implements Runnable{
    private TicketPoolService ticketPoolService;
    private int releaseRate;
    private final ReentrantLock lock=new ReentrantLock();
    private int ticketAmount;
    private boolean active=false;//used to make sure ticket amount is set

    public VendorTask(TicketPoolService ticketPoolService, int releaseRate) {//constructor
        this.ticketPoolService=ticketPoolService;
        this.releaseRate=releaseRate;
    }
    public VendorTask(int ticketAmount){
        this.ticketAmount=ticketAmount;
    }

    public void setTicketAmount(int ticketAmount) {
        lock.lock();//locks to improve synchronisation or avoid race error
        try{
            this.ticketAmount=ticketAmount;
            this.active=true;//once ticket amount set active becomes true
        }finally {
            lock.unlock();//unlocks finally before leaving the method
        }
    }
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try{
                Thread.sleep(releaseRate*1000);//elapse on each thread in seconds
                lock.lock();//locks to improve synchronisation or avoid race error
                try {
                    if (active && ticketAmount>0){//checks if ticket amount set and is above 0
                        ticketPoolService.addTicket(ticketAmount);
                    }
                }finally {
                    lock.unlock();//unlocks finally before leaving the method
                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();//interrupt the current thread if error occurs
            }
        }
    }
}
