package net.jmb.tuto.spring.config.contexte2;

import org.springframework.stereotype.Service;

import net.jmb.tuto.spring.service.CatalogDetailService;
import net.jmb.tuto.spring.service.CatalogServiceInterface;

@Service
public class CatalogService extends CatalogDetailService implements CatalogServiceInterface {

}
