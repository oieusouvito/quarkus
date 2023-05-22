package br.com.repository;

import java.util.List;

import br.com.model.Pessoa;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.ws.rs.QueryParam;

@ApplicationScoped
public class PessoaRepository {

    @Inject
    EntityManager entityManager;

    public List<Pessoa> findAll() {
        return entityManager.createQuery("SELECT e FROM Pessoa e", Pessoa.class)
                .getResultList();
    }

    public List<Pessoa> buscarPorIdade(@QueryParam("idade") int idade) {
        Query query = entityManager.createQuery("SELECT e FROM Pessoa e WHERE e.idade = :idade", Pessoa.class);
        query.setParameter("idade", idade);
        List<Pessoa> pessoas = query.getResultList();
        return pessoas;
    }

        public List<Pessoa> buscarPorNome(@QueryParam("nome") String nome) {
        Query query = entityManager.createQuery("SELECT e FROM Pessoa e WHERE e.nome = :nome", Pessoa.class);
        query.setParameter("nome", nome);
        List<Pessoa> pessoas = query.getResultList();
        return pessoas;
    }


    public Pessoa findById(Long id){
        return entityManager.find(Pessoa.class, id);
    }

}
