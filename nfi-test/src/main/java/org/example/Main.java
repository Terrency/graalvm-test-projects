package org.example;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class Main {
    public static void main(String[] args) {
        Context c = Context.newBuilder("test").allowAllAccess(true).build();
        Value o = c.eval("test", "");
    }
}