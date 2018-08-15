package com.zzq.netlib.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @auther tangedegushi
 * @creat 2018/8/14
 * @Decribe
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface ActivityScope {
}
