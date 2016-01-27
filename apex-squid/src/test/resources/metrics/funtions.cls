public static void debugStatusMessage() {
    String msg = 'Original value';
    processString(msg);
    // The value of the msg variable didn't
    // change; it is still the old value.
    System.assertEquals(msg, 'Original value');
}