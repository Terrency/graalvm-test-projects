package org.example;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.debug.DebuggerTags;
import com.oracle.truffle.api.instrumentation.ProvidedTags;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.source.Source;

import java.nio.file.Path;


@TruffleLanguage.Registration(id = "test", name = "TEST", characterMimeTypes = "application/x-test", dependentLanguages = {"nfi"}, contextPolicy = TruffleLanguage.ContextPolicy.SHARED,  //
        website = "https://www.graalvm.org/graalvm-as-a-platform/implement-language/")
@ProvidedTags({StandardTags.CallTag.class, StandardTags.StatementTag.class, StandardTags.RootTag.class, StandardTags.RootBodyTag.class, StandardTags.ExpressionTag.class, DebuggerTags.AlwaysHalt.class, StandardTags.ReadVariableTag.class, StandardTags.WriteVariableTag.class})
public class TestLanguage extends TruffleLanguage<TestContext> {
    @Override
    protected CallTarget parse(ParsingRequest request) throws Exception {
        Env env = getCurrentContext(this.getClass()).env;
        try {
            String dllPath = Path.of("libs", "liblapack.dll").toAbsolutePath().toString();
            String nfiSource = String.format("load '%s'", dllPath);
            Source source = Source.newBuilder("nfi", nfiSource, "loadLibrary").build();
            CallTarget target = env.parseInternal(source);
            Object obj = target.call();
            return target;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected TestContext createContext(Env env) {
        return new TestContext(this, env);
    }
}
