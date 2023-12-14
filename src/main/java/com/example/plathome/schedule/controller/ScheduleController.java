package com.example.plathome.schedule.controller;

import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.Login;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.schedule.domain.Schedule;
import com.example.plathome.schedule.dto.CreateSchedulesDto;
import com.example.plathome.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/chat/schedule")
@RequiredArgsConstructor
@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/auth")
    public ResponseEntity<List<Schedule>> createSchedule(
            @Parameter(hidden = true) @Login MemberSession memberSession,
            @RequestBody @Valid CreateSchedulesDto dto
    ) {
        List<Schedule> createdSchedules = scheduleService.reportSchedules(memberSession.id(), dto);
        return ResponseEntity.ok(createdSchedules);
    }

    @GetMapping("/auth/{roomId}")
    public ResponseEntity<List<Schedule>> createSchedule(
            @Parameter(hidden = true) @Login MemberSession memberSession,
            @PathVariable String roomId
    ) {
        List<Schedule> availableTimes = scheduleService.getAvailableTimes(memberSession, roomId);
        return ResponseEntity.ok(availableTimes);
    }
}
