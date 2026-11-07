# Exact-size PDF export for Android

## Product requirement

TraceDraw's PDF export must preserve the document's physical page dimensions at 1:1 scale. The PDF page size is the document size, not an automatically selected A4/Letter page.

Examples:
- 200 x 300 mm document -> 200 x 300 mm PDF page.
- 2500 x 500 mm banner -> 2500 x 500 mm PDF page.
- A4 document -> A4 PDF page.

## Geometry and units

- Store document dimensions in a canonical physical unit (prefer integer micrometres or a sufficiently precise fixed-point representation).
- Convert to PDF points using 72 points per inch and 25.4 mm per inch:
  `points = millimetres * 72 / 25.4`.
- Do not round dimensions to standard paper sizes.
- Keep orientation explicit and do not swap width/height except when the document orientation itself changes.
- PDF MediaBox/CropBox must describe the intended page dimensions. Keep artwork placement and scaling at 1:1.
- Avoid rasterizing the entire page unnecessarily; preserve vector text and paths where supported.

## Export and validation

- Export uses the document's exact dimensions as the PDF page box.
- Add a pre-export summary showing width, height, orientation and page count.
- Add automated tests for standard and custom sizes, including fractional millimetres and large-format banners.
- Validate generated PDF page boxes by reading the PDF back and comparing dimensions with the source document within a defined tolerance (for example 0.01 mm).
- Multi-page documents retain the same page size per page unless the document model explicitly supports mixed sizes.

## Android print handoff

- Keep Android PrintManager as a printer-service handoff, not as the authority for page dimensions.
- Pass the generated PDF unchanged to the print service.
- The user must choose the matching paper/custom media size in the printer service when available.
- Explain that printer margins and non-printable areas may prevent edge-to-edge output.
- Do not silently apply Fit to page, Shrink to fit, or automatic A4 conversion. If a print service forces scaling or does not expose custom media, warn the user and recommend printing from a host computer or using a service that supports the required media size.
- The user-facing print guidance should say Actual Size / 100% and disable Fit/Shrink when the service offers those controls.

## Large-format tiling

For a physical printer that cannot accept the full custom page size, a separate poster/tiling mode may split the artwork into printable sheets. Tiling must be explicit and must preserve 1:1 scale, with configurable overlap and registration marks. It must never be silently applied during normal PDF export.

## Color

PDF export should preserve source colors and supported vector data. ICC OutputIntent/profile embedding is a separate feature and must not be presented as a guarantee that Android PrintManager or a printer service will honor the profile. Avoid double color conversion.

## Status

This document defines the implementation and acceptance requirements. It does not claim that the exact-size export, PDF validation, custom media handoff, or poster tiling have already been implemented or physically tested on ROCK 3A.
