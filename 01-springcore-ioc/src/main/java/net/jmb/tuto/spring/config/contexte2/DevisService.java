package net.jmb.tuto.spring.config.contexte2;

import org.springframework.stereotype.Service;

import net.jmb.tuto.spring.service.DevisRemiseService;
import net.jmb.tuto.spring.service.DevisServiceInterface;

@Service
public class DevisService extends DevisRemiseService implements DevisServiceInterface {

}
