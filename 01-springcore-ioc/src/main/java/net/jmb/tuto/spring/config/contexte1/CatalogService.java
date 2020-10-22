package net.jmb.tuto.spring.config.contexte1;

import org.springframework.stereotype.Service;

import net.jmb.tuto.spring.service.CatalogBasicService;
import net.jmb.tuto.spring.service.CatalogServiceInterface;

@Service
public class CatalogService extends CatalogBasicService implements CatalogServiceInterface {

}
