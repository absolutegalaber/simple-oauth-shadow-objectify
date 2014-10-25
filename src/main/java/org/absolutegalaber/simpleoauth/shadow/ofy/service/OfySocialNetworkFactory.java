package org.absolutegalaber.simpleoauth.shadow.ofy.service;

import com.googlecode.objectify.ObjectifyFactory;
import lombok.extern.slf4j.Slf4j;
import org.absolutegalaber.simpleoauth.model.Network;
import org.absolutegalaber.simpleoauth.model.networks.NetworkIdentifier;
import org.absolutegalaber.simpleoauth.service.builder.NetworkConfigurationService;
import org.absolutegalaber.simpleoauth.shadow.ofy.model.OfyNetworkClientConfig;
import org.absolutegalaber.simpleoauth.shadow.ofy.repository.OfyNetworkClientConfigRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter Schneider-Manzell
 */
@Slf4j
public class OfySocialNetworkFactory implements NetworkConfigurationService {


    private final NetworkIdentifier[] supportedNetworks;
    private OfyNetworkClientConfigRepository networkClientConfigRepository;

    public OfySocialNetworkFactory(ObjectifyFactory factory, NetworkIdentifier[] supportedNetworks) {
        this.supportedNetworks = supportedNetworks;
        networkClientConfigRepository = new OfyNetworkClientConfigRepository(factory);
        loadNetworks();
    }

    @Override
    public Iterable<Network> loadNetworks() {
        log.info("Loading networks...");
        List<Network> result = new ArrayList<>();
        for (NetworkIdentifier supportedNetwork : supportedNetworks) {
            Network network = getNetwork(supportedNetwork);
            if (network != null) {
                result.add(network);
            }
        }
        log.info("Loaded networks {}", result);
        return result;
    }


    @Override
    public Network getNetwork(NetworkIdentifier networkIdentifier) {
        log.info("Searching for network {}", networkIdentifier);
        OfyNetworkClientConfig networkClientConfig = networkClientConfigRepository.load(networkIdentifier);
        if (networkClientConfig != null) {
            return networkIdentifier.createNetwork(networkClientConfig.getClientConfig());
        }
        log.warn("No persistent network config {}  was found", networkIdentifier);
        return null;
    }


}
