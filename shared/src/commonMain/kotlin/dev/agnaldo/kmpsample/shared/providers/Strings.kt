package dev.agnaldo.kmpsample.shared.providers

object Strings {
    val hello = LocalizedString(
        pt = "Olá, %s",
        en = "Hello, %s"
    )
    val or = LocalizedString(
        pt = "ou",
        en = "or"
    )
    val loading = LocalizedString(
        pt = "Processando, aguarde ...",
        en = "Loading, please wait ..."
    )
    val ok = LocalizedString(
        pt = "OK",
        en = "OK"
    )
    val cancel = LocalizedString(
        pt = "Cancelar",
        en = "Cancel"
    )
    val back = LocalizedString(
        pt = "Voltar",
        en = "Back"
    )

    val loginWith = LocalizedString(
        pt = "\uD83D\uDE43 Entrar <b>com</b>",
        en = "Login <b>with</b>"
    )
    val login = LocalizedString(
        pt = "Login",
        en = "Login"
    )
    val forgotPass = LocalizedString(
        pt = "Esqueci a senha",
        en = "Forgot password"
    )
    val loginErrorTitle = LocalizedString(
        pt = "Erro ao tentar realizar o login",
        en = "Error trying to login"
    )
    val loginEmailLabel = LocalizedString(pt = "Email")
    val loginEmailPlaceHolder = LocalizedString(pt = "email@host.com")
    val loginPassLabel = LocalizedString(pt = "Senha", en = "Password")
    val loginPassPlaceHolder = LocalizedString(pt = "********")
    val loginLogoContentDescription = LocalizedString(
        pt = "Meu logo da Aplicação",
        en = "My App logo"
    )
    val loginErrorMessage = LocalizedString(
        pt = "Verifique seu usuário e senha e tente novamente",
        en = "Check your username and password and try again"
    )
    val noLoginCreateAccount = LocalizedString(
        pt = "Não tem uma conta ainda. <a href=\"#\">Criar agora</a>",
        en = "Don’t have an account. <a href=\"#\">Sign up</a>"
    )
}
