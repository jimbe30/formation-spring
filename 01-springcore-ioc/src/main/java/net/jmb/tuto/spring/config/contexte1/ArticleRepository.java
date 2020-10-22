package net.jmb.tuto.spring.config.contexte1;

import org.springframework.stereotype.Repository;

import net.jmb.tuto.spring.repository.ArticleMemoryRepository;
import net.jmb.tuto.spring.repository.ArticleRepositoryInterface;

@Repository
public class ArticleRepository extends ArticleMemoryRepository implements ArticleRepositoryInterface {

}
