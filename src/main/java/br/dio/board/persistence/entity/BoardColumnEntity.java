package br.dio.board.persistence.entity;

import lombok.Data;

@Data
public class BoardColumnEntity {

    private Long id;
    private String name;
    private String order;
    private BoardColumnKindEnum kind;

}
