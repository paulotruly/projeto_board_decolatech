package br.dio.board.persistence.migration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MigrationStrategy {

    public final Connection connection;

    public void executeMigration() {
        // Aqui a gente guarda os lugares onde o programa imprime as mensagens no console,
        // para depois poder voltar a imprimir nessas partes depois de terminar o processo.
        var originalOut = System.out;
        var originalErr = System.err;
    
        try (var fos = new FileOutputStream("liquibase.log");
             var printStream = new PrintStream(fos)) {
    
            // A partir daqui, tudo o que o programa tentar imprimir vai para um arquivo chamado liquibase.log.
            System.setOut(printStream);
            System.setErr(printStream);
    
            // Vamos usar o Liquibase para atualizar o banco de dados. Liquibase é uma ferramenta que
            // ajuda a fazer mudanças no banco de dados, como se fosse uma atualização. A gente usa um arquivo
            // chamado db.changelog-master.yml que tem todas as instruções para essas mudanças. 
            // Aqui é onde essas instruções vão ser aplicadas no banco de dados.
            try (var jdbcConnection = new JdbcConnection(this.connection);
                 var liquibase = new Liquibase("/db/changelog/db.changelog-master.yml", new ClassLoaderResourceAccessor(), jdbcConnection)) {
    
                liquibase.update(); // Isso executa a atualização no banco de dados.
    
            } catch (LiquibaseException e) {
                // Se acontecer algum erro, a gente vai imprimir a mensagem de erro no arquivo liquibase.log
                System.err.println("Erro durante a migração: " + e.getMessage());
                e.printStackTrace();
            }
    
        } catch (IOException e) {
            // Se tiver algum problema para escrever no arquivo de log, mostra um erro no arquivo também.
            System.err.println("Erro ao escrever o log: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Por fim, voltamos a imprimir no console normal novamente.
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }
}
