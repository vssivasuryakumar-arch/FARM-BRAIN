# Publish FARM BRAIN to GitHub from an Android phone or Windows PC

This package is prepared for GitHub, but it has **not** been pushed to a repository. The linked GitHub account currently has no repository available to this workspace, and this session cannot create a new repository on your behalf.

## Create the repository

1. Sign in to GitHub as `vssivasuryakumar-arch`.
2. Open <https://github.com/new?name=farm-brain>.
3. Set repository name to `farm-brain` and choose **Private** while developing.
4. Create an empty repository. Do not add a README or .gitignore because these are already in this package.

## Upload using GitHub website

1. Download and extract `FARM_BRAIN_GitHub.zip`.
2. Open your new empty GitHub repository.
3. Choose **Add file → Upload files**.
4. Upload the *contents* of the extracted `FARM_BRAIN_GitHub` folder. Include the hidden `.github` folder and both workflow files. GitHub browser upload can be awkward for hidden folders; GitHub Desktop is more reliable.
5. Commit to branch `main` with message `Add FARM BRAIN product foundation`.

## Recommended Windows method (GitHub Desktop)

1. Install <https://desktop.github.com/> and sign in.
2. Extract this ZIP.
3. In GitHub Desktop, choose **File → Add local repository** and select the extracted folder. Choose **Create a repository** if the folder is not yet a Git repository.
4. Choose repository name `farm-brain`, then **Publish repository**. Keep it Private for now.
5. Check the GitHub repository page and confirm these files are present: `render.yaml`, `farm_brain/`, `tests/`, `android/`, `.github/workflows/build-android-apk.yml`, and `.github/workflows/test-backend.yml`.

## Deploy to Render

1. Create or sign in to Render: <https://dashboard.render.com/>.
2. Choose **New → Blueprint** and connect the `farm-brain` GitHub repository.
3. Review the Blueprint resources: `farm-brain-web` and `farm-brain-db` in Singapore.
4. Provide `ADMIN_EMAIL` (the exact founder email to receive the first admin role). Leave the optional data.gov API key and resource ID blank until you have official access.
5. Apply the Blueprint and watch the Render deploy logs. Open the web service's URL and confirm `/healthz` returns `{"status":"ok","service":"farm-brain"}`.
6. Create your account using the same email as `ADMIN_EMAIL`; test admin moderation. Set the Android app's website address to this exact HTTPS Render URL.

**Cost note:** the Blueprint uses Render Free for the initial web preview and a small paid PostgreSQL database so user records persist. Render Free web services can sleep and Free Postgres expires after 30 days; this Blueprint deliberately avoids a Free database. Upgrade the web service to paid compute before public production use.

## Build the Android APK

1. Open your GitHub `farm-brain` repository → **Actions**.
2. Select **Build FARM BRAIN Android APK** → **Run workflow** → choose `main` → **Run workflow**.
3. Wait for a green success run.
4. Open that run and download artifact `farm-brain-android-debug-apk`.
5. Extract the artifact ZIP on the phone and install `app-debug.apk`.
6. Open FARM BRAIN → menu `⋮` → **Set website address** → paste the exact Render HTTPS URL.

The artifact is a debug APK for testing. Do not use a debug APK as a Play Store release.

## Protect your original FARM BOOK / farm-brain-demo material

Do not overwrite an earlier repository without taking a backup and reviewing its content. The safest path is to create a new private repository named `farm-brain`, test it, then decide whether to archive or merge the earlier demo.

Never upload `.env`, signing files (`.jks` / `.keystore`), passwords, phone lists or private farm documents.
