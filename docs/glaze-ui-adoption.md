# Glaze UI 2.0 Adoption Candidate — GoreeCloud Keyboard

Status: **Adoption Candidate**  
Required Stable baseline: **Glaze UI 2.0.0**  
Reviewed canonical Stable revision: `ff3fff4306bd53ea9c0715a7c0d64265bb038617`  
Production eligible on the Glaze UI gate: **no**

## Scope

GoreeCloud Keyboard targets Glaze UI 2.0.0 as the current Stable design-system authority for keyboard geometry, spacing, interaction feedback, accessibility, visual state, and adaptive presentation. This record establishes current-Stable migration work; it does not claim complete Glaze UI 2.0 conformance or production acceptance.

The current native Android surface remains a first-party `KeyboardView`. The migration maps the semantics that surface actually consumes rather than importing a web UI runtime or third-party keyboard implementation.

## Implemented 2.0 mapping

- `GlazeKeyboardTokens` maps the reviewed 4 dp and 8 dp spacing steps used by the keyboard surface.
- Key geometry uses the reviewed 12 dp medium utility radius.
- The suggestion strip now uses the 48 dp general interaction floor instead of the prior 42 dp height.
- The Light canvas, surface, on-surface, and muted on-surface colors map to the promoted 2.0 Stable foundation tokens.
- Existing local Quill suggestions, private-editor gating, bounded typo correction, and key-release semantics remain first-party and on-device.
- No Android network permission, remote UI dependency, analytics, advertising, or Experimental Motion production dependency is introduced.

## Repository-local evidence

- `android/app/src/main/kotlin/com/goreecloud/keyboard/KeyboardView.kt` is the first-party rendering and pointer-input surface.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/GlazeKeyboardTokens.kt` is the bounded 2.0 mapping used by that surface.
- `GlazeKeyboardTokensTest` locks the mapped geometry and Light foundation values.
- The Android manifest requests no network permission.
- Unit tests cover local suggestion, correction, sensitive-editor, and Glaze token boundaries.
- Android CI runs repository-local Glaze UI / Glaze Motion governance checks, unit tests, debug assembly, and a dedicated emulator runtime job.
- Experimental Glaze Motion evaluation remains quarantined to documentation, validation tooling, and Android test source.

## Remaining application gates

This Adoption Candidate does not establish Dark or Deep Dark acceptance, complete Soft Glaze/Glaze/Deep Glaze/Live Glaze role mapping, complete 2.0 motion/reduced-motion behavior, expression modes, adaptive phone/tablet acceptance, large-text/reflow acceptance, increased-contrast/reduced-transparency acceptance, TalkBack/switch-access acceptance, representative physical-device acceptance, or production release acceptance.

The current custom Android surface can still be constrained by host IME geometry; the 48 dp mapping must therefore be validated on representative devices and accessibility configurations before production acceptance.

Glaze UI 2.0 Stable is the production design-system authority. Experimental Glaze Motion remains test-only and is not a production dependency.
