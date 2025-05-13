package com.fsm.security.entities;

import com.fsm.security.UserState;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@MappedEntity
public record User(@Nullable
                   @Id
                   @GeneratedValue
                   Long id,
                   @NotBlank
                   String username,
                   @NotBlank
                   String password,
                   boolean enabled,
                   boolean accountExpired,
                   boolean accountLocked,
                   boolean passwordExpired,
                   @Relation(value = Relation.Kind.ONE_TO_MANY) List<UserRole> roles) implements UserState {

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountExpired() {
        return accountExpired;
    }

    @Override
    public boolean isAccountLocked() {
        return accountLocked;
    }

    @Override
    public boolean isPasswordExpired() {
        return false;
    }

    @Override
    public Long id() {
        return id;
    }
}
