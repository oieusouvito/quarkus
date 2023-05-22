package br.com.controller;

import java.util.List;

import br.com.model.Pessoa;
import br.com.repository.PessoaRepository;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("pessoas")
public class PessoaController {

    @Inject
    PessoaRepository pr;
    @Inject
    EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pessoa> findAll(){
        return pr.findAll();
    }


    @GET
    @Path("/idade")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscaPorIdade(@QueryParam("idade") int idade){
        List<Pessoa> pessoas = pr.buscarPorIdade(idade);
        return Response.ok(pessoas).build();
    }

    @GET
    @Path("/nome")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorNome(@QueryParam("nome") String nome){
        List<Pessoa> pessoas = pr.buscarPorNome(nome);
        return Response.ok(pessoas).build();
    }

    @POST
    @Path("/inserir")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response criaPessoa(Pessoa pessoa){
        em.persist(pessoa);
        return Response.status(201).entity(pessoa).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletePessoa(@PathParam("id") Long id) {
        Pessoa pessoa = pr.findById(id);
        if (pessoa == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        em.remove(pessoa);
        return Response.noContent().build();
}

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response atualizaPessoa(@PathParam("id") Long id, Pessoa pessoaAtualizada) {
        Pessoa pessoa = pr.findById(id);
        if (pessoa == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        pessoa.setNome(pessoaAtualizada.getNome()); 
        pessoa.setIdade(pessoaAtualizada.getIdade());
        

        em.merge(pessoa); 

        return Response.ok(pessoa).build();
    }

}
