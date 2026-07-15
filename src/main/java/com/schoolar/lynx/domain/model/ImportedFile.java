package com.schoolar.lynx.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "imported_file")
@Builder
public class ImportedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


}
