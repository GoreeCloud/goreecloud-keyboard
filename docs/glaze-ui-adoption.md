# Glaze UI 2.1 Adoption Candidate — GoreeCloud Keyboard

Status: **Adoption Candidate**  
Required Stable baseline: **Glaze UI 2.1.0**  
Reviewed canonical Stable revision: `c49113eb8b93c267613fdf1bbca1f814495acad7`  
Reviewed Stable tag: `v2.1.0`  
Production eligible on the Glaze UI gate: **no**

## Scope

GoreeCloud Keyboard now targets Glaze UI 2.1.0 as the current Stable design-system authority for keyboard geometry, spacing, interaction feedback, accessibility, visual state, and adaptive presentation. This repository-local migration establishes the correct current-Stable target and a bounded source mapping; it does not claim complete Glaze UI 2.1 conformance or production acceptance.

The current native Android surface remains a first-party `KeyboardView`. Glaze UI 2.1 is a compatible refinement of 2.0, so this migration preserves validated task structure and compatible token values instead of visually rewriting the keyboard. The web reference runtime is not embedded. Glaze UI 2.2 remains non-consumer-eligible and is not used as production authority here.

## Implemented 2.1 mapping

- `GlazeKeyboardTokens` retains the canonical 4 dp and 8 dp spacing steps consumed by the keyboard surface.
- Key geometry retains the canonical 14 dp `radius.md` token.
- The suggestion strip retains the 48 dp general interaction floor.
- The bounded token map now exposes the 56 dp Touch Assistance interaction floor required by the 2.1 accessibility contract, while actual platform-setting detection and hit-area integration remain an application gate.
- Light maps the canonical canvas, surface, text, muted-text, and line values.
- Dark maps the canonical canvas, surface, text, muted-text, and line values and `KeyboardView` selects it from Android night mode at draw time.
- Deep Dark remains unimplemented because the current Stable `tokens/glaze.tokens.json` contract names `deep-dark` but still publishes concrete color blocks only for Light and Dark; Keyboard will not invent downstream values from a reference-only implementation.
- The 2.1 material hierarchy and accessibility precedence are authoritative migration requirements. Reduced Transparency/Solid, Forced Colors, Increased Contrast, Reduced Motion, Large Text, Touch Assistance, density resolution, and material-budget behavior remain unclaimed until the native Keyboard surface consumes and validates them.
- Existing local Quill suggestions, private-editor gating, bounded typo correction, key alternates, and key-release semantics remain first-party and on-device.
- No Android network permission, remote UI dependency, analytics, advertising, Candidate-only Glaze UI runtime, or Experimental Motion production dependency is introduced.

## Repository-local evidence

- `android/app/src/main/kotlin/com/goreecloud/keyboard/KeyboardView.kt` is the first-party rendering and pointer-input surface and resolves Light/Dark from Android configuration.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/GlazeKeyboardTokens.kt` is the bounded Stable token mapping used by that surface.
- `GlazeKeyboardTokensTest` locks current compatible geometry, Light/Dark values, the 48 dp normal floor, and the 56 dp Touch Assistance floor.
- The Android manifest requests no network permission.
- Android CI runs repository-local Glaze UI / Glaze Motion governance checks, unit tests, debug assembly, and a dedicated emulator runtime job.
- The repository governance check pins the Glaze UI 2.1.0 Stable merge revision and rejects stale 2.0/1.x production-authority declarations.
- Experimental Glaze Motion evaluation remains quarantined to documentation, validation tooling, and Android test source.

## Remaining application gates

This Adoption Candidate does not establish Deep Dark implementation/acceptance, complete Soft Glaze/Glaze/Deep Glaze/Live Glaze role mapping, Material Clarity rendering, Material Budgets or performance fallbacks, Reduced Transparency/Solid rendering, Forced Colors, Increased Contrast, Reduced Motion, 200% Large Text/reflow, Touch Assistance platform detection and 56 dp hit-area behavior, density-resolution behavior, complete adaptive phone/tablet acceptance, TalkBack/Switch Access acceptance, representative physical-device ergonomics, human Visual Excellence acceptance, production signing, distribution, or Stable qualification.

The current custom Android surface can still be constrained by host IME geometry. Source/build/emulator success therefore remains Development evidence only until the application-specific acceptance classes above are satisfied.

Glaze UI 2.1 Stable is the production design-system authority. Experimental Glaze Motion remains test-only and is not a production dependency.
