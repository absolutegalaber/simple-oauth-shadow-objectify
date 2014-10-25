package org.absolutegalaber.simpleoauth.shadow.ofy.repository;

import com.googlecode.objectify.ObjectifyFactory;
import org.absolutegalaber.simpleoauth.shadow.repository.IClientRepository;
import org.absolutegalaber.simpleoauth.shadow.ofy.model.OfyClient;

import java.util.Collection;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class OfyClientRepository extends BaseOfyRepository implements IClientRepository<OfyClient> {

    public OfyClientRepository(ObjectifyFactory factory) {
        super(factory);
    }

    @Override
    public OfyClient load(String clientId) {
        return ofy().load().type(OfyClient.class).id(clientId).now();
    }

    @Override
    public void save(OfyClient client) {
        ofy().save().entity(client).now();
    }

    @Override
    public Collection<OfyClient> all() {
        return ofy().load().type(OfyClient.class).list();
    }
}
