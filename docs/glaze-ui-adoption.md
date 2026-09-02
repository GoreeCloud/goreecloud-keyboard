# Glaze UI 2.2 Adoption Candidate — GoreeCloud Keyboard

Status: **Adoption Candidate**  
Required Stable baseline: **Glaze UI 2.2.0**  
Reviewed exact Stable promotion head: `fb5ecde4a8258503789ffde08ac46a2e524ef71e`  
Reviewed Stable release merge: `6731098b28dd0393faa878c70d989a221d714a20`  
Reviewed Stable tag: `v2.2.0`  
Production eligible on the Glaze UI gate: **no**

## Scope

GoreeCloud Keyboard targets Glaze UI 2.2.0 as the current Stable design-system authority for keyboard geometry, spacing, interaction feedback, accessibility, visual state, and adaptive presentation. This repository-local migration establishes the correct current-Stable target and a bounded source mapping; it does not claim complete Glaze UI 2.2 conformance or production acceptance.

The current native Android surface remains a first-party `KeyboardView`. Glaze UI 2.2 is an additive semantic refinement of the prior line, so this migration preserves validated task structure and compatible token values instead of visually rewriting the keyboard. No web runtime or Candidate-named Glaze implementation is embedded in the application.

## Implemented 2.2 mapping

- `GlazeKeyboardTokens` retains the canonical 4 dp and 8 dp spacing steps consumed by the keyboard surface.
- Key geometry retains the canonical 14 dp `radius.md` token.
- The suggestion strip retains the 48 dp general interaction floor.
- The bounded token map exposes the 56 dp Touch Assistance interaction floor required by the current Stable accessibility contract, while actual platform-setting detection and 56 dp hit-area integration remain an application gate.
- Light maps the canonical canvas, surface, text, muted-text, and line values.
- Dark maps the canonical canvas, surface, text, muted-text, and line values and `KeyboardView` selects it from Android night mode at draw time.
- Deep Dark remains unimplemented because the current Stable `tokens/glaze.tokens.json` contract names `deep-dark` but still publishes concrete color blocks only for Light and Dark; Keyboard will not invent downstream values.
- The keyboard itself is an **Application** surface under the 2.2 System Shell hierarchy. Long-press alternate-character presentation is transient application interaction, not a system-level Control Center or Universal Search surface.
- Long-press alternate-character cells use the current 48 dp general interaction floor and one viewport-bounded geometry authority shared by rendering and pointer hit testing. Compact widths may reduce the column count and use bounded multi-row layout; impossible layouts fail closed rather than exposing stale or off-surface commit targets.
- The active alternate-popup layout is retained only for the current gesture. Pointer movement delegates to the same `AlternatePopupLayoutResult.hitTest(...)` geometry used to render cells, so inter-cell gaps, unused final-row cells, non-finite coordinates, outside points, and absent/failed layouts resolve to no selected alternate.
- Ordinary key/suggestion content remains readable on Canvas/Surface semantics; transient interaction may use bounded Glaze semantics only where it represents interaction rather than durable content.
- Keyboard does not claim Universal Search, Control Center, Signature, or Intelligence components merely because 2.2 defines them. Its local emoji search remains local application search.
- Existing local Quill suggestions, private-editor gating, bounded typo correction, emoji, alternate-character, and key-release semantics remain first-party and on-device.
- No Android network permission, remote UI dependency, analytics, advertising, Candidate-only Glaze UI runtime, or Experimental Motion production dependency is introduced.

## Repository-local evidence

- `android/app/src/main/kotlin/com/goreecloud/keyboard/KeyboardView.kt` is the first-party rendering and pointer-input surface, resolves Light/Dark from Android configuration, and consumes the alternate-popup geometry result for both rendering and active pointer selection.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/GlazeKeyboardTokens.kt` is the bounded Stable token mapping used by that surface.
- `android/app/src/main/kotlin/com/goreecloud/keyboard/AlternatePopupLayout.kt` is the pure viewport-bounded geometry and hit-test authority for long-press alternates.
- `GlazeKeyboardTokensTest` locks current compatible geometry, Light/Dark values, the 48 dp normal floor, and the 56 dp Touch Assistance floor.
- `AlternatePopupLayoutTest` locks normal, top-edge, compact multi-row, impossible-layout, gap/unused-cell, outside-point, and non-finite hit-test behavior.
- The Android manifest requests no network permission.
- Android CI runs repository-local Glaze UI / Glaze Motion governance checks, unit tests, debug assembly, and a dedicated emulator runtime job.
- The repository governance check pins the Glaze UI 2.2.0 Stable promotion/release identity and rejects superseded 2.1/2.0/1.x production-authority declarations.
- Experimental Glaze Motion evaluation remains quarantined to documentation, validation tooling, and Android test source.

## 2.2 requirements still requiring application evidence

This Adoption Candidate does not establish Deep Dark implementation/acceptance; complete 2.2 component-contract mapping; full state-priority review; System Glaze budget acceptance; Material Clarity rendering; Material Budgets or performance fallbacks; Reduced Transparency/Solid rendering; Forced Colors; Increased Contrast; Reduced Motion; 200% Large Text/reflow; Touch Assistance platform detection and 56 dp hit-area behavior; density-resolution behavior; RTL/localization expansion; complete adaptive phone/tablet acceptance; TalkBack/Switch Access acceptance; representative physical-device long-press/slide/release ergonomics; application-specific Human Visual Excellence acceptance; production signing; distribution; or Stable qualification.

The current custom Android surface can still be constrained by host IME geometry. Source/build/emulator success therefore remains Development evidence only until the application-specific acceptance classes above are satisfied.

Glaze UI 2.2.0 Stable is the production design-system authority. Glaze UI 2.1.0 is retained only as a historical rollback/migration baseline. Experimental Glaze Motion remains test-only and is not a production dependency.
