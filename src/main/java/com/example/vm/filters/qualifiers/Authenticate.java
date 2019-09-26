package com.example.vm.filters.qualifiers;


import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;

/**
 * Created by Harriet on 9/25/2019.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD, METHOD, PARAMETER, CONSTRUCTOR, TYPE})
@NameBinding
public @interface Authenticate {
}
