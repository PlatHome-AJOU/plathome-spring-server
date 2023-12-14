package com.example.plathome.global.error;

public class ErrorStaticField {
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int NOT_MATCH = 405;
    public static final int OK = 200;
    public static final int CREATED = 201;

    //member related
    public static final String ERROR_MEMBER_NOT_FOUND = "오류: 해당 회원을 찾을 수 없습니다.";
    public static final String ERROR_MEMBER_DUPLICATE_EMAIL = "오류: 이미 등록된 이메일 주소입니다.";
    public static final String ERROR_MEMBER_DUPLICATE_NICKNAME = "오류: 이미 사용 중인 닉네임입니다.";
    public static final String ERROR_MEMBER_UNAUTHORIZED = "오류: 로그인이 필요한 서비스입니다.";
    public static final String ERROR_MEMBER_INVALID_ID = "오류: 유효하지 않은 아이디입니다.";
    public static final String ERROR_MEMBER_INVALID_PASSWORD = "오류: 비밀번호가 일치하지 않습니다.";
    public static final String ERROR_MEMBER_FORBIDDEN = "오류: 해당 회원은 이 서비스에 접근할 권한이 없습니다.";

    // Request related
    public static final String ERROR_REQUEST_BINDING = "오류: Form 형식이 올바르지 않습니다.";
    public static final String ERROR_REQUEST_BODY_NOT_FOUND = "오류: ReqeustBody에 필요한 정보가 누락되었습니다.";
    public static final String ERROR_REQUEST_CONVERSION = "오류: 요청 매개변수의 형식이 올바르지 않습니다";
    public static final String ERROR_REQUEST_INVALID_EMAIL_FORM = "오류: 이메일 형식이 올바르지 않습니다. 아주대 이메일을 사용해주세요. - @ajou.ac.kr";
    public static final String ERROR_REQUEST_INVALID_TYPE_INPUT = "오류: 유효하지 않은 타입이 입력되었습니다.";

    // Login related
    public static final String ERROR_LOGIN_INVALID_REFRESH_TOKEN = "오류: Refresh 토큰이 유효하지 않습니다.";
    public static final String ERROR_LOGIN_INVALID_ACCESS_TOKEN = "오류: Access 토큰이 유효하지 않습니다.";
    public static final String ERROR_LOGIN_EXPIRED_REFRESH_TOKEN = "오류: Refresh 토큰이 만료되었습니다. 다시 로그인해주세요.";
    public static final String ERROR_LOGIN_EXPIRED_ACCESS_TOKEN = "오류: Access 토큰이 만료되었습니다. 다시 시도해주세요.";
    public static final String ERROR_LOGIN_EXPIRED_AUTH_CODE = "오류: 인증 번호가 만료되었습니다. 다시 인증해주세요.";
    public static final String ERROR_LOGIN_INVALID_AUTH_CODE = "오류: 인증 번호가 일치하지 않습니다.";

    // Requested estate related
    public static final String ERROR_ESTATE_REQUEST_DUPLICATE = "오류: 한 회원당 요청 가능한 매물은 하나뿐입니다.";
    public static final String ERROR_ESTATE_REQUEST_NOT_FOUND = "오류: 요청하신 매물을 찾을 수 없습니다.";

    // Real estate related
    public static final String ERROR_ESTATE_NOT_FOUND = "오류: 등록된 매물을 찾을 수 없습니다.";
    public static final String ERROR_ESTATE_DUPLICATE = "오류: 한 회원당 등록 가능한 매물은 하나뿐입니다.";

    // User report related
    public static final String ERROR_USER_REPORT_NOT_FOUND = "오류: 해당 회원의 신고 내역을 찾을 수 없습니다.";
    public static final String ERROR_USER_REPORT_OWN_REPORT = "오류: 본인은 신고 할 수 없습니다.";

    // Estate report related
    public static final String ERROR_ESTATE_REPORT_NOT_FOUND = "오류: 해당 매물의 신고 내역을 찾을 수 없습니다.";
    public static final String ERROR_ESTATE_REPORT_OWN_REPORT = "오류: 자신의 매물은 신고 할 수 없습니다.";

    //wish list related
    public static final String ERROR_WISH_LIST_NOT_FOUND = "오류: 해당 찜 목록이 존재하지 않습니다.";

}
