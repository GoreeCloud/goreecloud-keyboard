# Alternate popup layout policy

Status: Development

The native keyboard now has a pure viewport-bounded geometry policy for long-press alternate character popups and the rendered `KeyboardView` consumes that policy directly.

The policy keeps the Glaze-sized cells inside the available keyboard viewport, reduces the column count on narrow surfaces, prefers placement above the source key, uses below-key placement when the top edge is constrained, and fails closed when the available surface cannot fit a valid popup.

`KeyboardView.drawAlternatePopup(...)` delegates placement and exact per-item geometry to `AlternatePopupLayout.calculate(...)` and `itemBounds(...)` instead of reconstructing row/column arithmetic. If a valid popup cannot be laid out, rendered item bounds are cleared and selection is explicitly nulled so an invisible alternate cannot be committed on pointer-up.

`AlternatePopupLayoutResult.hitTest(...)` now provides the same geometry authority with deterministic pointer hit testing. It returns an item only for a finite pointer coordinate inside an actual alternate cell; inter-cell gaps, unused cells in the final row, non-finite coordinates, and points outside the popup return no selection. This creates a single testable geometry contract for a later renderer hit-test migration without changing text-commit authority in this slice.

The layout path consumes only source-key and viewport geometry. It performs no surrounding-text inspection, clipboard read, language inference, learning, personalization, persistence, or network request.

Representative physical-device gesture, accessibility, compact-width, and IME acceptance remain required before Stable claims.
