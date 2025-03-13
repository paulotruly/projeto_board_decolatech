package br.dio.board;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.dio.board.persistence.config.ConnectionConfig;
import br.dio.board.persistence.migration.MigrationStrategy;

@SpringBootApplication
public class BoardApplication {

    public static void main(String[] args) throws SQLException {
        // Aqui é onde o Spring Boot inicia o aplicativo. Ele cuida de todo o processo de iniciar a aplicação.
        SpringApplication.run(BoardApplication.class, args);

        try (var connection = ConnectionConfig.getConnection()) {
            // A gente cria uma instância da classe MigrationStrategy, passando a conexão com o banco de dados,
            // para poder usar ela para rodar a migração.
            var migrationStrategy = new MigrationStrategy(connection);
            migrationStrategy.executeMigration(); // Chama o método que vai fazer as mudanças no banco.
        } catch (Exception e) {
            // Se acontecer algum erro durante a migração, mostra o erro no console.
            System.err.println("Erro ao executar a migração: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
