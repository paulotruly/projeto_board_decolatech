package br.dio.board.persistence.dao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.mysql.cj.jdbc.StatementImpl;
import br.dio.board.persistence.entity.BoardColumnEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardColumnDAO {

    private final Connection connection;

    public BoardColumnEntity insert(final BoardColumnEntity entity) throws SQLException {
        var sql = "INSERT INTO BOARDS_COLUMNDS (name, `order`, kind, board_id) VALUES (?, ?, ?, ?)";
        
        try(var statement = connection.prepareStatement(sql)) {
            var i = 1;
            statement.setString(i ++, entity.getName());
            statement.setInt(i ++, entity.getOrder());
            statement.setString(i ++, entity.getKind().name());
            statement.setLong(i, entity.getBoard().getId());
            statement.executeUpdate();
            if (statement instanceof StatementImpl impl) {
                entity.setId(impl.getLastInsertID());
            }
            return entity;
        }
    }

    public List<BoardColumnEntity> findByBoardId(final Long id) throws SQLException {
        return null;
    }

}
