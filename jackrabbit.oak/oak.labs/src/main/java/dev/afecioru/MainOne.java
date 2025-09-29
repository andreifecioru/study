package dev.afecioru;

import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Node;

import lombok.extern.slf4j.Slf4j;
import org.apache.jackrabbit.oak.Oak;
import org.apache.jackrabbit.oak.jcr.Jcr;
import org.apache.jackrabbit.oak.segment.SegmentNodeStoreBuilders;
import org.apache.jackrabbit.oak.segment.file.FileStore;
import org.apache.jackrabbit.oak.segment.file.FileStoreBuilder;

import java.io.File;

@Slf4j
public class MainOne {
    public static void main(String[] args) {
        // Path to local repository
        File repoDir = new File("repo");

        Session session = null;

        try (FileStore fileStore = FileStoreBuilder.fileStoreBuilder(repoDir).build()) {
            // Initialize the repo
            Oak oak = new Oak(SegmentNodeStoreBuilders.builder(fileStore).build());
            Jcr jcr = new Jcr(oak);
            Repository repository = jcr.createRepository();

            // Log into a JCR session
            session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));

            // Get to ROOT note and add some content
            Node rootNode = session.getRootNode();

            // Add some content
            log.info("Adding content to the repository");
            if (!rootNode.hasNode("hello")) {
                rootNode
                    .addNode("hello")
                    .addNode("world")
                    .setProperty("message", "Hello World!");

                // Save the changes
                session.save();
                log.info("Content saved in repo");
            }


            // Retrieve the content
            log.info("Retrieving content from the repository");
            Node worldNode = rootNode.getNode("hello/world");
            String message = worldNode.getProperty("message").getString();
            log.info("Message: {}", message);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.logout();
            }
        }
    }
}