# ROCK 3A Android printing target

Target device verified by the project owner:
- Radxa ROCK 3A, RK3568, 4 GB RAM
- Android 11
- USB Type-A ports used in Host mode
- Printers: Epson L3210, Epson L1110, HP LaserJet Pro P1102

## Hardware and Android USB host

ROCK 3A provides dedicated USB 3.0 Host and USB 2.0 Host Type-A ports, plus a USB 3.0 Type-A port switched between Host and OTG/Device. Keep the switch in Host when that switched port is used. Prefer a dedicated Host Type-A port for printers.

The Android manifest declares `android.hardware.usb.host` as optional. Runtime support must still enumerate devices with `UsbManager`, request per-device permission, claim the correct interface/endpoints and release the connection safely. Do not request a global storage permission for USB printing.

## Printer backends

### Epson L3210 and L1110

Investigate Epson's ESC/P-R Android SDK/library and confirm its current supported-model list and licensing before integrating it. The backend must be isolated behind a printer interface so that the editor does not depend on Epson classes. Verify USB enumeration, device permission, paper/media selection, quality settings, cancellation, disconnect/reconnect, and a real print on both exact models.

Do not assume a generic USB bulk transfer or raw PDF bytes are a valid Epson print job. The ESC/P-R protocol/backend must perform the printer-specific rendering and transport.

### HP LaserJet Pro P1102

HP's current USB-OTG support list includes the LaserJet Pro P1102/P1102s/P1102w family. Therefore test the official HP Print Service Plugin first on this Android 11 ROCK 3A using the USB Host port. If the plugin does not enumerate or print on this board's Android build, investigate a separately licensed/maintainable Android backend or a network/host-computer print bridge. Confirm the exact P1102 variant and firmware during physical testing. This printer is monochrome; offer grayscale/black density and text/graphics quality controls, not color ICC controls.

### PDF/system print fallback

Keep the existing Android PrintManager/PDF path as a fallback for printers supported by an installed Android print service. This is not equivalent to a direct USB driver and cannot guarantee arbitrary USB printer support.

## ICC and print pipeline

- Keep source artwork color-managed; do not destructively convert the editable document.
- Import ICC/ICM profiles using Android's Storage Access Framework.
- Associate each output profile with printer model, ink/toner, media type and quality/resolution. A single profile is not valid for all papers.
- Apply one color-management transform only. If TraceDraw converts with the selected output profile, disable additional driver color correction where the printer service exposes that control; otherwise let the driver manage color and do not double-transform.
- Treat PDF/X OutputIntent as embedded print-intent metadata, not proof that Android PrintManager or a printer driver will honor that profile.
- For HP P1102, expose grayscale controls only.

## Implementation status

The Android manifest declares optional USB Host hardware capability. Java UsbHost enumerates attached USB devices, reports VID/PID/interface counts and permission state, requests per-device permission, and opens a device after permission. TraceDrawActivity exposes device listing and permission requests; apps/tracedraw/src/android.rs bridges listing and permission requests to Rust. This is discovery/permission plumbing only: printer-specific backends, UI integration, ICC controls and physical-device print tests remain to be implemented and validated on the owner's ROCK 3A running Android 11.
