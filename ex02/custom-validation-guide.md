# Custom Validation Guide for Java Developers

## Creating Custom Validation Annotations

When standard validation annotations provided by the Jakarta Bean Validation API (formerly known as JSR-380) aren't sufficient for your specific requirements, you can create custom validation annotations. This guide explains the process step by step.

## Step 1: Define the Annotation

First, create your custom annotation:

```java
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyConstraintValidator.class)
public @interface MyCustomConstraint {
    String message() default "Invalid value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    // Optional additional parameters
    int min() default 0;
    int max() default Integer.MAX_VALUE;
}
```

## Step 2: Implement the ConstraintValidator

Create a validator class that implements the logic:

```java
public class MyConstraintValidator implements ConstraintValidator<MyCustomConstraint, Object> {
    
    private int min;
    private int max;
    
    @Override
    public void initialize(MyCustomConstraint constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }
    
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // Implement your validation logic here
        if (value == null) {
            return true; // null values are handled by @NotNull
        }
        
        // Example validation logic
        boolean isValid = /* your validation rules */;
        
        if (!isValid) {
            // You can customize error messages
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Custom error message")
                   .addConstraintViolation();
        }
        
        return isValid;
    }
}
```

## Step 3: Apply the Annotation

Use your custom annotation in your model classes:

```java
public class User {
    @MyCustomConstraint(min = 5, max = 10, message = "Value must be between 5 and 10")
    private String someField;
    
    // Getters and setters
}
```

## Best Practices

1. **Composition**: Consider composing complex validations from simpler ones
2. **Reusability**: Design annotations to be reusable across different models
3. **Localization**: Use message keys instead of hardcoded strings to support internationalization
4. **Testing**: Write unit tests for your validators to ensure they work correctly
5. **Documentation**: Document your custom annotations well so other developers understand how to use them

## Common Validator Types

The `ConstraintValidator` interface takes two type parameters:
- The annotation type
- The type of the element to validate

For example:
- `ConstraintValidator<MyAnnotation, String>` for validating String fields
- `ConstraintValidator<MyAnnotation, Integer>` for validating Integer fields
- `ConstraintValidator<MyAnnotation, Object>` for generic validation

You can also create multiple validators for the same annotation to support different types.
