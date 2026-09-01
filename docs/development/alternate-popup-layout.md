# Alternate popup layout policy

Status: Development

The native keyboard now has a pure viewport-bounded geometry policy for long-press alternate character popups.

The policy keeps the Glaze-sized cells inside the available keyboard viewport, reduces the column count on narrow surfaces, prefers placement above the source key, uses below-key placement when the top edge is constrained, and fails closed when the available surface cannot fit a valid popup.

It consumes only source-key and viewport geometry. It performs no surrounding-text inspection, clipboard read, language inference, learning, personalization, persistence, or network request.

## Remaining integration

`KeyboardView.drawAlternatePopup(...)` still needs to replace its local placement arithmetic with `AlternatePopupLayout.calculate(...)`. Keeping that rendered-view edit separate preserves the existing gesture slice and allows the pure geometry contract to validate independently first.

Representative physical-device gesture, accessibility, compact-width, and IME acceptance remain required before Stable claims.
