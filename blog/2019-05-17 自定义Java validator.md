# 自定义Java Validator

在项目中，针对汉字的长度计算，数据库和java的计算方式不一致，需要重新处理下java 的 Validator，使其满足项目

建立自定义的 validator annotation

```java
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = SizeValidator.class)		// 对应的验证class
public @interface DbSize {

    String message() default "{javax.validation.constraints.Size.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return size the element must be higher or equal to
     */
    int min() default 0;

    /**
     * @return size the element must be lower or equal to
     */
    int max() default Integer.MAX_VALUE;

}

```

创建验证的执行类

```java
public class DbSizeValidator implements ConstraintValidator<DbSize, String> {

    @Value("${db.charset:GBK}")
    private String charset;

    private DbSize size;

    @Override
    public void initialize(DbSize constraintAnnotation) {
        this.size = constraintAnnotation;
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || "".equals(value)) {
            return size.min() == 0 ? true : false;
        }
        int length = 0;
        try {
            length = value.getBytes(charset).length;
        } catch (Exception e) {
            try {
                length = value.getBytes(Charset.forName("GBK")).length;
            } catch (Exception e1){
                // 如果失败，虚拟机就直接狗带了，不需要了
                length = value.getBytes().length;
            }
        }
        return (length > size.max() || length < size.min()) ? false : true;
    }
}
```

测试

```java
public class DbSizeValidatorTest {

    @Test
    public void validatorTest() {
        ValidationModel model = new ValidationModel();
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<ValidationModel>> validate = validator.validate(model);
        List<String> messageList = new ArrayList<>();
        for (ConstraintViolation<ValidationModel> constraintViolation : validate) {
            messageList.add(constraintViolation.getMessage());
        }

        Assertions.assertEquals(true, messageList.stream().map(el -> "请使用类型".equals(el)).count() == 1);
        messageList.stream().forEach(System.out::println);
    }


    @Data
    private class ValidationModel {
        @DbSize(max = 2, message = "请使用类型")
        private String xdoSize = "错误";

        @DbSize(max = 2, message = "xdoSize正确")
        private String xdoSize1 = "对";

        @DbSize(max = 5, message = "xdoSizeEn正确")
        private String xdoSize2 = "对aB2";
    }
}

```

