package com.devintth.ticketsystem.CLI;

import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class TicketPool {
    private int numTickets;
    private int maxTickets;
    private final ReentrantLock lock=new ReentrantLock();
    private final Logger logger=LoggerFactory.getLogger(TicketPool.class.getName());
    public TicketPool(int numTickets, int maxTickets) {
        this.numTickets = numTickets;
        this.maxTickets = maxTickets;
    }

    public TicketPool() {

    }

    public void addTicket(int count) {//
        lock.lock();//locks the thread while executing so no race error
        try{
            if (numTickets + count <= maxTickets) {// checks if the tickets count if added is within max ticket capacity
                numTickets += count;//increases the current ticket count in pool
                logger.info("Added "+ count +" tickets successfully.Total tickets: " + numTickets);//outputs the logger details with no error

            }else{
                logger.info("Capacity reached,can add only "+(maxTickets-numTickets) +" ticket!");//output the error message in log details

            }

        }finally {
            lock.unlock();//unlock the thread finally before leaving this method
        }
    }
    public void removeTicket(int count) {
        lock.lock();//locks the thread while executing so no race error
        try{
            if (numTickets>=count) {// checks if the tickets count if removed is not a negative number(request to remove within pool's ticket)
                numTickets -= count;//removes the current ticket count in pool
                logger.info("Purchased "+ count +" tickets successfully.Total tickets: " + numTickets);//outputs the logger details with no error

            }else{
                logger.info("Capacity reached,can purchase only "+numTickets +" ticket!");//output the error message in log details

            }
        }
        finally {
            lock.unlock();//unlock the thread finally before leaving this method
        }

    }
}


