package com.zh.look.controller;

import com.zh.look.service.AppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointmentsController")
public class AppointmentsController {
    @Autowired
    private AppointmentsService appointmentsService;

}
