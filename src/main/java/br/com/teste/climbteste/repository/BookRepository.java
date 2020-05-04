package br.com.teste.climbteste.repository;

import br.com.climb.climbspring.ClimbRepository;
import br.com.climb.core.interfaces.ResultIterator;
import br.com.climb.climbspring.annotations.ClimbNativeQuery;
import br.com.teste.climbteste.model.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ClimbRepository<Book, Long> {

    @ClimbNativeQuery("select * from tb_book where id = ?1 or name = '?2'")
    ResultIterator getIdNome(Long id, String nome);

    @ClimbNativeQuery("delete from tb_book where id = ?1")
    void delete(Long id);
}
