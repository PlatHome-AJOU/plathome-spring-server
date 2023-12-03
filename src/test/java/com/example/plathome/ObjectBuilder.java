package com.example.plathome;


import com.example.plathome.estate.common.Floor;
import com.example.plathome.estate.common.Option;
import com.example.plathome.estate.common.RentalType;
import com.example.plathome.estate.common.RoomType;
import com.example.plathome.estate.real.domain.Estate;
import com.example.plathome.estate.real.domain.types.Area;
import com.example.plathome.estate.real.dto.request.EstateForm;
import com.example.plathome.estate.real.dto.request.UpdateEstateForm;
import com.example.plathome.estate.real.dto.search.Filter;
import com.example.plathome.estate.real.dto.search.filter.*;
import com.example.plathome.estate.requested.domain.ThumbNail;
import com.example.plathome.estate_report.domain.EstateReport;
import com.example.plathome.estate_report.dto.request.EstateReportForm;
import com.example.plathome.login.dto.request.LoginForm;
import com.example.plathome.login.dto.request.SignUpForm;
import com.example.plathome.member.domain.Member;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.domain.types.RoleType;
import com.example.plathome.user_report.domain.UserReport;
import com.example.plathome.user_report.dto.request.UserReportForm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import static com.example.plathome.login.constant.JwtStaticField.*;


public class ObjectBuilder {
    protected static final long ID = 1L;
    protected static final long WRONG_ID = 2L;
    protected static final String NICKNAME = "nickname";
    protected static final String EMAIL = "email@ajou.ac.kr";
    protected static final String WRONG_EMAIL = "email";
    protected static final String PASSWORD = "password";
    protected static final String AUTH_CODE = "123456";
    protected static final String SECRET_KEY = "secretKeysecretKeysecretKeysecretKeysecretKeysecretKey";
    protected static final String WRONG_SECRET_KEY = "wrongSecretKeywrongSecretKeywrongSecretKeywrongSecretKey";
    protected static final String LOCATION = "경기도 수원시 팔달구 아주로 xx번길 x";
    protected static final String CONTRACT_URL = "https://www.contract-url.com";
    protected static final String THUMBNAIL_URL = "https://www.thumb-nail.com";
    protected static final String CONTEXT = "AJOU 멋진 방입니다!";
    protected static final LocalDate CONTRACT_TERM = LocalDate.of(2030, 12, 31);
    protected static final double SQUARE_FEET = 32.6;
    protected static final double LONGITUDE = 126.632145;
    protected static final double LATITUDE = 89.562432;
    protected static final int DEPOSIT = 3000;
    protected static final int UPDATE_DEPOSIT = 4000;
    protected static final int MAINTENANCE_FEE = 10;
    protected static final int MONTHLY_RENT = 40;
    protected static final Area AREA = Area.GWANGGYO;
    protected static final RoomType ROOM_TYPE = RoomType.APARTMENT;
    protected static final RentalType RENTAL_TYPE = RentalType.JEONSE;
    protected static final Floor FLOOR = Floor.FIRST;

    protected static Member createMember() {
        return Member.of()
                .id(ID)
                .nickname(NICKNAME)
                .email(EMAIL)
                .password(PASSWORD)
                .roleType(RoleType.USER)
                .createdBy(NICKNAME)
                .modifiedBy(NICKNAME)
                .build();
    }

    protected static MemberSession createMemberSession(long memberId) {
        return MemberSession.of()
                .id(memberId)
                .nickname(NICKNAME)
                .email(EMAIL)
                .password(PASSWORD)
                .roleType(RoleType.USER)
                .build();
    }

    protected static SignUpForm createSignUpForm() {
        return SignUpForm.of()
                .authCode(AUTH_CODE)
                .nickname(NICKNAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    protected static SignUpForm createWrongEmailSignUpForm() {
        return SignUpForm.of()
                .authCode(AUTH_CODE)
                .nickname(NICKNAME)
                .email(WRONG_EMAIL)
                .password(PASSWORD)
                .build();
    }

    protected static LoginForm createLoginForm() {
        return LoginForm.of()
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    protected static String createRefreshToken(String secretKey) {
        return Jwts.builder()
                .subject(String.valueOf(1L))
                .id(UUID.randomUUID().toString()) // Unique ID for refresh token
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(getDecoded(secretKey)))
                .compact();
    }

    protected static String createAccessToken(String secretKey) {
        return Jwts.builder()
                .subject(String.valueOf(1L))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(getDecoded(secretKey)))
                .compact();
    }

    protected static String createExpiredAccessToken(String secretKey) {
        return Jwts.builder()
                .subject(String.valueOf(1L))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(Keys.hmacShaKeyFor(getDecoded(secretKey)))
                .compact();
    }

    protected static String createExpiredRefreshToken(String secretKey) {
        return Jwts.builder()
                .subject(String.valueOf(1L))
                .id(UUID.randomUUID().toString()) // Unique ID for refresh token
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(Keys.hmacShaKeyFor(getDecoded(secretKey)))
                .compact();
    }


    protected static MockHttpServletRequest createHttpRequestWithToken(String headerName, String token) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(headerName, BEARER + token);
        return request;
    }

    protected static byte[] getDecoded(String secretKey) {
        return Base64.getDecoder().decode(secretKey);
    }

