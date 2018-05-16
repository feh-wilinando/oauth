package br.com.caelum.oauth.commons.repositories;

import br.com.caelum.oauth.commons.models.vos.Role;
import org.springframework.data.repository.Repository;

public interface Roles extends Repository<Role, String> {
    void save(Role role);
}
