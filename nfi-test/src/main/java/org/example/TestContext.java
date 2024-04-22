package org.example;

import com.oracle.truffle.api.TruffleLanguage;

public class TestContext {
    private TestLanguage lang;
    public TruffleLanguage.Env env;
    public TestContext(TestLanguage lang, TruffleLanguage.Env env) {
        this.lang = lang;
        this.env = env;

    }
}
