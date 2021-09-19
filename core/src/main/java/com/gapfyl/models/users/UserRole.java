package com.gapfyl.models.users;

public enum UserRole {
    ROLE_USER,
    ROLE_CREATOR,
    ROLE_ORGANIZATION,
    ROLE_SCHOOL,
    ROLE_LEARNER,
    ROLE_SUPER_USER, //Manager/Above who has approval permission
    ROLE_ADMIN, //Admin user from product with read access
    ROLE_SUPER_ADMIN //Admin user of Product with all access
}
