package org.absolutegalaber.simpleoauth.shadow.ofy.repository;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import lombok.extern.slf4j.Slf4j;
import org.absolutegalaber.simpleoauth.model.IClient;
import org.absolutegalaber.simpleoauth.shadow.repository.IShadowTokenRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.absolutegalaber.simpleoauth.shadow.ofy.model.OfyShadowToken;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Slf4j
public class OfyShadowTokenRepository extends BaseOfyRepository implements IShadowTokenRepository<OfyShadowToken> {
    private SecureRandom random = new SecureRandom();

    public OfyShadowTokenRepository(ObjectifyFactory factory) {
        super(factory);
    }

    @Override
    public OfyShadowToken loadByAccessToken(String accessToken) {
        return ofy().load().type(OfyShadowToken.class).id(accessToken).now();
    }

    @Override
    public OfyShadowToken loadByRefreshToken(String refreshToken) {
        return ofy().load().type(OfyShadowToken.class).filter("refreshToken", refreshToken).first().now();
    }

    @Override
    public OfyShadowToken loadByAccountAndClient(String accountId, String clientId) {
        return ofy().load().type(OfyShadowToken.class)
                .filter("accountId", accountId)
                .filter("clientId", clientId)
                .first()
                .now();
    }

    @Override
    public OfyShadowToken createShadowToken(String accountId, IClient client, Collection<String> scopes) {
        OfyShadowToken token = new OfyShadowToken();
        token.setAccessToken(createToken(accountId, client, "access"));
        token.setRefreshToken(createToken(accountId, client, "refresh"));
        token.setAccountId(accountId);
        token.setClientId(client.clientId());
        token.setExpiresAt(newExpiry());
        token.getScopes().addAll(scopes);
        Key<OfyShadowToken> inserted = ofy().save().entity(token).now();
        return ofy().load().key(inserted).now();
    }

    private String createToken(String accountId, IClient client, String tokenType) {
        return generateTokenPrefix(accountId, client, tokenType) + generateToken();
    }

    private String generateTokenPrefix(String accountId, IClient client, String tokenType) {
        String toHash = client.clientId() + accountId + tokenType;
        return DigestUtils.md5Hex(toHash);
    }

    private String generateToken() {
        return new BigInteger(130, random).toString(32);
    }

    private Date newExpiry() {
        Calendar expiry = Calendar.getInstance();
        expiry.add(Calendar.HOUR, 3);
        return expiry.getTime();
    }
}
