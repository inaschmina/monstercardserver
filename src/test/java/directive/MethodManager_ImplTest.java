package directive;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MethodManager_ImplTest {
    MethodManager_Impl methodManager = new MethodManager_Impl();

    @Test
    void handleDirectivePackageCreatedNotByAdmin() {
        String username = "me";
        String path = "/packages";
        assertEquals("{\"code\": \"401\", \"message\": \"only admin is authorized to create packages\"}", methodManager.handleDirective("POST", path, null, username));
    }

    @Test
    void handleDirectiveUserDoesNotMatchToken() {
        String username = "me";
        String path = "/users/notme";
        assertEquals("{\"code\": \"401\", \"message\": \"user is not authorized for this action\"}", methodManager.handleDirective("POST", path, null, username));
    }

    @Test
    void handleDirectiveDeckCreationWithoutCards() {
        String username = "me";
        String path = "/deck";
        assertEquals("{\"code\": \"400\", \"message\": \"deck is not configured\"}", methodManager.handleDirective("PUT", path, null, username));
    }

}