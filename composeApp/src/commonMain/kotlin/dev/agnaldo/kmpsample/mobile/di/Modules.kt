package dev.agnaldo.kmpsample.mobile.di

import dev.agnaldo.kmpsample.mobile.ui.home.HomeScreenViewModel
import dev.agnaldo.kmpsample.mobile.ui.login.forgotpassword.ForgotPasswordScreenViewModel
import dev.agnaldo.kmpsample.mobile.ui.login.home.LoginScreenViewModel
import dev.agnaldo.kmpsample.mobile.ui.login.register.RegisterScreenViewModel
import dev.agnaldo.kmpsample.mobile.ui.splash.SplashScreenViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object Modules {
    val appComposeModule = module {
        singleOf(::LoginScreenViewModel)
        factoryOf(::SplashScreenViewModel)
        factoryOf(::HomeScreenViewModel)
        factoryOf(::ForgotPasswordScreenViewModel)
        factoryOf(::RegisterScreenViewModel)
    }
}
