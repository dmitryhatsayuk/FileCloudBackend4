package ru.netology.filecloud.entity;

//import jakarta.persistence.*;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String fileName;

    @Lob
    private byte[] fileContent;

    @ManyToOne
    private User user;

}
