package com.example.plathome.real_estate.dto.search;

import com.example.plathome.real_estate.dto.search.filter.*;
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
