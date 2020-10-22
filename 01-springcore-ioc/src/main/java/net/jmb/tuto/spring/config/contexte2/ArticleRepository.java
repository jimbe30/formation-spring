package net.jmb.tuto.spring.config.contexte2;

import org.springframework.stereotype.Repository;

import net.jmb.tuto.spring.repository.ArticleDatabaseRepository;
import net.jmb.tuto.spring.repository.ArticleRepositoryInterface;

@Repository
public class ArticleRepository extends ArticleDatabaseRepository implements ArticleRepositoryInterface {

}
