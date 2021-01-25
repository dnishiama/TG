package univap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import univap.model.Oid;
import univap.repository.OidRepo;

@Service
public class OidServiceImpl {
	@Autowired
	private OidRepo oidRepo;
	
	public Oid findByDescricao (String descricao) {
		return oidRepo.findByDescricao(descricao);
	}
}
