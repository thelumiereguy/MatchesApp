package com.thelumiereguy.matchesapp.di.qualifierAnnotations

import javax.inject.Qualifier


/**
 * Used to differentiate between Context and Application Context
 *
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ApplicationContext