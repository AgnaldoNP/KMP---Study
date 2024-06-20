
## KMP Study Case
### This Project has the sole purpose of serving as a code base for study, without being focused on what is a good practice. It is my first contact with Kotlin Multi Platform

---

This is a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop, Server.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* `/server` is for the Ktor server application.

* `/shared` is for the code that will be shared between all targets in the project.
  The most important subfolder is `commonMain`. If preferred, you can add code to the platform-specific folders here too.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

**Note:** Compose/Web is Experimental and may be changed at any time. Use it only for evaluation purposes.
We would appreciate your feedback on Compose/Web and Kotlin/Wasm in the public Slack channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
If you face any issues, please report them on [GitHub](https://github.com/JetBrains/compose-multiplatform/issues).

You can open the web application by running the `:composeApp:wasmJsBrowserDevelopmentRun` Gradle task.# KMP---Study

--- 

## Build/Run command lines
 > For every non Android  build the default variant is flavor1 debug (-Ptarget=flavor1 -Pvariant=debug), so you don't really need to add target and variant on the command parameters)

### Android
 - `./gradlew :composeApp:assembleAndroidFlavor1Debug`
 - `./gradlew :composeApp:assembleAndroidFlavor1Homolog`
 - `./gradlew :composeApp:assembleAndroidFlavor1Release`
 - `./gradlew :composeApp:assembleAndroidFlavor2Debug`
 - `./gradlew :composeApp:assembleAndroidFlavor2Homolog`
 - `./gradlew :composeApp:assembleAndroidFlavor2Release`
### Web
 - `./gradlew :composeApp:wasmJsBrowserDevelopmentRun`
 - `./gradlew :composeApp:wasmJsBrowserDevelopmentRun -Ptarget=flavor1 -Pvariant=debug`
 - `./gradlew :composeApp:wasmJsBrowserDevelopmentRun -Ptarget=flavor1 -Pvariant=homolog`
 - `./gradlew :composeApp:wasmJsBrowserDevelopmentRun -Ptarget=flavor1 -Pvariant=release`
 - `./gradlew :composeApp:wasmJsBrowserDevelopmentRun -Ptarget=flavor2 -Pvariant=debug`
 - `./gradlew :composeApp:wasmJsBrowserDevelopmentRun -Ptarget=flavor2 -Pvariant=homolog`
 - `./gradlew :composeApp:wasmJsBrowserDevelopmentRun -Ptarget=flavor2 -Pvariant=release`
### Desktop
 - `./gradlew :composeApp:desktopRun -DmainClass=MainKt --quiet`
 - `./gradlew :composeApp:desktopRun -DmainClass=MainKt --quiet -Ptarget=flavor1 -Pvariant=debug`
 - `./gradlew :composeApp:desktopRun -DmainClass=MainKt --quiet -Ptarget=flavor1 -Pvariant=homolog`
 - `./gradlew :composeApp:desktopRun -DmainClass=MainKt --quiet -Ptarget=flavor1 -Pvariant=release`
 - `./gradlew :composeApp:desktopRun -DmainClass=MainKt --quiet -Ptarget=flavor2 -Pvariant=debug`
 - `./gradlew :composeApp:desktopRun -DmainClass=MainKt --quiet -Ptarget=flavor2 -Pvariant=homolog`
 - `./gradlew :composeApp:desktopRun -DmainClass=MainKt --quiet -Ptarget=flavor2 -Pvariant=release`
### IOS
 - `./gradlew :composeApp:buildAndRunIos`
 - `./gradlew :composeApp:buildAndRunIos -Ptarget=flavor1 -Pvariant=debug`
 - `./gradlew :composeApp:buildAndRunIos -Ptarget=flavor1 -Pvariant=homolog`
 - `./gradlew :composeApp:buildAndRunIos -Ptarget=flavor1 -Pvariant=release`
 - `./gradlew :composeApp:buildAndRunIos -Ptarget=flavor2 -Pvariant=debug`
 - `./gradlew :composeApp:buildAndRunIos -Ptarget=flavor2 -Pvariant=homolog`
 - `./gradlew :composeApp:buildAndRunIos -Ptarget=flavor2 -Pvariant=release`
### Server
 - TBD

---

## Desings
 - design based: https://www.figma.com/community/file/996734892252716627/1ux-3ui

## Localized Strings
 _`LocalizedString`_ is a data class that represents a single string in all languages supported
 ```kotlin
data class LocalizedString(
    val pt: String,
    val en: String
) 
````

The _`Strings`_ is an _object_ that hold all your strings that must be a _`LocalizedString`_.
 ```kotlin
 object Strings {
    val hello = LocalizedString(
        pt = "Olá, %s",
        en = "Hello, %s"
    )
}
```

Both classes are localted under **_<u>dev.agnaldo.kmpsample.shared.providers</u>_** and as a commum resource are placed in `./shared/src/commonMain/kotlin/dev/agnaldo/kmpsample/providers/`

To use it the the composeApp you need just to call `Strings.stringName.localize()`. The _localize_ method also accpets arguments, so you can define _hello_ as shown before and call `Strings.hello.localize("Your Name")`.

### How localize works
The localize method relies on the language provided by each platatform implementation and normalized as a `SupportedLanguage` enum
```kotlin
enum class SupportedLanguage(val value: String) {
    PT("pt"),
    EN("en"),
}
```
```kotlin
expect fun getLanguage(): Language

interface Language {
    val name: String

    val supportedLanguage get() = SupportedLanguage.entries
        .firstOrNull { it.value == name }
        ?: SupportedLanguage.EN
}
```
The funcion `getLanguage()` has the reserved word `expect` that means that each platform must implement it and return a instance of `Language`.


Here is the localize method:
```kotlin
fun localize(
    vararg args: Any,
    supportedLanguage: SupportedLanguage = getLanguage().supportedLanguage
): String {
    val value = when (supportedLanguage) {
        SupportedLanguage.PT -> pt
        SupportedLanguage.EN -> en
    }
    return value.format(*args)
}
```

  
### `FlavorStrings` 
Strings that is different between flavors

To have a string that can be different between falvors you need to override, for each flavor in own directory and with the same name, the `AbsFlavorStrings`.
```kotlin
abstract class AbsFlavorStrings {
    abstract val click1: LocalizedString
}
````
 In this project you can see these files on:

 `./shared/src/commonFlavor1/kotlin/dev/agnaldo/kmpsample/shared/providers/FlavorStrings.kt`
 ```kotlin
 object FlavorStrings : AbsFlavorStrings() {
    override val click1 = LocalizedString(
        pt = "Clique aqui Flavor 1",
        en = "Click me Flavor 1"
    )
}
 ```
 `./shared/src/commonFlavor2/kotlin/dev/agnaldo/kmpsample/shared/providers/FlavorStrings.kt`
 ```kotlin
 object FlavorStrings : AbsFlavorStrings() {
    override val click1 = LocalizedString(
        pt = "Clique aqui Flavor 2",
        en = "Click me Flavor 2"
    )
}
 ```

### `VariantStrings`
Strings that is different between build variants

To have a string that can be different between build variants you need to override, for each build variant in its own directory and with the same name, the `AbsVariantStrings`.
```kotlin
abstract class AbsVariantStrings {
    abstract val variantStr: LocalizedString
}
````
 In this project you can see these files on:

 `./shared/src/commonFlavor1Debug/kotlin/dev/agnaldo/kmpsample/shared/providers/FlavorStrings.kt`
 ```kotlin
object VariantStrings : AbsVariantStrings() {
    override val variantStr = LocalizedString(
        pt = "Target: Flavor 1, Variante Debug",
        en = "Target: Flavor 1, Variant Debug"
    )
}
```

Pay attention at **_commonFlavor1Debug_** this is the build variant folder

## Design System
 - TBD

## Flavors
 - TBD
