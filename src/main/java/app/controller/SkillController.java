package app.controller;

import app.model.Skill;
import app.repository.SkillRepository;
import app.util.Nivel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/skill")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SkillController {

	@Autowired
	private SkillRepository repository;

	@GetMapping("/all")
	public ResponseEntity<List<Skill>> getAll() {
		List<Skill> listSkill = repository.findAll();
		if (listSkill.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

		} else {

			return ResponseEntity.ok(listSkill);
		}
	}

	@GetMapping("/select/{id}")
	public ResponseEntity<Skill> findById(@PathVariable("id") Long idvariavel) {
		return ResponseEntity.status(HttpStatus.OK).body(repository.findById(idvariavel).get());
	}

	@GetMapping("/skill/{skill}")
	public ResponseEntity<List<Skill>> findAllBySkill(@PathVariable("skill") String habilits) {
		List<Skill> list = repository.findAllBySkillContainingIgnoreCase(habilits);
		if (list.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deu Ruim!");
		} else {
			return ResponseEntity.ok(list);

		}
	}
	
	@GetMapping("/technicalSkill/{technicalSkill}")
	public ResponseEntity<List<Skill>> findAllByTechnicalSkill(@PathVariable("technicalSkill") String technicalSkill) {
		List<Skill> list = repository.findAllByTechnicalSkillContainingIgnoreCase(technicalSkill);
		if (list.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deu Ruim!");
		} else {
			return ResponseEntity.ok(list);

		}
	}

	@GetMapping("/nivel/{nivel}")
	public ResponseEntity<List<Skill>> findAllByNivel(@PathVariable("nivel") Nivel nivel) {
		List<Skill> list = repository.findAllByNivel(nivel);
		
		if(list.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lista Vazia!");
		} else { 
			return ResponseEntity.ok(list);
		}
	}

	@PostMapping("/insert")
	public ResponseEntity<Skill> insert(@Valid @RequestBody Skill intoSkill) {

	return ResponseEntity.status(201).body(repository.save(intoSkill));

	}

	@PutMapping("/update")
	public ResponseEntity<Skill> putSkill(@Valid @RequestBody Skill altSkill) {
		return repository.findById(altSkill.getId())
				.map(resp -> ResponseEntity.status(200).body(repository.save(altSkill))).orElseGet(() -> {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID não encontrado");
				});
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Optional<Skill> optional = repository.findById(id);

		if (optional.isPresent()) {

			repository.deleteById(id);
			return ResponseEntity.status(200).build();

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id não encontrado!");
		}
	}

}