package com.example.plathome.estate.real.repository;

import com.example.plathome.estate.real.domain.Estate;
import com.example.plathome.estate.real.dto.search.Filter;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.plathome.estate.common.Floor.*;
import static com.example.plathome.estate.common.RentalType.JEONSE;
import static com.example.plathome.estate.common.RentalType.MONTHLY;
import static com.example.plathome.estate.common.RoomType.*;
import static com.example.plathome.estate.real.domain.QEstate.estate;
import static com.example.plathome.estate.real.domain.constant.Area.*;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
@Repository
public class EstateRepositoryImpl implements EstateRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<Estate> filterSearch(Filter filter) {
        return queryFactory
                .select(estate)
                .from(estate)
                .where(
                        gwanggyo(filter.area().gwanggyo())
                                .or(ingyedong(filter.area().ingyedong()))
                                .or(uman(filter.area().uman()))
                                .or(woncheon(filter.area().woncheon()))
                                .or(maetan(filter.area().maetan()))
                                .or(depositBetween(filter.deposit().min(), filter.deposit().max()))
                                .or(first(filter.floor().first()))
                                .or(second(filter.floor().second()))
                                .or(third(filter.floor().third()))
                                .or(fourth(filter.floor().fourth()))
                                .or(fifth(filter.floor().fifth()))
                                .or(sixth(filter.floor().sixth()))
                                .or(seventhUpper(filter.floor().seventhUpper()))
                                .or(top(filter.floor().top()))
                                .or(under(filter.floor().under()))
                                .or(monthlyFeeBetween(filter.monthlyFee().min(), filter.monthlyFee().max()))
                                .or(monthly(filter.rentType().monthly()))
                                .or(jeonse(filter.rentType().jeonse()))
                                .or(roomSizeBetween(filter.roomSize().min(), filter.roomSize().max()))
                                .or(studio(filter.roomType().studio()))
                                .or(twoThreeRoom(filter.roomType().two_threeRoom()))
                                .or(officetel(filter.roomType().officetel()))
                                .or(apartment(filter.roomType().apartment()))
                                .or(elevator(filter.option().elevator()))
                                .or(park(filter.option().park()))
                                .or(cctv(filter.option().cctv()))
                                .or(doorLock(filter.option().doorLock()))
                                .or(pet(filter.option().pet()))
                                .or(veranda(filter.option().veranda()))
                                .or(bunner(filter.option().bunner()))
                                .or(airConditioner(filter.option().airConditioner()))
                                .or(refrigerator(filter.option().refrigerator()))
                                .or(sink(filter.option().sink()))
                                .or(tv(filter.option().tv()))
                                .or(internet(filter.option().internet()))
                                .or(bed(filter.option().bed()))
                                .or(desk(filter.option().desk()))
                                .or(microwave(filter.option().microwave()))
                                .or(closet(filter.option().closet()))
                                .or(shoeRack(filter.option().shoeRack()))
                                .or(bidet(filter.option().bidet()))
                                .or(interPhone(filter.option().interPhone()))
                                .or(parking(filter.option().parking()))
                                .or(security(filter.option().security()))
                                .or(deliveryBox(filter.option().deliveryBox()))
                                .or(buildingEntrance(filter.option().buildingEntrance()))
                                .or(washingMachine(filter.option().washingMachine())))
                .fetch();
    }

    /** Area */
    private BooleanExpression gwanggyo(boolean gwanggyo) {
        return gwanggyo ? estate.area.eq(GWANGGYO) : null;
    }

    private BooleanExpression ingyedong(boolean ingyedong) {
        return ingyedong ? estate.area.eq(INGYEDONG) : null;
    }

    private BooleanExpression uman(boolean uman) {
        return uman ? estate.area.eq(UMAN) : null;
    }

    private BooleanExpression woncheon(boolean woncheon) {
        return woncheon ? estate.area.eq(WONCHEON) : null;
    }

    private BooleanExpression maetan(boolean maetan) {
        return maetan ? estate.area.eq(MAETAN) : null;
    }

    /** deposit */
    private BooleanExpression depositBetween(Integer depositGoe, Integer depositLoe) {
        return depositLoe(depositLoe).and(depositGoe(depositGoe));
    }

    private BooleanExpression depositLoe(Integer depositLoe) {
        return depositLoe != null ?  estate.deposit.loe(depositLoe) : null;
    }

    private BooleanExpression depositGoe(Integer depositGoe) {
        return depositGoe != null ? estate.deposit.goe(depositGoe) : null;
    }

    /** floor */
    private BooleanExpression first(boolean first) {
        return first ? estate.floor.eq(FIRST) : null;
    }

    private BooleanExpression second(boolean second) {
        return second ? estate.floor.eq(SECOND) : null;
    }

    private BooleanExpression third(boolean third) {
        return third ? estate.floor.eq(THIRD) : null;
    }

    private BooleanExpression fourth(boolean fourth) {
        return fourth ? estate.floor.eq(FOURTH) : null;
    }

    private BooleanExpression fifth(boolean fifth) {
        return fifth ? estate.floor.eq(FIFTH) : null;
    }

    private BooleanExpression sixth(boolean sixth) {
        return sixth ? estate.floor.eq(SIXTH) : null;
    }

    private BooleanExpression seventhUpper(boolean seventhUpper) {
        return seventhUpper ? estate.floor.eq(SEVENTH_UPPER) : null;
    }

    private BooleanExpression top(boolean top) {
        return top ? estate.floor.eq(TOP) : null;
    }

    private BooleanExpression under(boolean under) {
        return under ? estate.floor.eq(UNDER) : null;
    }

    /** monthlyFee */
    private BooleanExpression monthlyFeeBetween(Integer monthlyFeeGoe, Integer monthlyFeeLoe) {
        return monthlyFeeLoe(monthlyFeeLoe).and(monthlyFeeGoe(monthlyFeeGoe));
    }

    private BooleanExpression monthlyFeeLoe(Integer monthlyFeeLoe) {
        return monthlyFeeLoe != null ?  estate.monthlyRent.loe(monthlyFeeLoe): null;
    }

    private BooleanExpression monthlyFeeGoe(Integer monthlyFeeGoe) {
        return monthlyFeeGoe != null ? estate.monthlyRent.goe(monthlyFeeGoe) : null;
    }

    /** renType */
    private BooleanExpression monthly(boolean monthly) {
        return monthly ? estate.rentalType.eq(MONTHLY) : null;
    }

    private BooleanExpression jeonse(boolean jeonse) {
        return jeonse ? estate.rentalType.eq(JEONSE) : null;
    }

    /** roomSize */
    private BooleanExpression roomSizeBetween(Double roomSizeGoe, Double roomSizeLoe) {
        return roomSizeLoe(roomSizeLoe).and(roomSizeGoe(roomSizeGoe));
    }

    private BooleanExpression roomSizeLoe(Double roomSizeLoe) {
        return roomSizeLoe != null ? estate.squareFeet.loe(roomSizeLoe): null;
    }

    private BooleanExpression roomSizeGoe(Double roomSizeGoe) {
        return roomSizeGoe != null ? estate.squareFeet.goe(roomSizeGoe) : null;
    }

    /** roomSize */
    private BooleanExpression studio(boolean studio) {
        return studio ? estate.roomType.eq(STUDIO) : null;
    }

    private BooleanExpression twoThreeRoom(boolean twoThreeRoom) {
        return twoThreeRoom ? estate.roomType.eq(TOW_THREEROOM) : null;
    }

    private BooleanExpression officetel(boolean officetel) {
        return officetel ? estate.roomType.eq(OFFICETEL) : null;
    }

    private BooleanExpression apartment(boolean apartment) {
        return apartment ? estate.roomType.eq(APARTMENT) : null;
    }

    /** option */
    private BooleanExpression elevator(boolean elevator) {
        return elevator ? estate.option.elevator.eq(true) : null;
    }

    private BooleanExpression park(boolean park) {
        return park ? estate.option.park.eq(true) : null;
    }

    private BooleanExpression cctv(boolean cctv) {
        return cctv ? estate.option.cctv.eq(true) : null;
    }

    private BooleanExpression doorLock(boolean doorLock) {
        return doorLock ? estate.option.doorLock.eq(true) : null;
    }

    private BooleanExpression pet(boolean pet) {
        return pet ? estate.option.pet.eq(true) : null;
    }

    private BooleanExpression veranda(boolean veranda) {
        return veranda ? estate.option.veranda.eq(true) : null;
    }

    private BooleanExpression bunner(String bunner) {
        return hasText(bunner) ? estate.option.bunner.eq(bunner) : null;
    }

    private BooleanExpression airConditioner(String airConditioner) {
        return hasText(airConditioner) ? estate.option.airConditioner.eq(airConditioner) : null;
    }

    private BooleanExpression refrigerator(boolean refrigerator) {
        return refrigerator ? estate.option.refrigerator.eq(true) : null;
    }

    private BooleanExpression sink(boolean sink) {
        return sink ? estate.option.sink.eq(true) : null;
    }

    private BooleanExpression tv(boolean tv) {
        return tv ? estate.option.tv.eq(true) : null;
    }

    private BooleanExpression internet(boolean internet) {
        return internet ? estate.option.internet.eq(true) : null;
    }

    private BooleanExpression bed(boolean bed) {
        return bed ? estate.option.bed.eq(true) : null;
    }

    private BooleanExpression desk(boolean desk) {
        return desk ? estate.option.desk.eq(true) : null;
    }

    private BooleanExpression microwave(boolean microwave) {
        return microwave ? estate.option.microwave.eq(true) : null;
    }

    private BooleanExpression closet(boolean closet) {
        return closet ? estate.option.closet.eq(true) : null;
    }

    private BooleanExpression shoeRack(boolean shoeRack) {
        return shoeRack ? estate.option.shoeRack.eq(true) : null;
    }

    private BooleanExpression bidet(boolean bidet) {
        return bidet ? estate.option.bidet.eq(true) : null;
    }

    private BooleanExpression interPhone(boolean interPhone) {
        return interPhone ? estate.option.interPhone.eq(true) : null;
    }

    private BooleanExpression parking(boolean parking) {
        return parking ? estate.option.parking.eq(true) : null;
    }

    private BooleanExpression security(boolean security) {
        return security ? estate.option.security.eq(true) : null;
    }

    private BooleanExpression deliveryBox(boolean deliveryBox) {
        return deliveryBox ? estate.option.deliveryBox.eq(true) : null;
    }

    private BooleanExpression buildingEntrance(boolean buildingEntrance) {
        return buildingEntrance ? estate.option.buildingEntrance.eq(true) : null;
    }

    private BooleanExpression washingMachine(boolean washingMachine) {
        return washingMachine ? estate.option.washingMachine.eq(true) : null;
    }

}
