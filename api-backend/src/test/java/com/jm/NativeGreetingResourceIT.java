package com.jm;

import io.quarkus.test.junit.NativeImageTest;

@SuppressWarnings("removal")
@NativeImageTest
public class NativeGreetingResourceIT extends GreetingResourceTest {

    // Ejecuta las mismas pruebas pero en modo nativo.
}
