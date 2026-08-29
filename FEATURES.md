# GoreeCloud Keyboard Features

## Implemented in Development source

- Native Android input method using `InputMethodService`.
- Original first-party keyboard rendering surface.
- QWERTY letter entry.
- Shift, backspace, space, and enter controls.
- Letters/symbols mode switching with a primary digit/common-punctuation page and a second first-party symbol page for brackets, operators, currency marks, and common typographic symbols.
- Direct `ABC`, `?123`, and `=\\<` navigation between implemented input layers.
- A bounded first-party emoji layer with single-code-point emoji plus selected composed variation-selector, skin-tone, ZWJ, family, rainbow-flag, and regional-indicator sequences.
- Complete-String emoji commit behavior and bounded text-unit backspace tests that require every currently exposed emoji key to delete in one backspace action.
- Local-only GoreeCloud Quill suggestion boundary.
- Deterministic prefix suggestions and bounded one-edit typo-correction candidates.
- Sensitive-editor privacy gating for suggestion capture/display/acceptance and text look-behind deletion logic.
- Composing-context reset across editor transitions and non-letter layer changes.
- Light and Dark Glaze UI 2.0 source token mapping.
- No Android network permission in the current foundation.
- Unit, build, governance, and emulator validation paths, including rendered secondary-symbol hit testing.

## Development / acceptance work still required

- Complete rendered Glaze UI 2.0 acceptance, including applicable material roles and accessibility settings.
- Representative physical-device IME acceptance.
- Complete TalkBack, switch-access, large-text, contrast, reduced-transparency, and adaptive-layout acceptance.
- Tablet/foldable/one-handed/split keyboard experiences.
- Full emoji categories, recents, search, broader composed-sequence coverage, complete grapheme segmentation, and additional language/locale input modes.
- Signed release packaging and distribution acceptance.

## Planned product capabilities — not current implementation claims

- Gesture/swipe typing.
- Richer local prediction/correction and dictionaries.
- Multilingual input and language switching.
- User-controlled personalization and learned-language features.
- Clipboard tools with explicit privacy boundaries.
- Optional voice/input adapters where platform and privacy policies permit.
- GoreeCloud Quill writing assistance beyond the current local suggestion boundary.
- Governed synchronization, backup/recovery, and portability where explicitly implemented.

All planned capabilities remain subject to GoreeCloud privacy, security, identity, continuity, integration, design, and release acceptance requirements.
