# Automatic numbering (Numerator) for Android

## Product requirement

Keep and provide an automatic sequential numbering workflow for print production. It must be additive to the existing TraceDraw editor and must not replace import/export, AI, or existing document features.

Typical uses: numbered coupons, tickets, queue cards, receipts, invoices, invitations, vouchers, and serial labels.

## Numbering controls

- Start number and end number, or total quantity.
- Step/increment (default 1).
- Minimum digit width with zero padding (e.g. 1 -> 001).
- Optional prefix and suffix (e.g. INV-001 or 001-A).
- Optional separator and formatting.
- Choose target text object/placeholder or create a new editable text object.
- Font, size, colour, alignment, rotation, position and text styling remain editable.
- Preview the first, next and last generated values before applying.
- Support multi-page documents and repeated copies.
- Define numbering order for sheet layouts: row-first or column-first.
- Support a fixed layout with multiple tickets/cards per page and continue numbering across pages.
- Allow explicit restart by page or batch only when the user selects it.

## Output and print

- Generate individual numbered editable objects or pages from a master design.
- Export the numbered result to PDF while preserving the exact physical page dimensions and 1:1 scale.
- Provide a clear preview of quantity, page count, first/last number and layout.
- Do not rasterize the numbering unnecessarily; keep it as editable/vector text when supported.
- Keep the original master design unchanged; use a generated copy or a reversible operation.
- Avoid duplicate or skipped numbers when regenerating, changing quantity, or exporting.

## Validation

- Test sequences such as 1–10, 001–250, INV-0001–INV-0500, and custom increments.
- Test prefix/suffix, zero padding, boundary values, restart rules and large batches.
- Test multiple items per page, row-first/column-first ordering, and page breaks.
- Verify uniqueness and continuity across the full output.
- Verify PDF page boxes retain the source document's exact physical dimensions.
- Run existing editor tests and Android build/CI; record failures honestly.

## Status

This document defines requirements only. It does not claim the Numerator UI, generation engine, or physical print workflow has already been implemented or tested on Android.
