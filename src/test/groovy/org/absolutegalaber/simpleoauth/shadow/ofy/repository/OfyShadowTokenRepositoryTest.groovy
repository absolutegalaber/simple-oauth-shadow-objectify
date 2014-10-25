package org.absolutegalaber.simpleoauth.shadow.ofy.repository

import com.googlecode.objectify.ObjectifyService
import org.absolutegalaber.simpleoauth.model.IClient
import org.absolutegalaber.simpleoauth.shadow.ofy.model.OfyClient
import org.absolutegalaber.simpleoauth.shadow.ofy.model.OfyNetworkClientConfig
import org.absolutegalaber.simpleoauth.shadow.ofy.model.OfyPersistentNetworkToken
import org.absolutegalaber.simpleoauth.shadow.ofy.model.OfyShadowToken


/**
 * @author Peter Schneider-Manzell
 */
class OfyShadowTokenRepositoryTest extends BaseRepositoryTest {

    static {
        ObjectifyService.register(OfyNetworkClientConfig.class);
        ObjectifyService.register(OfyClient.class);
        ObjectifyService.register(OfyPersistentNetworkToken.class);
        ObjectifyService.register(OfyShadowToken.class);
    }


    def "LoadByAccessToken"() {
        given:
        OfyShadowTokenRepository repository = new OfyShadowTokenRepository(ObjectifyService.factory())
        String accountId = "12345"
        String clientId = "test_client"
        IClient client = Mock(IClient)
        client.clientId() >> clientId
        def scopes = ["email"]
        def token = repository.createShadowToken(accountId, client, scopes)

        when:
        def loadedToken = repository.loadByAccessToken(token.accessToken)

        then:
        loadedToken != null
    }

    def "LoadByRefreshToken"() {
        given:
        OfyShadowTokenRepository repository = new OfyShadowTokenRepository(ObjectifyService.factory())
        String accountId = "12345"
        String clientId = "test_client"
        IClient client = Mock(IClient)
        client.clientId() >> clientId
        def scopes = ["email"]
        def token = repository.createShadowToken(accountId, client, scopes)

        when:
        def loadedToken = repository.loadByRefreshToken(token.refreshToken)

        then:
        loadedToken != null
    }

    def "LoadByAccountAndClient"() {
        given:
        OfyShadowTokenRepository repository = new OfyShadowTokenRepository(ObjectifyService.factory())
        String accountId = "12345"
        String clientId = "test_client"
        IClient client = Mock(IClient)
        client.clientId() >> clientId
        def scopes = ["email"]
        repository.createShadowToken(accountId, client, scopes)

        when:
        def token = repository.loadByAccountAndClient(accountId, clientId)

        then:
        token != null
    }

    def "CreateShadowToken"() {
        given:
        OfyShadowTokenRepository repository = new OfyShadowTokenRepository(ObjectifyService.factory())
        String accountId = "12345"
        String clientId = "test_client"
        IClient client = Mock(IClient)
        def scopes = ["email"]

        when:
        client.clientId() >> clientId
        def token = repository.createShadowToken(accountId, client, scopes)

        then:
        token != null
        token.accessToken != null
        token.refreshToken != null
        token.clientId == clientId
        token.accountId == accountId
        token.expiresAt != null
    }
}
