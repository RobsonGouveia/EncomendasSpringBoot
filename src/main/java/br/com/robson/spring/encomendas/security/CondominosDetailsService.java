package br.com.robson.spring.encomendas.security;

import br.com.robson.spring.encomendas.business.condominios.domain.Condomino;
import br.com.robson.spring.encomendas.infra.web.repository.CondominosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CondominosDetailsService implements UserDetailsService {

    @Autowired
    private CondominosRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Condomino condomino = this.repository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User not Found"));
        return new org.springframework.security.core.userdetails.User(condomino.getName(), condomino.getCpf(), new ArrayList<>());
    }
}
