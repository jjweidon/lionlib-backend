package com.likelion.lionlib.controller;

import com.likelion.lionlib.dto.ReservationCountResponse;
import com.likelion.lionlib.dto.ReservationRequest;
import com.likelion.lionlib.dto.ReservationResponse;
import com.likelion.lionlib.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReservationController {

    private final ReservationService reservationService;

    // 도서 예약 등록
    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        log.info("Request POST reservation: {}", request);
        ReservationResponse response = reservationService.addReservation(request);
        return ResponseEntity.ok(response);
    }

    // 예약 정보 조회
    @GetMapping("/reservations/{reservationId}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable Long reservationId) {
        log.info("Request GET reservation with ID: {}", reservationId);
        ReservationResponse response = reservationService.getReservation(reservationId);
        return ResponseEntity.ok(response);
    }

    // 예약 취소
    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId) {
        log.info("Request DELETE reservation with ID: {}", reservationId);
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.noContent().build();
    }

    // 사용자 예약 목록 조회
    @GetMapping("/members/{memberId}/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservationsByMember(@PathVariable Long memberId) {
        log.info("Request GET reservations for member with ID: {}", memberId);
        List<ReservationResponse> response = reservationService.getReservationsByMember(memberId);
        return ResponseEntity.ok(response);
    }

    // 도서 예약 수 현황 조회
    @GetMapping("/books/{bookId}/reservations")
    public ResponseEntity<ReservationCountResponse> getReservationCountByBook(@PathVariable Long bookId) {
        log.info("Request GET reservation count for book with ID: {}", bookId);
        ReservationCountResponse response = reservationService.getReservationCountByBook(bookId);
        return ResponseEntity.ok(response);
    }
}