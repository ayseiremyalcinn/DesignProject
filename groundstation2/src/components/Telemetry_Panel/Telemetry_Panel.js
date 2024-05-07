import React, { useEffect, useState } from 'react';
import '../Telemetry_Panel/Telemetry_Panel.css'
import MiniPanel from "./Mini_Panel/Mini_Panel.js";
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const Telemetry_Panel = () => {


    const [period, setPeriod] = useState(1000); 
    const [stompClient, setStompClient] = useState(null);
    const [telemetry, setTelemetry] = useState(["0","0", "0", "0"]);
  
    const handlePeriodChange = (e) => {
      const newPeriod = parseInt(e.target.value, 10);
      setPeriod(newPeriod);
    };
  
    useEffect(() => {
      const socket = new SockJS('http://localhost:8080/ws');
      const client = new Client({
        webSocketFactory: () => socket,
      });
  
      client.onConnect = () => {
        console.log("WebSocket'e bağlandı");
  
        client.subscribe('/topic/telemetry', (message) => {
          console.log("Veri alındı:", message.body);
          const array = message.body.split(",");
          setTelemetry(array); 
        });
      };
  
      client.activate();
      setStompClient(client);
  
      return () => {
        client.deactivate(); 
      };
  
    }, []);
  
    const handleSendBeacon = () => {

      if (stompClient) {
        stompClient.publish({
          destination: '/app/telemetryRequest', 
          body: String(period), 
        });
      }
    };

    return( 
        <div className='telemetry-container'>
            <div className='label-header'>Son Telemetri Paketi</div>
            <div className='row'>
                <div className='column'>
                <MiniPanel
                header = "Zaman"
                value = {telemetry[0]}/>
                <MiniPanel
                header = "Sıcaklık"
                value = {`${telemetry[2]}°C`}/>
                </div>
                <div className='column'>
                <MiniPanel
                header = "TEC Yoğunluğu"
                value = {`%${telemetry[1]}`}/>
                <MiniPanel
                header = "Pil Gerilimi"
                value = {`${telemetry[3]} V`}/>
                </div>

                
            </div>
            <div className="beacon-container">
            <div className='label'>Periyot: </div> 
            <input
                type="number"
                value={period}
                onChange={handlePeriodChange}
            />

            <button onClick={handleSendBeacon}>Telemetri İste</button>
            </div>

        </div>
    )   

};

export default Telemetry_Panel;