# ID Austria Credential
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-brightgreen.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
[![Kotlin](https://img.shields.io/badge/kotlin-multiplatform--mobile-orange.svg?logo=kotlin)](http://kotlinlang.org)
[![Kotlin](https://img.shields.io/badge/kotlin-1.9.10-blue.svg?logo=kotlin)](http://kotlinlang.org)
![Java](https://img.shields.io/badge/java-11-blue.svg?logo=OPENJDK)
[![Maven Central](https://img.shields.io/maven-central/v/at.asitplus.wallet/idacredential)](https://mvnrepository.com/artifact/at.asitplus.wallet/idacredential/)

Use data provided by ID Austria as a W3C VC, or ISO 18013-5 Credential, with the help of [KMM VC Lib](https://github.com/a-sit-plus/kmm-vc-library).

Be sure to call `at.asitplus.wallet.idaustria.Initializer.initWithVcLib` first thing in your application.

Implemented attributes are:
 - `firstname`
 - `lastname`
 - `date-of-birth`
 - `portrait`

## Changelog

Release 3.3.0:
 - Use `vclib` 3.3.0
 - Extend list of attributes
 - Rename `ConstantIndex.IdAustriaCredential` to `IdAustriaScheme`
 - Implement `IdAustriaScheme.Attributes` to list known attributes

Release 3.2.1:
 - Correctly publish iOS modules

Release 3.2.0:
 - Use `vclib` 3.2.0