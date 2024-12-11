package com.devintth.ticketsystem.controller;


import com.devintth.ticketsystem.service.TicketManagement;
import com.devintth.ticketsystem.service.TicketPoolService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController//indicates this class is a controller
@RequestMapping("/api/tickets")//end-point for this class
@CrossOrigin(origins = "http:localhost:3000")//allows request from the given origin (frontend)
public class TicketController {
    private final TicketManagement ticketManagement;
    private final TicketPoolService ticketPoolService;
    private boolean configured=false;//used to check if the system is configured
    private boolean running=false;//used to check if system is running


    public TicketController(TicketManagement ticketManagement, TicketPoolService ticketPoolService) {
        this.ticketManagement = ticketManagement;
        this.ticketPoolService = ticketPoolService;
    }
    @PostMapping("/configure")//endpoint for configuration
    public String configureSystem(//request specific parameters from frontend
            @RequestParam int totalTickets,
            @RequestParam int maxTicketCapacity,
            @RequestParam int numVendors,
            @RequestParam int releaseRate,
            @RequestParam int numCustomers,
            @RequestParam int retrievalRate
    ){
        ticketManagement.configureSystem(totalTickets,maxTicketCapacity);//called to initialise the ticketpool

        ticketManagement.createVendor(numVendors,releaseRate);//called to create vendor threads

        ticketManagement.createCustomer(numCustomers,retrievalRate);//called to create customer threads
        configured=true;//configure changed to true
        return "System configurated with "+totalTickets+" tickets and max capacity of "+maxTicketCapacity+". Threads of "+numVendors+" Vendors with release rate at "+releaseRate+ " and "+numCustomers+" Customers with retrieval rate at " + retrievalRate;



    }
    @PostMapping("/set-vendor-ticket-amount")//endpoint for vendor ticket amount
    public String setVendorTicketAmount(@RequestParam int VendorticketAmount){//request parameter from frontend
        if (configured){//checks if system configured as vendor ticket amount can be initialised after thread created
            ticketManagement.setVendorTicketAmount(VendorticketAmount);//initialise the vendor ticket amount
            return "Vendor threads adds " +VendorticketAmount+ " tickets";
        }else{//error message returned to frontend to display
            return "Please configure system first to set vendor ticket amount";
        }
    }

    @PostMapping("/set-customer-ticket-amount")//endpoint for customer ticket amount
    public String setCustomerTicketAmount(@RequestParam int CustomerticketAmount){//request parameter from frontend
        if (configured){//checks if system configured as customer ticket amount can be initialized after thread created
            ticketManagement.setCustomerTicketAmount(CustomerticketAmount);//initialise the customer ticket amount
            return "Customer threads purchases "+CustomerticketAmount+" tickets";
        }else{//error message returned to frontend to display
            return "Please configure system first to set customer ticket amount";
        }
    }
    @PostMapping("/start")//endpoint for start
    public String start(){
        if(configured){// checks if system configured(cannot start without configuration)
            if(!running){// checks if system not running(trying to start a running program led to error)
                ticketManagement.start();//start the threads
                running=true;//system running becomes true
                return "System started successfully";

            }else{//error message returned to frontend to display
                return "System has already started";
            }
        }else{//error message returned to frontend to display
            return "System not configured. Please configure system first";
        }
    }@PostMapping("/stop")//endpoint for stop
    public String stop(){
        if(running){//checks if system is running(can stop a system which not running)
            ticketManagement.stop();//interrupt the threads
            return "System stopped successfully";
        }else{//error message returned to frontend to display
            return "System has already stopped";
        }
    }@GetMapping("/states")//endpoint to states
    public String states(){
        return ticketPoolService.getSystemStates()+ticketManagement.useState();// return the states as string
    }@GetMapping("/log")//end point for log history
    public List<String> getLogHistory() {
        return ticketPoolService.getLogHistory();//return the log history list
    }
}

