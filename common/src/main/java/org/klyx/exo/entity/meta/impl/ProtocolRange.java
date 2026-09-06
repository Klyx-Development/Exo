package org.klyx.exo.entity.meta.impl;

public record ProtocolRange(int min, int max) {

    public static ProtocolRange atLeast(int min) {
        return new ProtocolRange(min, Integer.MAX_VALUE);
    }

    public static ProtocolRange atMost(int max) {
        return new ProtocolRange(Integer.MIN_VALUE, max);
    }

    public static ProtocolRange between(int min, int max) {
        return new ProtocolRange(min, max);
    }

    public static ProtocolRange exactly(int version) {
        return new ProtocolRange(version, version);
    }

    public boolean contains(int protocolVersion) {
        return protocolVersion >= min && protocolVersion <= max;
    }

    boolean overlaps(ProtocolRange other) {
        return min <= other.max && other.min <= max;
    }
}
