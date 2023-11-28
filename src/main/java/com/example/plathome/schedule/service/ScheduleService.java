package com.example.plathome.schedule.service;

import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.schedule.domain.Schedule;
import com.example.plathome.schedule.dto.CreateSchedulesDto;
import com.example.plathome.schedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> reportSchedules(long memberId, CreateSchedulesDto dto) {
        List<Schedule> createdSchedules = new ArrayList<>();

        for (LocalDateTime availableTime : dto.getAvailableTimes()) {
            Schedule schedule = Schedule.builder()
                    .memberId(memberId)
                    .roomId(dto.getRoomId())
                    .availableDate(availableTime)
                    .createdAt(LocalDateTime.now())
                    .build();

            Schedule createdSchedule = scheduleRepository.save(schedule);
            createdSchedules.add(createdSchedule);
        }

        return createdSchedules;
    }

    public List<Schedule> getAvailableTimes(MemberSession memberSession, String roomId) {
        return scheduleRepository.findByRoomId(roomId);
    }
}