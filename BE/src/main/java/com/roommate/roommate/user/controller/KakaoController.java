package com.roommate.roommate.user.controller;

import com.roommate.roommate.common.DefaultResponseDto;
import com.roommate.roommate.feign.kakao.dto.KakaoInfoDto;
import com.roommate.roommate.user.domain.User;
import com.roommate.roommate.user.dto.response.AccountTokenInfoDto;
import com.roommate.roommate.user.dto.response.UserLoginResponseDto;
import com.roommate.roommate.user.service.KakaoService;
import com.roommate.roommate.user.dto.request.SignUpRequestDto;
import com.roommate.roommate.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class KakaoController {

    private final KakaoService kakaoService;
    private final UserService userService;


    @ApiOperation(value="카카오 로그인 및 회원가입")
    @PostMapping("/oauth/kakao/redirect")
    public ResponseEntity<DefaultResponseDto<Object>> loginOrJoin(HttpServletRequest request,
                                                               @RequestBody SignUpRequestDto signUpRequestDto){

        String code = request.getParameter("code");
        User user = kakaoService.loginOrJoin(code, signUpRequestDto);
        AccountTokenInfoDto accountTokenInfoDto = userService.createToken(user);
        UserLoginResponseDto response = new UserLoginResponseDto(user,accountTokenInfoDto);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("KAKAO_LOGIN")
                        .responseMessage("KAKAO 로그인")
                        .data(response)
                        .build());
    }


}
