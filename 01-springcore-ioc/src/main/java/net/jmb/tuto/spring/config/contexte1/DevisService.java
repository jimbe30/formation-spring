package net.jmb.tuto.spring.config.contexte1;

import org.springframework.stereotype.Service;

import net.jmb.tuto.spring.service.DevisServiceInterface;
import net.jmb.tuto.spring.service.DevisSimpleService;

@Service
public class DevisService extends DevisSimpleService implements DevisServiceInterface {

}
