package org.apd;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class AppTest extends Assertions{

    @org.junit.jupiter.api.Test
    public void is10() {
        Assertions.assertEquals(10, App.is10());
    }
}