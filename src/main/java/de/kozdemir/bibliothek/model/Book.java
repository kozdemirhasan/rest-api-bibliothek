package de.kozdemir.bibliothek.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@ToString
//@EqualsAndHashCode
@Builder
//@Table(name = "books")
public class Book implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "BOOK_ID")
    private long id;

//    @Size(min = 2, max = 100)
    @Column(name = "TITLE")
    private String title;

//    @Size(min = 2, max = 1000)
    @Column(name = "DESCRIPTION")
    private String description;

//    @Size(min = 2, max = 100)
    @Column(name = "AUTHOR")
    private String author;

//    @Size(min = 2, max = 25)
    @Column(name = "ISBN")
    private String isbn;


    @Enumerated(EnumType.STRING)
    @Column(name = "GENERA")
    private Genera genera;


    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @Column(name = "RENT_DATE")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME )
    private LocalDateTime rentDate;

}
