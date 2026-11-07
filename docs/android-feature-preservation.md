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


## Image editing and clean background removal

Image editing must remain available on Android, including the existing on-device background-removal path. Treat background removal as a core image-editing capability, not only as an AI prompt feature.

### Quality requirements

- Remove backgrounds cleanly while preserving the main subject, including fine edges such as hair, fur, thin lines, transparent objects and product contours where feasible.
- Avoid visible halos, jagged edges, leftover background pixels, excessive subject cut-off and unwanted holes.
- Provide a transparent checkerboard preview and export with a real alpha channel (PNG/WebP where supported); do not replace transparency with a white background.
- Provide edge refinement controls where supported: brush to keep/remove areas, edge radius/smoothing, feather, and restore/erase refinement.
- Preserve useful details such as hair strands and thin object boundaries; allow the user to inspect at high zoom before applying.
- Keep the original image unchanged and make the removal reversible/non-destructive.
- Support manual cleanup after automatic removal. AI-assisted removal may be an optional additional method, but the local/offline path must not be silently removed or replaced.
- Keep image quality and resolution; do not downsample the source unnecessarily during editing or export.

### Acceptance checks

- Test portraits with hair, people against busy backgrounds, product photos, logos, objects with thin edges, and images with low contrast.
- Inspect edges at high zoom on light and dark preview backgrounds.
- Confirm transparent pixels remain transparent in exported PNG/WebP and in reopened files.
- Confirm undo/redo, restore/erase refinement, and original-image recovery work.
- Test on the target Android device for memory use and responsiveness; use tiled/progressive processing for large images if needed.

This is a quality and integration requirement. It does not claim the Android background-removal UI or refinement tools have already been implemented or verified.

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
