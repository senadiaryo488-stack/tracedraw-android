# Print presets supplied by the Android fork

The dimensions below are installed as saved presets in `apps/tracedraw/src/new_document.rs` and appear in File > New > Preset on a fresh app profile. The list is a starting point; print providers should confirm final dimensions, bleed, resolution and colour profiles before production.

## Banner presets (document size)
| Name | Width | Height | Units |
|---|---:|---:|---|
| Banner 1 x 3 m | 300 | 100 | cm |
| Banner 2 x 1 m | 200 | 100 | cm |
| Banner 3 x 1 m | 300 | 100 | cm |
| Banner 4 x 1.5 m | 400 | 150 | cm |
| Banner 4 x 3 m | 400 | 300 | cm |
| A4 | 210 | 297 | mm |
| A3 | 297 | 420 | mm |
| X-banner | 60 | 160 | cm |
| Sticker square | 100 | 100 | mm |

## Print/export profile
- Units: mm, cm, m, inch.
- Raster resolution presets: 150 and 300 DPI; retain the application's native resolution controls.
- Safety guidance: keep critical text/logos within the user-selected safe margin; never silently resize the document.
- Bleed presets: 3 mm for small-format print and configurable 5-10 mm for large-format signage; printer must confirm final requirements.
- Color: use RGB for screen preview and only claim CMYK output when a suitable ICC profile/conversion is applied and verified.
- Export: PDF for print workflow, PNG/JPEG for raster output, SVG for vector handoff.
