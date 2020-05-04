package br.com.teste.climbteste;

import br.com.climb.core.interfaces.ManagerFactory;
import br.com.climb.core.interfaces.ResultIterator;
import br.com.teste.climbteste.model.Book;
import br.com.teste.climbteste.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

@SpringBootTest
@ComponentScan("br.com.climb.climbspring")
class ClimbtesteApplicationTests {

    @Autowired
    private BookRepository repository;

    @Test
    void contextLoads()
    {

        ResultIterator resultIterator = repository.getIdNome(22l, "taliba");

        while (resultIterator.next()) {
            Book book = (Book) resultIterator.getObject();
            System.out.println("NNN: " + book);
            repository.delete(book.getId());
        }


//        Book book = new Book();
//        book.setName("Livro de teste");
//        repository.save(book);
//
//        book = repository.findOne(book.getId());
//
//        ResultIterator iterator = repository.find();
//
//        while (iterator.next()) {
//            Book book1 = (Book) iterator.getObject();
//            System.out.println("YYYY: " + book1);
//        }

//        System.out.println(book);
//        book.setName("atualizado");
//
//        repository.update(book);
//        System.out.println(book);
//
//        repository.delete(book);
//        System.out.println(book.getId());


//        repository.delete(book);

//        List<Book> books = repository.buscar();
//
//        book.setName("novo nome");
//        repository.save(book);
//        repository.delete(book);
    }

}
