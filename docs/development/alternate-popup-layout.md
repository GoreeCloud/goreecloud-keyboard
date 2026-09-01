# Alternate popup layout policy

Status: Development

The native keyboard now has a pure viewport-bounded geometry policy for long-press alternate character popups.

The policy keeps the Glaze-sized cells inside the available keyboard viewport, reduces the column count on narrow surfaces, prefers placement above the source key, uses below-key placement when the top edge is constrained, and fails closed when the available surface cannot fit a valid popup.

The returned layout now also owns the exact per-item cell geometry. Rendered code no longer needs to reconstruct row/column placement independently once it consumes this policy; invalid item indexes fail closed at the same pure geometry boundary.

It consumes only source-key and viewport geometry. It performs no surrounding-text inspection, clipboard read, language inference, learning, personalization, persistence, or network request.

## Remaining integration

`KeyboardView.drawAlternatePopup(...)` still needs to replace its local placement and per-cell arithmetic with `AlternatePopupLayout.calculate(...)` plus the returned `itemBounds(...)`. Keeping that rendered-view edit separate avoids an unsafe whole-file rewrite through the current repository connector and preserves exact-head reviewability.

Representative physical-device gesture, accessibility, compact-width, and IME acceptance remain required before Stable claims.
