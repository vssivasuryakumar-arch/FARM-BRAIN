# FARM BRAIN — Full-stack agriculture platform foundation

**FARM BRAIN** is the current product and brand. This repository carries forward the earlier FARM BOOK concept and the decisions in the FARM BRAIN master brief and engineering specification. It is a deployable early-release foundation, not a claim that every screen in the 128-route design catalogue is complete.

## What this release is designed to do

FARM BRAIN brings farmers, buyers, sellers, processors, transporters, land and machinery stakeholders, livestock participants, employers, workers, students and agriculture service providers into one account system. A member can select multiple capabilities without opening duplicate accounts.

| Area | What works in this release |
|---|---|
| Farmer / member profile | Public profile, bio, profile/cover image URLs, farm details, crops, method, soil and irrigation fields |
| Farmer Basic | Free plan for accounts registered with **Farmer / Grower** capability |
| Community | Public feed, posts, profiles, follow/unfollow, likes, comments and member notifications |
| Agri Products | Searchable offers and buyer requirements across crop and produce categories |
| Agri Business | Generic listings for factories, transport, land, machinery, electrical components, careers and finance discovery |
| Transport | Different records for **load request** and **vehicle availability**, with origin, destination/cargo/weight or vehicle/capacity fields |
| Livestock | Livestock, products, feed, meat industry, manure and veterinarian discovery categories |
| Farm Aid | Seeds, saplings, farm-made products, fertilizers, machinery, labour, modern farming and learning categories |
| Inventory | Basic stock register, reorder levels and stock movement records |
| Farm records | Personal crop cycle diary with planting/expected harvest dates and status |
| Business profile | Self-managed business profile; it is marked unverified until a separate review exists |
| Moderation | Member reports, admin-only review, hide/resolve/dismiss actions and an audit record |
| API / mobile web | Read-only JSON starting points, responsive website and installable PWA manifest/service worker |
| Android | Android WebView wrapper and GitHub Actions workflow to create an installable debug APK |

## Important workflow rules

- **Offer / supply** and **requirement / demand** are different records.
- An enquiry is a conversation lead, not an order.
- A quotation is indicative. “Accepted for discussion” does not confirm a contract, sale, payment or delivery.
- Transport is discovery and enquiry only. There is no freight booking, live GPS, insurance or proof of delivery.
- Inventory is a basic stock register, not an accounting or full ERP system.
- Finance posts are discovery/enquiry only. There is no lending, brokerage, investment advice or funding guarantee.
- A veterinarian listing is not a diagnosis, emergency service or credential verification.
- Member and farm information is self-reported unless a specific verification status is displayed.

## Technology

- Python 3.12, Flask, Flask-SQLAlchemy, Flask-Login and Flask-WTF
- PostgreSQL on Render; SQLite only for local development and automated test cases
- Gunicorn WSGI server
- Jinja templates, responsive CSS and lightweight JavaScript
- Android WebView app built by GitHub Actions

The current release uses a modular Flask monolith to get a testable product online quickly. The engineering specification's future direction—API-first services, PostgreSQL/PostGIS, richer mobile apps and modular service boundaries—remains the roadmap rather than a claim about the current code.

## Run on Windows

1. Install Python 3.12.
2. Extract this repository folder.
3. Double-click `RUN_FARM_BRAIN.bat`.
4. On the first run it creates a virtual environment and installs `requirements.txt`.
5. Open `http://127.0.0.1:5000` in Chrome.

Or use Command Prompt in the repository folder:

```powershell
py -3.12 -m venv .venv
.venv\Scripts\activate
python -m pip install --upgrade pip
pip install -r requirements.txt
copy .env.example .env
python run.py
```

Local SQLite is for development only. Do not use a local `.db` file as the hosted Render database.

## Tests

```powershell
.venv\Scripts\activate
pip install -r requirements-dev.txt
pytest -q
```

GitHub Actions also runs Python compilation and the test suite on pushes and pull requests.

## Deploy on Render

The root `render.yaml` defines a Render Web Service and a PostgreSQL database in Singapore. Import the GitHub repository into Render as a Blueprint and review the resources before applying.

- The web service uses Render's **Free** tier for first preview only. It can sleep and is not recommended for a production launch.
- PostgreSQL uses the smallest paid Render Postgres plan ID (`0.1c-256mb`) so the database is not the 30-day expiring Free database.
- For public launch, upgrade the web service to a paid compute plan, keep scheduled backups, monitor logs, and configure a real storage service before enabling user photo uploads.
- Set `ADMIN_EMAIL` to the founder/admin email. Register with that same email to receive the admin moderation role. Never add admin access from a public form.
- `OGD_API_KEY` and `OGD_MANDI_RESOURCE_ID` are optional secrets. Leave blank until the official API key and exact resource ID are obtained.

