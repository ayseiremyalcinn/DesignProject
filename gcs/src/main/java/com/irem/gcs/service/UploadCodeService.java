package com.irem.gcs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;

@Service
public class UploadCodeService {

    @Autowired
    private SerialPortService serialPortService;

    public boolean sendCodeFile(String file) throws Exception {
        if(!serialPortService.isPortOpen()){
            serialPortService.openPort("COM3");
        }
        String combined = "code," + file;
        byte[] data = combined.getBytes(StandardCharsets.UTF_8);
        serialPortService.writeData(data);
        return true;
    }
}
