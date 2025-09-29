package dev.afecioru;

import javax.jcr.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.jackrabbit.commons.JcrUtils;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class AEMConnectionTest {
    
    static final String[] REPO_URLS = {
        "http://localhost:4502/crx/server",
        "http://localhost:4502/server", 
        "http://localhost:4502/crx/repository",
        "http://localhost:4502"
    };
    
    static final String USER = "admin";
    static final String PASSWORD = "admin";
    
    public static void main(String[] args) {
        // First, test basic HTTP connectivity
        testHttpConnectivity();
        
        // Then test JCR connections
        testJcrConnections();
    }
    
    private static void testHttpConnectivity() {
        log.info("=== Testing HTTP Connectivity ===");
        
        try {
            URL url = new URL("http://localhost:4502");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            int responseCode = connection.getResponseCode();
            log.info("HTTP response code for http://localhost:4502: {}", responseCode);
            
            if (responseCode == 200) {
                log.info("✓ AEM is accessible via HTTP");
            } else {
                log.warn("⚠ AEM returned HTTP {}", responseCode);
            }
            
        } catch (Exception e) {
            log.error("✗ Cannot connect to AEM via HTTP: {}", e.getMessage());
            log.error("Make sure AEM is running on port 4502");
            return;
        }
    }
    
    private static void testJcrConnections() {
        log.info("=== Testing JCR Connections ===");
        
        for (String repoUrl : REPO_URLS) {
            log.info("Testing repository URL: {}", repoUrl);
            
            try {
                Repository repository = JcrUtils.getRepository(repoUrl);
                log.info("✓ Repository object created successfully");
                
                Session session = repository.login(new SimpleCredentials(USER, PASSWORD.toCharArray()));
                log.info("✓ Login successful with {}/{}", USER, PASSWORD);
                
                // Test basic operations
                Node rootNode = session.getRootNode();
                log.info("✓ Root node accessed: {}", rootNode.getPath());
                
                session.logout();
                log.info("✓ Session closed successfully");
                log.info("SUCCESS: Connection works with URL: {}", repoUrl);
                return;
                
            } catch (Exception e) {
                log.error("✗ Failed with URL {}: {}", repoUrl, e.getMessage());
            }
        }
        
        log.error("All repository URLs failed. Check AEM configuration.");
    }
}
