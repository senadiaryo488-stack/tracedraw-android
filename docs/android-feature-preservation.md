# Android feature preservation requirements

## Non-regression rule

Android printing and exact-size PDF work are additive. They must not remove, disable, silently replace, or regress existing TraceDraw editor, import, export, automation, colour-management, or AI features. Keep changes on the android-branding-and-print-presets branch; do not modify main.

Before changing format or AI code, inspect the current upstream implementation and the Android packaging path. Preserve existing code paths and provider configuration. If a feature cannot yet be supported on Android, report it clearly rather than hiding or deleting it.

## Import formats to preserve

- Native TraceDraw `.tdraw` and templates.
- CorelDRAW `.cdr` (reader versions 1–27) and CMX.
- SVG and SVGZ.
- PDF, AI and EPS.
- DXF, PSD, EMF, WMF and HPGL/PLT.
- Bitmap images: PNG, JPEG/JPG, BMP, GIF, TIFF and WebP.
- Text/document formats: TXT, RTF and DOCX.

Android document-picker integration must continue to open supported content URIs without requiring broad storage permissions.

## Save and export formats to preserve

- Native `.tdraw` JSON.
- `.cdr` writer versions 7–27.
- SVG, PDF, AI, EPS, DXF, EMF, WMF/PLT, PSD and HTML.
- PNG, JPEG, WebP, GIF, BMP and TIFF.
- PDF/X-1a, PDF/X-3 and PDF/X-4, plus bleed where supported.
- Raster export resolution/DPI and transparent-background options where supported.
- Print through a generated PDF and Android PrintManager.
- Data Merge from CSV.

## Exact-size PDF addition

- PDF page dimensions must match the source document's physical dimensions at 1:1 scale, including custom sizes.
- Do not silently convert custom page sizes to A4/Letter.
- Do not silently apply Fit to page or Shrink to fit.
- Preserve vector content where supported and keep artwork geometry at 1:1.
- Expose the page dimensions in the export summary and validate the PDF page boxes.
- Poster tiling is a separate explicit option, never an implicit export behavior.
- Android print services may impose media limits and margins; communicate those limitations without altering the exported PDF.

## AI features: mandatory preservation

Keep the AI Generate panel and all existing capabilities:
- Generate images from prompts, with aspect ratio, style and palette controls.
- Remix a selected image.
- Generate vector artwork (including image-to-vector tracing workflow).
- Remove image backgrounds.
- Draw with Words: use natural-language instructions to create or modify vector shapes through the existing script/command path.
- Preserve provider configuration for Google Gemini, OpenAI, compatible APIs and local model servers.
- Preserve user-supplied API keys/settings and explicit user-initiated request behavior.
- Do not hard-code one provider, remove local-server support, or send prompts/files without user action.

## Acceptance / regression checks

Before release, compare Android functionality with the existing TraceDraw feature set and test:
1. Open representative files from each supported import family.
2. Save native files and export vector, PDF and raster formats.
3. Verify round-trip/open-save behavior for supported `.cdr` versions.
4. Verify custom-size PDF page boxes and 1:1 dimensions.
5. Verify AI settings remain available and each configured AI workflow still runs.
6. Verify Android document picker, share and print handoff still work.
7. Run existing workspace tests and Android build/CI; record failures instead of claiming success.

This document is a preservation requirement, not a claim that every listed format or AI workflow has already been physically tested on Android.
