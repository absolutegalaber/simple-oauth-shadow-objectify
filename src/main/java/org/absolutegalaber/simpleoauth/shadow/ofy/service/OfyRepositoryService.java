package org.absolutegalaber.simpleoauth.shadow.ofy.service;

import com.googlecode.objectify.ObjectifyFactory;
import org.absolutegalaber.simpleoauth.shadow.repository.IClientRepository;
import org.absolutegalaber.simpleoauth.shadow.repository.IPersistenNetworkTokenRepository;
import org.absolutegalaber.simpleoauth.shadow.repository.IShadowTokenRepository;
import org.absolutegalaber.simpleoauth.shadow.service.IRepositoryService;
import org.absolutegalaber.simpleoauth.shadow.ofy.repository.*;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class OfyRepositoryService implements IRepositoryService {
    private final OfyShadowTokenRepository ofyShadowTokenRepository;
    private final OfyClientRepository ofyClientRepository;
    private final OfyPersistentNetworkTokenRepository ofyPersistentNetworkTokenRepository;

    public OfyRepositoryService(ObjectifyFactory objectifyFactory) {
        ofyShadowTokenRepository = new OfyShadowTokenRepository(objectifyFactory);
        ofyClientRepository = new OfyClientRepository(objectifyFactory);
        ofyPersistentNetworkTokenRepository = new OfyPersistentNetworkTokenRepository(objectifyFactory);
    }

    @Override
    public IShadowTokenRepository getShadowTokenRepository() {
        return ofyShadowTokenRepository;
    }

    @Override
    public IClientRepository getClientRepository() {
        return ofyClientRepository;
    }

    @Override
    public IPersistenNetworkTokenRepository getPersistenNetworkTokenRepository() {
        return ofyPersistentNetworkTokenRepository;
    }
}
