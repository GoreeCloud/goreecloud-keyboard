# Glaze UI 2.0 Adoption Candidate — GoreeCloud Keyboard

Status: **Adoption Candidate**  
Required Stable baseline: **Glaze UI 2.0.0**  
Reviewed canonical Stable revision: `d19c576336881e44c4795d687d768b3cbb7bdf45`  
Production eligible on the Glaze UI gate: **no**

## Scope

GoreeCloud Keyboard targets Glaze UI 2.0.0 as the current Stable design-system authority for keyboard geometry, spacing, interaction feedback, accessibility, visual state, and adaptive presentation. This record establishes current-Stable migration work; it does not claim complete Glaze UI 2.0 conformance or production acceptance.

The current native Android surface remains a first-party `KeyboardView`. The migration maps only semantics published by the canonical Stable token map and actually consumed by this surface rather than importing a web runtime or third-party keyboard implementation.

## Implemented 2.0 mapping

- `GlazeKeyboardTokens` maps the current 4 dp and 8 dp spacing steps used by the keyboard surface.
- Key geometry follows the current 14 dp `radius.md` token.
- The suggestion strip uses the 48 dp general interaction floor.
- Light maps the canonical canvas, surface, text, muted-text, and line values.
- Dark maps the canonical canvas, surface, text, muted-text, and line values and `KeyboardView` selects it from Android night mode at draw time.
- Deep Dark remains unimplemented because the current Stable semantic map names the appearance but does not publish a separate concrete Deep Dark color block in `tokens/glaze.tokens.json`; Keyboard will not invent downstream values.
- Existing local Quill suggestions, private-editor gating, bounded typo correction, and key-release semantics remain first-party and on-device.
- No Android network permission, remote UI dependency, analytics, advertising, or Experimental Motion production dependency is introduced.

## Repository-local evidence

- `android/app/src/main/kotlin/com/goreecloud/keyboard/KeyboardView.kt` is the first-party rendering and pointer-input surface and resolves Light/Dark from Android configuration.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/GlazeKeyboardTokens.kt` is the bounded Stable token mapping used by that surface.
- `GlazeKeyboardTokensTest` locks current geometry plus Light/Dark token values.
- The Android manifest requests no network permission.
- Unit tests cover local suggestion, correction, sensitive-editor, and Glaze token boundaries.
- Android CI runs repository-local Glaze UI / Glaze Motion governance checks, unit tests, debug assembly, and a dedicated emulator runtime job.
- Experimental Glaze Motion evaluation remains quarantined to documentation, validation tooling, and Android test source.

## Remaining application gates

This Adoption Candidate does not establish rendered Dark acceptance, Deep Dark implementation/acceptance, complete Soft Glaze/Glaze/Deep Glaze/Live Glaze role mapping, complete 2.0 motion/reduced-motion behavior, expression modes, adaptive phone/tablet acceptance, large-text/reflow acceptance, increased-contrast/reduced-transparency acceptance, TalkBack/switch-access acceptance, representative physical-device acceptance, or production release acceptance.

The current custom Android surface can still be constrained by host IME geometry; the 48 dp mapping and Light/Dark appearance must therefore be validated on representative devices and accessibility configurations before production acceptance.

Glaze UI 2.0 Stable is the production design-system authority. Experimental Glaze Motion remains test-only and is not a production dependency.
