package it.cosenonjaviste.utils;

import java.util.GregorianCalendar;

public class ClockImpl implements Clock {
    @Override public GregorianCalendar now() {
        return new GregorianCalendar();
    }
}
