# FARM BRAIN — Android APK build and install

This repository includes a native Android WebView wrapper and a GitHub Actions build workflow. It does not include a precompiled APK binary. The workflow compiles `app-debug.apk` after the repository is uploaded to GitHub.

## Build through GitHub Actions

1. Push the entire repository to GitHub, including `.github/workflows/build-android-apk.yml`.
2. Open the repository → **Actions**.
3. Select **Build FARM BRAIN Android APK** → **Run workflow** → branch `main` → **Run workflow**.
4. Wait for the job to finish successfully (green tick).
5. Download artifact **farm-brain-android-debug-apk** from the run page. GitHub provides it as a ZIP.

## Install on Android

1. Extract the downloaded ZIP.
2. Tap `app-debug.apk`.
3. Android may ask to allow installs from the app you used to open the file (for example, Chrome or Files). Enable that permission only for the installer you used.
4. Tap Install, then Open.
5. In the app menu → **Set website address**, paste the exact HTTPS Render URL from your new deployment.

## App behaviour

- Android launcher icon and FARM BRAIN toolbar
- Secure HTTPS-only website address
- WebView cookies / session support
- Android back navigation
- Photo picker for website file inputs
- Retry screen when the hosted website is unreachable
- Website address setting stored on the device

The Android package is a companion shell. Business logic and data live on the deployed website and PostgreSQL database. Offline editing/sync is not implemented.

## Build versions

- Java 17
- Android SDK 36 / Build Tools 36.0.0
- Android Gradle Plugin 9.5.0
- Gradle 9.6.0

The workflow verifies the APK exists and uploads the artifact. The actual build result is only known after GitHub Actions completes.

## Public release warning

This debug APK is installable for internal testing but is not a Play Store release. A production Android release needs a privately controlled signing key, secure secret storage, privacy and permissions review, Play policy compliance, accessibility checks and physical device testing. Never place the private signing key or its password in the repository.
