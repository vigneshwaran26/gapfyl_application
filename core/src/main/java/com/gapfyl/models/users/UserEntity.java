package com.gapfyl.models.users;

import com.gapfyl.constants.Collections;
import com.gapfyl.enums.users.ProfileType;
import com.gapfyl.models.common.BaseAuditEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@TypeAlias("users")
@Document(collection = Collections.USERS)
@EqualsAndHashCode(callSuper=false)
@ToString(exclude = {"password"})
public class UserEntity extends BaseAuditEntity implements UserDetails {

    @Size(min = 1, max = 200, message = "Size.user.firstName")
    @Field("first_name")
    private String firstName;

    @Size(max = 200, message = "Size.user.lastName")
    @Field("last_name")
    private String lastName;

    @Size(max = 80, message = "Size.user.Email")
    @Field("email")
    @Indexed(unique = true)
    private String email;

    @Size(min = 6, max = 15, message = "Size.user.mobile")
    @Field("mobile")
    @Indexed(unique = true)
    private String mobile;

    @Field("password")
    private String password;

    @Field("profile_image_url")
    private String profileImageUrl;

    @Field("address")
    private AddressEntity address;

    @Field("roles")
    private List<String> roles;

    @Field("activated")
    private Boolean activated;

    @Field("recovery_token")
    private String recoveryToken;

    @Field("status")
    private Status status;

    @Field("profile_type")
    private ProfileType profileType;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(roles == null || roles.isEmpty() ) return java.util.Collections.<GrantedAuthority>emptyList();
        return  AuthorityUtils.createAuthorityList(roles.toArray(new String[roles.size()]));
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return isActivated();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return isActivated();
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return isActivated();
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return isActivated();
    }

    public boolean isSuperUser() {
        return getRoles() !=null && getRoles().contains(UserRole.ROLE_SUPER_USER.name());
    }

    public boolean isAdmin() {
        return getRoles() !=null && getRoles().contains(UserRole.ROLE_ADMIN.name());
    }

    public boolean isSuperAdmin() {
        return getRoles() !=null && getRoles().contains(UserRole.ROLE_SUPER_ADMIN.name());
    }


    @Transient
    Map<String, Boolean> user_roles;

    public Map getUser_roles() {
        if(user_roles == null || user_roles.isEmpty()) {
            if(roles != null && !roles.isEmpty()) {
                return roles.stream().collect(Collectors.toMap(Function.identity(), value -> Boolean.TRUE));
            }
        }
        return user_roles;
    }

    public void setUser_roles(Map<String, Boolean> _user_roles) {
        this.user_roles = _user_roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public boolean isActivated() {
        return activated == null ? false : activated;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
}
