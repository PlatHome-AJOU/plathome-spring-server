package com.example.plathome.schedule.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CreateSchedulesDto {
    private String roomId;

    private List<LocalDateTime> availableTimes;

    public String getRoomId(){
        return roomId;
    }

    public List<LocalDateTime> getAvailableTimes(){
        return availableTimes;
    }
}
