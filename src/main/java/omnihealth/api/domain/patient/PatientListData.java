package omnihealth.api.domain.patient;

public record PatientListData(Long id, String name, String email, String cpf, Boolean active) {

	public PatientListData(Patient patient) {
		this(patient.getId(), patient.getName(), patient.getEmail(), patient.getCpf(), patient.getActive());
	};
}
