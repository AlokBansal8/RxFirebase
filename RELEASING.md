Releasing
========

 1. Change the version in `gradle.properties` to a non-SNAPSHOT version.
 2. Update the `CHANGELOG.md` for the impending release.
 3. Update the `README.md` with the new version.
 4. `git commit -am "Prepare for release X.Y.Z."` (where X.Y.Z is the new version)
 5. `git tag -a X.Y.Z -m "Version X.Y.Z"` (where X.Y.Z is the new version)
 6. Update the dryRun and publish in `bintray-upload.gradle` to false and true respectively
 7. `./gradlew bintrayUpload`
 8. Update the dryRun and publish in `bintray-upload.gradle` to true and false respectively
 9. Update the `gradle.properties` to the next SNAPSHOT version.
 10. `git commit -am "Prepare next development version."`
 11. `git push && git push --tags`
 12. Paste the `CHANGELOG.md` contents for this version into [a Release](https://github.com/AlokBansal8/RxFirebase/releases) on GitHub along with the Groovy for depending on the new version.