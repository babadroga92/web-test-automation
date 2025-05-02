package org.selenium.pom.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingAddress {
    private String firstName;
    private String lastName;
    private String country;
    private String addressLineOne;
    private String city;
    private String postalCode;
    private String email;
}
