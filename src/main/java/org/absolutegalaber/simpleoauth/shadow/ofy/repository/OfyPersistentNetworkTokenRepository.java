package org.absolutegalaber.simpleoauth.shadow.ofy.repository;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import org.absolutegalaber.simpleoauth.model.INetworkToken;
import org.absolutegalaber.simpleoauth.shadow.model.IPersistentNetworkToken;
import org.absolutegalaber.simpleoauth.shadow.repository.IPersistenNetworkTokenRepository;
import org.absolutegalaber.simpleoauth.shadow.ofy.model.OfyPersistentNetworkToken;


/**
 * Created by Josip.Mihelko @ Gmail
 */
public class OfyPersistentNetworkTokenRepository extends BaseOfyRepository implements IPersistenNetworkTokenRepository {

    public OfyPersistentNetworkTokenRepository(ObjectifyFactory factory) {
        super(factory);
    }

    @Override
    public IPersistentNetworkToken load(String network, String accountId) {
        return ofy().load().type(OfyPersistentNetworkToken.class)
                .filter("network", network)
                .filter("accountId", accountId)
                .first()
                .now();
    }

    @Override
    public IPersistentNetworkToken create(String accountId, String networkUserid, INetworkToken networkToken) {
        OfyPersistentNetworkToken token = new OfyPersistentNetworkToken();
        token.setAccessToken(networkToken.getAccessToken());
        token.setAccountId(accountId);
        token.setExpiresAt(networkToken.getExpiresAt());
        token.setNetwork(networkToken.getNetwork());
        token.setRefreshToken(networkToken.getRefreshToken());
        token.setTokenSecret(networkToken.getTokenSecret());
        token.setNetworkUserId(networkUserid);
        Key<OfyPersistentNetworkToken> saved = ofy().save().entity(token).now();
        return ofy().load().key(saved).now();
    }
}
