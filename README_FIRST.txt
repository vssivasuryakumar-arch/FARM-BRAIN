FARM BRAIN — FIRST RUN

1. Install Python 3.12 on your Windows computer. The starter script checks for Python 3.12 specifically.
2. Extract this ZIP folder.
3. Double-click RUN_FARM_BRAIN.bat.
4. Let the first-time setup install the packages.
5. Open http://127.0.0.1:5000 in Chrome.
6. Register a test farmer account. Farmer / Grower is the free FARMER BASIC plan.

To deploy:
- Follow GITHUB_WINDOWS_GUIDE.md
- Create a new private GitHub repository named farm-brain
- Upload the extracted folder contents, including the hidden .github folder
- Connect the repository to Render as a Blueprint using render.yaml

To get the Android APK:
- Open GitHub repository -> Actions
- Run Build FARM BRAIN Android APK
- Download farm-brain-android-debug-apk artifact
- Extract and install app-debug.apk on your phone
- In the Android app menu, set the actual HTTPS Render website URL

Important:
- The ZIP includes source code and APK automation, not a prebuilt APK binary.
- SQLite is for local testing only. Render Blueprint creates PostgreSQL for hosted data.
- Render Free web is for preview, not a live commercial launch; upgrade before public use.
- Read README.md for actual functionality and limitations.
