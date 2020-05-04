package br.com.teste.climbteste.model;

import br.com.climb.core.PersistentEntity;
import br.com.climb.core.mapping.Entity;
import br.com.climb.core.mapping.QueryResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


//@Data
@Getter
@Setter
@ToString
@QueryResult
@Entity(name = "tb_book")
public class Book extends PersistentEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
    private String name;

}