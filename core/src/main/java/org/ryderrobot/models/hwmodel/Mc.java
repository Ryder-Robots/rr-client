package org.ryderrobot.models.hwmodel;

public class Mc {

    private String port;
    private String baud;
    private String charsize;
    private String flow_control;
    private String stop_bits;
    private String parity;

    public String getPort() {
        return port;
    }

    public String getBaud() {
        return baud;
    }

    public String getCharsize() {
        return charsize;
    }

    public String getFlowControl() {
        return flow_control;
    }

    public String getStopBits() {
        return stop_bits;
    }

    public String getParity() {
        return parity;
    }
}
