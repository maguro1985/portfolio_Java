package com.example.attendance.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.attendance.entity.Attendance;
import com.example.attendance.repository.AttendanceRepository;

@CrossOrigin(origins = "*") // テスト用。公開用は制限する
@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceRepository repository;

    private static final ZoneId JST = ZoneId.of("Asia/Tokyo");

    // 出勤
    @PostMapping("/checkin")
    public Attendance checkin() {

        // 今日の日付を日本時間で取得
        LocalDate today = LocalDate.now(JST);

        // 今日のレコードがあれば取得、なければ新規作成
        Attendance a = repository.findByDate(today)
                .orElseGet(Attendance::new);

        a.setDate(today);
        a.setCheckIn(LocalDateTime.now(JST));

        return repository.save(a);
    }

    // 退勤
    @PostMapping("/checkout")
    public Attendance checkout() {

        // 今日の日付を日本時間で取得
        LocalDate today = LocalDate.now(JST);

        // 出勤していない場合はエラー
        Attendance a = repository.findByDate(today)
                .orElseThrow(() -> new RuntimeException("本日の出勤記録が存在しません"));

        a.setCheckOut(LocalDateTime.now(JST));

        return repository.save(a);
    }

    // 全レコード取得
    @GetMapping
    public List<Attendance> findAll() {
        return repository.findAll();
    }
}