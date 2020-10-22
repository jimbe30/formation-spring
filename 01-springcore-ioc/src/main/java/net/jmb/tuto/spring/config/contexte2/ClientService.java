package net.jmb.tuto.spring.config.contexte2;

import org.springframework.stereotype.Service;

import net.jmb.tuto.spring.service.ClientServiceInterface;
import net.jmb.tuto.spring.service.ClientSimpleService;

@Service
public class ClientService extends ClientSimpleService implements ClientServiceInterface {

}
