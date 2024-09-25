package utils;


import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

public class TestWatcherExtension implements TestWatcher {
    private static boolean hasFailed;

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        hasFailed = true;
        System.out.println("Test: " + context.getDisplayName() + " failed because " + cause.getMessage());
    }

    public static boolean hasFailed() {
        return hasFailed;
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        hasFailed = false;
        System.out.println("Test: " + context.getDisplayName() + " passed");
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        TestWatcher.super.testDisabled(context, reason);
        System.out.println("Test: " + context.getDisplayName() + " is disabled for now");
    }
}
