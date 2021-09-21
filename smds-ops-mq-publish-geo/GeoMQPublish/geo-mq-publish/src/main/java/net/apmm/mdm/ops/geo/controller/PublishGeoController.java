package net.apmm.mdm.ops.geo.controller;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.model.StatusResponse;
import net.apmm.mdm.ops.geo.service.PublishGeographyMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("opsmdm")
@Slf4j
@Validated
public class PublishGeoController {

    @Autowired
    PublishGeographyMQService publishGeographyMQService;

    @PostMapping("geo/mq/city/publish")
    public ResponseEntity<StatusResponse> publishCity(@RequestParam String geoRowID) throws Exception {
        log.info("Request received for GeoROWID {} running on thread {}", geoRowID, Thread.currentThread().getName());
        StatusResponse statusResponse = publishGeographyMQService.publishGeographyDetails(geoRowID);
        return new ResponseEntity<>(statusResponse, HttpStatus.OK);

    }
}
