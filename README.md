# GoreeCloud Keyboard

GoreeCloud Keyboard is an original, native GoreeCloud keyboard implementation. The current implemented platform is Android through a first-party input-method service and rendering surface. Apple-platform support remains product direction and is not claimed as currently implemented.

## Current Development foundation

The current Android source includes:

- Android `InputMethodService` integration.
- A first-party keyboard rendering surface.
- Core QWERTY input, shift, backspace, space, and enter actions.
- First-party letters-and-symbols switching with `?123` / `ABC`, plus a second `=\\<` symbol page for brackets, operators, currency marks, and common typographic symbols.
- A bounded first-party emoji layer that routes complete Unicode text through the existing String-safe input contract and now includes both single-code-point emoji and selected composed sequences such as variation-selector hearts, skin-tone modifiers, ZWJ people/family emoji, rainbow-flag composition, and regional-indicator flags.
- Bounded Unicode-aware backspace for common emoji modifiers, ZWJ-linked emoji, regional-indicator flags, keycaps, variation selectors, combining marks, and CRLF while sensitive editors retain one-code-point deletion without text look-behind.
- A local GoreeCloud Quill suggestion boundary with deterministic prefix candidates and bounded typo correction.
- Sensitive-editor privacy gating that suppresses suggestion capture, display, acceptance, and backspace look-behind inspection.
- Privacy-by-default behavior with no Android network permission.
- Glaze UI 2.0.0 Adoption Candidate source mapping for current geometry, target floor, and Light/Dark appearance values.
- Explicit Wardveil Security, Privacy Shield, Everkeep, GoreeCloud Identity, and GoreeCloud Mesh integration boundaries.
- Android unit/build/governance and emulator validation infrastructure, including rendered native symbol-page hit testing.

## Product direction

The keyboard is intended to grow into a privacy-first input platform with gesture typing, richer local suggestions and correction, dictionaries, multilingual input, a complete searchable/categorized emoji surface, clipboard tools, voice/input adapters where appropriate, accessibility, one-handed and split layouts, tablet/foldable adaptation, and GoreeCloud Quill writing capabilities.

The current emoji layer remains intentionally bounded rather than a full emoji picker. Its selected multi-code-point keys are admitted only where the current text-unit deletion model is tested to delete the entire key in one backspace action. The backspace helper still does not claim complete Unicode UAX #29 grapheme segmentation for every script. Emoji categories, recents, search, broader composed-sequence coverage, complete grapheme segmentation, and broader multilingual editing acceptance remain product targets rather than current implementation claims.

## Documentation

- `SPECIFICATIONS.md` — canonical product/source specification and acceptance boundaries.
- `FEATURES.md` — implemented versus planned capability inventory.
- `BENEFITS.md` — product benefits grounded in current architecture.
- `COMPETITIVE-OBJECTIVES.md` — product-quality objectives and evidence discipline.
- `USER-MANUAL.md` — current Development user guidance.
- `docs/native-architecture.md` — native implementation architecture.
- `docs/glaze-ui-adoption.md` — Glaze UI 2.0 Adoption Candidate evidence and remaining gates.
- `docs/glaze-motion-evaluation.md` — test-only Experimental Glaze Motion evaluation.

## Development model

This repository contains original GoreeCloud-owned application code. Third-party libraries may be used only as narrowly scoped supporting dependencies where justified; they must not become the primary keyboard implementation.

## Status

**Development — native Android foundation.** Source or CI validation does not by itself establish production acceptance, signed release, representative physical-device acceptance, or Stable qualification.