    protected static EstateForm createEstateForm() {
        return EstateForm.of()
                .memberId(ID)
                .location(LOCATION)
                .area(AREA)
                .roomType(ROOM_TYPE)
                .rentalType(RENTAL_TYPE)
                .floor(FLOOR)
                .contractUrl(CONTRACT_URL)
                .context(CONTEXT)
                .thumbNailUrl(THUMBNAIL_URL)
                .contractTerm(CONTRACT_TERM)
                .option(createOption())
                .squareFeet(SQUARE_FEET)
                .lng(LONGITUDE)
                .lat(LATITUDE)
                .deposit(DEPOSIT)
                .maintenanceFee(MAINTENANCE_FEE)
                .monthlyRent(MONTHLY_RENT).build();
    }

    protected static Estate createEstate() {
        return Estate.builder()
                .memberId(ID)
                .location(LOCATION)
                .area(AREA)
                .roomType(ROOM_TYPE)
                .rentalType(RENTAL_TYPE)
                .floor(FLOOR)
                .contractUrl(CONTRACT_URL)
                .context(CONTEXT)
                .thumbNailUrl(THUMBNAIL_URL)
                .contractTerm(CONTRACT_TERM)
                .option(createOption())
                .squareFeet(SQUARE_FEET)
                .lng(LONGITUDE)
                .lat(LATITUDE)
                .deposit(DEPOSIT)
                .maintenanceFee(MAINTENANCE_FEE)
                .monthlyRent(MONTHLY_RENT).build();
    }

    protected static ThumbNail createThumbNail() {
        return ThumbNail.builder()
                .memberId(ID)
                .url(THUMBNAIL_URL).build();
    }

    protected static UpdateEstateForm createUpdateEstateForm() {
        return UpdateEstateForm.of()
                .estateId(ID)
                .context(CONTEXT)
                .contractTerm(CONTRACT_TERM)
                .option(createOption())
                .squareFeet(SQUARE_FEET)
                .deposit(UPDATE_DEPOSIT)
                .maintenanceFee(MAINTENANCE_FEE)
                .monthlyRent(MONTHLY_RENT).build();
    }

    protected static Filter createFilter() {
        return Filter.builder()
                .area(createAreaFilter())
                .roomType(createRoomTypeFilter())
                .rentType(createRentTypeFilter())
                .deposit(createDepositFilter())
                .monthlyFee(createMonthlyFeeFilter())
                .roomSize(createRoomSizeFilter())
                .floor(createFloorFilter())
                .option(createOptionFilter()).build();
    }

    protected static EstateReportForm createEstateReportForm() {
        return EstateReportForm.of()
                .estateId(ID)
                .context(CONTEXT).build();
    }

    protected static EstateReport createEstateReport() {
        return EstateReport.builder()
                .memberId(ID)
                .estateId(ID)
                .context(CONTEXT).build();
    }

    protected static UserReportForm createUserReportForm() {
        return UserReportForm.of()
                .targetMemberId(ID)
                .context(CONTEXT).build();
    }

    protected static UserReport createUserReport() {
        return UserReport.builder()
                .reportMemberId(ID)
                .targetMemberId(WRONG_ID)
                .context(CONTEXT).build();
    }
    private static Option createOption() {
        return Option.builder()
                .elevator(true)
                .park(true)
                .cctv(true)
                .doorLock(true)
                .pet(true)
                .veranda(true)
                .bunner("false")
                .airConditioner("false")
                .refrigerator(true)
                .sink(true)
                .tv(true)
                .internet(true)
                .bed(true)
                .desk(true)
                .microwave(true)
                .closet(true)
                .shoeRack(true)
                .bidet(true)
                .interPhone(true)
                .parking(true)
                .security(true)
                .deliveryBox(true)
                .buildingEntrance(true)
                .washingMachine(true)
                .build();
    }

    private static AreaFilter createAreaFilter() {
        return AreaFilter.builder()
                .gwanggyo(true)
                .ingyedong(false)
                .uman(false)
                .woncheon(false)
                .maetan(false).build();
    }

    private static RoomTypeFilter createRoomTypeFilter() {
        return RoomTypeFilter.builder()
                .studio(false)
                .two_threeRoom(false)
                .officetel(false)
                .apartment(true).build();
    }

    private static RentTypeFilter createRentTypeFilter() {
        return RentTypeFilter.builder()
                .monthly(true)
                .jeonse(true).build();
    }

    private static DepositFilter createDepositFilter() {
        return DepositFilter.builder()
                .min(0)
                .max(9999).build();
    }

    private static MonthlyFeeFilter createMonthlyFeeFilter() {
        return MonthlyFeeFilter.builder()
                .min(0)
                .max(999).build();
    }

    private static RoomSizeFilter createRoomSizeFilter() {
        return RoomSizeFilter.builder()
                .min(0.0)
                .max(100.0).build();
    }

    private static FloorFilter createFloorFilter() {
        return FloorFilter.builder()
                .first(true)
                .second(false)
                .third(false)
                .fourth(false)
                .fifth(false)
                .sixth(false)
                .seventhUpper(false)
                .top(false)
                .under(false).build();
    }

    private static OptionFilter createOptionFilter() {
        return OptionFilter.builder()
                .elevator(true)
                .park(true)
                .cctv(true)
                .doorLock(true)
                .pet(true)
                .veranda(true)
                .bunner("false")
                .airConditioner("false")
                .refrigerator(true)
                .sink(true)
                .tv(true)
                .internet(true)
                .bed(true)
                .desk(true)
                .microwave(true)
                .closet(true)
                .shoeRack(true)
                .bidet(true)
                .interPhone(true)
                .parking(true)
                .security(true)
                .deliveryBox(true)
                .buildingEntrance(true)
                .washingMachine(true).build();
    }


}
