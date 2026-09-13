# GoreeCloud Keyboard

GoreeCloud Keyboard is an original, native GoreeCloud keyboard implementation. The current implemented platform is Android through a first-party input-method service and rendering surface. Apple-platform support remains product direction and is not claimed as currently implemented.

## Current Development foundation

The current Android source includes:

- Android `InputMethodService` integration and runtime registration checks through `InputMethodManager`.
- A first-party `KeyboardView` rendering, hit-testing, and pointer-input surface.
- Core QWERTY input, shift, backspace, space, and enter actions.
- First-party letters-and-symbols switching with `?123` / `ABC`, plus a second symbol page.
- A bounded first-party emoji layer with deterministic categories, local recents, explicit Clear behavior, and private device-local persistence.
- Fully offline emoji search over only the packaged first-party catalog.
- Unicode-aware backspace handling for common emoji/modifier/ZWJ/flag/keycap/variation/combining cases, with sensitive editors retaining conservative deletion behavior.
- Deterministic local long-press alternates with viewport-bounded popup geometry, pointer selection, cancellation, haptic feedback, and fail-closed hit testing.
- A local GoreeCloud Quill suggestion boundary with bounded transient capture and commit authority bound to currently presented candidates for the active editor session.
- Sensitive-editor and host no-suggestions privacy gating that suppresses suggestion capture/display/acceptance and resets fail closed at authoritative editor-session lifecycle boundaries.
- Privacy-by-default behavior with no Android network permission.
- A privacy-minimized `goreecloud-keyboard-preferences/1` format containing exactly the last explicitly selected emoji category, with explicit user-controlled import/export, preview-before-write import, and review/freeze-before-destination export.
- **GLAZE UI V1.4 (`1.4.0`) Development source mapping** pinned to exact merged Stable Glaze source revision `84cb3db4884042f0fa25ed6d475a127fb110f596`.
- Neutral Frosted key material where neutral glass remains the substrate and color remains accent/semantic rather than default material tint.
- Light/Dark/Deep Dark source palettes, 4/8 dp spacing, 12 dp control radius, 48/56 dp interaction floors, optical geometry references, and pressed/selected/focus calibration.
- A Keyboard-specific V1.4 Optical Intelligence policy that keeps semantic protection maximal, disables decorative environmental tint, caps Environmental Color Memory at **0%**, and fails closed to solid-accessible treatment for Reduced Transparency or Forced Colors.
- Explicit prohibition on using typed/editor content, composing/surrounding text, suggestions, clipboard state, application identity, emoji history, key history, telemetry, or remote context to drive optical presentation.
- Explicit Wardveil Security, Privacy Shield, Everkeep, GoreeCloud Identity, GoreeCloud Mesh, and Manager acceptance boundaries.
- Android unit/build/governance and emulator validation infrastructure covering native registration, interaction, privacy lifecycle, emoji search, Unicode deletion, alternate popup geometry/hit testing, portability, accessibility, and Glaze source boundaries.

The GLAZE UI V1.4 mapping remains Development/migration evidence only. `KeyboardView` currently selects Light/Dark from Android night mode; it does not infer or auto-select Deep Dark. Complete component/state/material-role mapping across all surfaces, runtime Reduced Motion/Transparency, Increased Contrast and forced-colors/native equivalents, 200% text/reflow, runtime Touch Assistance resolution, RTL/localization, TalkBack/Switch Access, representative phone/tablet/foldable and physical-device ergonomics, V1.4.1 human optical validation, production signing/distribution, release, and Stable Keyboard qualification remain separate gates.

## GLAZE UI V1.4 privacy boundary

Keyboard intentionally adopts a stricter optical profile than ordinary application surfaces because an IME processes highly sensitive content.

`GlazeKeyboardOptics` may use only explicitly supplied non-content presentation/accessibility state. It cannot use typed text, composing text, surrounding text, suggestions, clipboard contents, application identity, sensitive-editor contents, emoji search/recents, key history, or remote context. It requires no telemetry.

