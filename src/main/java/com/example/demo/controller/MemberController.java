package com.example.demo.controller;

import java.util.List;

import com.example.demo.controller.dto.request.MemberLoginRequest;
import com.example.demo.controller.dto.response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.service.MemberService;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponse>> getMembers() {
        List<MemberResponse> response = memberService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponse> getMember(
        @PathVariable Long id
    ) {
        MemberResponse response = memberService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/members")
    public ResponseEntity<MemberResponse> create(
        @RequestBody MemberCreateRequest request
    ) {
        MemberResponse response = memberService.create(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/members/login")
    public ResponseEntity<LoginResponse> login(@RequestBody MemberLoginRequest request) {
        return ResponseEntity.ok(memberService.login(request));
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<MemberResponse> updateMember(
        @PathVariable Long id,
        @RequestBody MemberUpdateRequest request
    ) {
        MemberResponse response = memberService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteMember(
        @PathVariable Long id
    ) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
