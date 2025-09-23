package todo.uninter.controllers;

import todo.uninter.model.Tarefa;
import todo.uninter.repository.TarefaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "*")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    // CREATE - Criar uma nova tarefa
    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@Valid @RequestBody Tarefa tarefa) {
        try {
            Tarefa novaTarefa = tarefaRepository.save(tarefa);
            return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Consultar todas as tarefas
   @GetMapping
   public ResponseEntity<List<Tarefa>> listarTodasTarefas() {
       try {
           List<Tarefa> tarefas = tarefaRepository.findAll();

           if (tarefas.isEmpty()) {
               return new ResponseEntity<>(HttpStatus.NO_CONTENT);
           }

           return new ResponseEntity<>(tarefas, HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

    // READ - Consultar uma tarefa específica pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarTarefaPorId(@PathVariable("id") Long id) {
        try {
            Optional<Tarefa> tarefaData = tarefaRepository.findById(id);

            if (tarefaData.isPresent()) {
                return new ResponseEntity<>(tarefaData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE - Atualizar uma tarefa existente
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable("id") Long id, @Valid @RequestBody Tarefa tarefaAtualizada) {
        try {
            Optional<Tarefa> tarefaData = tarefaRepository.findById(id);

            if (tarefaData.isPresent()) {
                Tarefa tarefa = tarefaData.get();
                tarefa.setNome(tarefaAtualizada.getNome());
                tarefa.setDataEntrega(tarefaAtualizada.getDataEntrega());
                tarefa.setResponsavel(tarefaAtualizada.getResponsavel());

                return new ResponseEntity<>(tarefaRepository.save(tarefa), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE - Remover uma tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removerTarefa(@PathVariable("id") Long id) {
        try {
            if (tarefaRepository.existsById(id)) {
                tarefaRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE - Remover todas as tarefas
    @DeleteMapping
    public ResponseEntity<HttpStatus> removerTodasTarefas() {
        try {
            tarefaRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar tarefas por responsável
    @GetMapping("/responsavel/{responsavel}")
    public ResponseEntity<List<Tarefa>> buscarTarefasPorResponsavel(@PathVariable("responsavel") String responsavel) {
        try {
            List<Tarefa> tarefas = tarefaRepository.findByResponsavel(responsavel);

            if (tarefas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tarefas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar tarefas por nome
    @GetMapping("/buscar")
    public ResponseEntity<List<Tarefa>> buscarTarefasPorNome(@RequestParam String nome) {
        try {
            List<Tarefa> tarefas = tarefaRepository.findByNomeContainingIgnoreCase(nome);

            if (tarefas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tarefas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}