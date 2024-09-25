package com.likelion.lionlib.service;

import com.likelion.lionlib.domain.Book;
import com.likelion.lionlib.domain.Member;
import com.likelion.lionlib.domain.Reservation;
import com.likelion.lionlib.dto.ReservationCountResponse;
import com.likelion.lionlib.dto.ReservationRequest;
import com.likelion.lionlib.dto.ReservationResponse;
import com.likelion.lionlib.repository.BookRepository;
import com.likelion.lionlib.repository.MemberRepository;
import com.likelion.lionlib.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;

    private final GlobalService globalService;

    public ReservationResponse addReservation(ReservationRequest request) {
        Member member = globalService.findMemberById(request.getMemberId());
        Book book = globalService.findBookById(request.getBookId());

        Reservation reservation = Reservation.builder()
                .member(member)
                .book(book)
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationResponse.fromEntity(savedReservation);
    }

    public ReservationResponse getReservation(Long reservationId) {
        Reservation reservation = findReservationById(reservationId);
        return ReservationResponse.fromEntity(reservation);
    }

    public void cancelReservation(Long reservationId) {
        Reservation reservation = findReservationById(reservationId);
        reservationRepository.delete(reservation);
    }

    public List<ReservationResponse> getReservationsByMember(Long memberId) {
        Member member = globalService.findMemberById(memberId);
        List<Reservation> reservations = reservationRepository.findAllByMember(member);
        return reservations.stream()
                .map(ReservationResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public ReservationCountResponse getReservationCountByBook(Long bookId) {
        Long count = reservationRepository.countByBookId(bookId);
        return new ReservationCountResponse(count);
    }

    private Reservation findReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
    }
}
