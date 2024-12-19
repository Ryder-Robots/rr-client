package org.ryderrobot.models.protocols.rrp;

public class MspIdentPayload {
    public MspIdentPayload(final int version,
                    final MultiType multitype,
                    final MspVersion mspversion,
                    final int capability) {
        this.version = version;
        this.multitype = multitype;
        this.mspversion = mspversion;
        this.capability = capability;
    }

    public int getVersion() {
        return version;
    }

    public MultiType getMultitype() {
        return multitype;
    }

    public MspVersion getMspversion() {
        return mspversion;
    }

    public int getCapability() {
        return capability;
    }

    private final int version;
    private final MultiType multitype;
    private final MspVersion mspversion;
    private final int capability;
}
