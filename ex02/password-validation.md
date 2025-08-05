# Password Validation Guide

## ValidPassword Annotation

The `@ValidPassword` annotation is a custom validation annotation that ensures passwords meet specific security requirements. When applied to a password field, it enforces the following rules:

- Minimum length of 8 characters
- At least one uppercase letter
- At least one lowercase letter
- At least one digit
- At least one special character
- No whitespace allowed

## How It Works

1. The `@ValidPassword` annotation is defined as a constraint annotation that can be applied to fields, methods, or parameters.
2. The validation logic is implemented in the `PasswordValidator` class, which checks if the password meets all the required criteria.
3. When validation fails, appropriate error messages are generated and displayed to the user.

## Usage Example

```java
public class User {
    
    @ValidPassword
    private String password;
    
    // Other fields and methods
}
```

When a User object is validated (e.g., during form submission), the password field will be checked against all the defined criteria.

## Error Messages

The validation provides specific error messages for each validation rule that fails, such as:
- "Password must be at least 8 characters"
- "Password must contain at least one uppercase letter"
- "Password must contain at least one lowercase letter"
- "Password must contain at least one digit"
- "Password must contain at least one special character"
- "Password cannot contain whitespace"

These messages can be customized in the application's message properties files for internationalization support.
