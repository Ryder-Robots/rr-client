package org.ryderrobot.models;

/**
 * initial request sent to fat controller.
 */

import static org.ryderrobot.Constants.applicationName;
import static org.ryderrobot.Constants.applicationVersion;

public class ConnectionRequest {
    public ConnectionRequest(String at_hash, String client_id) {
        this.at_hash = at_hash;
        this.client_id = client_id;
    }

    public String getAt_hash() {
        return at_hash;
    }

    public  String getClient_id() {
        return client_id;
    }

    public String getSwname() {
        return swname;
    }

    public String getSwversion() {
        return swversion;
    }

    private final String at_hash;
    private final String client_id;
    private final String swname =  applicationName;
    private final String swversion = applicationVersion;
}
