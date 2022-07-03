package pl.sda.arppl4.hibernateschool.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String name;
    private String surname;
    private String indexNo;
    private LocalDate birthDate;

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Set<Mark> marks;

    public Student(String name, String surname, String indexNo, LocalDate birthDate) {
        this.name = name;
        this.surname = surname;
        this.indexNo = indexNo;
        this.birthDate = birthDate;
    }
}
