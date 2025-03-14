package br.dio.board.persistence.entity;
import java.util.ArrayList;
import lombok.Data;

@Data
public class BoardEntity {

    private Long id;
    private String name;
    private List<BoardColumnEntity> boardColumns = new ArrayList<>();

}
