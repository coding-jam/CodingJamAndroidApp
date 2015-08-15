package it.cosenonjaviste.core.utils;

import com.annimon.stream.Collector;
import com.annimon.stream.function.BiConsumer;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Supplier;

public class JoiningCollectors {

    public static Collector<CharSequence, ?, String> joining(final CharSequence delimiter) {
        return new Collector<CharSequence, StringBuilder, String>() {

            @Override
            public Supplier<StringBuilder> supplier() {
                return StringBuilder::new;
            }

            @Override
            public BiConsumer<StringBuilder, CharSequence> accumulator() {
                return (t, u) -> {
                    if (t.length() > 0) {
                        t.append(delimiter);
                    }
                    t.append(u);
                };
            }

            @Override
            public Function<StringBuilder, String> finisher() {
                return StringBuilder::toString;
            }
        };
    }
}
