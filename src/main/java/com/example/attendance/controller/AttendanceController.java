package com.example.attendance.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.attendance.entity.Attendance;
import com.example.attendance.repository.AttendanceRepository;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceRepository repository;

    // 出勤
    @PostMapping("/checkin")
    public Attendance checkin() {

        LocalDate today = LocalDate.now();

        Attendance a = repository.findByDate(today)
                .orElseGet(Attendance::new);

        a.setDate(today);
        a.setCheckIn(LocalDateTime.now());

        return repository.save(a);
    }

    // 退勤
    @PostMapping("/checkout")
    public Attendance checkout() {

        LocalDate today = LocalDate.now();

        Attendance a = repository.findByDate(today)
                .orElseThrow(); // 出勤してない場合エラー

        a.setCheckOut(LocalDateTime.now());

        return repository.save(a);
    }
}