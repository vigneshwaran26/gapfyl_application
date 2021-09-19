package com.gapfyl.util;


import org.springframework.security.crypto.password.PasswordEncoder;


public class PBKDF2PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence sequence) {
        try {
            return PasswordStorage.createHash(sequence.toString());
        } catch (PasswordStorage.CannotPerformOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean matches(CharSequence sequence, String password) {
        try {
            return PasswordStorage.verifyPassword(sequence.toString(), password);
        } catch (PasswordStorage.CannotPerformOperationException e) {
            throw new RuntimeException(e);
        } catch (PasswordStorage.InvalidHashException e) {
            throw new RuntimeException(e);
        }
    }

}

