# GoreeCloud Keyboard

GoreeCloud Keyboard is an original, native GoreeCloud keyboard implementation. The current implemented platform is Android through a first-party input-method service and rendering surface. Apple-platform support remains product direction and is not claimed as currently implemented.

## Current Development foundation

The current Android source includes:

- Android `InputMethodService` integration and runtime registration checks through `InputMethodManager`.
- A first-party `KeyboardView` rendering, hit-testing, and pointer-input surface.
- Core QWERTY input, shift, backspace, space, and editor-action handling.
- A dedicated 1–0 number row on the letters layer, enabled by default and controllable through the Android IME settings surface with a device-local presentation preference.
- Adaptive editor action presentation for ordinary Enter plus Go, Search, Send, Next, Done, and Previous host semantics.
- First-party letters-and-symbols switching with `?123` / `ABC`, plus a second `=\\<` symbol page for brackets, operators, currency marks, and common typographic symbols.
- A bounded first-party emoji layer with deterministic Smileys, People, Nature, Food, Symbols, and Travel categories.
- A compact emoji category strip, bounded private recents with explicit Clear behavior, and fully offline emoji search over the packaged first-party catalog.
- Bounded Unicode-aware backspace for common emoji modifiers, ZWJ-linked emoji, regional-indicator flags, keycaps, variation selectors, combining marks, and CRLF, with fail-closed handling for ambiguous/truncated context.
- Deterministic local long-press alternates for common Latin diacritics and punctuation, with viewport-bounded popup geometry, pointer selection, cancellation, haptic feedback, and accessibility semantics.
- A local GoreeCloud Quill suggestion boundary with deterministic prefix candidates, bounded typo correction, bounded transient capture, and suggestion commit authority tied to the exact candidates presented for the active editor session.
- Sensitive-editor and host no-suggestions privacy gating that suppresses suggestion capture, display, and acceptance and resets fail-closed at Android editor lifecycle boundaries.
- Privacy-by-default behavior with no Android network permission.
- A privacy-minimized `goreecloud-keyboard-preferences/1` portability format containing exactly the last explicitly selected emoji category, with explicit user-controlled Storage Access Framework import/export. The number-row preference remains device-local and is intentionally not added to that portable format.
- GLAZE UI V1.3 / `1.3.0` Development source/material mapping pinned to exact Stable integration revision `fc7cc91d2eace8da2371371c2855c24cbcb326a1` through the current stacked migration work.
- The inherited Frosted Neutral Light/Dark/Deep Dark source foundation, 48/56 dp interaction floors, deterministic pressed/focus behavior, and V1.3 Adaptive Resonance authority boundaries. Typed/editor content remains outside Adaptive Resonance color authority; environmental memory, remote color derivation, persistent sample history, semantic inference, telemetry, and animated atmosphere remain disabled.
- Android unit/build/governance and emulator validation infrastructure covering registration, interaction, privacy lifecycle, emoji search, Unicode deletion, alternate popup geometry/hit testing, portability, accessibility semantics, Glaze UI source boundaries, the dedicated number row, and adaptive editor-action presentation.

Current GLAZE UI V1.3 work remains Development/migration evidence only. Passing source or CI validation does not establish complete rendered consumer acceptance, physical-device acceptance, assistive-technology acceptance, Privacy Shield/Wardveil/Everkeep acceptance, Release Candidate qualification, or Stable status.

## Inspiration-driven input-surface direction

The approved GoreeCloud Keyboard direction uses supplied Android-keyboard references as inspiration rather than as a design to copy. The target is a polished, spacious, Glaze-native input surface with a suggestion strip, optional number row, adaptive action key, direct symbols/emoji access, strong local behavior, and eventually a governed utility toolbar and adaptive layouts.

The current implementation tranche adds the optional dedicated number row and adaptive action key as real behavior. Existing local suggestions, rounded Glaze key surfaces, Light/Dark system adaptation, emoji, symbols, and long-press alternates already align with that direction.

Remaining approved inspiration goals are tracked as planned obligations: a configurable utility toolbar; Secure Paste/clipboard tools; GIF/sticker discovery; voice and translation; gesture typing and cursor/selection gestures; multilingual layouts and language switching; manual appearance controls; one-handed, floating, split, tablet/foldable/posture-aware layouts; richer dictionaries/correction; broader Quill assistance; and explicit governed GoreeCloud ecosystem actions. Placeholder buttons or decorative controls do not count as implementation.

See `docs/development/keyboard-inspiration-input-surface.md` for the current Development boundary.

## Product direction

GoreeCloud Keyboard is intended to become a beautiful, polished, feature-rich, privacy-first input platform while remaining fast, accessible, dependable, and native to supported platforms. Feature richness must remain substantive: behavior, privacy/security boundaries, accessibility, tests, lifecycle integration, and appropriate runtime acceptance are required before a capability is treated as implemented.

Private on-device recents are a convenience cache, not a general learned-language or usage-profile system. Any broader persistence, synchronization, backup, personalization, downloadable model, clipboard, voice, remote-content, or account-backed behavior requires separate Privacy Shield, Wardveil Security, Everkeep, Identity, Mesh, user-control, retention, and implementation acceptance as applicable.

## Documentation

- `SPECIFICATIONS.md` — canonical repository product/source specification and acceptance boundaries.
- `FEATURES.md` — implemented versus planned capability inventory.
- `BENEFITS.md` — product benefits grounded in current architecture.
- `COMPETITIVE-OBJECTIVES.md` — product-quality objectives and evidence discipline.
- `USER-MANUAL.md` — current Development user guidance.
- `docs/native-architecture.md` — native implementation architecture.
- `docs/glaze-ui-adoption.md` — current GLAZE UI adoption evidence and remaining gates.
- `docs/glaze-motion-evaluation.md` — historical/test-only Experimental Glaze Motion evaluation.
- `docs/development/` — bounded Development evidence and implementation notes.

## Development model

This repository contains original GoreeCloud-owned application code. Third-party libraries may be used only as narrowly scoped supporting dependencies where justified; they must not become the primary keyboard implementation.

## Status

**Development — native Android stabilization.** Source or CI validation does not by itself establish production acceptance, signed release, representative physical-device acceptance, Release Candidate status, or Stable qualification.
