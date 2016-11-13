package com.github.alokagrawal8.rxfirebase.database;

import android.support.annotation.IntDef;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD }) @IntDef(value = {
    ChildEvent.ADDED, ChildEvent.CHANGED, ChildEvent.MOVED, ChildEvent.REMOVED
}, flag = true) public @interface ChildEventType {

}
