package server.services;

import common.Token;

/* InputValidator class
 * This class offers some methods to perform sanity checks on user inputs and any other kind of input validation.
 */
public class InputValidator {


    // Returns true if username contains an @ and does neither start nor end with a "."
    public boolean validateUsername(String username) {
        String[] usernameParts = username.split("@");
        return usernameParts.length != 0 && !usernameParts[0].startsWith(".") && !usernameParts[1].endsWith(".");
    }

    // Returns true if password is at least 3, at max 20 characters long.
    public boolean validatePassword(String password) {
        return password.length() >= 3 && password.length() <= 20;
    }

    // Returns false if the token expired
    public boolean isTokenStillAlive(Token token) {
        return (System.currentTimeMillis() / 1000L) < token.getUnixTimeOfDeath();
    }

}
