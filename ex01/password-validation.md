# Password Validation in Spring

This document explains how the custom password validation works in our Spring application.

## Overview

Our application uses Jakarta Bean Validation (formerly known as Java Bean Validation) together with custom annotations to enforce password strength requirements. This is implemented through two main components:

1. The `@ValidPassword` annotation
2. The `PasswordValidator` class that handles the validation logic

## The ValidPassword Annotation

The `@ValidPassword` annotation is a custom constraint annotation that can be applied to fields, methods, or parameters that represent passwords.

```java
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Password must be at least 8 characters and contain uppercase, lowercase letters, and at least one digit";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

Key components:
- `@Documented`: Indicates that elements using this annotation should be documented by javadoc tools
- `@Constraint`: Specifies the validator class (PasswordValidator) that will validate this constraint
- `@Target`: Defines where the annotation can be applied (fields, methods, parameters)
- `@Retention`: Specifies how long the annotation information should be kept (RUNTIME means available at runtime via reflection)

## The PasswordValidator Class

The `PasswordValidator` class implements the `ConstraintValidator` interface and contains the actual validation logic:

```java
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        boolean hasLength = password.length() >= 8;
        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasLowercase = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");

        return hasLength && hasUppercase && hasLowercase && hasDigit;
    }
}
```

The validator checks that passwords:
- Are not null
- Are at least 8 characters long
- Contain at least one uppercase letter
- Contain at least one lowercase letter
- Contain at least one digit

## How to Use the Validation

To use this validation in your model classes or DTOs, simply add the `@ValidPassword` annotation to the password field:

```java
public class UserRegistrationDto {
    private String username;
    
    @ValidPassword
    private String password;
    
    // other fields, getters, and setters
}
```

## Validation Process

1. When Spring processes a form submission or REST API request, it automatically triggers validation for fields with constraint annotations
2. For fields annotated with `@ValidPassword`, Spring calls the `PasswordValidator.isValid()` method
3. If validation fails, Spring adds the error message (defined in the annotation) to the binding result
4. Controllers can then check for validation errors and respond accordingly

## Benefits

- Centralized password validation logic
- Reusable across the application
- Easy to modify validation requirements in one place
- Clean separation of validation logic from business logic
