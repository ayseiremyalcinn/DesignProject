package com.irem.gcs.service;

import com.fazecast.jSerialComm.SerialPort;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
@Data
@Service
public class SerialPortService {
    private SerialPort serialPort;
    public void openPort(String portDescriptor) {
        serialPort = SerialPort.getCommPort(portDescriptor);
        serialPort.setComPortParameters(9600, 8, 1, 0); // Baud rate, data bits, stop bits, parity
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0); // Non-blocking için

        if (!serialPort.openPort()) {
            throw new RuntimeException("Serial port açılamadı.");
        }

    }
    public void closePort() {
        if (serialPort != null) {
            serialPort.closePort();
        }
    }

    public void writeData(byte[] data) throws Exception {
        if (serialPort == null || !serialPort.isOpen()) {
            throw new RuntimeException("Serial port açık değil.");
        }
        OutputStream out = serialPort.getOutputStream();
        out.write(data);
        out.flush();
    }
    public byte[] readData(int maxLength) throws Exception {
        if (serialPort == null || !serialPort.isOpen()) {
            throw new RuntimeException("Serial port açık değil.");
        }
        InputStream in = serialPort.getInputStream();
        byte[] buffer = new byte[maxLength];
        int bytesRead = in.read(buffer);
        if (bytesRead == -1) {
            return null;
        }
        byte[] result = new byte[bytesRead];
        System.arraycopy(buffer, 0, result, 0, bytesRead);
        return result;
    }

    public boolean isPortOpen() {
        if (serialPort != null) {
            return serialPort.isOpen();
        }
        return false;
    }
}
