package net.apmm.mdm.ops.facility.controller;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.facility.model.StatusResponse;
import net.apmm.mdm.ops.facility.service.PublishFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

@RestController
@RequestMapping("opsmdm")
@Slf4j
@Validated

public class PublishFacilityController {

    @Autowired
    PublishFacilityService publishFacilityService;

    @PostMapping("facility/publish")

    public ResponseEntity<StatusResponse> publishFacilityService(@RequestParam String fctRowID,
                                                                 @RequestParam String eventDetails) throws Exception {
        log.info("Request received for fctRowID {} running on thread {}", fctRowID, Thread.currentThread().getName());
        StatusResponse statusResponse = publishFacilityService.publishOpsFacilityDetails(fctRowID, eventDetails.toUpperCase());
        return new ResponseEntity<>(statusResponse, HttpStatus.ACCEPTED);

    }
}