Environmental Color Memory is disabled for Keyboard and its maximum influence is **0%**. Decorative environment tint/aura is also disabled. This prevents V1.4 visual adaptation from becoming a new observation channel over editor activity.

Reduced Transparency and Forced Colors resolve to solid-accessible presentation with zero blur and full semantic protection. Increased Contrast increases protective frost without enabling decorative tinting.

These source rules do not by themselves prove complete runtime accessibility acceptance. See `docs/glaze-ui-adoption.md` for the exact migration and acceptance boundary.

## V1.4.1 human-validation boundary

Shared Glaze UI human/manual/physical-device/subjective optical validation is assigned to V1.4.1 and is not represented as passed V1.4.0 evidence. Keyboard-specific release acceptance may remain stricter, particularly for typing latency, ergonomics, TalkBack/Switch Access behavior, physical-device interaction, performance, power, and thermal behavior.

## Product direction

GoreeCloud Keyboard is intended to become a polished, feature-rich, privacy-first input platform while remaining fast, accessible, dependable, and native to supported platforms. Planned capability families include gesture typing, stronger local correction and dictionaries, multilingual input and language switching, richer emoji/symbol discovery, GoreeCloud Secure Paste, privacy-approved voice input, one-handed and split layouts, tablet/foldable adaptation, user dictionaries, Quill-assisted writing, and explicitly governed personalization/continuity features.

Feature richness must remain substantive. A feature is not considered implemented merely because a button, label, placeholder, or visual treatment exists; behavior, privacy/security boundaries, accessibility, tests, lifecycle integration, and appropriate runtime acceptance are required.

Private on-device recents are a convenience cache, not a general learned-language or usage-profile system. Any broader persistence, synchronization, backup, personalization, downloadable model, clipboard, voice, or remote-content behavior requires separate Privacy Shield, Wardveil Security, Everkeep, Identity, Mesh, user-control, retention, and implementation acceptance as applicable.

## Glaze Motion boundary

Glaze Motion 0.5 remains separately governed **Experimental** evaluation work and test-only. Advancing Keyboard to Glaze UI V1.4 does not activate or promote Experimental Motion. Immediate deterministic pressed-state feedback in `KeyboardView` remains ordinary interaction-state rendering rather than a Motion runtime dependency.

## Platform and conformance status

`goreecloud.platform.yaml` targets GLAZE UI V1.4 / `1.4.0` but keeps the Glaze result `applicable-migration-required` and overall product conformance `nonconformant` until application-specific acceptance is complete.

Source or CI success does not establish accepted Privacy Shield, Wardveil Security, Everkeep, Identity, Mesh, Manager, representative-device, signed-release, or production qualification.

## Documentation

- `SPECIFICATIONS.md` — canonical repository product/source specification and acceptance boundaries.
- `FEATURES.md` — implemented versus planned capability inventory.
- `BENEFITS.md` — product benefits grounded in current architecture.
- `COMPETITIVE-OBJECTIVES.md` — product-quality objectives and evidence discipline.
- `USER-MANUAL.md` — current Development user guidance.
- `docs/native-architecture.md` — native implementation architecture.
- `docs/glaze-ui-adoption.md` — current GLAZE UI V1.4 source migration, privacy boundary, and remaining gates.
- `docs/glaze-motion-evaluation.md` — historical/test-only Experimental Glaze Motion evaluation.
- `docs/development/` — bounded Development evidence and implementation notes.

## Development model

This repository contains original GoreeCloud-owned application code. Third-party libraries may be used only as narrowly scoped supporting dependencies where justified; they must not become the primary keyboard implementation.

## Status

**Development — native Android stabilization.** Source, CI, unit, build, or emulator validation does not by itself establish production acceptance, signed release, representative physical-device acceptance, Release Candidate status, or Stable qualification.
