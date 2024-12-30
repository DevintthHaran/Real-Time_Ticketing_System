import React,{useState,useEffect} from "react";
import axios from "axios";

const TicketFrontEnd=()=>{
    const [systemState,setSystemStates]=useState("");//used to fetch string of system state from backend
    const [logHistory, setLogHistory]=useState([]);//used to fetch list of log histort from backend
    //used to fetch parameters from the frontend
    const [formData,setFormData]=useState({
        totalTickets:0,
        maxTicketCapacity:0,
        numVendors:0,
        releaseRate:0,
        numCustomers:0,
        retrievalRate:0,
        ticketAmount:0,
    });

    const handleInputData=(e)=>{//function used in input handling changes for all params
        const {name, value} = e.target;
        const parseInput=parseInt(value,10)||0;
        setFormData({...formData,[name]: parseInput});
    };
    const isConfigurationValid =//condition for validation used to enable/disable the configure button
        formData.totalTickets > 0 &&
        formData.maxTicketCapacity > 0 &&
        formData.numVendors > 0 &&
        formData.releaseRate > 0 &&
        formData.numCustomers > 0 &&
        formData.retrievalRate > 0;

    const isVendorAmountValid=//condition for validation used to enable/disable the Add Vendor Tickets button
        formData.VendorticketAmount>-1;

    const isCustomerAmountValid=//condition for validation used to enable/disable the Add Customer Tickets button
        formData.CustomerticketAmount>-1;
    // Fetches the system state from the backend API
    const getStates=async ()=>{// function called by button click for use states
        try{
            const input=await axios.get("api/tickets/states");//the backend endpoint for states
            setSystemStates(input.data);
        }catch(error){
            console.error("Error fetching tickets from API for states:",error);
        }
    };
    // Fetches the log history from the backend API
    const getLogHistory=async ()=>{// function called by button click for log history
        try{
            const input=await axios.get("api/tickets/log");
            setLogHistory(input.data);
        }catch(error){
            console.error("Error fetching log history from API:",error);
        }
    };
    // Configures the system with input parameters via backend API
    const configureSystem= async ()=>{// function called by button click for configuration
        const {
            totalTickets,
            maxTicketCapacity,
            numVendors,
            releaseRate,
            numCustomers,
            retrievalRate,
        }=formData;


        try{
            const input=await axios.post("api/tickets/configure",null,{
                params:{
                    totalTickets,
                    maxTicketCapacity,
                    numVendors,
                    releaseRate,
                    numCustomers,
                    retrievalRate,
                },
            });
            alert(input.data);
        }catch (error){
            console.error("Error configuring system:",error);
        }
    };
    // Adds vendor tickets via backend API
    const addVendorTickets=async ()=>{// function called by button click for add vendor ticket
        if(formData.VendorticketAmount<0){//validation check for input data
            alert("Please fill the vendor ticket amount fields with values greater than or equal to 0 ");
        }
        try{
            const input=await axios.post("api/tickets/set-vendor-ticket-amount",
                null,
                {
                    params: {VendorticketAmount: formData.VendorticketAmount,},
                }
                );
            alert(input.data);
        }catch (error) {
            console.error("Error adding Vendor Tickets:",error);
        }
    };
    // Adds customer tickets via backend API
    const addCustomerTickets=async ()=>{// function called by button click for add customer ticket
        if(formData.CustomerticketAmount<0){//validation check for input data
            alert("Please fill the customer ticket amount fields with values greater than or equal to 0 ");
        }
        try{
            const input=await axios.post("api/tickets/set-customer-ticket-amount",
                null,
                {
                    params: {CustomerticketAmount: formData.CustomerticketAmount,},
                }
                );
            alert(input.data);
        }catch (error){
            console.error("Error adding Customer Tickets:",error);
        }
    };
    // Starts the ticketing system via backend API
    const startSystem=async ()=>{// function called by button click for start
        try{
            const input=await axios.post("api/tickets/start");// API call to start the system
            alert(input.data);
        }catch (error){
            console.error("Error Starting System:",error);
        }
    };
    // Stops the ticketing system via backend API
    const stopSystem=async ()=>{// function called by button click for stop
        try{
            const input=await axios.post("api/tickets/stop");
            alert(input.data);
        }catch (error){
            console.error("Error Stopping System:",error);
        }
    };
    // Fetch initial system state and log history when the component initialize
    useEffect(()=>{
        getStates();
        getLogHistory();
    },[]);
    //Display structure of the UI
    return(
        <div style={{padding: "20px"}}>
            <h1>Ticket Management System</h1>
            <div>
                <h2>Configure System</h2>
                <label>CurrentTotalTickets: </label>
                <input type="number" name="totalTickets" placeholder="Total Tickets" onChange={handleInputData}/><br/>
                {formData.totalTickets <= 0 && <p style={{ color: 'red' }}>Total Tickets must be greater than 0.</p>}
                <label>MaxTicketCapacity: </label>
                <input type="number" name="maxTicketCapacity" placeholder="Max Ticket Capacity"
                       onChange={handleInputData}/><br/>
                {formData.maxTicketCapacity <= 0 && <p style={{ color: 'red' }}>Max Ticket Capacity must be greater than 0.</p>}
                <label>Number of Vendor Threads: </label>
                <input type="number" name="numVendors" placeholder="Number of Vendors" onChange={handleInputData}/><br/>
                {formData.numVendors <= 0 && <p style={{ color: 'red' }}>Number of Vendors Threads must be greater than 0.</p>}
                <label>Release Rate (s): </label>
                <input type="number" name="releaseRate" placeholder="Release Rate" onChange={handleInputData}/><br/>
                {formData.releaseRate <= 0 && <p style={{ color: 'red' }}>Release Rate must be greater than 0.</p>}
                <label>Number of Customer Threads: </label>
                <input type="number" name="numCustomers" placeholder="Number of Customer"
                       onChange={handleInputData}/><br/>
                {formData.numCustomers <= 0 && <p style={{ color: 'red' }}>Number of Customers Threads must be greater than 0.</p>}
                <label>Retrieval Rate (s): </label>
                <input type="number" name="retrievalRate" placeholder="Retival Rate" onChange={handleInputData}/><br/>
                {formData.retrievalRate <= 0 && <p style={{ color: 'red' }}>Retrieval Rate must be greater than 0.</p>}
                <button onClick={configureSystem} disabled={!isConfigurationValid}>Configure</button>
            </div>
            <div>
                <h2>Manage Tickets</h2>
            <label>Ticket Amount for Vendor: </label>
            <input type="number" name="VendorticketAmount" placeholder="Ticket Amount for Vendor" onChange={handleInputData}/>
            <button onClick={addVendorTickets} disabled={!isVendorAmountValid}>Add Vendor Tickets</button><br/>
                {formData.VendorticketAmount < 0 && <p style={{ color: 'red' }}>Vendor ticket amount must be greater than or equal to 0.</p>}
            <label>Ticket Amount for Customer: </label>
            <input type="number" name="CustomerticketAmount" placeholder="Ticket Amount for Customer" onChange={handleInputData}/>
            <button onClick={addCustomerTickets} disabled={!isCustomerAmountValid}>Add Customer Tickets</button>
                {formData.CustomerticketAmount < 0 && <p style={{ color: 'red' }}>Customer ticket amount must be greater than or equal to 0.</p>}
            </div>
            <div>
                <h2>Control System</h2>
                <button onClick={startSystem}>Start System</button>
                <button onClick={stopSystem}>Stop System</button>
            </div>
            <div>
                <h2>System State</h2>
                <p style={{whiteSpace:'pre-line'}}>{systemState}</p>
                <button onClick={getStates}>Refresh States</button>
            </div>
            <div>
                <h2>Log History</h2>
                <ul>
                    {logHistory.map((log,index)=>(
                        <li key={index}>{log}</li>
                    ))}
                </ul>
                <button onClick={getLogHistory}>Refresh Logs</button>
            </div>
      </div>
    );
};

export default TicketFrontEnd;