package ru.netology.filecloud.entity;

//import jakarta.persistence.*;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class File {
    //Сущность для файла
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String fileName;

    @Lob
    private byte[] fileContent;

    @ManyToOne
    private Users users;

}
