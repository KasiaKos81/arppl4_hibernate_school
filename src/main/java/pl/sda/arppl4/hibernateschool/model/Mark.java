package pl.sda.arppl4.hibernateschool.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private LocalDateTime dateOfMarkReceived;
    private LocalDateTime dateOfMarkCorrected;
    private Double markValue;
    @Enumerated(EnumType.STRING)
    private Subject subject;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Student student;

    public Mark(LocalDateTime dateOfMarkReceived, Double markValue, Subject subject, Student student) {
        this.dateOfMarkReceived = dateOfMarkReceived;
        this.markValue = markValue;
        this.subject = subject;
        this.student = student;
    }
}
