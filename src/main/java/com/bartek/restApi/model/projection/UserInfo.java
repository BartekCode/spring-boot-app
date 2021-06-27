package com.bartek.restApi.model.projection;

import lombok.Value;

@Value
public class UserInfo {
    String firstName;
    String lastName;
    String email;
}
