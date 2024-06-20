package dev.agnaldo.kmpsample.shared.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

object ComponentInjector : KoinComponent {
    inline fun <reified A> loadInjectableComponent(obj: A) {
        val runtimeModule = module { single<A> { obj } }
        getKoin().loadModules(listOf(runtimeModule))
    }
    inline fun <reified A> inject(): A = get()
}
