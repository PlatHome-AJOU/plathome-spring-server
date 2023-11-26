//package com.example.plathome.controller.helper.common;
//
//import com.example.plathome.login.jwt.dto.MemberWithTokenDto;
//import com.example.plathome.login.jwt.dto.request.LoginForm;
//import com.example.plathome.login.jwt.dto.request.SignUpForm;
//import com.example.plathome.login.jwt.dto.response.MemberWithTokenResponse;
//import com.example.plathome.member.domain.Member;
//import com.example.plathome.member.domain.MemberSession;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//
//import javax.crypto.SecretKey;
//import java.util.Base64;
//import java.util.Date;
//import java.util.UUID;
//
//import static com.example.plathome.login.jwt.common.JwtStaticField.ACCESS_TOKEN_EXPIRATION;
//import static com.example.plathome.login.jwt.common.JwtStaticField.REFRESH_TOKEN_EXPIRATION;
//
//public class ControllerObjectBuilder {
//    protected static final Long ID = 1L;
//    protected static final String USERNAME = "nickname";
//    protected static final String USERID = "email@ajou.ac.kr";
//    protected static final String WRONG_USERID = "email";
//    protected static final String PASSWORD = "password";
//    protected static final String WRONG_PASSWORD = "";
//
//    protected static Member createMember() {
//        return Member.of()
//                .id(ID)
//                .nickname(USERNAME)
//                .email(USERID)
//                .password(PASSWORD)
//                .build();
//    }
//
//    protected static MemberSession createMemberSession() {
//        return MemberSession.of()
//                .id(ID)
//                .nickname(USERNAME)
//                .email(USERID)
//                .password(PASSWORD)
//                .build();
//    }
//
//    protected static SignUpForm createSignUpForm() {
//        return SignUpForm.of()
//                .nickname(USERNAME)
//                .email(USERID)
//                .password(PASSWORD)
//                .build();
//    }
//
//    protected static SignUpForm createWrongUserIdSignUpForm() {
//        return SignUpForm.of()
//                .nickname(USERNAME)
//                .email(WRONG_USERID)
//                .password(PASSWORD)
//                .build();
//    }
//
//    protected static SignUpForm createWrongPasswordSignUpForm() {
//        return SignUpForm.of()
//                .nickname(USERNAME)
//                .email(WRONG_USERID)
//                .password(WRONG_PASSWORD)
//                .build();
//    }
//
//    protected static LoginForm createLoginForm() {
//        return LoginForm.of()
//                .email(USERID)
//                .password(PASSWORD)
//                .build();
//    }
//
//    protected static LoginForm createWrongUserIdLoginForm() {
//        return LoginForm.of()
//                .email(WRONG_USERID)
//                .password(PASSWORD)
//                .build();
//    }
//
//    protected static LoginForm createWrongPasswordLoginForm() {
//        return LoginForm.of()
//                .email(USERID)
//                .password(WRONG_PASSWORD)
//                .build();
//    }
//
//    protected static MemberWithTokenDto createMemberWithoutTokenDto() {
//        return MemberWithTokenDto.of()
//                .id(ID)
//                .nickname(USERNAME)
//                .email(USERID)
//                .build();
//    }
//
//    protected static MemberWithTokenDto createMemberWithTokenDto(String refreshToken) {
//        return MemberWithTokenDto.of()
//                .id(ID)
//                .nickname(USERNAME)
//                .email(USERID)
//                .refreshToken(refreshToken)
//                .build();
//    }
//
//    protected static MemberWithTokenResponse createMemberWithoutTokenResponse() {
//        return MemberWithTokenResponse.of()
//                .nickname(USERNAME)
//                .email(USERID)
//                .build();
//    }
//
//    protected static MemberWithTokenResponse createMemberWithTokenResponse(String refreshToken) {
//        return MemberWithTokenResponse.of()
//                .nickname(USERNAME)
//                .email(USERID)
//                .refreshToken(refreshToken)
//                .build();
//    }
//
//    protected static String createRefreshToken(String secretKey) {
//        return Jwts.builder()
//                .subject(USERID)
//                .id(UUID.randomUUID().toString()) // Unique ID for refresh token
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
//                .signWith(Keys.hmacShaKeyFor(createDecodingSecretKey(secretKey)))
//                .compact();
//    }
//
//    protected static String createAccessToken(String secretKey) {
//        return Jwts.builder()
//                .subject(USERID)
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
//                .signWith(Keys.hmacShaKeyFor(createDecodingSecretKey(secretKey)))
//                .compact();
//    }
//
//    protected static String createExpiredAccessToken(String secretKey) {
//        return Jwts.builder()
//                .subject(USERID)
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() - 1000))
//                .signWith(Keys.hmacShaKeyFor(createDecodingSecretKey(secretKey)))
//                .compact();
//    }
//
//    protected static String createExpiredRefreshToken(String secretKey) {
//        return Jwts.builder()
//                .subject(USERID)
//                .id(UUID.randomUUID().toString()) // Unique ID for refresh token
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() - 1000))
//                .signWith(Keys.hmacShaKeyFor(createDecodingSecretKey(secretKey)))
//                .compact();
//    }
//
//    protected static Claims createClaims(String secretKey) {
//        SecretKey key = Keys.hmacShaKeyFor(createDecodingSecretKey(secretKey));
//        return Jwts.parser()
//                .verifyWith(key)
//                .build()
//                .parseSignedClaims(createAccessToken(secretKey))
//                .getPayload();
//    }
//
//    private static byte[] createDecodingSecretKey(String secretKey) {
//        return Base64.getDecoder().decode(secretKey);
//    }
//}