Render Blueprint reference: <https://render.com/docs/blueprint-spec>. Free instance limitations: <https://render.com/docs/free>.

## Agriculture data and external services

| Source | Included behaviour | What is still required |
|---|---|---|
| data.gov.in / AGMARKNET | Optional connector to query the official current daily mandi price dataset | Obtain an API key and the exact resource ID from the official dataset API tab; test the returned fields before showing prices to users |
| IMD Agromet | Direct links to current block/district bulletins and IMD agriculture services | No direct live API is claimed in this release |
| Soil Health Card | Official portal and API guide linked in the information centre | Register as API consumer and obtain authorised credentials/token before integration |
| e-NAM | Official e-NAM portal linked for users | Direct integration requires its approved provider/integration process; FARM BRAIN does not claim to be an empanelled provider |

Full source register, access prerequisites, freshness and safety rules: [`docs/AGRICULTURE_DATA_SOURCES.md`](docs/AGRICULTURE_DATA_SOURCES.md).

Official sources:

- AGMARKNET daily mandi dataset: <https://data.gov.in/catalog/current-daily-price-various-commodities-various-markets-mandi>
- Variety-wise daily mandi prices: <https://data.gov.in/resource/variety-wise-daily-market-prices-data-commodity>
- IMD agriculture meteorological services: <https://mausam.imd.gov.in/responsive/servicesMetAgriculture.php>
- IMD block-level agromet bulletins: <https://mausam.imd.gov.in/imd_latest/contents/agromet/advisory/blocalmain.php>
- Soil Health Card portal: <https://soilhealth.dac.gov.in/>
- Soil Health Card API guide: <https://soilhealth.dac.gov.in/files/SHC_API_Integration_Guidelines.pdf>
- e-NAM: <https://www.enam.gov.in/web/>

The app must show the source, place and retrieval date for external information. A source link is not the same as a connected data feed. Do not use sample values as live prices or turn weather bulletins into personalised crop prescriptions.

## Android APK

The `android/` folder is a WebView companion: it opens the deployed FARM BRAIN website inside a native Android launcher, handles back navigation and allows a photo picker for compatible website file inputs. The website and database remain hosted online.

1. Push this repository to GitHub with `.github/workflows/build-android-apk.yml` included.
2. Open **Actions → Build FARM BRAIN Android APK → Run workflow**.
3. When the run passes, download the `farm-brain-android-debug-apk` artifact.
4. Extract the artifact ZIP and install `app-debug.apk` on the Android device.
5. Open the app menu → **Set website address** and enter the actual HTTPS Render URL for this deployment.

The workflow produces a debug-signed APK for testing. It is not a Play Store release. A release build needs a privately managed signing key, privacy/policy review and device testing. No APK binary is committed to this repository; GitHub Actions creates it.

## Honest limitations / next build phase

Not yet implemented: OTP or email verification, password reset, direct messages/chat, image/file storage, business KYC, organisation/FPO multi-user teams, job applications, livestock-specific workflows, veterinary consultation, land title checks, machinery inspection, route optimisation/GPS freight booking, payments/escrow, accounting-grade ERP, personalised agronomy/AI advice, complete IMD/Soil Health/e-NAM integration, multilingual translations and a production Play Store signing pipeline.

India is the first validation market. UAE commercial work can proceed in parallel as a partner-pilot plan, but this repository does not assert any UAE partner, permit, pilot, funding or operation is signed or live. Tax, payments, import/export and local compliance must be configured and reviewed country by country.

## Repository map

- `farm_brain/` — Flask app, data models, web routes, templates, CSS and client code
- `tests/` — automated product and workflow tests
- `docs/` — product blueprint, architecture, researched agriculture data-source register, route catalogue and roadmap
- `android/` — Android app source
- `.github/workflows/` — backend CI and APK build
- `render.yaml` — Render Web Service + PostgreSQL Blueprint

## Security and release checklist

- Never commit `.env`, database credentials, API keys or Android signing keys.
- Set a long random `SECRET_KEY` in hosting (Render generates one in the Blueprint).
- Keep CSRF enabled; all write forms include CSRF tokens.
- Verify admin email and test admin-only moderation before launch.
- Test registration/login, profile privacy, ownership boundaries, enquiry/quotation actions and stock adjustments on the deployed URL.
- Configure backups, monitoring, spam prevention, email delivery, privacy notice, terms, deletion/export process and local legal review before public commercial use.
