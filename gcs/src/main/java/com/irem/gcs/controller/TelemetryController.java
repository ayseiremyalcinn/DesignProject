package com.irem.gcs.controller;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.irem.gcs.converter.TelemetryConverter;
import com.irem.gcs.entity.Telemetry;
import com.irem.gcs.repository.TelemetryRepository;
import com.irem.gcs.service.SerialPortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Controller
@CrossOrigin("*")
public class TelemetryController {
    private final SimpMessagingTemplate template;
    @Autowired
    private SerialPortService serialPortService;
    @Autowired
    private TelemetryConverter telemetryConverter;
    @Autowired
    private TelemetryRepository telemetryRepository;

    public TelemetryController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/telemetryRequest")
    public void telemetryRequest(int period) throws Exception {
        sendRequestToSat(period);
        receiveTelemetry();
    }
    private void receiveTelemetry() throws Exception {
        serialPortService.getSerialPort().addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }
            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    try {
                        InputStream in = serialPortService.getSerialPort().getInputStream();
                        byte[] buffer = new byte[serialPortService.getSerialPort().bytesAvailable()]; // Mevcut verileri al
                        int bytesRead = in.read(buffer);
                        String receivedData = new String(buffer, 0, bytesRead);
                        Telemetry tm = telemetryConverter.toTelemetry(receivedData);
                        telemetryRepository.save(tm);
                        sendTelemetryToGCS(receivedData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void sendRequestToSat(int period) throws Exception{
        if(!serialPortService.isPortOpen()){
            serialPortService.openPort("COM3");
        }
        String combined = "telemetry," + period;
        byte[] data = combined.getBytes(StandardCharsets.UTF_8);
        serialPortService.writeData(data);
    }
    public void sendTelemetryToGCS(String data) {
        template.convertAndSend("/topic/telemetry", data);
    }

}
