package com.marklogic.gradle.xcc;

import java.net.URI;

import com.marklogic.util.LoggingObject;
import com.marklogic.xcc.AdhocQuery;
import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.ContentSourceFactory;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;

public class XccHelper extends LoggingObject {

    private ContentSource contentSource;

    public XccHelper(String uri) {
        if (logger.isDebugEnabled()) {
            // This will print the password!
            logger.debug("Connecting to XDBC server at ${uri}");
        }
        try {
            this.contentSource = ContentSourceFactory.newContentSource(new URI(uri));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public String executeXquery(String xquery) {
        Session session = contentSource.newSession();
        try {
            if (logger.isInfoEnabled()) {
                logger.info("Executing XQuery: " + xquery);
            }
            AdhocQuery q = session.newAdhocQuery(xquery);
            return session.submitRequest(q).asString();
        } catch (RequestException re) {
            throw new RuntimeException(re);
        } finally {
            session.close();
        }
    }
}
