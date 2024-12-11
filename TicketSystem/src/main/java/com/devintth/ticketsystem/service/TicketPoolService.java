package com.devintth.ticketsystem.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TicketPoolService {
    private int numTickets;
    private int maxTickets;
    private final ReentrantLock lock=new ReentrantLock();
    private static final Logger logger=LoggerFactory.getLogger(TicketPoolService.class);//used for logger info of this class
    private final List<String> logHistory=new ArrayList<>();//used to show the log list in frontend
    private String log;

    public void Systemconfig(int numTickets, int maxTickets){//get data for use state
        lock.lock();
        try{
            this.numTickets=numTickets;
            this.maxTickets=maxTickets;
            System.out.println("Systemconfigured");
        }finally {
            lock.unlock();
        }
    }
    public void addTicket(int count) {//should change later
        lock.lock();
        try{
            if (numTickets + count <= maxTickets) {//checks if the add ticket amount with the current tickets is below max
                numTickets += count;//increase the ticket count of the pool
                log="Added"+ count +"tickets successfully.Total tickets: " + numTickets;
                logger.info(log);
                logHistory.add(log);//store the log in the log list
            }else{//message if the request will reach out of boundary in the ticket pool capacity
                log="Capacity reached,can add only"+(maxTickets-numTickets) +"ticket!";
                logger.info(log);
                logHistory.add(log);//store the log in the log list
            }

        }finally {
            lock.unlock();
        }
    }
    public void removeTicket(int count) {
        lock.lock();
        try{
            if (numTickets>=count) {//checks if remove count is above or equal to current ticket amount
                numTickets -= count;//reduces the ticket count of the pool
                log="Purchased"+ count +"tickets successfully.Total tickets: " + numTickets;
                logger.info(log);
                logHistory.add(log);//store the log in the log list
            }else{//message if the request to purchase ticket is above the current tickets available
                log="Capacity reached,can purchase only"+numTickets +"ticket!";
                logger.info(log);
                logHistory.add(log);//store the log in the log list
            }
        }
        finally {
            lock.unlock();
        }
    }
    public String getSystemStates() {//return the string to be used in use state in frontend
        lock.lock();
        try {
            return "Current Tickets: " + numTickets +"\n"+"Max Ticket Capacity: " + maxTickets;
        } finally {
            lock.unlock();
        }
    }
    public List<String> getLogHistory() {
        return new ArrayList<>(logHistory); // Return a new copy to prevent modification
    }
}
