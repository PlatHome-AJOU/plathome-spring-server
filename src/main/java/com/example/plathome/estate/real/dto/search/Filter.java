package com.example.plathome.estate.real.dto.search;

import com.example.plathome.estate.real.dto.search.filter.*;
import lombok.Builder;

@Builder
public record Filter(
        AreaFilter area,
        RoomTypeFilter roomType,
        RentTypeFilter rentType,
        DepositFilter deposit,
        MonthlyFeeFilter monthlyFee,
        RoomSizeFilter roomSize,
        FloorFilter floor,
        OptionFilter option
) {

}
