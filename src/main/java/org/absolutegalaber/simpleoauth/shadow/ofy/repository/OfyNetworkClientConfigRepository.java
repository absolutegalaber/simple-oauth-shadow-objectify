package org.absolutegalaber.simpleoauth.shadow.ofy.repository;

import com.googlecode.objectify.ObjectifyFactory;
import lombok.extern.slf4j.Slf4j;
import org.absolutegalaber.simpleoauth.model.networks.NetworkIdentifier;
import org.absolutegalaber.simpleoauth.shadow.ofy.model.OfyNetworkClientConfig;

/**
 * @author Peter Schneider-Manzell
 */
@Slf4j
public class OfyNetworkClientConfigRepository extends BaseOfyRepository {
    public OfyNetworkClientConfigRepository(ObjectifyFactory factory) {
        super(factory);
    }

    public OfyNetworkClientConfig load(NetworkIdentifier networkIdentifier) {
        return ofy().load().type(OfyNetworkClientConfig.class).id(networkIdentifier.getKey()).now();
    }

    public void save(OfyNetworkClientConfig ofyNetworkClientConfig) {
        ofy().save().entity(ofyNetworkClientConfig).now();
        log.info("Updated OfyNetworkClientConfig {}", ofyNetworkClientConfig);
    }
}
