package com.irem.gcs.controller;

import com.irem.gcs.service.UploadCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController(value = "/uploadCode")
@CrossOrigin("*")
public class UploadCodeController {

    @Autowired private UploadCodeService uploadCodeService;

    @PostMapping("/uploadCode")
    public ResponseEntity UploadCode(@RequestBody String file) throws Exception {
        boolean isSend = uploadCodeService.sendCodeFile(file);
        if(isSend) {
            return new ResponseEntity<>("Code file is sent!", HttpStatus.OK);
        }
        return null;
    }

}
