# TraceDraw Android — kustomisasi proyek

## Identitas
- Nama aplikasi: TraceDraw Android
- Gaya: Dark modern
- Ikon launcher: `packaging/android/res/drawable/ic_launcher.xml`
- Package ID tetap `dev.tracedraw.app` pada build pengembangan fork agar perubahan minimal. Ganti ID hanya jika direncanakan sebagai aplikasi terpisah dan seluruh intent/provider/store references diperbarui.

## Workspace dan Android
TraceDraw sudah memiliki Android native build dan Touch workspace. Layout phone/tablet ditentukan oleh implementasi UI yang sudah ada. Periksa `docs/behavior/android.md` dan `docs/behavior/workspaces.md`.

## Preset banner & percetakan
Ukuran/preset yang direncanakan tercantum dalam `assets/templates/print-presets/README.md`. Implementasi UI belum ditambahkan ke editor; spesifikasi ini menjadi baseline untuk PR berikutnya.

## Build
Workflow upstream `.github/workflows/build.yml` sudah punya job Android. Run workflow melalui Actions akan menyimpan APK sebagai artifact bernama `android-apk`. Perubahan pada branch fork juga memicu workflow tersebut bila job dan trigger mencakup branch.
