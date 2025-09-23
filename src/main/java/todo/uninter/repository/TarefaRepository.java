package todo.uninter.repository;

import todo.uninter.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    // Buscar tarefas por responsável
    List<Tarefa> findByResponsavel(String responsavel);

    // Buscar tarefas por data de entrega
    List<Tarefa> findByDataEntrega(LocalDate dataEntrega);

    // Buscar tarefas que vencem até uma determinada data
    @Query("SELECT t FROM Tarefa t WHERE t.dataEntrega <= :data")
    List<Tarefa> findTarefasVencendoAte(@Param("data") LocalDate data);

    // Buscar tarefas por nome (contém)
    List<Tarefa> findByNomeContainingIgnoreCase(String nome);

    // Contar tarefas por responsável
    @Query("SELECT COUNT(t) FROM Tarefa t WHERE t.responsavel = :responsavel")
    Long countByResponsavel(@Param("responsavel") String responsavel);
}