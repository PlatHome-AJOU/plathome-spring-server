package com.example.plathome.real_estate.controller;

import com.example.plathome.real_estate.dto.request.EstateForm;
import com.example.plathome.real_estate.dto.request.UpdateEstateForm;
import com.example.plathome.real_estate.dto.response.EstateResponse;
import com.example.plathome.real_estate.dto.response.MapInfoEstateResponse;
import com.example.plathome.real_estate.dto.response.SimpleEstateResponse;
import com.example.plathome.real_estate.dto.search.Filter;
import com.example.plathome.real_estate.service.EstateFilterService;
import com.example.plathome.real_estate.service.EstateService;
import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.Admin;
import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.Login;
import com.example.plathome.member.domain.MemberSession;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/estate")
@RequiredArgsConstructor
@RestController
public class EstateController {
    private final EstateService estateService;
    private final EstateFilterService estateFilterService;

    @PostMapping("/auth")
    public String register(
            @Parameter(hidden = true) @Admin MemberSession memberSession,
            @RequestBody @Valid EstateForm estateForm
    ) {
        estateService.register(estateForm);
        return "success";
    }

    @GetMapping("/no-auth/map")
    public List<MapInfoEstateResponse> getMap() {
        return estateService.getAllMapInfoResponse();
    }

    @GetMapping("/no-auth/board")
    public List<SimpleEstateResponse> getSimple() {
        return estateService.getAllSimpleInfoResponse();
    }

    @GetMapping("/no-auth/{estateId}")
    public EstateResponse getDetail(@PathVariable long estateId) {
        return estateService.getDetail(estateId);
    }

    @DeleteMapping("/auth/{estateId}")
    public String delete(
            @Parameter(hidden = true) @Login MemberSession memberSession,
            @PathVariable long estateId
    ) {
        estateService.delete(memberSession, estateId);
        return "success";
    }

    @PatchMapping("/auth/{estateId}")
    public String update(
            @Parameter(hidden = true) @Login MemberSession memberSession,
            @RequestBody @Valid UpdateEstateForm updateEstateForm
    ) {
        estateService.update(memberSession, updateEstateForm);
        return "success";
    }


    @PostMapping("/no-auth/map/filter")
    public List<MapInfoEstateResponse> getMapInfoByFilter(
            @RequestBody @Valid Filter filter) {
        return estateFilterService.mapFilter(filter);   }

    @PostMapping("/no-auth/board/filter")
    public List<SimpleEstateResponse> getBoardInfoByFilter(
            @RequestBody @Valid Filter filter) {
        return estateFilterService.boardFilter(filter);
    }
}
