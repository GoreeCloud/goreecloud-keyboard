# GoreeCloud Keyboard

GoreeCloud Keyboard is an original, native GoreeCloud keyboard implementation. The current implemented platform is Android through a first-party input-method service and rendering surface. Apple-platform support remains product direction and is not claimed as currently implemented.

## Current Development foundation

The current Android source includes:

- Android `InputMethodService` integration.
- A first-party keyboard rendering surface.
- Core QWERTY input, shift, backspace, space, and enter actions.
- First-party letters-and-symbols switching with `?123` / `ABC`, plus a second `=\\<` symbol page for brackets, operators, currency marks, and common typographic symbols.
- A bounded first-party emoji layer that routes complete Unicode text through the existing String-safe input contract, includes selected composed sequences, and organizes the current local set into deterministic Smileys, People, Nature, Food, and Symbols categories.
- A compact emoji category strip with fuller accessibility announcements than its visible icon labels.
- A bounded **Recent** category that appears after emoji are committed. Recents retain exact String values, deduplicate by promotion, are capped at 24 entries, and persist only in Android private application preferences for GoreeCloud Keyboard.
- The local recents list survives an IME process restart on the same installation and is removed from both memory and private preferences by the explicit **Clear** control.
- Emoji recents are not synchronized, logged, transmitted, or used for learned-use profiling, and the persisted value does not include surrounding typed text, editor contents, application identity, or timestamps.
- Bounded Unicode-aware backspace for common emoji modifiers, ZWJ-linked emoji, regional-indicator flags, keycaps, variation selectors, combining marks, and CRLF while sensitive editors retain one-code-point deletion without text look-behind.
- A local GoreeCloud Quill suggestion boundary with deterministic prefix candidates and bounded typo correction.
- Sensitive-editor privacy gating that suppresses suggestion capture, display, acceptance, and backspace look-behind inspection.
- Privacy-by-default behavior with no Android network permission.
- GLAZE UI V1.1 (`1.1.0`) Development source mapping pinned to Stable release authority `15cc76d2bcd4065552dc31c77145b63f34d9e7b2`, preserving inherited 4/8 dp spacing, the 12 dp structural small/control radius, 48/56 dp interaction floors and Light/Dark mappings while adding separate V1.1 optical geometry references and an explicit Deep Dark source palette.
- A bounded non-semantic V1.1 Deep Teal + Soft Amber atmosphere source contract that is not rendered by `KeyboardView` and enables no editor/content sampling, remote derivation, persistence, semantic inference, or animated atmosphere.
- Explicit Wardveil Security, Privacy Shield, Everkeep, GoreeCloud Identity, and GoreeCloud Mesh integration boundaries.
- Android unit/build/governance and emulator validation infrastructure, including rendered native symbol-page hit testing and pure recency/serialization coverage.

The GLAZE UI V1.1 mapping remains Development/migration evidence only. `KeyboardView` still selects inherited Light/Dark from Android night mode; it does not infer or auto-select Deep Dark, and this slice adds no appearance preference. Complete V1.1 component/state/material-role mapping, Reduced Motion/Transparency, Increased Contrast, forced-colors/native equivalents, 200% text/reflow, runtime Touch Assistance resolution, RTL/localization, TalkBack/Switch Access, representative-device/form-factor ergonomics, Human Visual Excellence, signed distribution, release, and Stable qualification remain separate gates. Earlier V1.0 and pre-reset Glaze UI 2.x evidence is historical implementation ancestry only and is not current V1.1 acceptance.

## Product direction

The keyboard is intended to grow into a privacy-first input platform with gesture typing, richer local suggestions and correction, dictionaries, multilingual input, richer emoji discovery, clipboard tools, voice/input adapters where appropriate, accessibility, one-handed and split layouts, tablet/foldable adaptation, and GoreeCloud Quill writing capabilities.

The current emoji surface remains intentionally bounded rather than a complete picker. Every exposed categorized key is required by unit coverage to be compatible with the current one-backspace text-unit deletion model before it is admitted. The backspace helper still does not claim complete Unicode UAX #29 grapheme segmentation for every script. Emoji search, synchronization, broader composed-sequence coverage, complete grapheme segmentation, and broader multilingual editing acceptance remain product targets rather than current implementation claims.

Private on-device recents are a convenience cache, not a general learned-language or usage-profile system. Any broader persistence, synchronization, backup, personalization, downloadable model, or content-source behavior requires separate Privacy Shield, security, continuity, and user-control acceptance.

## Documentation

- `SPECIFICATIONS.md` — canonical product/source specification and acceptance boundaries.
- `FEATURES.md` — implemented versus planned capability inventory.
- `BENEFITS.md` — product benefits grounded in current architecture.
- `COMPETITIVE-OBJECTIVES.md` — product-quality objectives and evidence discipline.
- `USER-MANUAL.md` — current Development user guidance.
- `docs/native-architecture.md` — native implementation architecture.
- `docs/glaze-ui-adoption.md` — current GLAZE UI V1.1 source-migration evidence and remaining gates.
- `docs/glaze-motion-evaluation.md` — historical/test-only Experimental Glaze Motion evaluation.

## Development model

This repository contains original GoreeCloud-owned application code. Third-party libraries may be used only as narrowly scoped supporting dependencies where justified; they must not become the primary keyboard implementation.

## Status

**Development — native Android foundation.** Source or CI validation does not by itself establish production acceptance, signed release, representative physical-device acceptance, or Stable qualification.
