# TraceDraw Android — kustomisasi proyek

## Identitas
- Nama aplikasi: TraceDraw Android
- Gaya: Dark modern
- Ikon launcher: `packaging/android/res/drawable/ic_launcher.xml`
- Package ID fork: `id.senadiaryo488.tracedrawandroid`. Activity Java tetap berada di namespace `dev.tracedraw.app`; provider authority dan tautan Help > Updates sudah diarahkan ke fork.

## Workspace dan Android
TraceDraw sudah memiliki Android native build dan Touch workspace. Layout phone/tablet ditentukan oleh implementasi UI yang sudah ada. Periksa `docs/behavior/android.md` dan `docs/behavior/workspaces.md`.

## Preset banner & percetakan
Preset awal banner 2×1 m, 3×1 m, 4×1 m, 4×1.5 m, X-banner, stiker, dan kartu nama sudah dimasukkan ke dropdown File > New melalui `NewDocSettings::default`. Detail dan batasan cetak ada di `assets/templates/print-presets/README.md`.

## Build
Workflow upstream `.github/workflows/build.yml` sudah punya job Android. Run workflow melalui Actions akan menyimpan APK sebagai artifact bernama `android-apk`. Perubahan pada branch fork juga memicu workflow tersebut bila job dan trigger mencakup branch.
