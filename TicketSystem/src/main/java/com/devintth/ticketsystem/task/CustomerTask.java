package com.devintth.ticketsystem.task;

import com.devintth.ticketsystem.service.TicketPoolService;

import java.util.concurrent.locks.ReentrantLock;

public class CustomerTask implements Runnable{
    private TicketPoolService ticketPoolService;
    private int retrievalRate;
    private final ReentrantLock lock=new ReentrantLock();
    private int ticketAmount;
    private boolean active=false;//used to make sure ticket amount is set

    public CustomerTask(TicketPoolService ticketPoolService, int retrievalRate) {//constructor
        this.ticketPoolService=ticketPoolService;
        this.retrievalRate=retrievalRate;
    }
    public CustomerTask(int ticketAmount){
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
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(retrievalRate*1000);//elapse on each thread in seconds
                lock.lock();//locks to improve synchronisation or avoid race error
                try {
                    if (active && ticketAmount > 0) {//checks if ticket amount set and is above 0
                        ticketPoolService.removeTicket(ticketAmount);
                    }
                } finally {
                    lock.unlock();//unlocks finally before leaving the method
                }
                }catch (InterruptedException e) {
                Thread.currentThread().interrupt();//interrupt the current thread if error occurs
              }
            }
        }
    }
