package org.apache.airavata.security.configurations;

import junit.framework.TestCase;
import org.apache.airavata.security.Authenticator;
import org.apache.airavata.security.userstore.JDBCUserStore;
import org.apache.airavata.security.userstore.LDAPUserStore;

import java.io.File;
import java.util.List;

/**
 * A test class for authenticator configuration reader.
 * Reads the authenticators.xml in resources directory.
 */
public class AuthenticatorConfigurationReaderTest extends TestCase {

    private String configurationFile = this.getClass().getClassLoader().getResource("authenticators.xml").getFile();

    public void setUp() throws Exception {

        File f = new File(".");
        System.out.println(f.getAbsolutePath());

        File file = new File(configurationFile);

        if (!file.exists() && !file.canRead()) {
            throw new Exception("Error reading configuration file " + configurationFile);

        }
    }

    public void testInit() throws Exception {

        AuthenticatorConfigurationReader authenticatorConfigurationReader
                = new AuthenticatorConfigurationReader();
        authenticatorConfigurationReader.init(configurationFile);

        List<Authenticator> authenticators = authenticatorConfigurationReader.getAuthenticatorList();

        assertEquals(authenticators.size(), 3);

        for (Authenticator authenticator : authenticators) {
            if (authenticator instanceof TestDBAuthenticator1) {
                assertEquals("dbAuthenticator1", authenticator.getAuthenticatorName());
                assertEquals(6, authenticator.getPriority());
                assertEquals(true, authenticator.isEnabled());
                assertEquals("jdbc:sql:thin:@//myhost:1521/mysql1", ((TestDBAuthenticator1) authenticator).getDatabaseURL());
                assertEquals("org.myqsql.Driver1", ((TestDBAuthenticator1) authenticator).getDatabaseDriver());
                assertEquals("mysql1", ((TestDBAuthenticator1) authenticator).getDatabaseUserName());
                assertEquals("secret1", ((TestDBAuthenticator1) authenticator).getDatabasePassword());
                assertNotNull(authenticator.getUserStore());
                assertTrue(authenticator.getUserStore() instanceof JDBCUserStore);
            } else if (authenticator instanceof TestDBAuthenticator2) {
                assertEquals("dbAuthenticator2", authenticator.getAuthenticatorName());
                assertEquals(7, authenticator.getPriority());
                assertEquals(true, authenticator.isEnabled());
                assertEquals("jdbc:sql:thin:@//myhost:1521/mysql2", ((TestDBAuthenticator2) authenticator).getDatabaseURL());
                assertEquals("org.myqsql.Driver2", ((TestDBAuthenticator2) authenticator).getDatabaseDriver());
                assertEquals("mysql2", ((TestDBAuthenticator2) authenticator).getDatabaseUserName());
                assertEquals("secret2", ((TestDBAuthenticator2) authenticator).getDatabasePassword());
                assertNotNull(authenticator.getUserStore());
                assertTrue(authenticator.getUserStore() instanceof LDAPUserStore);
            }  else if (authenticator instanceof TestDBAuthenticator3) {
                assertEquals("dbAuthenticator3", authenticator.getAuthenticatorName());
                assertEquals(8, authenticator.getPriority());
                assertEquals(true, authenticator.isEnabled());
                assertEquals("jdbc:sql:thin:@//myhost:1521/mysql3", ((TestDBAuthenticator3) authenticator).getDatabaseURL());
                assertEquals("org.myqsql.Driver3", ((TestDBAuthenticator3) authenticator).getDatabaseDriver());
                assertEquals("mysql3", ((TestDBAuthenticator3) authenticator).getDatabaseUserName());
                assertEquals("secret3", ((TestDBAuthenticator3) authenticator).getDatabasePassword());
                assertNotNull(authenticator.getUserStore());
                assertTrue(authenticator.getUserStore() instanceof JDBCUserStore);
            }
        }

        assertEquals(8, authenticators.get(0).getPriority());
        assertEquals(7, authenticators.get(1).getPriority());
        assertEquals(6, authenticators.get(2).getPriority());

    }
}
