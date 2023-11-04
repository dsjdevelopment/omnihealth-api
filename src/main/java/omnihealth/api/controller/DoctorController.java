package omnihealth.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import omnihealth.api.domain.doctor.Doctor;
import omnihealth.api.domain.doctor.DoctorDetailData;
import omnihealth.api.domain.doctor.DoctorListData;
import omnihealth.api.domain.doctor.DoctorRegistrationData;
import omnihealth.api.domain.doctor.DoctorRepository;
import omnihealth.api.domain.doctor.DoctorUpdateData;

@RestController
@RequestMapping("/doctors")
@SecurityRequirement(name = "bearer-key")
public class DoctorController {

	@Autowired
	private DoctorRepository repository;

	@PostMapping
	@Transactional
	public ResponseEntity register(@RequestBody @Valid DoctorRegistrationData data, UriComponentsBuilder uriBuilder) {
		var doctor = new Doctor(data);
		repository.save(doctor);

		var uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();

		return ResponseEntity.created(uri).body(new DoctorDetailData(doctor));
	}

	@GetMapping
	public ResponseEntity<Page<DoctorListData>> list(
			@PageableDefault(size = 10, sort = { "name" }) Pageable pagination) {
		var page = repository.findAllByActiveTrue(pagination).map(DoctorListData::new);

		return ResponseEntity.ok(page);
	}

	@PutMapping
	@Transactional
	public ResponseEntity update(@RequestBody @Valid DoctorUpdateData data) {
		var doctor = repository.getReferenceById(data.id());
		doctor.dataUpdate(data);

		return ResponseEntity.ok(new DoctorDetailData(doctor));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity delete(@PathVariable Long id) {
		var doctor = repository.getReferenceById(id);
		doctor.delete();

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity detail(@PathVariable Long id) {
		var doctor = repository.getReferenceById(id);

		return ResponseEntity.ok(new DoctorDetailData(doctor));
	}
}
