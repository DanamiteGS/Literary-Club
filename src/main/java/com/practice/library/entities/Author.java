package com.practice.library.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "Authors")
@Table(name = "Authors")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
/* @SQLDelete(sql = "UPDATE Author SET deactivationDate = '' WHERE idAuthor = ?")
@Where(clause = "deactivationDate IS NULL") */
public class Author {

    /*
     TODO: ALLOW ADMIN TO UPLOAD AN IMAGE OF THE AUTHOR.
     TODO: EACH AUTHOR CAN HAVE MORE THAN ONE GENRE ASSIGNED
    */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAuthor;

    @Column(nullable = false)
    private String nameAuthor;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Book> books;

    @CreatedDate
    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate creationDate;

    @Column(columnDefinition = "DATE")
    private LocalDate  deactivationDate;

    @LastModifiedDate
    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate lastModificationDate;

    @OneToOne(fetch = FetchType.LAZY)
    private Description aboutAuthor;

    /*

    @Column(nullable = false)
    private String authorImgPath;

    @Enumerated(EnumType.ORDINAL)
    private List<Genre> genres;

    */
}