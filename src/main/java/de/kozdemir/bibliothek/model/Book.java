package de.kozdemir.bibliothek.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@EqualsAndHashCode
@Builder
@Table(name = "books")
public class Book implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "BOOK_ID")
    private long id;

    @NotEmpty(message = "Title is not empty.")
    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "AUTHOR")
    private String author;

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
