package com.bartek.restApi.registration;

import lombok.Value;

@Value
public class RegistrationUserInfo {
      String firstName;
      String lastName;
      String email;
      String password;
}
