package com.example.plathome.service;

import com.example.plathome.ObjectBuilder;
import com.example.plathome.login.domain.AccessSecretKey;
import com.example.plathome.login.domain.RefreshSecretKey;
import com.example.plathome.login.exception.ExpiredAccessTokenException;
import com.example.plathome.login.exception.ExpiredRefreshTokenException;
import com.example.plathome.login.exception.InvalidAccessTokenException;
import com.example.plathome.login.exception.InvalidRefreshTokenException;
import com.example.plathome.login.service.JwtValidateService;
import com.example.plathome.login.service.redis.RefreshTokenRedisService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;

import static com.example.plathome.global.constant.JwtStaticField.ACCESS_HEADER;
import static com.example.plathome.global.constant.JwtStaticField.REFRESH_HEADER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비지니스 로직 - 토큰 검증")
@ExtendWith(MockitoExtension.class)
class JwtValidateServiceTest extends ObjectBuilder {
    @InjectMocks private JwtValidateService sut;
    @Mock private RefreshTokenRedisService refreshTokenRedisService;
    @Mock private AccessSecretKey accessSecretKey;
    @Mock private RefreshSecretKey refreshSecretKey;
    
    @DisplayName("AcessToken 검증: input - HttpServletRequest | output StringMemberId")
    @Test
    void givenHttpServletRequest_whenValidationAccessToken_thenReturnsStringMemberId(){
      //given
        String accessToken = createAccessToken(SECRET_KEY);
        MockHttpServletRequest request = createHttpRequestWithToken(ACCESS_HEADER, accessToken);
        given(accessSecretKey.getDecoded()).willReturn(getDecoded(SECRET_KEY));

        //when
        String stringMemberId = sut.validateAccessToken(request);

        //then
        assertThat(stringMemberId).isEqualTo(String.valueOf(1L));
        then(accessSecretKey).should().getDecoded();
    }

    @DisplayName("AcessToken 검증: input - HttpServletRequest With Invalid Token | output 401")
    @Test
    void givenHttpServletRequestWithInvalidToken_whenValidationAccessToken_thenReturnsUnAuthorizedException(){
        //given
        String accessToken = createAccessToken(WRONG_SECRET_KEY);
        MockHttpServletRequest request = createHttpRequestWithToken(ACCESS_HEADER, accessToken);
        given(accessSecretKey.getDecoded()).willReturn(getDecoded(SECRET_KEY));

        //when & then
        assertThrows(InvalidAccessTokenException.class, () -> sut.validateAccessToken(request));
        then(accessSecretKey).should().getDecoded();
    }

    @DisplayName("AcessToken 검증: input - HttpServletRequest With Expired Token | output 401")
    @Test
    void givenHttpServletRequestWithExpiredToken_whenValidationAccessToken_thenReturnsUnAuthorizedException(){
        //given
        String expiredAccessToken = createExpiredAccessToken(SECRET_KEY);
        MockHttpServletRequest request = createHttpRequestWithToken(ACCESS_HEADER, expiredAccessToken);
        given(accessSecretKey.getDecoded()).willReturn(getDecoded(SECRET_KEY));

        //when & then
        assertThrows(ExpiredAccessTokenException.class, () -> sut.validateAccessToken(request));
        then(accessSecretKey).should().getDecoded();
    }

    @DisplayName("RefreshToken 검증: input - HttpServletRequest | output StringMemberId")
    @Test
    void givenHttpServletRequest_whenValidationRefreshToken_thenReturnsStringMemberId(){
        //given
        String refreshToken = createRefreshToken(SECRET_KEY);
        MockHttpServletRequest request = createHttpRequestWithToken(REFRESH_HEADER, refreshToken);
        given(refreshSecretKey.getDecoded()).willReturn(getDecoded(SECRET_KEY));
        given(refreshTokenRedisService.checkExistValue(String.valueOf(1L))).willReturn(true);
        given(refreshTokenRedisService.getData(String.valueOf(1L))).willReturn(refreshToken);

        //when
        String stringMemberId = sut.validateRefreshToken(request);

        //then
        assertThat(stringMemberId).isEqualTo(String.valueOf(1L));
        then(refreshSecretKey).should().getDecoded();
        then(refreshTokenRedisService).should().checkExistValue(String.valueOf(1L));
        then(refreshTokenRedisService).should().getData(String.valueOf(1L));
    }

    @DisplayName("RefreshToken 검증: input - HttpServletRequest With Invalid Token | output 401")
    @Test
    void givenHttpServletRequestWithInvalidToken_whenValidationRefreshToken_thenReturnsUnAuthorizedException(){
        //given
        String refreshToken = createRefreshToken(WRONG_SECRET_KEY);
        MockHttpServletRequest request = createHttpRequestWithToken(REFRESH_HEADER ,refreshToken);
        given(refreshSecretKey.getDecoded()).willReturn(getDecoded(SECRET_KEY));

        //when & then
        assertThrows(InvalidRefreshTokenException.class, () -> sut.validateRefreshToken(request));
        then(refreshSecretKey).should().getDecoded();
        then(refreshTokenRedisService).shouldHaveNoInteractions();
    }

    @DisplayName("RefreshToken 검증: input - HttpServletRequest With Expired Token | output 401")
    @Test
    void givenHttpServletRequestWithExpiredToken_whenValidationRefreshToken_thenReturnsUnAuthorizedException(){
        //given
        String expiredRefreshToken = createExpiredRefreshToken(SECRET_KEY);
        MockHttpServletRequest request = createHttpRequestWithToken(REFRESH_HEADER, expiredRefreshToken);
        given(refreshSecretKey.getDecoded()).willReturn(getDecoded(SECRET_KEY));

        //when & then
        assertThrows(ExpiredRefreshTokenException.class, () -> sut.validateRefreshToken(request));
        then(refreshSecretKey).should().getDecoded();
        then(refreshTokenRedisService).shouldHaveNoInteractions();
    }
}
