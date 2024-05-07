import React, { useState, useEffect } from "react";
import "./App.css";

import TopBar from "./components/TopBar/TopBar";
import Map from "./components/Map/Map"; 
import ComPanel from "./components/Command_panel/Com_Panel";
import TelemetryPanel from "./components/Telemetry_Panel/Telemetry_Panel";
import TablePass from "./components/Passes/Table_pass";


function App() {
   
  return (

    <div>
      <TopBar/>
      <div className="main-container">
        {/* <Map/> */}
        <div className="container-one">
          <ComPanel/> 
          <TelemetryPanel/> 
        </div>
        <TablePass/>
               
      </div>

    </div>
    
    
    


    // <div className="App">
      
    //   <User 
    //   name = "irem"
    //   school = "hacettepe"
    //   clas = "4"
    //   />

    //   <User 
    //   name = "ayse"
    //   school = "oafl"
    //   clas = "16"
    //   />
    //   <h4>Selamm <i class="fa fa-thermometer-empty" aria-hidden="true"></i> </h4>
    // </div>
  );
}

export default App;


