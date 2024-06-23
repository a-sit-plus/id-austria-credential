# ID Austria Credential
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-brightgreen.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
[![Kotlin](https://img.shields.io/badge/kotlin-multiplatform--mobile-orange.svg?logo=kotlin)](http://kotlinlang.org)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
![Java](https://img.shields.io/badge/java-17-blue.svg?logo=OPENJDK)
[![Maven Central](https://img.shields.io/maven-central/v/at.asitplus.wallet/idacredential)](https://mvnrepository.com/artifact/at.asitplus.wallet/idacredential/)

Use data provided by ID Austria as a W3C VC, or ISO 18013-5 Credential, with the help of [KMM VC Lib](https://github.com/a-sit-plus/kmm-vc-library).

Be sure to call `at.asitplus.wallet.idaustria.Initializer.initWithVcLib` first thing in your application.

Implemented attributes are:
 - `bpk`, in OIDC `urn:pvpgvat:oidc.bpk`
 - `firstname`, in OIDC `given_name`
 - `lastname`, in OIDC `family_name`
 - `date-of-birth`, in OIDC `birthdate`
 - `portrait`, in OIDC `org.iso.18013.5.1:portrait`
 - `main-address`, in OIDC `urn:eidgvat:attributes.mainAddress` (see <https://eid.egiz.gv.at/meldeadresse/>)
 - `age-over-14`, in OIDC `org.iso.18013.5.1:age_over_14`
 - `age-over-16`, in OIDC `org.iso.18013.5.1:age_over_16`
 - `age-over-18`, in OIDC `org.iso.18013.5.1:age_over_18`
 - `age-over-21`, in OIDC `org.iso.18013.5.1:age_over_21`
 - `vehicle-registration`, in OIDC `urn:eidgvat:attributes.vehicleRegistrations` (see <https://eid.egiz.gv.at/zulassungsscheindaten/>)
 - `gender`, in OIDC `urn:eidgvat:attributes.gender`

## Changelog

Release 3.8.1:
 - Fix compatiblity with `vclib` 3.8.0

Release 3.8.0:
 - Extend list of attributes: Add vehicle registration, gender
 - Compatibility with `vclib` 3.8.0

Release 3.5.0:
 - Compatibility with KMP Crypto 3.2.0
 - Compatibility with `vclib` >=3.8.0-SNAPSHOT

Release 3.4.0:
 - Use `vclib` 3.4.0

Release 3.3.0:
 - Use `vclib` 3.3.0
 - Extend list of attributes
 - Rename `ConstantIndex.IdAustriaCredential` to `IdAustriaScheme`
 - Implement `IdAustriaScheme.Attributes` to list known attributes

Release 3.2.1:
 - Correctly publish iOS modules

Release 3.2.0:
 - Use `vclib` 3.2.0
