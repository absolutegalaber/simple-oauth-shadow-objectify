package org.absolutegalaber.simpleoauth.shadow.ofy.repository

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import spock.lang.Specification

/**
 * @author Peter Schneider-Manzell
 */
class BaseRepositoryTest extends Specification {

    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(
                    new LocalDatastoreServiceTestConfig()
                            .setNoStorage(true).setApplyAllHighRepJobPolicy()
            )

    def setup() {
        helper.setUp()
    }

    def tearDown() {
        helper.tearDown()
    }
}
