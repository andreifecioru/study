package dev.afecioru;

import javax.jcr.*;
import javax.jcr.Session;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.jackrabbit.commons.JcrUtils;


/*
select * from [nt:unstructured] where [jcr:path] like '/afecioru/data/%' and id like '%111%'
 */

@Slf4j
public class MainTwo {
    // Try these different URLs for AEM:
    static final String REPO_URL = "http://localhost:4502/crx/server";
    // Alternative URLs to try:
    // static final String REPO_URL = "http://localhost:4502/server";
    // static final String REPO_URL = "http://localhost:4502/crx/repository";
    // static final String REPO_URL = "http://localhost:4502";

    static final String USER = "admin";
    static final String PASSWORD = "admin";

    @FunctionalInterface
    public interface ThrowingConsumer<T, E extends Exception> {
        void accept(T t) throws E;
    }

    static void withSession(Session session, ThrowingConsumer<Session, Exception> cbk) throws RepositoryException {
        try {
            cbk.accept(session);
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
        finally {
            if (session != null) {
                log.info("Logging out of the session");
                session.logout();
            }
        }
    }

    static void inTransaction(Session session, ThrowingConsumer<Session, Exception> cbk) throws RepositoryException {
        try {
            cbk.accept(session);
            log.info("Saving changes");
            session.save();
        } catch (Exception e) {
            session.refresh(false);
            throw new RepositoryException(e);
        }
    }

    public static void main(String[] args) {

        Session session = null;

        try {
            log.info("Attempting to connect to AEM repository at: {}", REPO_URL);

            // Connect to the remote repository
            Repository repository = JcrUtils.getRepository(REPO_URL);
            log.info("Repository connection established successfully");

            log.info("Attempting to login with user: {}", USER);
            session = repository.login(new SimpleCredentials(USER, PASSWORD.toCharArray()));
            log.info("Login successful!");

            withSession(session, (s) -> {
                inTransaction(s, (s2) -> {
                    // Get to ROOT note and add some content
                    Node rootNode = s2.getRootNode();

                    // Add some content
                    log.info("Creating /afecioru/data path");
                    val afecioruFolder = JcrUtils.getOrAddNode(rootNode, "afecioru", "nt:unstructured");
                    val dataFolder = JcrUtils.getOrAddNode(afecioruFolder, "data", "nt:unstructured");
                    dataFolder.setProperty("jcr:title", "afecioru data folder");

                    for (int i = 0; i < 10; i++) {
                        val nodeSuffix = String.format("%05d", i);
                        val dataNode = JcrUtils.getOrAddNode(dataFolder, "data-" + nodeSuffix, "nt:unstructured");
                        dataNode.setProperty("name", "node " + nodeSuffix);
                        dataNode.setProperty("id", "111" + nodeSuffix);
                    }
                });
            });
        } catch (Exception e) {
            log.error("Error occurred: {}", e.getMessage(), e);
            e.printStackTrace();
        }
        finally {
            if (session != null) {
                session.logout();
            }

        }

    }
}
